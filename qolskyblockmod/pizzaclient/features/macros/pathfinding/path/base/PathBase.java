package qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.MovementType;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.MutableBlockPosNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.AStarPathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.NoMovementPathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.PathNodeOpenSet;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.collections.fastutil.maps.IntObjectOpenHashMap;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;
import qolskyblockmod.pizzaclient.util.rotation.RotationUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public abstract class PathBase implements IPath {
   public BetterBlockPos goalPos;
   public BetterBlockPos currentPos;
   public boolean finished;
   public List<BlockPos> moves = new ArrayList();
   public final IntObjectOpenHashMap<PathNode> checked = new IntObjectOpenHashMap(1024, 0.6F);
   public final PathNodeOpenSet openSet = new PathNodeOpenSet();
   public final MutableBlockPosNode mutableNode = new MutableBlockPosNode();
   public boolean canGoUp;
   public int fixFallingState;
   public long lastPathfindTime;

   public PathBase(Vec3 from, Vec3 to) {
      this.currentPos = new BetterBlockPos(from);
      this.goalPos = new BetterBlockPos(to);
   }

   public PathBase(BetterBlockPos from, BetterBlockPos to) {
      this.currentPos = from;
      this.goalPos = to;
   }

   public PathBase(BetterBlockPos to) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.ceil(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = to;
   }

   public PathBase(Vec3 to) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.ceil(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = new BetterBlockPos(to);
   }

   public void shutdown() {
      this.finished = true;
      Movement.disableMovement();
      AStarPathfinder.path = null;
      Pathfinding.unregister();
      PizzaClient.rotater = null;
      this.moves.clear();
   }

   public final void addMovement(BlockPos current) {
      Movement.endMovement();
      if (PizzaClient.mc.field_71439_g.field_70122_E) {
         double nextTickX = PizzaClient.mc.field_71439_g.field_70165_t;
         double nextTickZ = PizzaClient.mc.field_71439_g.field_70161_v;
         EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
         if (!MathUtil.inBetween(nextTickZ, (double)current.func_177952_p() + 0.3D, (double)current.func_177952_p() + 0.7D)) {
            if (nextTickZ <= (double)current.func_177952_p() + 0.3D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.RIGHT);
               }
            } else if (nextTickZ >= (double)current.func_177952_p() + 0.7D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.LEFT);
               }
            }
         }

         if (!MathUtil.inBetween(nextTickX, (double)current.func_177958_n() + 0.3D, (double)current.func_177958_n() + 0.7D)) {
            if (nextTickX <= (double)current.func_177958_n() + 0.3D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.FORWARDS);
               }
            } else if (nextTickX >= (double)current.func_177958_n() + 0.7D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.BACKWARDS);
               }
            }
         }

      }
   }

   public void rotateIfNeeded() {
      if (!Rotater.rotating) {
         int index = MathUtil.min(this.moves.size(), 4) - 1;
         EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
         if (!((BlockPos)this.moves.get(0)).func_177972_a(facing).equals(((BlockPos)this.moves.get(index)).func_177967_a(facing, 3))) {
            (new Rotater((BlockPos)this.moves.get(index))).setRotationAmount(15).addSlightRandomRotateAmount().rotate();
         }
      }

   }

   public final boolean isVerticalPassable(AxisAlignedBB aabb) {
      return VecUtil.entityRayTrace(aabb);
   }

   public final void useDefaultMovement() {
      if (!this.shouldStop()) {
         Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         BetterBlockPos player = new BetterBlockPos(position);
         BlockPos current = (BlockPos)this.moves.get(0);
         if (PizzaClient.mc.field_71439_g.field_70122_E) {
            this.addMovement(current);
         } else {
            Movement.disableMovement();
            Movement.addMovement(position, current);
         }

         BlockPos pos = current.func_177977_b();
         Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
         block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
         if (PizzaClient.mc.field_71441_e.func_180495_p(player).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
         } else if (PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 0.4D, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
         } else if (block.func_180646_a(PizzaClient.mc.field_71441_e, pos).field_72337_e > PizzaClient.mc.field_71439_g.field_70163_u + 0.6D && PizzaClient.mc.field_71439_g.field_70122_E && !Utils.isUncollidable(block, pos)) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            this.fixFallingState = 1;
         } else if (!PizzaClient.mc.field_71439_g.field_70122_E) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
            this.fixFallingState = 1;
         } else {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
            this.rotateIfNeeded();
         }
      }
   }

   public void update() {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.checked.clear();
      this.moves.clear();
   }

   public void update(BetterBlockPos goalPos) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = goalPos;
      this.checked.clear();
      this.moves.clear();
   }

   public void initRotater() {
      (new Rotater(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotation(Utils.toVec((BlockPos)this.moves.get(1))).yaw) - PizzaClient.mc.field_71439_g.field_70177_z, 0.0F)).randomPitch().setRotationAmount(17).rotate();

      while(PizzaClient.rotater != null) {
         Utils.sleep(5);
      }

      Utils.sleep(100);
   }

   public final void closeScreenIfOpen() {
      while(PizzaClient.mc.field_71462_r != null) {
         Movement.disableMovement();
         PizzaClient.mc.field_71439_g.func_71053_j();
         Utils.sleep(600);
      }

   }

   public void clearPath() {
      this.openSet.clear();
      this.checked.clear();
      this.mutableNode.resetValues();
   }

   public boolean isJumping() {
      return true;
   }

   public void onCorner() {
      Movement.moveOpposite();
   }

   public void stayAligned() {
      Vec3 position = PlayerUtil.getMotionPosition();
      BetterBlockPos player = new BetterBlockPos(position);
      if (this.moves.size() >= 2 && !(PizzaClient.mc.field_71439_g.field_70181_x > 0.0D)) {
         for(int i = 0; i < this.moves.size(); ++i) {
            BlockPos pos = (BlockPos)this.moves.get(i);
            if (player.isSameXandZ(pos)) {
               if (this.isPathFree(player, pos)) {
                  this.moves.subList(0, i).clear();
                  Movement.addMovement(position, (BlockPos)this.moves.get(0));
               } else {
                  boolean move = Movement.isMovingForward();
                  KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
                  KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), move);
               }

               return;
            }
         }

         Movement.addMovement(position, (BlockPos)this.moves.get(0));
      } else {
         boolean move = Movement.isMovingForward();
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), move);
      }
   }

   public boolean isPathFree(BetterBlockPos pos, BlockPos next) {
      if (this.moves.size() < 2) {
         return false;
      } else {
         BetterBlockPos copy = pos.copyOf();

         for(copy.field_177960_b = next.func_177956_o() + 1; copy.field_177960_b <= pos.field_177960_b + 1; ++copy.field_177960_b) {
            if (!Utils.isUncollidable(copy)) {
               return false;
            }
         }

         return true;
      }
   }

   public void recalculateMoves() {
      (new Thread(() -> {
         try {
            (new NoMovementPathfinder(this.reuse())).run(false);
         } catch (Exception var2) {
         }

      })).start();
   }

   public final boolean removeNode() {
      return this.moves.size() != 1 || PizzaClient.mc.field_71439_g.field_70122_E;
   }

   public boolean hasNode(BetterBlockPos player) {
      Iterator var2 = this.moves.iterator();

      BlockPos pos;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         pos = (BlockPos)var2.next();
      } while(!player.isSameXandZ(pos));

      BetterBlockPos copy = player.copyOf();

      for(copy.field_177960_b = pos.func_177956_o(); copy.field_177960_b <= player.field_177960_b + 1; ++copy.field_177960_b) {
         if (!Utils.isUncollidable(copy)) {
            return false;
         }
      }

      return true;
   }

   public final void updatePathfindTime() {
      this.lastPathfindTime = System.currentTimeMillis();
   }

   public PathBase reuse() {
      this.clearCache();
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      return this;
   }

   public void clearCache() {
      this.openSet.clear();
      this.checked.clear();
   }

   public boolean shouldRecalcPath() {
      return System.currentTimeMillis() - this.lastPathfindTime >= 1400L && this.moves.size() > 3;
   }

   public void checkCanGoUp(PathNode currentNode) {
      this.canGoUp = Utils.isUncollidable(new BlockPos(currentNode.x, currentNode.y + 2, currentNode.z));
   }

   public boolean shouldStop() {
      Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      BetterBlockPos player = new BetterBlockPos(position);
      if (this.removeNode() && this.hasNode(player)) {
         if (this.moves.size() < 2 || this.fixFallingState != 0) {
            while(!player.isSameXandZ((BlockPos)this.moves.get(0))) {
               this.moves.remove(0);
            }

            this.moves.remove(0);
            return true;
         }

         MovementType type = Movement.calculateRequiredMovement((BlockPos)this.moves.get(0), (BlockPos)this.moves.get(1));

         while(!player.isSameXandZ((BlockPos)this.moves.get(0))) {
            this.moves.remove(0);
         }

         this.moves.remove(0);
         if (this.moves.size() >= 2 && type != Movement.calculateRequiredMovement((BlockPos)this.moves.get(0), (BlockPos)this.moves.get(1))) {
            this.onCorner();
            return true;
         }
      }

      switch(this.fixFallingState) {
      case 1:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
         PlayerUtil.moveOpposite();
         this.fixFallingState = 2;
         return true;
      case 2:
         Movement.disableMovement();
         if (Movement.isMovingForward()) {
            this.fixFallingState = 3;
         } else {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         }

         return true;
      case 3:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
         if (PizzaClient.mc.field_71439_g.field_70122_E) {
            this.fixFallingState = 0;
         } else {
            this.stayAligned();
         }

         return true;
      default:
         return false;
      }
   }

   public void onBeginMove() {
      this.initRotater();
   }
}
