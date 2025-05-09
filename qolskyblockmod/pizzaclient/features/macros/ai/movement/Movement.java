package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.vectors.Vec2;

public class Movement {
   public static final double SPRINT_MODIFIER = 1.300000011920929D;
   public static final float SPEED_IN_AIR = 0.02F;

   public static void setMovement(MovementType movement) {
      switch(movement) {
      case FORWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case BACKWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case LEFT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      }

   }

   public static void setMovementToForward() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
   }

   public static void setMovement(BetterBlockPos current, BetterBlockPos next) {
      setMovement(calculateRequiredMovement((BlockPos)current, next));
   }

   public static void setMovement(Vec3 current, BetterBlockPos next) {
      setMovement(calculateRequiredMovement((BlockPos)(new BetterBlockPos(current)), next));
   }

   public static void addMovement(MovementType type) {
      switch(type) {
      case FORWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         break;
      case BACKWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
         break;
      case LEFT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
         break;
      default:
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "movement returned null, this shouldn't happen"));
      }

   }

   public static void endMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
   }

   public static void moveOpposite() {
      if (PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
      } else if (PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
      }

      if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      } else if (PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
      }

   }

   public static void addMovement(BlockPos current, BlockPos next) {
      addMovement(calculateRequiredMovement(current, next));
   }

   public static void addMovement(Vec3 current, BlockPos next) {
      addMovement(calculateRequiredMovement(current, next));
   }

   public static void disableMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
   }

   public static MovementType calculateRequiredMovement(BlockPos current, BlockPos next) {
      EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
      double bestDist = Utils.getXandZDistanceSquared(next, current.func_177972_a(facing));
      MovementType bestMove = MovementType.FORWARDS;
      double dist = Utils.getXandZDistanceSquared(next, current.func_177972_a(Utils.getLeftEnumfacing(facing)));
      if (bestDist > dist) {
         bestMove = MovementType.LEFT;
         bestDist = dist;
      }

      dist = Utils.getXandZDistanceSquared(next, current.func_177972_a(Utils.getRightEnumfacing(facing)));
      if (bestDist > dist) {
         bestMove = MovementType.RIGHT;
      }

      return bestMove;
   }

   public static MovementType calculateRequiredMovement(Vec3 current, BlockPos next) {
      EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
      double bestDist = Utils.getXandZDistanceSquared(next, VecUtil.offsetVec(current, facing));
      MovementType bestMove = MovementType.FORWARDS;
      double dist = Utils.getXandZDistanceSquared(next, VecUtil.offsetVec(current, Utils.getLeftEnumfacing(facing)));
      if (bestDist > dist) {
         bestMove = MovementType.LEFT;
         bestDist = dist;
      }

      dist = Utils.getXandZDistanceSquared(next, VecUtil.offsetVec(current, Utils.getRightEnumfacing(facing)));
      if (bestDist > dist) {
         bestMove = MovementType.RIGHT;
      }

      return bestMove;
   }

   public static boolean isMoving() {
      return PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d() || PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d() || PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d() || PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d();
   }

   public static float getStrafe() {
      float strafe = 0.0F;
      if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d()) {
         ++strafe;
      }

      if (PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
         --strafe;
      }

      return strafe;
   }

   public static float getForward() {
      float forward = 0.0F;
      if (PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d()) {
         ++forward;
      }

      if (PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d()) {
         --forward;
      }

      return forward;
   }

   public static Vec3 getMotionForRotation(float yaw, double motionY, BlockPos pos, boolean sprint) {
      float strafe = getStrafe() * 0.98F;
      float forward = getForward() * 0.98F;
      if (sprint) {
         sprint = (double)strafe >= 0.8D;
      }

      double motionX = 0.0D;
      double motionZ = 0.0D;
      EntityPlayerSP player = PizzaClient.mc.field_71439_g;
      float slipperiness = 0.91F;
      if (player.field_70122_E) {
         slipperiness = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().field_149765_K * 0.91F;
      }

      float moveSpeedMultiplier = 0.16277136F / (slipperiness * slipperiness * slipperiness);
      float moveSpeed = player.field_70122_E ? PlayerUtil.getSpeedModifier(sprint) * moveSpeedMultiplier : getJumpSpeed();
      if (!player.func_70090_H() && !player.func_180799_ab()) {
         Vec2 movement = move(strafe, forward, moveSpeed, yaw);
         if (movement != null) {
            motionX += movement.x;
            motionZ += movement.y;
            movement.scale(slipperiness);
         }

         if (!player.field_70122_E) {
            motionY -= 0.08D;
            motionY *= 0.9800000190734863D;
         }
      }

      return new Vec3(motionX, motionY, motionZ);
   }

   public static Vec3 getMotionForRotation(float yaw, boolean sprint) {
      return getMotionForRotation(yaw, PizzaClient.mc.field_71439_g.field_70181_x, new BlockPos(MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t), MathUtil.floor(PizzaClient.mc.field_71439_g.func_174813_aQ().field_72338_b) - 1, MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v)), sprint);
   }

   public static float getJumpSpeed() {
      float speed = 0.02F;
      if (PizzaClient.mc.field_71439_g.func_70051_ag()) {
         speed = (float)((double)speed + 0.005999999865889549D);
      }

      return speed;
   }

   public static Vec2 move(float strafe, float forward, float friction, float yaw) {
      float f = strafe * strafe + forward * forward;
      if (f >= 1.0E-4F) {
         f = MathHelper.func_76129_c(f);
         if (f < 1.0F) {
            f = 1.0F;
         }

         f = friction / f;
         strafe *= f;
         forward *= f;
         float f1 = MathHelper.func_76126_a(yaw * 0.017453292F);
         float f2 = MathHelper.func_76134_b(yaw * 0.017453292F);
         return new Vec2((double)(strafe * f2 - forward * f1), (double)(forward * f2 + strafe * f1));
      } else {
         return null;
      }
   }

   public static boolean isOnGround(Vec3 position) {
      double minY = position.field_72448_b;
      BlockPos pos = new BlockPos(position);
      IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
      if (state.func_177230_c().func_176209_a(state, true) && state.func_177230_c().func_180640_a(PizzaClient.mc.field_71441_e, pos, state).field_72338_b == minY) {
         return true;
      } else {
         pos = pos.func_177977_b();
         state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         return state.func_177230_c().func_176209_a(state, true) && state.func_177230_c().func_180640_a(PizzaClient.mc.field_71441_e, pos, state).field_72338_b == minY;
      }
   }

   public static boolean isMovingForward() {
      Vec3 look = PlayerUtil.get2DLook();
      Vec3 position = PlayerUtil.getPosition();
      Vec3 motion = PlayerUtil.getMotionPosition();
      return position.func_72441_c(look.field_72450_a, 0.0D, look.field_72449_c).func_72436_e(motion) > position.func_178786_a(look.field_72450_a, 0.0D, look.field_72449_c).func_72436_e(motion);
   }
}
