package qolskyblockmod.pizzaclient.features.macros.ai.failsafes;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.timer.TickTimer;
import qolskyblockmod.pizzaclient.util.render.RenderType;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class PlayerDetectionFailsafe {
   private static Entity niceGuy;
   private static long lastInteractTime;
   private static int phase;

   public static boolean checkForPlayers() {
      if (PizzaClient.config.stopWhenNearPlayer && MacroBuilder.currentMacro.applyPlayerFailsafes()) {
         if (niceGuy != null) {
            onPlayerSeen();
            return true;
         } else {
            return isBeingSeen();
         }
      } else {
         return false;
      }
   }

   private static boolean isBeingSeen() {
      Entity player = PlayerUtil.getFacingPlayer();
      if (player != null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "A nice player with the name " + player.func_70005_c_() + EnumChatFormatting.GREEN + " is standing infront of you!"));
         niceGuy = player;
         return true;
      } else {
         Frustum frustum = RenderUtil.setupFrustrum();
         Vec3 position = PlayerUtil.getPositionEyes();
         Iterator var3 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         Entity e;
         EntityPlayer entity;
         String name;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var3.hasNext()) {
                              return false;
                           }

                           e = (Entity)var3.next();
                        } while(!(e instanceof EntityOtherPlayerMP));

                        entity = (EntityPlayer)e;
                        name = entity.func_145748_c_().func_150254_d();
                     } while(!name.startsWith("§r§"));
                  } while(name.startsWith("§r§f"));
               } while(name.endsWith(" "));
            } while(entity.func_82150_aj() && !Failsafes.hasSorrow(entity));
         } while(!(position.func_72436_e(new Vec3(entity.field_70165_t, entity.field_70163_u + 1.6200000047683716D, entity.field_70161_v)) < (double)(PizzaClient.config.stopWhenNearPlayerRange * PizzaClient.config.stopWhenNearPlayerRange)) || !VecUtil.isPlayerBeingLookedAt(entity, frustum));

         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + name + " is looking at you!"));
         niceGuy = e;
         return true;
      }
   }

   private static void onPlayerSeen() {
      switch(phase) {
      case 0:
         lastInteractTime = System.currentTimeMillis();
         phase = 1;
      case 1:
         if (System.currentTimeMillis() - lastInteractTime >= 250L && TickTimer.ticks % 3 == 0) {
            if (!isBeingSeen()) {
               niceGuy = null;
               phase = 0;
               return;
            }

            Failsafes.unpressMovement();
            lastInteractTime = System.currentTimeMillis();
            phase = 2;
         }
         break;
      case 2:
         if (System.currentTimeMillis() - lastInteractTime >= 150L && TickTimer.ticks % 3 == 0) {
            if (!isBeingSeen()) {
               niceGuy = null;
               phase = 0;
               return;
            }

            Failsafes.unpressMovement();
            (new Rotater(niceGuy.func_174824_e(1.0F))).antiSus(12.0F, 15.0F).rotate();
            Failsafes.writeToWebhook("Player Detection (Name: " + StringUtils.func_76338_a(niceGuy.func_70005_c_()) + ")");
            Utils.playOrbSound();
            lastInteractTime = System.currentTimeMillis();
            phase = 3;
         }
      case 3:
         if (System.currentTimeMillis() - lastInteractTime >= 4500L && TickTimer.ticks % 30 == 0) {
            if (!isBeingSeen()) {
               niceGuy = null;
               phase = 0;
               return;
            }

            PizzaClient.mc.field_71439_g.func_71165_d("/hub");
            lastInteractTime = System.currentTimeMillis();
            phase = 4;
         }
         break;
      case 4:
         if (System.currentTimeMillis() - lastInteractTime >= 4000L) {
            phase = 0;
            niceGuy = null;
         }
      }

   }

   public static void renderNiceGuy() {
      if (niceGuy != null) {
         RenderType.setUniversalOutlineColor(Color.RED);
         RenderType.addEntity(niceGuy);
      }

   }
}
