package qolskyblockmod.pizzaclient.features.slayers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class AutoSwapDagger {
   private static long lastClickTime;

   public static void swapToCrystal() {
      for(int i = 0; i < 8; ++i) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (item != null) {
            String name = item.func_82833_r();
            if (name.contains("Deathripper Dagger") || name.contains("Mawdredge Dagger") || name.contains("Twilight Dagger")) {
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               if (item.func_77973_b() != Items.field_151048_u) {
                  PlayerUtil.forceUpdateController();
                  PlayerUtil.rightClick();
               }

               lastClickTime = System.currentTimeMillis();
               break;
            }
         }
      }

   }

   public static void swapToSprit() {
      for(int i = 0; i < 8; ++i) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (item != null) {
            String name = item.func_82833_r();
            if (name.contains("Deathripper Dagger") || name.contains("Mawdredge Dagger") || name.contains("Twilight Dagger")) {
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               if (item.func_77973_b() != Items.field_151040_l) {
                  PlayerUtil.forceUpdateController();
                  PlayerUtil.rightClick();
               }

               lastClickTime = System.currentTimeMillis();
               break;
            }
         }
      }

   }

   public static void swapToAshen() {
      for(int i = 0; i < 8; ++i) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (item != null) {
            String name = item.func_82833_r();
            if (name.contains("Pyrochaos Dagger") || name.contains("Kindlebane Dagger") || name.contains("Firedust Dagger")) {
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               if (item.func_77973_b() != Items.field_151052_q) {
                  PlayerUtil.forceUpdateController();
                  PlayerUtil.rightClick();
               }

               lastClickTime = System.currentTimeMillis();
               break;
            }
         }
      }

   }

   public static void swapToAuric() {
      for(int i = 0; i < 8; ++i) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (item != null) {
            String name = item.func_82833_r();
            if (name.contains("Pyrochaos Dagger") || name.contains("Kindlebane Dagger") || name.contains("Firedust Dagger")) {
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               if (item.func_77973_b() != Items.field_151010_B) {
                  PlayerUtil.forceUpdateController();
                  PlayerUtil.rightClick();
               }

               lastClickTime = System.currentTimeMillis();
               break;
            }
         }
      }

   }

   public static boolean shouldClick() {
      return System.currentTimeMillis() - lastClickTime >= 500L;
   }
}
