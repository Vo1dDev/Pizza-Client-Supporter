package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves;

import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.MutableBlockPosNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.util.Utils;

public enum FlyMoves implements IMovement {
   NORTH {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y;
         pos.field_177961_c = parent.z - 1;
         if (pos.isBlockLoaded() && Utils.isUncollidable(pos)) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               pos.cost = 0.9999D;
               return true;
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
         if (pos.isBlockLoaded() && Utils.isUncollidable(pos)) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               pos.cost = 0.9999D;
               return true;
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
         if (pos.isBlockLoaded() && Utils.isUncollidable(pos)) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               pos.cost = 0.9999D;
               return true;
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
         if (pos.isBlockLoaded() && Utils.isUncollidable(pos)) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               pos.cost = 0.9999D;
               return true;
            }
         }

         return false;
      }
   },
   UP {
      public boolean addBlock(PathNode parent) {
         MutableBlockPosNode pos = BasePathfinder.path.mutableNode;
         pos.field_177962_a = parent.x;
         pos.field_177960_b = parent.y + 1;
         pos.field_177961_c = parent.z;
         if (Utils.isUncollidable(pos)) {
            ++pos.field_177960_b;
            if (Utils.isUncollidable(pos)) {
               --pos.field_177960_b;
               pos.cost = 0.9999D;
               return true;
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
         if (Utils.isUncollidable(pos)) {
            pos.cost = 0.9999D;
            return true;
         } else {
            return false;
         }
      }
   };

   private FlyMoves() {
   }

   // $FF: synthetic method
   FlyMoves(Object x2) {
      this();
   }
}
