package qolskyblockmod.pizzaclient.util.rotation.helper;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotater;

public class BlockPosFakeRotationHelper {
   public BlockPos bestPos;
   private double bestRotation = 9.9999999E7D;
   private final float maxDist = 20.25F;
   private final Vec3 player = PlayerUtil.getPositionEyes();

   public BlockPosFakeRotationHelper() {
      if (FakeRotater.lastPitch == 420.0F) {
         FakeRotater.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         FakeRotater.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      }

   }

   public void compare(Vec3 vecIn) {
      if (this.player.func_72436_e(vecIn) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(vecIn, FakeRotater.lastYaw, FakeRotater.lastPitch).sum();
         if (dist < this.bestRotation) {
            this.bestPos = new BlockPos(vecIn);
            this.bestRotation = dist;
         }
      }

   }

   public void compare(MovingObjectPosition pos) {
      if (this.player.func_72436_e(pos.field_72307_f) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(pos.field_72307_f, FakeRotater.lastYaw, FakeRotater.lastPitch).sum();
         if (dist < this.bestRotation) {
            this.bestPos = pos.func_178782_a();
            this.bestRotation = dist;
         }
      }

   }

   public void compare(BlockPos pos) {
      Vec3 vecIn = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      if (this.player.func_72436_e(vecIn) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(vecIn, FakeRotater.lastYaw, FakeRotater.lastPitch).sum();
         if (dist < this.bestRotation) {
            this.bestPos = pos;
            this.bestRotation = dist;
         }
      }

   }

   public boolean isNotNull() {
      return this.bestPos != null;
   }
}
