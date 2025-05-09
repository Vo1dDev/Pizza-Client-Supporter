package qolskyblockmod.pizzaclient.util;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Slot;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.mixins.mixin.accessor.AccessorRenderManager;
import qolskyblockmod.pizzaclient.util.shader.Shader;

public class RenderUtil {
   public static final RenderManager renderManager;
   public static final AccessorRenderManager accessorRender;
   private static final Frustum frustum;
   public static boolean skipEvent;
   public static ScaledResolution resolution;

   public static void drawFilledEsp(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179084_k();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void drawFilledEsp(AxisAlignedBB aabb, Color c) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledBoxNoESP(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledBoxNoESP(AxisAlignedBB aabb, Color c) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledBoxNoESPWithFrustum(AxisAlignedBB aabb, Color c) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
         drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
      }
   }

   public static void drawFilledEsp(BlockPos pos, Color c, float alphaMultiplier) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEspWithFrustum(BlockPos pos, Color c, float alphaMultiplier) {
      AxisAlignedBB aabb = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos);
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179097_i();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
         drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179126_j();
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
      }
   }

   public static void drawFilledEsp(BlockPos pos, Color c, float alphaMultiplier, float expand) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72314_b((double)expand, (double)expand, (double)expand).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEspWithFrustum(BlockPos pos, Color c, float alphaMultiplier, float expand) {
      AxisAlignedBB aabb = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72314_b((double)expand, (double)expand, (double)expand);
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179097_i();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
         drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179126_j();
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
      }
   }

   public static void drawFilledEsp(BlockPos pos, Color c) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEspWithFrustum(BlockPos pos, Color c) {
      AxisAlignedBB aabb = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos);
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179097_i();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
         drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179126_j();
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
      }
   }

   public static void drawOutlinedEsp(BlockPos pos, Color c, float width) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedEsp(BlockPos pos, Color c) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedEspWithFrustum(BlockPos pos, Color c, float width) {
      AxisAlignedBB aabb = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos);
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179097_i();
         GlStateManager.func_179140_f();
         GL11.glLineWidth(width);
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
         drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GL11.glLineWidth(1.0F);
         GlStateManager.func_179098_w();
         GlStateManager.func_179126_j();
      }
   }

   public static void drawOutlinedEspWithFrustum(BlockPos pos, Color c) {
      AxisAlignedBB aabb = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos);
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179097_i();
         GlStateManager.func_179140_f();
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
         drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179098_w();
         GlStateManager.func_179126_j();
      }
   }

   public static void drawOutlinedEsp(AxisAlignedBB aabb, Color c) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedEsp(AxisAlignedBB aabb, Color c, float width) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedBoxNoEsp(AxisAlignedBB aabb, Color c, float width) {
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GL11.glLineWidth(1.0F);
   }

   public static void drawOutlinedEspWithFrustum(AxisAlignedBB aabb, Color c, float width) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      if (frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f)) {
         GlStateManager.func_179097_i();
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GL11.glLineWidth(width);
         GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
         drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
         GlStateManager.func_179098_w();
         GL11.glLineWidth(1.0F);
         GlStateManager.func_179126_j();
      }
   }

   private static void drawOutlinedAABB(AxisAlignedBB boundingBox) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
   }

   private static void drawFullAABB(AxisAlignedBB aabb) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawOnSlot(Slot slot, int color) {
      Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color);
   }

   public static void drawOnSlot(Slot slot, Color color) {
      Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color.getRGB());
   }

   public static void drawRainbowPath(List<BlockPos> positions, float width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         BlockPos pos = (BlockPos)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 50)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.1D, (double)pos.func_177952_p() + 0.5D).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void drawRainbowLines(List<Vec3> positions) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         Vec3 pos = (Vec3)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 50)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void drawRainbowLines(List<Vec3> positions, float width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         Vec3 pos = (Vec3)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 50)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void draw3DLine(Vec3 end, Color color) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179124_c((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v).func_181675_d();
      worldRenderer.func_181662_b(end.field_72450_a, end.field_72448_b, end.field_72449_c).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
   }

   public static void draw3DLine(Vec3 end, Color color, int width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth((float)width);
      GlStateManager.func_179124_c((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v).func_181675_d();
      worldRenderer.func_181662_b(end.field_72450_a, end.field_72448_b, end.field_72449_c).func_181675_d();
      tessellator.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
   }

   public static void draw3DLine(Vec3 start, Vec3 end, Color color) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179124_c((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(start.field_72450_a, start.field_72448_b, start.field_72449_c).func_181675_d();
      worldRenderer.func_181662_b(end.field_72450_a, end.field_72448_b, end.field_72449_c).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
   }

   public static void draw3DLine(Vec3 start, Vec3 end, Color color, int width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth((float)width);
      GlStateManager.func_179124_c((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(start.field_72450_a, start.field_72448_b, start.field_72449_c).func_181675_d();
      worldRenderer.func_181662_b(end.field_72450_a, end.field_72448_b, end.field_72449_c).func_181675_d();
      tessellator.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
   }

   public static void drawQuad() {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, (float)resolution.func_78328_b());
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f((float)resolution.func_78326_a(), (float)resolution.func_78328_b());
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f((float)resolution.func_78326_a(), 0.0F);
      GL11.glEnd();
   }

   public static void drawQuad(ScaledResolution sr) {
      GL11.glBegin(5);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(0.0F, (float)sr.func_78328_b());
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f((float)sr.func_78326_a(), 0.0F);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f((float)sr.func_78326_a(), (float)sr.func_78328_b());
      GL11.glEnd();
   }

   public static void bindFramebufferTexture() {
      GlStateManager.func_179144_i(PizzaClient.mc.func_147110_a().field_147617_g);
   }

   public static void resetColor() {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void updateFramebuffers() {
      Shader.updateFramebuffer();
   }

   public static Framebuffer updateFramebuffer(Framebuffer framebuffer) {
      if (framebuffer.field_147621_c == PizzaClient.mc.field_71443_c && framebuffer.field_147618_d == PizzaClient.mc.field_71440_d) {
         framebuffer.func_147614_f();
         return framebuffer;
      } else {
         framebuffer.func_147608_a();
         return new Framebuffer(PizzaClient.mc.field_71443_c, PizzaClient.mc.field_71440_d, true);
      }
   }

   public static Framebuffer updateFramebuffer(Framebuffer framebuffer, int customWidth, int customHeight) {
      if (framebuffer.field_147621_c == customWidth && framebuffer.field_147618_d == customHeight) {
         framebuffer.func_147614_f();
         return framebuffer;
      } else {
         framebuffer.func_147608_a();
         return new Framebuffer(customWidth, customHeight, true);
      }
   }

   public static void startESP() {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
   }

   public static void endESP() {
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void startChams() {
      GlStateManager.func_179136_a(1.0F, -2000000.0F);
      GlStateManager.func_179088_q();
      GlStateManager.func_179140_f();
   }

   public static void endChams() {
      GlStateManager.func_179136_a(0.0F, 0.0F);
      GlStateManager.func_179113_r();
   }

   public static void startESPWithBlend() {
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
   }

   public static void endESPWithBlend() {
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179084_k();
   }

   public static boolean isInLineOfSight(Entity ent) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      return frustum.func_78546_a(ent.func_174813_aQ());
   }

   public static boolean isInLineOfSight(AxisAlignedBB aabb) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      return frustum.func_78548_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f);
   }

   public static boolean isInLineOfSight(BlockPos pos) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      return frustum.func_78546_a(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos));
   }

   public static boolean isInLineOfSight(Vec3 pos) {
      Entity e = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(e.field_70165_t, e.field_70163_u, e.field_70161_v);
      return frustum.func_78548_b(pos.field_72450_a - 0.01D, pos.field_72448_b - 0.01D, pos.field_72449_c - 0.01D, pos.field_72450_a + 0.01D, pos.field_72448_b + 0.01D, pos.field_72449_c + 0.01D);
   }

   public static void renderEntityNoShadowNoEvent(Entity entity, float partialTicks) {
      if (entity.field_70173_aa == 0) {
         entity.field_70142_S = entity.field_70165_t;
         entity.field_70137_T = entity.field_70163_u;
         entity.field_70136_U = entity.field_70161_v;
      }

      int i = entity.func_70070_b(partialTicks);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)(i % 65536), (float)(i / 65536));
      doRenderEntityNoShadow(entity, entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderManager.field_78730_l, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderManager.field_78731_m, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderManager.field_78728_n, entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks, partialTicks);
   }

   public static void renderLivingEntityNoShadowNoEvent(Entity entity, float partialTicks) {
      if (entity.field_70173_aa == 0) {
         entity.field_70142_S = entity.field_70165_t;
         entity.field_70137_T = entity.field_70163_u;
         entity.field_70136_U = entity.field_70161_v;
      }

      int i = entity.func_70070_b(partialTicks);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)(i % 65536), (float)(i / 65536));
      doRenderLivingEntityNoShadow(entity, entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderManager.field_78730_l, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderManager.field_78731_m, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderManager.field_78728_n, entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks, partialTicks);
   }

   public static double getViewerPosX(float partialTicks) {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      return viewer.field_70142_S + (viewer.field_70165_t - viewer.field_70142_S) * (double)partialTicks;
   }

   public static double getViewerPosY(float partialTicks) {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      return viewer.field_70137_T + (viewer.field_70163_u - viewer.field_70137_T) * (double)partialTicks;
   }

   public static double getViewerPosZ(float partialTicks) {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      return viewer.field_70136_U + (viewer.field_70161_v - viewer.field_70136_U) * (double)partialTicks;
   }

   public static void doRenderEntityNoShadow(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      Render<Entity> render = renderManager.func_78713_a(entity);
      if (render instanceof RendererLivingEntity) {
         ((RendererLivingEntity)render).func_177086_a(accessorRender.getRenderOutlines());
      }

      render.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
   }

   public static void doRenderLivingEntityNoShadow(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      Render<Entity> render = renderManager.func_78713_a(entity);
      ((RendererLivingEntity)render).func_177086_a(accessorRender.getRenderOutlines());
      render.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
   }

   public static Frustum setupFrustrum() {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(viewer.field_70165_t, viewer.field_70163_u, viewer.field_70161_v);
      return frustum;
   }

   public static Frustum setupFirstPersonFrustrum() {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(viewer.field_70165_t, viewer.field_70163_u, viewer.field_70161_v);
      return frustum;
   }

   public static Frustum createFrustrum() {
      Entity viewer = PizzaClient.mc.func_175606_aa();
      frustum.func_78547_a(viewer.field_70165_t, viewer.field_70163_u, viewer.field_70161_v);
      return frustum;
   }

   public static FloatBuffer createFloatBuffer(float[] positions) {
      FloatBuffer buffer = BufferUtils.createFloatBuffer(positions.length);
      buffer.put(positions);
      buffer.flip();
      return buffer;
   }

   public static void color(int color) {
      GlStateManager.func_179124_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F);
   }

   public static void colorRainbow() {
      int color = Color.HSBtoRGB((float)(System.currentTimeMillis() % 3500L) / 3500.0F, PizzaClient.config.rgbBrightness, 1.0F);
      GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 0.4F);
   }

   static {
      renderManager = PizzaClient.mc.func_175598_ae();
      accessorRender = (AccessorRenderManager)renderManager;
      frustum = new Frustum();
      resolution = new ScaledResolution(PizzaClient.mc);
   }
}
