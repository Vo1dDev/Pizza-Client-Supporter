package qolskyblockmod.pizzaclient.features.macros.mining;

import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.GemstonePath;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.AdvancedMiningBot;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.AStarPathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.skills.WorldScanner;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class GemstoneMacro extends Macro {
   public static BlockPos getBestGemstoneVein() {
      double bestCost = 9.9999999E7D;
      BlockPos bestPos = null;
      Iterator var3 = WorldScanner.markedBlocks.keySet().iterator();

      while(var3.hasNext()) {
         BlockPos pos = (BlockPos)var3.next();
         int count = 0;
         Iterator var6 = BlockPos.func_177980_a(new BlockPos(pos.func_177958_n() - 4, pos.func_177956_o() - 3, pos.func_177952_p() - 4), new BlockPos(pos.func_177958_n() + 4, pos.func_177956_o() + 4, pos.func_177952_p())).iterator();

         while(var6.hasNext()) {
            BlockPos adjacent = (BlockPos)var6.next();
            if (PizzaClient.mc.field_71441_e.func_180495_p(adjacent).func_177230_c() == Blocks.field_150399_cn) {
               ++count;
            }
         }

         double cost = AdvancedMiningBot.getEstimatedCost(pos, count);
         if (cost < bestCost) {
            bestCost = cost;
            bestPos = pos;
         }
      }

      return bestPos;
   }

   public static void runGemstonePathfinder() {
      (new Thread(() -> {
         (new AStarPathfinder(new GemstonePath(new BetterBlockPos(getBestGemstoneVein())))).run(true);
      })).start();
   }

   public void onTick() {
      if (PizzaClient.location == Locations.CHOLLOWS) {
         if (!(BasePathfinder.path instanceof GemstonePath)) {
            runGemstonePathfinder();
         }

      }
   }

   public void onToggle(boolean toggled) {
      this.addToggleMessage("Gemstone Macro");
   }

   public boolean applyFailsafes() {
      return true;
   }

   public boolean applyPositionFailsafe() {
      return true;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return PizzaClient.config.stopWhenNearPlayer;
   }
}
