package qolskyblockmod.pizzaclient.features.skills;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.WorldChangeEvent;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiElement;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiLocation;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiManager;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.FontUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.render.RenderType;
import qolskyblockmod.pizzaclient.util.shader.shaders.TintShader;

public class MiningFeatures {
   private static boolean sayCoordsBal = true;
   protected static boolean sayCoordsFairyGrotto = true;
   private static boolean sayCoordsCorleone = true;
   public static boolean foundCorleone = false;
   protected static final ArrayList<String> miningCoords = new ArrayList();

   public MiningFeatures() {
      new MiningFeatures.MiningElement();
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public void onRenderEntity(Pre<EntityLivingBase> event) {
      if (PizzaClient.location == Locations.CHOLLOWS || PizzaClient.location == Locations.DWARVENMINES) {
         double x = event.entity.field_70165_t;
         double y = event.entity.field_70163_u;
         double z = event.entity.field_70161_v;
         if (event.entity instanceof EntityArmorStand) {
            EntityArmorStand entity = (EntityArmorStand)event.entity;
            if (!entity.func_145818_k_()) {
               return;
            }

            String entityName = StringUtils.func_76338_a(entity.func_95999_t());
            if (PizzaClient.config.balEsp && entityName.equals("[Lv100] Bal ???❤")) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 4.0D, y - 10.0D, z - 4.0D, x + 4.0D, y, z + 4.0D), Color.RED, 5.0F);
               if (sayCoordsBal) {
                  Utils.playSound("random.orb", 0.5D);
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Bal in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                  miningCoords.add("§cBal: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                  sayCoordsBal = false;
               }

               return;
            }

            if (PizzaClient.config.corleonePing && entityName.startsWith("[Lv200] Boss Corleone ") && sayCoordsCorleone) {
               Utils.playSound("random.orb", 0.5D);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Boss Corleone in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
               miningCoords.add("§bBoss Corleone: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
               sayCoordsCorleone = false;
            }
         } else if (event.entity instanceof EntityOtherPlayerMP) {
            String entityName = event.entity.func_70005_c_();
            if (PizzaClient.config.teamTresuriteEsp && entityName.equals("Team Treasurite")) {
               RenderType.renderTintChams();
               TintShader.instance.applyTint(new Color(255, 255, 51, 191));
               event.setCanceled(false);
               return;
            }

            if (PizzaClient.config.goblinEsp) {
               if (entityName.startsWith("Weakling")) {
                  RenderType.renderTintChams();
                  TintShader.instance.applyTint(new Color(130, 67, 0, 191));
                  event.setCanceled(false);
                  return;
               }

               if (entityName.startsWith("Goblin")) {
                  RenderType.renderTintChams();
                  TintShader.instance.applyTint(new Color(130, 67, 0, 191));
                  event.setCanceled(false);
                  return;
               }
            }

            if (PizzaClient.config.hideGoldenGoblin && entityName.startsWith("Goblin") && Utils.isGoldenGoblin((EntityOtherPlayerMP)event.entity)) {
               PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
               event.setCanceled(true);
            }
         } else if (event.entity instanceof EntitySlime) {
            if (event.entity instanceof EntityMagmaCube) {
               if (PizzaClient.config.yogEsp) {
                  RenderType.renderTintChams();
                  TintShader.instance.applyTint(new Color(175, 34, 34, 191));
                  event.setCanceled(false);
               }
            } else if (PizzaClient.config.sludgeEsp) {
               RenderType.renderTintChams();
               TintShader.instance.applyTint(new Color(50, 200, 50, 191));
               event.setCanceled(false);
            }
         } else if (event.entity instanceof EntityIronGolem && PizzaClient.config.ironGolemEsp) {
            RenderType.renderTintChams();
            TintShader.instance.applyTint(new Color(0, 0, 111, 191));
            event.setCanceled(false);
         }
      }

   }

   @SubscribeEvent
   public void onWorldLoad(WorldChangeEvent event) {
      sayCoordsBal = true;
      sayCoordsFairyGrotto = true;
      sayCoordsCorleone = true;
      foundCorleone = false;
      miningCoords.clear();
      WorldScanner.markedBlocks.clear();
   }

   public static class MiningElement extends GuiElement {
      public MiningElement() {
         super(new GuiLocation(100, 30));
         GuiManager.registerElement(this);
      }

      public String getName() {
         return "Mining List";
      }

      public void render() {
         if (PizzaClient.config.coordsList && PizzaClient.location == Locations.CHOLLOWS) {
            for(int i = 0; i < MiningFeatures.miningCoords.size(); ++i) {
               FontUtil.drawString((String)MiningFeatures.miningCoords.get(i), this.getActualX(), this.getActualY() + (float)(i * PizzaClient.mc.field_71466_p.field_78288_b), 16777215);
            }
         }

      }

      public void demoRender() {
         String[] locations = new String[]{"§dFairy Grotto: §ax 1000, y 256, z 1000", "§bBoss Corleone: §ax 1000, y 256, z 1000", "§cBal: §ax 1000, y 256, z 1000"};

         for(int i = 0; i < locations.length; ++i) {
            FontUtil.drawString(locations[i], this.getActualX(), this.getActualY() + (float)(i * PizzaClient.mc.field_71466_p.field_78288_b), 16777215);
         }

      }

      public boolean isToggled() {
         return PizzaClient.config.coordsList && PizzaClient.location == Locations.CHOLLOWS;
      }

      public int getHeight() {
         return PizzaClient.mc.field_71466_p.field_78288_b * 3;
      }

      public int getWidth() {
         return PizzaClient.mc.field_71466_p.func_78256_a("Boss Corleone: x -1000, y 200, z -1000 ");
      }
   }
}
