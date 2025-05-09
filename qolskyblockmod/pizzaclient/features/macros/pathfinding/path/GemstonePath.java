package qolskyblockmod.pizzaclient.features.macros.pathfinding.path;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.AiMining;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.BlockFinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.AdvancedMiningBot;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.Utils;

public class GemstonePath extends AdvancedMiningBot {
   public GemstonePath(BetterBlockPos to) {
      super(to);
   }

   public BlockFinder getFinder() {
      return (pos) -> {
         return AiMining.mineables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
      };
   }

   public double getMiningCost(BlockPos pos, Block block) {
      if (AiMining.mineables.contains(block)) {
         return 2.0D;
      } else {
         return Utils.isUncollidable(pos, block) ? 0.0D : 2.0D;
      }
   }
}
