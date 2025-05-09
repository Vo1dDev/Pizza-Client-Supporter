package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.MutableBlockPosNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.util.Utils;

public interface IMovement {
   double ACTION_COST = 0.9999D;

   boolean addBlock(PathNode var1);

   default double calculatePos() {
      MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
      --pos.field_177960_b;
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      if (!Utils.isUncollidable((BlockPos)pos, (Block)block)) {
         ++pos.field_177960_b;
         return 0.9999D;
      } else if (block instanceof BlockLiquid) {
         ++pos.field_177960_b;
         return 4.9999D;
      } else {
         --pos.field_177960_b;

         while(Utils.isUncollidable((Block)(block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()), (BlockPos)pos)) {
            if (block instanceof BlockLiquid) {
               return 5.9999D;
            }

            --pos.field_177960_b;
         }

         ++pos.field_177960_b;
         return 1.3999D;
      }
   }
}
