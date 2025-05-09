package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.MutableBlockPosNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.util.Utils;

public enum Moves implements IMovement {
   NORTH {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z - 1;
         if (pos.isBlockLoaded()) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               PathNode previous;
               if (Utils.isUncollidable((BlockPos)pos, (Block)block)) {
                  previous = parent.previous;
                  pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? this.calculatePos() + 1.1999D : this.calculatePos();
                  return true;
               }

               if (BasePathfinder.path.canGoUp) {
                  pos.field_177960_b += 2;
                  if (Utils.isUncollidable(pos)) {
                     pos.field_177960_b -= 2;
                     if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                        ++pos.field_177960_b;
                        previous = parent.previous;
                        pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.9999D : 2.4999D;
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   },
   EAST {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x + 1;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z;
         if (pos.isBlockLoaded()) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               PathNode previous;
               if (Utils.isUncollidable((BlockPos)pos, (Block)block)) {
                  previous = parent.previous;
                  pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? this.calculatePos() + 1.1999D : this.calculatePos();
                  return true;
               }

               if (BasePathfinder.path.canGoUp) {
                  pos.field_177960_b += 2;
                  if (Utils.isUncollidable(pos)) {
                     pos.field_177960_b -= 2;
                     if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                        ++pos.field_177960_b;
                        previous = parent.previous;
                        pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.9999D : 2.4999D;
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   },
   SOUTH {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z + 1;
         if (pos.isBlockLoaded()) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               PathNode previous;
               if (Utils.isUncollidable((BlockPos)pos, (Block)block)) {
                  previous = parent.previous;
                  pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? this.calculatePos() + 1.1999D : this.calculatePos();
                  return true;
               }

               if (BasePathfinder.path.canGoUp) {
                  pos.field_177960_b += 2;
                  if (Utils.isUncollidable(pos)) {
                     pos.field_177960_b -= 2;
                     if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                        ++pos.field_177960_b;
                        previous = parent.previous;
                        pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.9999D : 2.4999D;
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   },
   WEST {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x - 1;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z;
         if (pos.isBlockLoaded()) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               PathNode previous;
               if (Utils.isUncollidable((BlockPos)pos, (Block)block)) {
                  previous = parent.previous;
                  pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? this.calculatePos() + 1.1999D : this.calculatePos();
                  return true;
               }

               if (BasePathfinder.path.canGoUp) {
                  pos.field_177960_b += 2;
                  if (Utils.isUncollidable(pos)) {
                     pos.field_177960_b -= 2;
                     if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                        ++pos.field_177960_b;
                        previous = parent.previous;
                        pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.9999D : 2.4999D;
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   };

   private Moves() {
   }

   // $FF: synthetic method
   Moves(Object x2) {
      this();
   }
}
