package qolskyblockmod.pizzaclient.util.rotation.fake;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public abstract class SmoothFakeRotater extends FakeRotater {
   protected Vec3 rotationVec;
   protected boolean rotatingBack;
   protected final int rotationAmount;
   protected boolean shouldInteract;
   protected boolean isFinished;

   public SmoothFakeRotater(Vec3 rotationVec, int rotationAmount) {
      this.rotationVec = rotationVec;
      this.rotationAmount = rotationAmount;
   }

   public void onPlayerMovePre() {
      this.storeCurrentRotation();
      if (this.rotatingBack) {
         if (this.smoothRotateToVec()) {
            this.isFinished = true;
         }
      } else if (this.smoothRotateToVec()) {
         this.rotatingBack = true;
         this.rotationVec = PlayerUtil.getPositionEyes().func_178787_e(PlayerUtil.getLook(this.realYaw, this.realPitch, 4.5F));
         this.shouldInteract = true;
      }

   }

   public void onPlayerMovePost() {
      this.rotateBack();
      if (this.shouldInteract) {
         this.interact();
         this.shouldInteract = false;
      }

      if (this.isFinished) {
         this.terminate();
      }

   }

   public abstract void interact();

   public void onOpenGui() {
      this.rotateToGoal(this.rotationVec);
   }

   protected boolean smoothRotateToVec() {
      Rotation rotation = Rotation.getRotationDifference(this.rotationVec, lastYaw, lastPitch);
      if (MathUtil.abs(rotation.yaw) < (float)this.rotationAmount && MathUtil.abs(rotation.pitch) < (float)this.rotationAmount) {
         this.rotateToGoal(lastYaw + rotation.yaw, lastPitch + rotation.pitch);
         return true;
      } else {
         float rotateYaw;
         float rotatePitch;
         if (MathUtil.abs(rotation.yaw) > MathUtil.abs(rotation.pitch)) {
            rotateYaw = (float)PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotatePitch = rotateYaw * MathUtil.abs(rotation.pitch / rotation.yaw);
         } else {
            rotatePitch = (float)PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotateYaw = rotatePitch * MathUtil.abs(rotation.yaw / rotation.pitch);
         }

         if (rotation.yaw < 0.0F) {
            rotateYaw = -rotateYaw;
         }

         if (rotation.pitch < 0.0F) {
            rotatePitch = -rotatePitch;
         }

         this.rotateToGoal(lastYaw + rotateYaw, lastPitch + rotatePitch);
         return false;
      }
   }

   public boolean rotatingToGoal() {
      return !this.rotatingBack;
   }

   public boolean isRotatingBack() {
      return this.rotatingBack;
   }

   public void setRotationHeadYaw() {
      PizzaClient.mc.field_71439_g.field_70759_as = lastYaw + (Rotation.getRotationDifference(this.rotationVec, lastYaw, lastPitch).yaw < 0.0F ? (float)(-this.rotationAmount) : (float)this.rotationAmount * Utils.getPartialTicks());
   }

   public float setRotationHeadPitch() {
      return (lastPitch + (Rotation.getRotationDifference(this.rotationVec, lastYaw, lastPitch).pitch < 0.0F ? (float)(-this.rotationAmount) : (float)this.rotationAmount * Utils.getPartialTicks())) / 57.29578F;
   }
}
