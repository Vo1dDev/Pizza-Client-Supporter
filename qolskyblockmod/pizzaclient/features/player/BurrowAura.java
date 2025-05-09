package qolskyblockmod.pizzaclient.features.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.core.events.WorldChangeEvent;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.api.SkyblockAPI;
import qolskyblockmod.pizzaclient.util.handler.ThreadHandler;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class BurrowAura {
   public static final List<BlockPos> burrowPositions = new ArrayList();
   private long lastCheckTime;
   private static long lastInteractTime;
   public static BlockPos lastHitPos;
   public static boolean clickAgain;

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.burrowAura && PizzaClient.mc.field_71462_r == null && PizzaClient.location == Locations.HUB && this.spadeInInv()) {
         if (burrowPositions.size() != 0 && System.currentTimeMillis() - this.lastCheckTime < 60000L) {
            if (clickAgain) {
               clickAndRemove();
            }

            if (System.currentTimeMillis() - lastInteractTime >= 600L) {
               Iterator var2 = burrowPositions.iterator();

               while(var2.hasNext()) {
                  BlockPos pos = (BlockPos)var2.next();
                  if (!pos.equals(lastHitPos) && VecUtil.canReachBlock(pos, PizzaClient.config.burrowAuraReach)) {
                     lastHitPos = pos;
                     clickBurrow();
                     lastInteractTime = System.currentTimeMillis();
                     return;
                  }
               }
            }

         } else {
            if (ThreadHandler.isDead()) {
               this.lastCheckTime = System.currentTimeMillis();
               ThreadHandler.enqueue(() -> {
                  try {
                     JsonArray burrows = SkyblockAPI.getLatestProfileSkyblockAPI().getBurrows();
                     burrowPositions.clear();
                     Iterator var1 = burrows.iterator();

                     while(var1.hasNext()) {
                        JsonElement element = (JsonElement)var1.next();
                        JsonObject obj = element.getAsJsonObject();
                        burrowPositions.add(new BlockPos(obj.get("x").getAsInt(), obj.get("y").getAsInt(), obj.get("z").getAsInt()));
                     }

                     if (lastHitPos != null && !burrowPositions.contains(lastHitPos)) {
                        lastHitPos = null;
                     }
                  } catch (Exception var4) {
                     var4.printStackTrace();
                  }

               });
            }

         }
      }
   }

   @SubscribeEvent
   public void onEntityDeath(LivingDeathEvent event) {
      if (lastHitPos != null && !(event.entity instanceof EntityArmorStand) && event.entity.func_70068_e(PizzaClient.mc.field_71439_g) < 50.0D) {
         if (event.entity instanceof EntityOcelot) {
            Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

            while(var2.hasNext()) {
               Entity entity = (Entity)var2.next();
               if (entity instanceof EntityOcelot && ((EntityOcelot)event.entity).func_110138_aP() > 0.0F && event.entity.func_70068_e(PizzaClient.mc.field_71439_g) < 100.0D) {
                  return;
               }
            }
         }

         clickAgain = true;
      }

   }

   @SubscribeEvent
   public void onWorldLoad(WorldChangeEvent event) {
      burrowPositions.clear();
   }

   private static void swapToSpade() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == Items.field_151038_n) {
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
            PlayerUtil.forceUpdateController();
            break;
         }
      }

   }

   public static void clickBurrow() {
      if (!holdingSpade()) {
         int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
         swapToSpade();
         if (PizzaClient.mc.field_71442_b.func_180511_b(lastHitPos, EnumFacing.UP)) {
            PizzaClient.mc.field_71439_g.func_71038_i();
         }

         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
      } else {
         if (PizzaClient.mc.field_71442_b.func_180511_b(lastHitPos, EnumFacing.UP)) {
            PizzaClient.mc.field_71439_g.func_71038_i();
         }

      }
   }

   private static boolean holdingSpade() {
      ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      return stack != null && stack.func_77973_b() == Items.field_151038_n;
   }

   private boolean spadeInInv() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == Items.field_151038_n) {
            return true;
         }
      }

      return false;
   }

   public static void removeFromList() {
      burrowPositions.remove(lastHitPos);
      lastHitPos = null;
   }

   public static void clickAndRemove() {
      if (lastHitPos == null) {
         clickAgain = false;
      } else {
         if (System.currentTimeMillis() - lastInteractTime >= 1100L && VecUtil.canReachBlock(lastHitPos, PizzaClient.config.burrowAuraReach)) {
            if (!holdingSpade()) {
               int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
               swapToSpade();
               if (PizzaClient.mc.field_71442_b.func_180511_b(lastHitPos, EnumFacing.UP)) {
                  PizzaClient.mc.field_71439_g.func_71038_i();
               }

               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
            } else if (PizzaClient.mc.field_71442_b.func_180511_b(lastHitPos, EnumFacing.UP)) {
               PizzaClient.mc.field_71439_g.func_71038_i();
            }

            burrowPositions.remove(lastHitPos);
            lastHitPos = null;
            clickAgain = false;
            lastInteractTime = System.currentTimeMillis();
         }

      }
   }
}
