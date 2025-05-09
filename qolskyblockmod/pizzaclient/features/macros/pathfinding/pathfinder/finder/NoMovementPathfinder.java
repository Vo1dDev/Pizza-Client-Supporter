package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves.IMovement;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.PathNodeOpenSet;
import qolskyblockmod.pizzaclient.util.Utils;

public class NoMovementPathfinder extends BasePathfinder {
   public NoMovementPathfinder(PathBase path) {
      super(path);
   }

   public boolean run(boolean messages) {
      long test = System.currentTimeMillis();
      if (path == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Path returned null. Please report this."));
         return false;
      } else if (path.currentPos.equals(path.goalPos)) {
         this.shutdown();
         return true;
      } else {
         IMovement[] directions = path.getDirections();
         PathNodeOpenSet openSet = path.openSet;
         openSet.insert(new PathNode());

         try {
            PathNode best = null;

            while(openSet.size != 0) {
               PathNode currentNode = openSet.removeLowest();
               if (path.goalPos.equals(currentNode)) {
                  best = currentNode;
                  break;
               }

               path.checkCanGoUp(currentNode);
               IMovement[] var8 = directions;
               int var9 = directions.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  IMovement movement = var8[var10];
                  if (movement.addBlock(currentNode)) {
                     PathNode neighbor = new PathNode(path.mutableNode.field_177962_a, path.mutableNode.field_177960_b, path.mutableNode.field_177961_c);
                     int hash = neighbor.hashCode();
                     PathNode existing = (PathNode)path.checked.get(hash);
                     double tentativeCost = currentNode.cost + path.mutableNode.cost;
                     if (existing != null) {
                        if (existing.heapPosition != -1 && existing.cost > tentativeCost) {
                           existing.previous = currentNode;
                           existing.cost = tentativeCost;
                           existing.combinedCost = tentativeCost + existing.estimatedCostToGoal;
                           openSet.update(existing);
                        }
                     } else {
                        path.checked.put(hash, neighbor);
                        neighbor.previous = currentNode;
                        neighbor.cost = tentativeCost;
                        neighbor.combinedCost = tentativeCost + neighbor.estimatedCostToGoal;
                        openSet.insert(neighbor);
                     }
                  }
               }
            }

            if (best == null) {
               if (messages) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Failed to find a path!"));
               }

               return false;
            } else {
               clearCache();
               if (messages) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Path! " + (System.currentTimeMillis() - test)));
               }

               Pathfinding.register();
               path.moves = best.calculateMoves();
               path.updatePathfindTime();
               return true;
            }
         } catch (Exception var17) {
            var17.printStackTrace();
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "PizzaClient caught an logged an exception when calculating the path. Please report this."));
            this.shutdown();
            return false;
         }
      }
   }

   public PathBase calculateAndGetPath() {
      return this.run(true) ? path : null;
   }

   public PathBase calculateAndGetPath(boolean messages) {
      return this.run(messages) ? path : null;
   }

   public List<BlockPos> calculateAndGetMoves() {
      return this.run(true) ? path.moves : null;
   }

   public List<BlockPos> calculateAndGetMoves(boolean messages) {
      return this.run(messages) ? path.moves : null;
   }
}
