package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.RotationUtil;

public class Rotater implements IRotater {
   public static boolean rotating;
   protected float divider = 190.0F;
   protected float changedDivider;
   protected long timeElapsed;
   protected float yaw;
   protected float pitch;
   protected float changedYaw;
   protected float changedPitch;
   protected float startYaw;
   protected float startPitch;
   protected float goalYaw;
   protected float goalPitch;

   public Rotater(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      this.yaw = (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      this.pitch = (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
      this.startPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      this.startYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
   }

   public Rotater(BlockPos pos) {
      Vec3 target = new Vec3((double)pos.func_177958_n() + 0.5D, PizzaClient.mc.field_71439_g.field_70163_u + 1.6200000047683716D, (double)pos.func_177952_p() + 0.5D);
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      this.yaw = RotationUtil.getYawClosestTo90Degrees((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z));
      this.pitch = (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
      this.startPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      this.startYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
   }

   public Rotater(float yawIn, float pitchIn) {
      this.yaw = yawIn;
      this.pitch = pitchIn;
      this.startPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      this.startYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
   }

   public static Rotater rotateTo(float yaw, float pitch) {
      return new Rotater(yaw - PizzaClient.mc.field_71439_g.field_70177_z, MathUtil.clampPitch(pitch - PizzaClient.mc.field_71439_g.field_70125_A));
   }

   public Rotater setPitch(float pitch) {
      this.pitch = pitch;
      this.goalPitch = pitch + PizzaClient.mc.field_71439_g.field_70125_A;
      return this;
   }

   public Rotater setYaw(float yaw) {
      this.yaw = yaw;
      this.goalYaw = yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      return this;
   }

   public Rotater addPitch(float pitch) {
      this.pitch += pitch;
      this.goalPitch += pitch;
      return this;
   }

   public Rotater addYaw(float yaw) {
      this.yaw += yaw;
      this.goalYaw += yaw;
      return this;
   }

   public Rotater randomPitch() {
      this.pitch = MathUtil.randomFloat();
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
      return this;
   }

   public Rotater randomYaw() {
      this.yaw = MathUtil.randomFloat();
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      return this;
   }

   public Rotater setRotationAmount(int rotation) {
      this.divider = (float)(rotation * 10);
      return this;
   }

   public void rotate() {
      rotating = true;
      this.changedDivider = this.divider;
      this.yaw /= this.divider;
      this.pitch /= this.divider;
      this.changedPitch = this.pitch;
      this.changedYaw = this.yaw;
      PizzaClient.rotater = this;
      this.timeElapsed = System.currentTimeMillis();
   }

   public void add() {
      float elapsed = (float)(System.currentTimeMillis() - this.timeElapsed);
      if (elapsed >= this.changedDivider) {
         PizzaClient.mc.field_71439_g.field_70177_z = this.goalYaw;
         PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.goalPitch);
         PizzaClient.rotater = null;
      } else {
         if (elapsed > 80.0F) {
            float progress = 1.0F + (elapsed - 80.0F) / this.changedDivider;
            float diff = progress * progress / 1.8F;
            this.changedYaw = this.yaw / diff;
            this.changedPitch = this.pitch / diff;
            this.changedDivider = this.divider * diff;
         }

         PizzaClient.mc.field_71439_g.field_70177_z = this.startYaw + this.changedYaw * elapsed;
         PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.startPitch + this.changedPitch * elapsed);
      }
   }

   public Rotater addRandom() {
      this.yaw += MathUtil.randomFloat();
      this.pitch += MathUtil.randomFloat();
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
      return this;
   }

   public Rotater addRandomRotateAmount() {
      this.divider += (float)MathUtil.floor(Utils.random.nextGaussian() * 8.0D);
      return this;
   }

   public Rotater addSlightRandomRotateAmount() {
      this.divider += (float)MathUtil.floor(Utils.random.nextGaussian() * 5.0D);
      return this;
   }

   public Rotater addBigRandomRotateAmount() {
      this.divider += (float)MathUtil.floor(Utils.random.nextGaussian() * 13.0D);
      return this;
   }

   public Rotater addRandomRotateAmount(float multiplier) {
      this.divider += (float)MathUtil.floor(Utils.random.nextGaussian() * (double)multiplier);
      return this;
   }

   public Rotater randomYaw(float min, float rand, boolean positive) {
      this.yaw += positive ? MathUtil.positiveFloat() * rand + min : MathUtil.negativeFloat() * rand - min;
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      return this;
   }

   public Rotater randomPitch(float min, float rand, boolean positive) {
      float nextPitch = positive ? MathUtil.positiveFloat() * rand + min : MathUtil.negativeFloat() * rand - min + this.pitch;
      float nextGoalPitch = nextPitch + PizzaClient.mc.field_71439_g.field_70125_A;
      if (!(nextGoalPitch > 90.0F) && !(nextGoalPitch < -90.0F)) {
         this.pitch += nextPitch;
         this.goalPitch = nextGoalPitch;
         return this;
      } else {
         return this.randomPitch(min, rand, !positive);
      }
   }

   public Rotater randomYaw(float min, float rand) {
      this.yaw += Utils.random.nextBoolean() ? MathUtil.positiveFloat() * rand + min : MathUtil.negativeFloat() * rand - min;
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      return this;
   }

   public Rotater randomPitch(float min, float rand) {
      this.pitch += Utils.random.nextBoolean() ? MathUtil.positiveFloat() * rand + min : MathUtil.negativeFloat() * rand - min;
      this.goalPitch = this.pitch + PizzaClient.mc.field_71439_g.field_70125_A;
      return this;
   }

   public Rotater antiSus(float amt) {
      this.divider = ((float)MathUtil.round((MathUtil.abs(this.yaw) + MathUtil.abs(this.pitch) + MathUtil.randomFloat()) / 8.0F) + amt + 2.0F) * 10.0F;
      return this;
   }

   public Rotater antiSus(float divider, float amt) {
      this.divider = ((float)MathUtil.round((MathUtil.abs(this.yaw) + MathUtil.abs(this.pitch) + MathUtil.randomFloat()) / divider) + amt + 2.0F) * 10.0F;
      return this;
   }

   public Rotater antiSus() {
      this.divider = (float)((MathUtil.round((MathUtil.abs(this.yaw) + MathUtil.abs(this.pitch) + MathUtil.randomFloat()) / 8.0F) + 22) * 10);
      return this;
   }
}
