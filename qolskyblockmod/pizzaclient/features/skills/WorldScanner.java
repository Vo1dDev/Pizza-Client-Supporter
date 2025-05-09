package qolskyblockmod.pizzaclient.features.skills;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.BlockChangeEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.misc.timer.TickTimer;

public class WorldScanner {
   public static Map<BlockPos, Color> markedBlocks = new ConcurrentHashMap();
   public static final Color GEM_RED = new Color(200, 15, 45);
   public static final Color GEM_LIME = new Color(160, 255, 47);
   public static final Color GEM_ORANGE = new Color(255, 110, 0);
   public static final Color GEM_YELLOW = new Color(255, 210, 0);
   public static final Color GEM_LIGHTBLUE = new Color(102, 178, 255);
   public static final Color GEM_PURPLE = new Color(153, 50, 204);
   public static final Color GEM_MAGENTA = new Color(250, 0, 250);
   public static final Color GEM_OPAL = new Color(240, 247, 255);

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.location == Locations.CHOLLOWS) {
         if (PizzaClient.config.gemstoneScanner && TickTimer.ticks % (PizzaClient.config.scannerDelay * 20) == 0) {
            (new Thread(() -> {
               int pY = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u);
               int belowY = pY - PizzaClient.config.scannerWidth;
               int aboveY = pY + PizzaClient.config.scannerWidth;
               if (belowY < 31) {
                  belowY = 31;
               }

               if (aboveY > 189) {
                  aboveY = 189;
               }

               Map<BlockPos, Color> foundBlocks = new ConcurrentHashMap();

               for(int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t) - PizzaClient.config.scannerWidth; (double)x < PizzaClient.mc.field_71439_g.field_70165_t + (double)PizzaClient.config.scannerWidth; ++x) {
                  for(int y = belowY; y < aboveY; ++y) {
                     for(int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v) - PizzaClient.config.scannerWidth; (double)z < PizzaClient.mc.field_71439_g.field_70161_v + (double)PizzaClient.config.scannerWidth; ++z) {
                        BlockPos pos = new BlockPos(x, y, z);
                        IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                        if (state.func_177230_c() == Blocks.field_150399_cn) {
                           switch((EnumDyeColor)state.func_177229_b(BlockStainedGlass.field_176547_a)) {
                           case RED:
                              if (PizzaClient.config.redGlassEsp) {
                                 foundBlocks.put(pos, GEM_RED);
                              }
                              break;
                           case LIME:
                              if (PizzaClient.config.limeGlassEsp) {
                                 foundBlocks.put(pos, GEM_LIME);
                              }
                              break;
                           case ORANGE:
                              if (PizzaClient.config.orangeGlassEsp) {
                                 foundBlocks.put(pos, GEM_ORANGE);
                              }
                              break;
                           case YELLOW:
                              if (PizzaClient.config.yellowGlassEsp) {
                                 foundBlocks.put(pos, GEM_YELLOW);
                              }
                              break;
                           case LIGHT_BLUE:
                              if (PizzaClient.config.lightBlueGlassEsp) {
                                 foundBlocks.put(pos, GEM_LIGHTBLUE);
                              }
                              break;
                           case PURPLE:
                              if (PizzaClient.config.purpleGlassEsp) {
                                 foundBlocks.put(pos, GEM_PURPLE);
                              }
                              break;
                           case MAGENTA:
                              if (PizzaClient.config.pinkGlassEsp) {
                                 foundBlocks.put(pos, GEM_MAGENTA);
                              }

                              if (MiningFeatures.sayCoordsFairyGrotto) {
                                 Utils.playSound("random.orb", 0.5D);
                                 PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Fairy Grotto in: " + EnumChatFormatting.RED + "X = " + x + ", Y = " + y + ", Z = " + z));
                                 MiningFeatures.miningCoords.add("§dFairy Grotto: §ax " + x + ", y " + y + ", z " + z);
                                 MiningFeatures.sayCoordsFairyGrotto = false;
                              }
                           }
                        } else if (state.func_177230_c() == Blocks.field_150397_co && PizzaClient.config.glassPanesEsp) {
                           switch((EnumDyeColor)state.func_177229_b(BlockStainedGlassPane.field_176245_a)) {
                           case RED:
                              if (PizzaClient.config.redGlassEsp) {
                                 foundBlocks.put(pos, GEM_RED);
                              }
                              break;
                           case LIME:
                              if (PizzaClient.config.limeGlassEsp) {
                                 foundBlocks.put(pos, GEM_LIME);
                              }
                              break;
                           case ORANGE:
                              if (PizzaClient.config.orangeGlassEsp) {
                                 foundBlocks.put(pos, GEM_ORANGE);
                              }
                              break;
                           case YELLOW:
                              if (PizzaClient.config.yellowGlassEsp) {
                                 foundBlocks.put(pos, GEM_YELLOW);
                              }
                              break;
                           case LIGHT_BLUE:
                              if (PizzaClient.config.lightBlueGlassEsp) {
                                 foundBlocks.put(pos, GEM_LIGHTBLUE);
                              }
                              break;
                           case PURPLE:
                              if (PizzaClient.config.purpleGlassEsp) {
                                 foundBlocks.put(pos, GEM_PURPLE);
                              }
                              break;
                           case MAGENTA:
                              if (PizzaClient.config.pinkGlassEsp) {
                                 foundBlocks.put(pos, GEM_MAGENTA);
                              }
                           }
                        }
                     }
                  }
               }

               markedBlocks = foundBlocks;
            })).start();
         }
      } else if (PizzaClient.location == Locations.CRIMSON_ISLE && (PizzaClient.config.whiteGlassEsp || PizzaClient.config.sulphurEsp) && TickTimer.ticks % (PizzaClient.config.scannerDelay * 20) == 0) {
         (new Thread(() -> {
            int pY = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u);
            int belowY = pY - PizzaClient.config.scannerWidth;
            int aboveY = pY + PizzaClient.config.scannerWidth;
            if (belowY < 30) {
               belowY = 30;
            }

            if (aboveY > 250) {
               aboveY = 250;
            }

            Map<BlockPos, Color> foundBlocks = new ConcurrentHashMap();

            for(int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t) - PizzaClient.config.scannerWidth; (double)x < PizzaClient.mc.field_71439_g.field_70165_t + (double)PizzaClient.config.scannerWidth; ++x) {
               for(int y = belowY; y < aboveY; ++y) {
                  for(int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v) - PizzaClient.config.scannerWidth; (double)z < PizzaClient.mc.field_71439_g.field_70161_v + (double)PizzaClient.config.scannerWidth; ++z) {
                     BlockPos pos = new BlockPos(x, y, z);
                     IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                     if (PizzaClient.config.whiteGlassEsp) {
                        if (state.func_177230_c() == Blocks.field_150399_cn) {
                           if (state.func_177229_b(BlockStainedGlass.field_176547_a) == EnumDyeColor.SILVER) {
                              foundBlocks.put(pos, GEM_OPAL);
                           }
                        } else if (state.func_177230_c() == Blocks.field_150397_co && PizzaClient.config.glassPanesEsp && state.func_177229_b(BlockStainedGlassPane.field_176245_a) == EnumDyeColor.WHITE) {
                           foundBlocks.put(pos, GEM_OPAL);
                        }
                     }

                     if (PizzaClient.config.sulphurEsp && state.func_177230_c() == Blocks.field_150360_v) {
                        foundBlocks.put(pos, GEM_YELLOW);
                     }
                  }
               }
            }

            markedBlocks = foundBlocks;
         })).start();
      }

   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (!markedBlocks.isEmpty()) {
         Iterator var2 = markedBlocks.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<BlockPos, Color> map = (Entry)var2.next();
            RenderUtil.drawOutlinedEspWithFrustum((BlockPos)map.getKey(), (Color)map.getValue(), (float)PizzaClient.config.gemstoneEspSize / 16.0F);
         }
      }

   }

   @SubscribeEvent
   public void onBlockChange(BlockChangeEvent event) {
      if (!markedBlocks.isEmpty()) {
         markedBlocks.remove(event.pos);
      }

   }

   public static Color getGemstoneColor(BlockPos pos) {
      IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
      if (state.func_177230_c() == Blocks.field_150399_cn) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlassPane.field_176245_a)) {
         case RED:
            return GEM_RED;
         case LIME:
            return GEM_LIME;
         case ORANGE:
            return GEM_ORANGE;
         case YELLOW:
            return GEM_YELLOW;
         case LIGHT_BLUE:
            return GEM_LIGHTBLUE;
         case PURPLE:
            return GEM_PURPLE;
         case MAGENTA:
            return GEM_MAGENTA;
         case SILVER:
            return GEM_OPAL;
         }
      } else if (state.func_177230_c() == Blocks.field_150397_co) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlassPane.field_176245_a)) {
         case RED:
            return GEM_RED;
         case LIME:
            return GEM_LIME;
         case ORANGE:
            return GEM_ORANGE;
         case YELLOW:
            return GEM_YELLOW;
         case LIGHT_BLUE:
            return GEM_LIGHTBLUE;
         case PURPLE:
            return GEM_PURPLE;
         case MAGENTA:
            return GEM_MAGENTA;
         case SILVER:
         default:
            break;
         case WHITE:
            return GEM_OPAL;
         }
      }

      return Color.BLACK;
   }

   public static int getGemstoneType(BlockPos pos) {
      IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
      if (state.func_177230_c() == Blocks.field_150399_cn) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlass.field_176547_a)) {
         case RED:
            return 1;
         case LIME:
            return 2;
         case ORANGE:
            return 3;
         case YELLOW:
            return 4;
         case LIGHT_BLUE:
            return 5;
         case PURPLE:
            return 6;
         case MAGENTA:
            return 7;
         case SILVER:
            return 8;
         }
      } else if (state.func_177230_c() == Blocks.field_150397_co) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlass.field_176547_a)) {
         case RED:
            return 1;
         case LIME:
            return 2;
         case ORANGE:
            return 3;
         case YELLOW:
            return 4;
         case LIGHT_BLUE:
            return 5;
         case PURPLE:
            return 6;
         case MAGENTA:
            return 7;
         case SILVER:
         default:
            break;
         case WHITE:
            return 8;
         }
      }

      return -1;
   }

   public static int getGemstoneType(IBlockState state) {
      if (state.func_177230_c() == Blocks.field_150399_cn) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlass.field_176547_a)) {
         case RED:
            return 1;
         case LIME:
            return 2;
         case ORANGE:
            return 3;
         case YELLOW:
            return 4;
         case LIGHT_BLUE:
            return 5;
         case PURPLE:
            return 6;
         case MAGENTA:
            return 7;
         case SILVER:
            return 8;
         }
      } else if (state.func_177230_c() == Blocks.field_150397_co) {
         switch((EnumDyeColor)state.func_177229_b(BlockStainedGlass.field_176547_a)) {
         case RED:
            return 1;
         case LIME:
            return 2;
         case ORANGE:
            return 3;
         case YELLOW:
            return 4;
         case LIGHT_BLUE:
            return 5;
         case PURPLE:
            return 6;
         case MAGENTA:
            return 7;
         case SILVER:
         default:
            break;
         case WHITE:
            return 8;
         }
      }

      return -1;
   }
}
