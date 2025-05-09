package qolskyblockmod.pizzaclient.features.slayers;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.SBInfo;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.render.RenderType;

public class SlayerFeatures {
   private final Color LOW_LEVEL;
   private final Color HIGH_LEVEL;
   private final Color BOSS_COLOR;

   public SlayerFeatures() {
      this.LOW_LEVEL = Color.CYAN;
      this.HIGH_LEVEL = new Color(148, 0, 211);
      this.BOSS_COLOR = new Color(140, 0, 0);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST,
      receiveCanceled = true
   )
   public void onRenderEntity(Pre<EntityLivingBase> event) {
      if (event.entity instanceof EntityArmorStand) {
         EntityArmorStand entity = (EntityArmorStand)event.entity;
         if (!entity.func_145818_k_()) {
            return;
         }

         String entityName = StringUtils.func_76338_a(entity.func_95999_t());
         double x = event.entity.field_70165_t;
         double y = event.entity.field_70163_u;
         double z = event.entity.field_70161_v;
         if (PizzaClient.config.allMobEsp) {
            if (entityName.startsWith("[Lv")) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.4D, y - 2.0D, z - 0.4D, x + 0.4D, y - 0.1D, z + 0.4D), Color.CYAN);
            }

            return;
         }

         if (PizzaClient.location == Locations.CRIMSON_ISLE && PizzaClient.mc.field_71462_r == null) {
            if (PizzaClient.config.autoBlazeDagger && AutoSwapDagger.shouldClick()) {
               if (entityName.startsWith("CRYSTAL")) {
                  if (VecUtil.isFacingAABB(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D), 5.0F)) {
                     AutoSwapDagger.swapToCrystal();
                  }

                  return;
               }

               if (entityName.startsWith("ASHEN")) {
                  if (VecUtil.isFacingAABB(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D), 5.0F)) {
                     AutoSwapDagger.swapToAshen();
                  }

                  return;
               }

               if (entityName.startsWith("AURIC")) {
                  if (VecUtil.isFacingAABB(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D), 5.0F)) {
                     AutoSwapDagger.swapToAuric();
                  }

                  return;
               }

               if (entityName.startsWith("SPIRIT")) {
                  if (VecUtil.isFacingAABB(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D), 5.0F)) {
                     AutoSwapDagger.swapToSprit();
                  }

                  return;
               }
            }

            if (PizzaClient.config.ashfangAura && entityName.equals("Blazing Soul")) {
               if (AshfangAura.onRenderOrb(event.entity)) {
                  PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
                  event.setCanceled(true);
               }

               return;
            }
         }

         if (PizzaClient.config.minibossEsp && !SBInfo.bossSpawned && !entityName.contains("0")) {
            if (entityName.startsWith("Voidcrazed Maniac")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.BOSS_COLOR);
               return;
            }

            if (entityName.startsWith("Voidling Radical")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.HIGH_LEVEL);
               return;
            }

            if (entityName.startsWith("Voidling Devotee")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 3.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Revenant ")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Atoned Champion")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Atoned Revenant")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.HIGH_LEVEL);
               return;
            }

            if (entityName.startsWith("Deformed Revenant")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.HIGH_LEVEL);
               return;
            }

            if (entityName.startsWith("Tarantula ")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66D, y - 1.0D, z - 0.66D, x + 0.66D, y - 0.25D, z + 0.66D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Mutant Tarantula")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66D, y - 1.0D, z - 0.66D, x + 0.66D, y - 0.25D, z + 0.66D), this.HIGH_LEVEL);
               return;
            }

            if (entityName.startsWith("Pack Enforcer")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Sven Follower")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.LOW_LEVEL);
               return;
            }

            if (entityName.startsWith("Sven Alpha")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.HIGH_LEVEL);
               return;
            }
         }

         if (PizzaClient.config.bossEsp && !SBInfo.bossSpawned && !entityName.contains("0")) {
            if (entityName.contains("Sven Packmaster")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.BOSS_COLOR);
            } else if (entityName.contains("Tarantula Broodfather")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66D, y - 1.0D, z - 0.66D, x + 0.66D, y - 0.25D, z + 0.66D), this.BOSS_COLOR);
            } else if (entityName.contains("Revenant Horror")) {
               RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5D, y - 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), this.BOSS_COLOR);
            }
         }
      } else if (event.entity instanceof EntityHorse) {
         if (PizzaClient.config.hideHorses) {
            PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
            event.setCanceled(true);
         }
      } else if (event.entity instanceof EntityBlaze) {
         if (PizzaClient.config.blazeEsp) {
            RenderType.renderTintChams(PizzaClient.config.blazeEspColor);
            event.setCanceled(false);
         }
      } else if (event.entity instanceof EntityDragon && PizzaClient.config.dragonEsp) {
         Entity[] var10 = event.entity.func_70021_al();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            Entity e = var10[var12];
            RenderUtil.drawOutlinedBoxNoEsp(e.func_174813_aQ(), Color.CYAN, 2.5F);
         }

         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      }

   }

   public static void useSoulCry() {
      if (PizzaClient.location == Locations.END && SBInfo.bossSpawned) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
         if (item.func_77973_b() == Items.field_151048_u) {
            String displayName = item.func_82833_r();
            if (displayName.contains("Atomsplit Katana") || displayName.contains("Vorpal Katana")) {
               KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            }
         }

      }
   }
}
