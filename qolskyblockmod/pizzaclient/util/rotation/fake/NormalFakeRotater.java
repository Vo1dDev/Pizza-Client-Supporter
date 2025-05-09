package qolskyblockmod.pizzaclient.util.rotation.fake;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public abstract class NormalFakeRotater extends FakeRotater {
   public float rotateYaw;
   public float rotatePitch;

   public NormalFakeRotater(Vec3 rotationVec) {
      Rotation rotation = Rotation.getRotation(rotationVec);
      this.rotateYaw = rotation.yaw;
      this.rotatePitch = rotation.pitch;
   }

   public NormalFakeRotater(Rotation rotation) {
      this.rotateYaw = rotation.yaw;
      this.rotatePitch = rotation.pitch;
   }

   public void onPlayerMovePre() {
      this.storeCurrentRotation();
      this.rotateToGoal(this.rotateYaw, this.rotatePitch);
   }

   public void onPlayerMovePost() {
      this.interact();
      this.rotateBack();
      this.terminate();
   }

   public abstract void interact();
}
