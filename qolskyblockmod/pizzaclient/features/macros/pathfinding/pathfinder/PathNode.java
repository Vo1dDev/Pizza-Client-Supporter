package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class PathNode {
   public static final double COST_INF = 1000000.0D;
   public final int x;
   public final int y;
   public final int z;
   public final double estimatedCostToGoal;
   public double cost;
   public double combinedCost;
   public PathNode previous;
   public int heapPosition = -1;

   public PathNode(BetterBlockPos pos) {
      this.x = pos.field_177962_a;
      this.y = pos.field_177960_b;
      this.z = pos.field_177961_c;
      this.cost = 1000000.0D;
      this.estimatedCostToGoal = (double)(MathUtil.abs(this.x - BasePathfinder.path.goalPos.field_177962_a) + MathUtil.abs(this.y - BasePathfinder.path.goalPos.field_177960_b) + MathUtil.abs(this.z - BasePathfinder.path.goalPos.field_177961_c));
   }

   public PathNode(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.cost = 1000000.0D;
      this.estimatedCostToGoal = (double)(MathUtil.abs(x - BasePathfinder.path.goalPos.field_177962_a) + MathUtil.abs(y - BasePathfinder.path.goalPos.field_177960_b) + MathUtil.abs(z - BasePathfinder.path.goalPos.field_177961_c));
   }

   public PathNode() {
      this.x = BasePathfinder.path.currentPos.field_177962_a;
      this.y = BasePathfinder.path.currentPos.field_177960_b;
      this.z = BasePathfinder.path.currentPos.field_177961_c;
      this.estimatedCostToGoal = (double)(MathUtil.abs(this.x - BasePathfinder.path.goalPos.field_177962_a) + MathUtil.abs(this.y - BasePathfinder.path.goalPos.field_177960_b) + MathUtil.abs(this.z - BasePathfinder.path.goalPos.field_177961_c));
      this.combinedCost = this.estimatedCostToGoal;
   }

   public BlockPos toBlockPos() {
      return new BlockPos(this.x, this.y, this.z);
   }

   public BetterBlockPos toBetterBlockPos() {
      return new BetterBlockPos(this.x, this.y, this.z);
   }

   public boolean isBlockLoaded() {
      return this.x >= -30000000 && this.z >= -30000000 && this.x < 30000000 && this.z < 30000000 && this.y >= 0 && this.y < 256 && Utils.isChunkLoaded(this.x >> 4, this.z >> 4);
   }

   public boolean isSameXandZ(BetterBlockPos pos) {
      return pos.field_177962_a == this.x && pos.field_177961_c == this.z;
   }

   public boolean isSameXandZ(int posX, int posZ) {
      return posX == this.x && posZ == this.z;
   }

   public int hashCode() {
      return (int)(482263L * (751913L * (long)this.x + (long)this.y) + (long)this.z);
   }

   public boolean equals(Object obj) {
      PathNode other = (PathNode)obj;
      return this.x == other.x && this.y == other.y && this.z == other.z;
   }

   public boolean equals(PathNode other) {
      return this.x == other.x && this.y == other.y && this.z == other.z;
   }

   public double heuristic() {
      return (double)(MathUtil.abs(this.x - BasePathfinder.path.goalPos.field_177962_a) + MathUtil.abs(this.y - BasePathfinder.path.goalPos.field_177960_b) + MathUtil.abs(this.z - BasePathfinder.path.goalPos.field_177961_c));
   }

   public List<BlockPos> calculateMoves() {
      PathNode current = this;

      LinkedList tempNodes;
      for(tempNodes = new LinkedList(); current != null; current = current.previous) {
         tempNodes.addFirst(new BlockPos(current.x, current.y, current.z));
      }

      return new ArrayList(tempNodes);
   }

   public Block getBlock() {
      return PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(this.x, this.y, this.z)).func_177230_c();
   }

   public BlockPos getPos() {
      return new BlockPos(this.x, this.y, this.z);
   }
}
