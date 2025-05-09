package qolskyblockmod.pizzaclient.features.dungeons;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ResizeWindowEvent;
import qolskyblockmod.pizzaclient.core.events.WorldChangeEvent;
import qolskyblockmod.pizzaclient.features.dungeons.f7.FunnyDevice;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.render.EntityType;
import qolskyblockmod.pizzaclient.util.render.OutlineRenderer;
import qolskyblockmod.pizzaclient.util.render.RenderType;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotater;

public class DungeonFeatures {
   public static boolean shouldLividsSpawn = false;
   private static Color lividColor;
   public static Entity lividEntity;
   public static long blindnessDuration;
   private static final Map<Entity, EntityType> starMobs = new HashMap();
   public static final OutlineRenderer felsOutline;
   public static final OutlineRenderer minibossOutline;
   public static final Set<String> BLOOD_MOBS;

   @SubscribeEvent(
      priority = EventPriority.LOW,
      receiveCanceled = true
   )
   public void onRenderLiving(Pre<EntityLivingBase> event) {
      if (PizzaClient.location == Locations.DUNGEON) {
         String name;
         if (event.entity instanceof EntityArmorStand) {
            if (event.entity.func_145818_k_()) {
               name = StringUtils.func_76338_a(event.entity.func_95999_t());
               if (PizzaClient.config.starredMobsEsp && name.startsWith("✯ ")) {
                  if (Utils.getXandZDistanceSquared(event.entity.field_70165_t, event.entity.field_70161_v) < (double)(PizzaClient.config.starredMobsEspRange * PizzaClient.config.starredMobsEspRange)) {
                     EntityType mob = (EntityType)starMobs.get(event.entity);
                     if (mob != null) {
                        if (mob.isDead()) {
                           starMobs.remove(event.entity);
                           return;
                        }

                        EntityLivingBase e;
                        switch(mob.type) {
                        case 0:
                           if (PizzaClient.config.starredMobsEspMode == 2) {
                              RenderType.setUniversalOutlineColor(PizzaClient.config.starredMobsEspColor);
                              RenderType.addEntity(mob.entityToRender);
                           } else {
                              e = mob.entityToRender;
                              RenderUtil.drawOutlinedEsp(new AxisAlignedBB(e.field_70165_t - 0.35D, e.field_70163_u, e.field_70161_v - 0.35D, e.field_70165_t + 0.35D, e.field_70163_u + 2.0D, e.field_70161_v + 0.35D), PizzaClient.config.starredMobsEspColor, 3.0F);
                           }
                           break;
                        case 1:
                           if (PizzaClient.config.starredMobsEspMode == 0) {
                              RenderUtil.drawOutlinedEsp(mob.entityToRender.func_174813_aQ(), PizzaClient.config.starredMobsEspColor, 3.0F);
                           } else {
                              RenderType.addEntity(felsOutline, mob.entityToRender);
                           }
                           break;
                        case 2:
                           if (PizzaClient.config.starredMobsEspMode == 0) {
                              e = mob.entityToRender;
                              RenderUtil.drawOutlinedEsp(new AxisAlignedBB(e.field_70165_t - 0.4D, e.field_70163_u, e.field_70161_v - 0.4D, e.field_70165_t + 0.4D, e.field_70163_u + 2.0D, e.field_70161_v + 0.4D), new Color(105, 0, 10), 3.0F);
                           } else {
                              RenderType.addEntity(minibossOutline, mob.entityToRender);
                           }
                        }
                     } else if (Utils.getXandZDistanceSquared(event.entity.field_70165_t, event.entity.field_70161_v) < (double)(PizzaClient.config.starredMobsEspRange * PizzaClient.config.starredMobsEspRange)) {
                        this.getStarredMobsWithinAABB(event.entity);
                     }
                  }

                  return;
               }

               if (PizzaClient.config.dungeonKeyEsp) {
                  if (name.equals("Wither Key")) {
                     RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.4D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u + 0.9D, event.entity.field_70161_v + 0.5D), new Color(20, 20, 20), 3.0F);
                     return;
                  }

                  if (name.equals("Blood Key")) {
                     RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.4D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u + 0.9D, event.entity.field_70161_v + 0.5D), new Color(180, 0, 0), 3.0F);
                     return;
                  }
               }

               if (PizzaClient.config.hideSuperboom && (name.equals("Revive Stone") || name.startsWith("Superboom TNT") || name.startsWith("Blessing of"))) {
                  PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
                  event.setCanceled(true);
                  return;
               }

               if (PizzaClient.config.autoSimonSays && !FunnyDevice.clickedSimonSays && name.startsWith("Inactive")) {
                  MovingObjectPosition intercept = VecUtil.calculateInterceptLook(FunnyDevice.simonSaysStart, PizzaClient.config.autoSimonSaysRange);
                  if (intercept != null) {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Starting auto simon says!"));
                     if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), FunnyDevice.simonSaysStart, intercept.field_178784_b, intercept.field_72307_f)) {
                        PizzaClient.mc.field_71439_g.func_71038_i();
                     }

                     FunnyDevice.clickedSimonSays = true;
                  }

                  return;
               }
            }

            if (lividEntity == event.entity) {
               if (PizzaClient.config.lividAura) {
                  FakeRotater.rotate(event.entity.func_174824_e(1.0F));
               }

               if (PizzaClient.config.lividEsp) {
                  RenderType.setUniversalOutlineColor(lividColor);
                  RenderType.addEntity(event.entity);
               }
            }
         } else if (event.entity instanceof EntityOtherPlayerMP) {
            name = event.entity.func_70005_c_().trim();
            if (PizzaClient.config.alwaysAimAtSpiritBear && name.equals("Spirit Bear")) {
               FakeRotater.rotate(event.entity.field_70163_u % 1.0D == 0.0D ? event.entity.func_174824_e(1.0F) : new Vec3(event.entity.field_70165_t, 69.5D, event.entity.field_70161_v));
               return;
            }

            if (PizzaClient.config.starredMobsEsp && name.equals("Shadow Assassin")) {
               if (PizzaClient.config.starredMobsEspMode == 0) {
                  Entity e = event.entity;
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(e.field_70165_t - 0.4D, e.field_70163_u, e.field_70161_v - 0.4D, e.field_70165_t + 0.4D, e.field_70163_u + 2.0D, e.field_70161_v + 0.4D), new Color(105, 0, 10), 3.0F);
               } else {
                  RenderType.addEntity(minibossOutline, event.entity);
               }
            }
         } else if (event.entity instanceof EntityEnderman) {
            if (PizzaClient.config.showHiddenMobs && event.entity.func_82150_aj()) {
               event.entity.func_82142_c(false);
            }
         } else if (event.entity instanceof EntityBat && PizzaClient.config.batEsp) {
            float maxHP = event.entity.func_110138_aP();
            if (maxHP == 100.0F || maxHP == 200.0F) {
               RenderType.renderChromaChams(0.77F);
               event.setCanceled(false);
            }
         }

      }
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onEntityJoin(EntityJoinWorldEvent event) {
      if (PizzaClient.location == Locations.DUNGEON) {
         if (event.entity instanceof EntityOtherPlayerMP) {
            String trimmed = event.entity.func_70005_c_().trim();
            if (PizzaClient.config.spiritBearAura && trimmed.equals("Spirit Bear")) {
               FakeRotater.leftClick(new Vec3(event.entity.field_70165_t, 69.5D, event.entity.field_70161_v));
               return;
            }

            if (PizzaClient.config.showHiddenMobs && trimmed.equals("Shadow Assassin")) {
               event.entity.func_82142_c(false);
               return;
            }

            if (PizzaClient.config.bloodTriggerBot && BLOOD_MOBS.contains(trimmed) && !Utils.isGuiOpen()) {
               if (PizzaClient.config.bloodTriggerBotAimbot) {
                  if (event.entity.func_70068_e(PizzaClient.mc.field_71439_g) < 500.0D) {
                     FakeRotater.leftClick(new Vec3(event.entity.field_70165_t, event.entity.field_70163_u + 0.2D, event.entity.field_70161_v));
                  }
               } else if (VecUtil.isFacingAABB(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u - 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u + 3.0D, event.entity.field_70161_v + 0.5D), 30.0F)) {
                  PlayerUtil.leftClick();
               }
            }
         }

      }
   }

   @SubscribeEvent
   public void onWorldLoad(WorldChangeEvent event) {
      lividEntity = null;
      blindnessDuration = 0L;
      starMobs.clear();
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void onFogDensity(FogDensity event) {
      if (PizzaClient.mc.field_71441_e != null) {
         if (PizzaClient.mc.field_71439_g.func_70644_a(Potion.field_76440_q) && (PizzaClient.config.lividEsp || PizzaClient.config.lividAura) && shouldLividsSpawn) {
            if (blindnessDuration == 0L) {
               blindnessDuration = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - blindnessDuration >= 600L) {
               getLivid();
            }
         }

         if (PizzaClient.config.antiBlindness) {
            event.density = 0.0F;
            event.setCanceled(true);
         }

      }
   }

   @SubscribeEvent
   public void onResizeWindow(ResizeWindowEvent event) {
      felsOutline.updateFramebuffer();
      minibossOutline.updateFramebuffer();
      RenderType.universalOutline.updateFramebuffer();
   }

   @SubscribeEvent
   public void onEntityDeath(LivingDeathEvent event) {
      if (starMobs.size() != 0) {
         starMobs.remove(event.entity);
      }

   }

   private static void getLivid() {
      EnumChatFormatting chatColor;
      switch((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(6, 109, 43)).func_177229_b(BlockStainedGlass.field_176547_a)) {
      case WHITE:
         chatColor = EnumChatFormatting.WHITE;
         lividColor = new Color(255, 250, 250);
         break;
      case PINK:
         chatColor = EnumChatFormatting.LIGHT_PURPLE;
         lividColor = Color.MAGENTA;
         break;
      case RED:
         chatColor = EnumChatFormatting.RED;
         lividColor = new Color(200, 0, 0);
         break;
      case SILVER:
         chatColor = EnumChatFormatting.GRAY;
         lividColor = new Color(180, 180, 180);
         break;
      case GRAY:
         chatColor = EnumChatFormatting.GRAY;
         lividColor = new Color(100, 100, 100);
         break;
      case GREEN:
         chatColor = EnumChatFormatting.DARK_GREEN;
         lividColor = new Color(34, 100, 34);
         break;
      case LIME:
         chatColor = EnumChatFormatting.GREEN;
         lividColor = new Color(0, 245, 0);
         break;
      case BLUE:
         chatColor = EnumChatFormatting.BLUE;
         lividColor = new Color(0, 0, 225);
         break;
      case PURPLE:
         chatColor = EnumChatFormatting.DARK_PURPLE;
         lividColor = new Color(128, 0, 160);
         break;
      case YELLOW:
         chatColor = EnumChatFormatting.YELLOW;
         lividColor = new Color(245, 245, 0);
         break;
      case MAGENTA:
         chatColor = EnumChatFormatting.LIGHT_PURPLE;
         lividColor = new Color(240, 0, 240);
         break;
      default:
         lividColor = null;
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.DARK_RED + "ERROR: " + EnumChatFormatting.RED + "Could not find the glass color! Please report this. The unknown Glass Color is: " + EnumChatFormatting.GOLD + ((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(205, 109, 242)).func_177229_b(BlockStainedGlass.field_176547_a)).name().toLowerCase()));
         return;
      }

      Iterator var1 = PizzaClient.mc.field_71441_e.func_72910_y().iterator();

      while(var1.hasNext()) {
         Entity entity = (Entity)var1.next();
         if (entity.func_70005_c_().startsWith(chatColor + "﴾ " + chatColor + "§lLivid")) {
            lividEntity = entity;
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > Real Livid: " + lividEntity.func_70005_c_()));
            shouldLividsSpawn = false;
            starMobs.clear();
            break;
         }
      }

   }

   public static void clipGhostBlocks() {
      Iterator var0 = Arrays.asList(new BlockPos(88, 167, 41), new BlockPos(89, 167, 41), new BlockPos(90, 167, 41), new BlockPos(91, 166, 40), new BlockPos(91, 166, 41), new BlockPos(91, 167, 40), new BlockPos(91, 167, 41), new BlockPos(92, 166, 40), new BlockPos(92, 166, 41), new BlockPos(92, 167, 40), new BlockPos(92, 167, 41), new BlockPos(93, 166, 40), new BlockPos(93, 166, 41), new BlockPos(93, 167, 40), new BlockPos(93, 167, 41), new BlockPos(94, 166, 40), new BlockPos(94, 166, 41), new BlockPos(94, 167, 41), new BlockPos(94, 167, 40), new BlockPos(95, 166, 40), new BlockPos(95, 166, 41), new BlockPos(95, 167, 41), new BlockPos(95, 167, 40), new BlockPos(96, 167, 41), new BlockPos(96, 167, 40)).iterator();

      while(var0.hasNext()) {
         BlockPos pos = (BlockPos)var0.next();
         PlayerUtil.ghostBlock(pos);
      }

   }

   public static void clipGhostBlocksToP5() {
      Iterator var0 = Arrays.asList(new BlockPos(54, 64, 80), new BlockPos(54, 64, 79), new BlockPos(54, 63, 79)).iterator();

      while(var0.hasNext()) {
         BlockPos pos = (BlockPos)var0.next();
         PlayerUtil.ghostBlock(pos);
      }

   }

   private void getStarredMobsWithinAABB(Entity entity) {
      AxisAlignedBB aabb = new AxisAlignedBB(entity.field_70165_t + 0.4D, entity.field_70163_u - 2.0D, entity.field_70161_v + 0.4D, entity.field_70165_t - 0.4D, entity.field_70163_u + 0.2D, entity.field_70161_v - 0.4D);
      int i = MathHelper.func_76128_c(aabb.field_72340_a - 1.0D) >> 4;
      int j = MathHelper.func_76128_c(aabb.field_72336_d + 1.0D) >> 4;
      int k = MathHelper.func_76128_c(aabb.field_72339_c - 1.0D) >> 4;
      int l = MathHelper.func_76128_c(aabb.field_72334_f + 1.0D) >> 4;

      for(int i1 = i; i1 <= j; ++i1) {
         for(int j1 = k; j1 <= l; ++j1) {
            this.getStarredMobsWithinAABBForEntity(PizzaClient.mc.field_71441_e.func_72964_e(i1, j1), entity, aabb);
         }
      }

   }

   private void getStarredMobsWithinAABBForEntity(Chunk chunk, Entity entityIn, AxisAlignedBB aabb) {
      ClassInheritanceMultiMap<Entity>[] entityLists = chunk.func_177429_s();
      int i = MathHelper.func_76128_c((aabb.field_72338_b - World.MAX_ENTITY_RADIUS) / 16.0D);
      int j = MathHelper.func_76128_c((aabb.field_72337_e + World.MAX_ENTITY_RADIUS) / 16.0D);
      i = MathHelper.func_76125_a(i, 0, entityLists.length - 1);
      j = MathHelper.func_76125_a(j, 0, entityLists.length - 1);

      label73:
      for(int k = i; k <= j; ++k) {
         if (!entityLists[k].isEmpty()) {
            Iterator var8 = entityLists[k].iterator();

            while(true) {
               while(true) {
                  Entity e;
                  do {
                     if (!var8.hasNext()) {
                        continue label73;
                     }

                     e = (Entity)var8.next();
                  } while(!e.func_174813_aQ().func_72326_a(aabb));

                  if (e instanceof EntityOtherPlayerMP) {
                     if (!(((EntityOtherPlayerMP)e).func_110143_aJ() <= 0.0F)) {
                        String var10 = e.func_70005_c_().trim();
                        byte var11 = -1;
                        switch(var10.hashCode()) {
                        case -658070465:
                           if (var10.equals("Diamond Guy")) {
                              var11 = 1;
                           }
                           break;
                        case 1317990878:
                           if (var10.equals("Lost Adventurer")) {
                              var11 = 0;
                           }
                        }

                        switch(var11) {
                        case 0:
                        case 1:
                           starMobs.put(entityIn, new EntityType((EntityLivingBase)e, 2));
                           break;
                        default:
                           if (!e.func_82150_aj() && e.func_110124_au().version() == 2) {
                              starMobs.put(entityIn, new EntityType((EntityLivingBase)e, 0));
                           }
                        }
                     }
                  } else if (!(e instanceof EntitySkeleton) && !(e instanceof EntityZombie)) {
                     if (e instanceof EntityEnderman && ((EntityEnderman)e).func_110143_aJ() > 0.0F) {
                        starMobs.put(entityIn, new EntityType((EntityLivingBase)e, 1));
                     }
                  } else if (!(((EntityMob)e).func_110143_aJ() <= 0.0F) && !e.func_82150_aj()) {
                     starMobs.put(entityIn, new EntityType((EntityLivingBase)e, 0));
                  }
               }
            }
         }
      }

   }

   static {
      felsOutline = new OutlineRenderer(Color.MAGENTA);
      minibossOutline = new OutlineRenderer(new Color(140, 20, 50));
      BLOOD_MOBS = new HashSet(Arrays.asList("Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "Bonzo", "Scarf", "Livid", "WanderingSoul"));
   }
}
