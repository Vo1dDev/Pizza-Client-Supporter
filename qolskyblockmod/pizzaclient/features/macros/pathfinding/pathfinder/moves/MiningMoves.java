package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves;

import net.minecraft.block.Block;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.AdvancedMiningBot;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.MutableBlockPosNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;

public enum MiningMoves implements IMovement {
   NORTH {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z - 1;
         if (pos.isBlockLoaded()) {
            ++pos.field_177960_b;
            AdvancedMiningBot miningBot = (AdvancedMiningBot)BasePathfinder.path;
            double extraCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            if (extraCost != -1.0D) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               double cost = miningBot.getMiningCost(pos, block);
               double nextCost;
               PathNode previous;
               if (cost != -1.0D) {
                  if (cost != 0.0D && miningBot.canGoUp) {
                     pos.field_177960_b += 2;
                     nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                     if (nextCost != -1.0D) {
                        pos.field_177960_b -= 2;
                        if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                           ++pos.field_177960_b;
                           previous = parent.previous;
                           pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                           return true;
                        }
                     }
                  }

                  PathNode previousx = parent.previous;
                  pos.cost = previousx != null && previousx.x - pos.field_177962_a != 0 && previousx.z - pos.field_177961_c != 0 ? this.calculatePos() + cost + extraCost + 1.1999D : this.calculatePos() + cost + extraCost;
                  return true;
               }

               if (miningBot.canGoUp) {
                  pos.field_177960_b += 2;
                  nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                  if (nextCost != -1.0D) {
                     --pos.field_177960_b;
                     previous = parent.previous;
                     pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                     return true;
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
            AdvancedMiningBot miningBot = (AdvancedMiningBot)BasePathfinder.path;
            double extraCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            if (extraCost != -1.0D) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               double cost = miningBot.getMiningCost(pos, block);
               double nextCost;
               PathNode previous;
               if (cost != -1.0D) {
                  if (cost != 0.0D && miningBot.canGoUp) {
                     pos.field_177960_b += 2;
                     nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                     if (nextCost != -1.0D) {
                        pos.field_177960_b -= 2;
                        if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                           ++pos.field_177960_b;
                           previous = parent.previous;
                           pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                           return true;
                        }
                     }
                  }

                  PathNode previousx = parent.previous;
                  pos.cost = previousx != null && previousx.x - pos.field_177962_a != 0 && previousx.z - pos.field_177961_c != 0 ? this.calculatePos() + cost + extraCost + 1.1999D : this.calculatePos() + cost + extraCost;
                  return true;
               }

               if (miningBot.canGoUp) {
                  pos.field_177960_b += 2;
                  nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                  if (nextCost != -1.0D) {
                     --pos.field_177960_b;
                     previous = parent.previous;
                     pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                     return true;
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
            AdvancedMiningBot miningBot = (AdvancedMiningBot)BasePathfinder.path;
            double extraCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            if (extraCost != -1.0D) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               double cost = miningBot.getMiningCost(pos, block);
               double nextCost;
               PathNode previous;
               if (cost != -1.0D) {
                  if (cost != 0.0D && miningBot.canGoUp) {
                     pos.field_177960_b += 2;
                     nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                     if (nextCost != -1.0D) {
                        pos.field_177960_b -= 2;
                        if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                           ++pos.field_177960_b;
                           previous = parent.previous;
                           pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                           return true;
                        }
                     }
                  }

                  PathNode previousx = parent.previous;
                  pos.cost = previousx != null && previousx.x - pos.field_177962_a != 0 && previousx.z - pos.field_177961_c != 0 ? this.calculatePos() + cost + extraCost + 1.1999D : this.calculatePos() + cost + extraCost;
                  return true;
               }

               if (miningBot.canGoUp) {
                  pos.field_177960_b += 2;
                  nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                  if (nextCost != -1.0D) {
                     --pos.field_177960_b;
                     previous = parent.previous;
                     pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                     return true;
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
            AdvancedMiningBot miningBot = (AdvancedMiningBot)BasePathfinder.path;
            double extraCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            if (extraCost != -1.0D) {
               --pos.field_177960_b;
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               double cost = miningBot.getMiningCost(pos, block);
               double nextCost;
               PathNode previous;
               if (cost != -1.0D) {
                  if (cost != 0.0D && miningBot.canGoUp) {
                     pos.field_177960_b += 2;
                     nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                     if (nextCost != -1.0D) {
                        pos.field_177960_b -= 2;
                        if ((double)pos.field_177960_b + block.func_149669_A() - ((double)parent.y + parent.getBlock().func_149669_A()) <= 1.0D) {
                           ++pos.field_177960_b;
                           previous = parent.previous;
                           pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                           return true;
                        }
                     }
                  }

                  PathNode previousx = parent.previous;
                  pos.cost = previousx != null && previousx.x - pos.field_177962_a != 0 && previousx.z - pos.field_177961_c != 0 ? this.calculatePos() + cost + extraCost + 1.1999D : this.calculatePos() + cost + extraCost;
                  return true;
               }

               if (miningBot.canGoUp) {
                  pos.field_177960_b += 2;
                  nextCost = miningBot.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
                  if (nextCost != -1.0D) {
                     --pos.field_177960_b;
                     previous = parent.previous;
                     pos.cost = previous != null && previous.x - pos.field_177962_a != 0 && previous.z - pos.field_177961_c != 0 ? 2.4999D + nextCost + extraCost : 1.9999D + nextCost + extraCost;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   },
   DOWN {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y - 1;
         pos.field_177961_c = parent.z;
         AdvancedMiningBot miningBot = (AdvancedMiningBot)BasePathfinder.path;
         Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
         double cost = miningBot.getMiningCost(pos, block);
         if (cost != -1.0D) {
            pos.cost = this.calculatePos() + cost;
            return true;
         } else {
            return false;
         }
      }
   };

   private MiningMoves() {
   }

   // $FF: synthetic method
   MiningMoves(Object x2) {
      this();
   }
}
