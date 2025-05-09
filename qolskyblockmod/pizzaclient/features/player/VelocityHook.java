package qolskyblockmod.pizzaclient.features.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class VelocityHook {
   public static double motionMultiplier;
   public static int velocityTicks;

   public static void onTick() {
      if (velocityTicks != 0) {
         if (velocityTicks == 1) {
            EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
            var10000.field_70159_w *= motionMultiplier;
            var10000 = PizzaClient.mc.field_71439_g;
            var10000.field_70179_y *= motionMultiplier;
            velocityTicks = 0;
         } else {
            --velocityTicks;
         }
      }

   }

   public static void setupVelocity() {
      velocityTicks = 3;
      motionMultiplier = calculateMotion(getMotionMultiplier());
   }

   private static double getMotionMultiplier() {
      ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      String name = held.func_82833_r();
      if (name.contains("Leaping Sword")) {
         return 1.100000023841858D;
      } else if (name.contains("Bonzo's Staff")) {
         return (double)PizzaClient.config.increasedVelocityAmount;
      } else {
         return name.contains("Jerry-chine") ? 1.0D : 1.2999999523162842D;
      }
   }

   private static double calculateMotion(double multiplier) {
      return 1.0D + (multiplier - 1.0D) * (double)PlayerUtil.getSpeedModifier() * 2.5D;
   }
}
