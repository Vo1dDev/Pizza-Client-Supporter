package qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.BlockFinder;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.MovementType;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves.IMovement;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves.MiningMoves;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public abstract class AdvancedMiningBot extends MiningBot {
   public static double getEstimatedCost(BlockPos goal, int blockCount) {
      double cost = MathUtil.abs((double)goal.func_177958_n() - PizzaClient.mc.field_71439_g.field_70165_t) + MathUtil.abs((double)goal.func_177956_o() - PizzaClient.mc.field_71439_g.field_70163_u);
      double yDiff = (double)goal.func_177956_o() - PizzaClient.mc.field_71439_g.field_70163_u;
      if (yDiff > 0.0D) {
         cost += yDiff * 1.5D;
      } else {
         cost += -yDiff;
      }

      return cost / (double)blockCount;
   }

   public AdvancedMiningBot(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public AdvancedMiningBot(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public AdvancedMiningBot(BetterBlockPos to) {
      super(to);
   }

   public AdvancedMiningBot(Vec3 to) {
      super(to);
   }

   public abstract BlockFinder getFinder();

   public void move() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
      BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      BlockPos posToMine = null;
      boolean removeNodes = this.removeNode();
      Iterator var4 = (new ArrayList(this.moves)).iterator();

      label87:
      while(var4.hasNext()) {
         BlockPos pos = (BlockPos)var4.next();
         if (!this.isPathFree(pos)) {
            posToMine = pos;
            break;
         }

         if (removeNodes && player.isSameXandZ(pos)) {
            BetterBlockPos copy = player.copyOf();

            for(copy.field_177960_b = pos.func_177956_o(); copy.field_177960_b <= player.field_177960_b + 1; ++copy.field_177960_b) {
               if (!Utils.isUncollidable(copy)) {
                  continue label87;
               }
            }

            if (this.moves.size() >= 2 && this.fixFallingState == 0) {
               MovementType type = Movement.calculateRequiredMovement((BlockPos)this.moves.get(0), (BlockPos)this.moves.get(1));

               while(!player.isSameXandZ((BlockPos)this.moves.get(0))) {
                  this.moves.remove(0);
               }

               this.moves.remove(0);
               if (this.moves.size() < 2 || type == Movement.calculateRequiredMovement((BlockPos)this.moves.get(0), (BlockPos)this.moves.get(1))) {
                  continue;
               }

               this.onCorner();
               break;
            }

            while(!player.isSameXandZ((BlockPos)this.moves.get(0))) {
               this.moves.remove(0);
            }

            this.moves.remove(0);
            break;
         }
      }

      if (!this.moves.isEmpty()) {
         if (posToMine != null) {
            this.minePath();
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
         }

         switch(this.fixFallingState) {
         case 1:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
            PlayerUtil.moveOpposite();
            this.fixFallingState = 2;
            return;
         case 2:
            Movement.disableMovement();
            if (Movement.isMovingForward()) {
               this.fixFallingState = 3;
            } else {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
            }

            return;
         case 3:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
            if (PizzaClient.mc.field_71439_g.field_70122_E) {
               this.fixFallingState = 0;
            } else {
               this.stayAligned();
            }

            return;
         default:
            this.useDefaultMovement();
         }
      }
   }

   public boolean shouldStop() {
      return false;
   }

   public void rotateIfNeeded() {
      if (!PizzaClient.mc.field_71474_y.field_74312_F.func_151470_d()) {
         super.rotateIfNeeded();
      }
   }

   public Block getBlockToMine() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this.goalPos).func_177230_c();
   }

   public IMovement[] getDirections() {
      return MiningMoves.values();
   }

   public void checkCanGoUp(PathNode currentNode) {
      BlockPos pos = new BlockPos(currentNode.x, currentNode.y + 2, currentNode.z);
      this.canGoUp = this.getMiningCost(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()) != -1.0D;
   }

   public void minePath() {
      if (!Rotater.rotating) {
         BlockPos pos = (BlockPos)this.moves.get(0);
         double diffY = (double)pos.func_177956_o() - PizzaClient.mc.field_71439_g.field_70163_u;
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(diffY + ""));
         if (diffY == 0.0D) {
            if (this.mineBlock(pos)) {
               return;
            }

            this.mineBlock(pos.func_177984_a());
         } else if (diffY < 0.0D) {
            this.mineBlock(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)(MathUtil.ceil(PizzaClient.mc.field_71439_g.field_70163_u) - 1), PizzaClient.mc.field_71439_g.field_70161_v));
         } else {
            if (this.mineBlock(pos)) {
               return;
            }

            if (this.mineBlock(pos.func_177984_a())) {
               return;
            }

            this.mineBlock(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 2.0D, PizzaClient.mc.field_71439_g.field_70161_v));
         }

      }
   }

   public boolean isPathFree() {
      BlockPos pos = (BlockPos)this.moves.get(0);
      double diffY = (double)pos.func_177956_o() - PizzaClient.mc.field_71439_g.field_70163_u;
      if (diffY == 0.0D) {
         return Utils.isUncollidable(pos) && Utils.isUncollidable(pos.func_177984_a());
      } else if (diffY < 0.0D) {
         BlockPos player = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.ceil(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
         return !Utils.isSameXandZ(pos, player) ? true : Utils.isUncollidable(player.func_177977_b());
      } else {
         return Utils.isUncollidable(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 2.0D, PizzaClient.mc.field_71439_g.field_70161_v)) && Utils.isUncollidable(pos) && Utils.isUncollidable(pos.func_177984_a());
      }
   }

   public boolean isPathFree(BlockPos pos) {
      double diffY = (double)pos.func_177956_o() - PizzaClient.mc.field_71439_g.field_70163_u;
      if (diffY == 0.0D) {
         return VecUtil.canReachBlock(pos) && Utils.isUncollidable(pos) && Utils.isUncollidable(pos.func_177984_a());
      } else if (diffY < 0.0D) {
         BlockPos player = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.ceil(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
         return !Utils.isSameXandZ(pos, player) && Utils.isUncollidable(player.func_177977_b());
      } else {
         return VecUtil.canReachBlock(pos) && Utils.isUncollidable(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 2.0D, PizzaClient.mc.field_71439_g.field_70161_v)) && Utils.isUncollidable(pos) && Utils.isUncollidable(pos.func_177984_a());
      }
   }

   public boolean mineBlock(BlockPos pos) {
      if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a) {
         return false;
      } else {
         if (PizzaClient.mc.field_71476_x == null || !pos.equals(PizzaClient.mc.field_71476_x.func_178782_a())) {
            (new Rotater(new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D))).setRotationAmount(15).addSlightRandomRotateAmount().rotate();
         }

         return true;
      }
   }

   public abstract double getMiningCost(BlockPos var1, Block var2);
}
