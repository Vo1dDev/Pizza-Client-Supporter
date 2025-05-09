package qolskyblockmod.pizzaclient.util.rotation.fake;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public abstract class FakeRotater {
   public static float lastYaw;
   public static float lastPitch = 420.0F;
   public static FakeRotater rotater;
   public float realYaw;
   public float realPitch;

   public FakeRotater() {
      if (lastPitch == 420.0F) {
         lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
         lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      }

   }

   public void storeCurrentRotation() {
      this.realYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.realPitch = PizzaClient.mc.field_71439_g.field_70125_A;
   }

   public void rotateBack() {
      lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      PizzaClient.mc.field_71439_g.field_70125_A = this.realPitch;
      PizzaClient.mc.field_71439_g.field_70177_z = this.realYaw;
   }

   public void rotateToGoal(float yaw, float pitch) {
      PizzaClient.mc.field_71439_g.field_70177_z = yaw;
      PizzaClient.mc.field_71439_g.field_70125_A = pitch;
   }

   public void rotateToGoal(Vec3 rotateVec) {
      Rotation rotation = Rotation.getRotation(rotateVec);
      PizzaClient.mc.field_71439_g.field_70177_z = rotation.yaw;
      PizzaClient.mc.field_71439_g.field_70125_A = rotation.pitch;
   }

   public void onOpenGui() {
   }

   public void use() {
      rotater = this;
   }

   public void terminate() {
      rotater = null;
   }

   public abstract void onPlayerMovePre();

   public abstract void onPlayerMovePost();

   public static void leftClick(Vec3 rotationVec) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
            PizzaClient.mc.field_71439_g.func_71038_i();
         }
      };
   }

   public static void rotate(Vec3 rotationVec) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
         }
      };
   }

   public static void rotate(Rotation rotation) {
      rotater = new NormalFakeRotater(rotation) {
         public void interact() {
         }
      };
   }

   public static void clickEntity(Rotation rotation, final Entity entity) {
      rotater = new NormalFakeRotater(rotation) {
         public void interact() {
            PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, entity);
         }
      };
   }

   public static void clickEntity(Vec3 rotationVec, final Entity entity) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
            PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, entity);
         }
      };
   }

   public static void rightClick(final Vec3 rotationVec, final BlockPos hitPos, final int clickAmount) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
            MovingObjectPosition position = VecUtil.calculateInterceptLook(hitPos, rotationVec, 4.5F);
            if (position != null) {
               for(int i = 0; i < clickAmount; ++i) {
                  if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), hitPos, position.field_178784_b, position.field_72307_f)) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }
               }
            }

         }
      };
   }

   public static void rightClickWithItem(final Vec3 rotationVec, final BlockPos hitPos, final int itemSlot) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
            MovingObjectPosition position = VecUtil.calculateInterceptLook(hitPos, rotationVec, 4.5F);
            if (position != null) {
               int lastSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = itemSlot;
               if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), hitPos, position.field_178784_b, position.field_72307_f)) {
                  PizzaClient.mc.field_71439_g.func_71038_i();
               }

               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
            }

         }
      };
   }

   public static void rightClickWithItem(final Vec3 rotationVec, final BlockPos hitPos, final int clickAmount, final int itemSlot) {
      rotater = new NormalFakeRotater(rotationVec) {
         public void interact() {
            MovingObjectPosition position = VecUtil.calculateInterceptLook(hitPos, rotationVec, 4.5F);
            if (position != null) {
               int lastSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = itemSlot;

               for(int i = 0; i < clickAmount; ++i) {
                  if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), hitPos, position.field_178784_b, position.field_72307_f)) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }
               }

               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
            }

         }
      };
   }

   public void setRotationHeadYaw() {
      PizzaClient.mc.field_71439_g.field_70759_as = lastYaw;
   }

   public float setRotationHeadPitch() {
      return lastPitch / 57.29578F;
   }
}
