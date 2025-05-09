package qolskyblockmod.pizzaclient.features.macros.pathfinding;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.Path;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.AStarPathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class Pathfinding {
   public static final Pathfinding instance = new Pathfinding();

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (BasePathfinder.path == null) {
         unregister();
      } else if (!BasePathfinder.path.moves.isEmpty()) {
         try {
            RenderUtil.drawRainbowPath(BasePathfinder.path.moves, 2.0F);
         } catch (Exception var3) {
            var3.printStackTrace();
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("caught exception"));
         }

      }
   }

   public static void runAStarPathfinder(BetterBlockPos goal) {
      (new Thread(() -> {
         (new AStarPathfinder(new Path(goal))).run();
      })).start();
   }

   public static void unregister() {
      MinecraftForge.EVENT_BUS.unregister(instance);
   }

   public static void register() {
      MinecraftForge.EVENT_BUS.register(instance);
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (BasePathfinder.path == null) {
         unregister();
      } else {
         if (BasePathfinder.path.shouldRecalcPath()) {
            BasePathfinder.path.recalculateMoves();
            BasePathfinder.path.updatePathfindTime();
         }

         if (BasePathfinder.path.moves.isEmpty()) {
            BasePathfinder.path.onEndMove();
            BasePathfinder.path.shutdown();
            this.shutdown();
         } else if (PizzaClient.mc.field_71462_r != null) {
            Movement.disableMovement();
            if (PizzaClient.tickTask != null) {
               long lastInteractTime = System.currentTimeMillis();
               PizzaClient.tickTask = () -> {
                  if (System.currentTimeMillis() - lastInteractTime >= 700L) {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Somehow opened gui, closing it."));
                     PlayerUtil.closeScreen();
                     PizzaClient.tickTask = null;
                  }

               };
            }

         } else {
            try {
               BasePathfinder.path.move();
            } catch (Exception var4) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Caught an exception when moving. Please report this."));
               var4.printStackTrace();
               Movement.disableMovement();
            }

         }
      }
   }

   public void shutdown() {
      BasePathfinder.path = null;
      unregister();
      Movement.disableMovement();
   }
}
