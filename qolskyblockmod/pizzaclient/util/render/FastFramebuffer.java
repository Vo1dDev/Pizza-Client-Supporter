package qolskyblockmod.pizzaclient.util.render;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class FastFramebuffer {
   public int framebufferTextureWidth;
   public int framebufferTextureHeight;
   public int framebufferWidth;
   public int framebufferHeight;
   public int framebufferObject;
   public int framebufferTexture;
   public int depthBuffer;
   public final float[] framebufferColor;
   public int framebufferFilter;

   public FastFramebuffer(int p_i45078_1_, int p_i45078_2_) {
      this.framebufferColor = new float[4];
      this.framebufferObject = -1;
      this.framebufferTexture = -1;
      this.depthBuffer = -1;
      this.framebufferColor[0] = 1.0F;
      this.framebufferColor[1] = 1.0F;
      this.framebufferColor[2] = 1.0F;
      this.framebufferColor[3] = 0.0F;
      this.createBindFramebuffer(p_i45078_1_, p_i45078_2_);
   }

   public FastFramebuffer() {
      this(PizzaClient.mc.field_71443_c, PizzaClient.mc.field_71440_d);
   }

   public void createBindFramebuffer(int width, int height) {
      GlStateManager.func_179126_j();
      if (this.framebufferObject >= 0) {
         this.deleteFramebuffer();
      }

      this.createFramebuffer(width, height);
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
   }

   public void deleteFramebuffer() {
      GlStateManager.func_179144_i(0);
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
      if (this.depthBuffer > -1) {
         OpenGlHelper.func_153184_g(this.depthBuffer);
         this.depthBuffer = -1;
      }

      if (this.framebufferTexture > -1) {
         GlStateManager.func_179150_h(this.framebufferTexture);
         this.framebufferTexture = -1;
      }

      if (this.framebufferObject > -1) {
         OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
         OpenGlHelper.func_153174_h(this.framebufferObject);
         this.framebufferObject = -1;
      }

   }

   public void createFramebuffer(int width, int height) {
      this.framebufferWidth = width;
      this.framebufferHeight = height;
      this.framebufferTextureWidth = width;
      this.framebufferTextureHeight = height;
      this.framebufferObject = OpenGlHelper.func_153165_e();
      this.framebufferTexture = GL11.glGenTextures();
      this.depthBuffer = OpenGlHelper.func_153185_f();
      this.setFramebufferFilter(9728);
      GlStateManager.func_179144_i(this.framebufferTexture);
      GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer)null);
      switch(ShaderUtil.framebufferType) {
      case 0:
         GL30.glBindFramebuffer(OpenGlHelper.field_153198_e, this.framebufferObject);
         GL30.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, this.framebufferTexture, 0);
         GL30.glBindRenderbuffer(OpenGlHelper.field_153199_f, this.depthBuffer);
         GL30.glRenderbufferStorage(OpenGlHelper.field_153199_f, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
         GL30.glFramebufferRenderbuffer(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, this.depthBuffer);
         break;
      case 1:
         ARBFramebufferObject.glBindFramebuffer(OpenGlHelper.field_153198_e, this.framebufferObject);
         ARBFramebufferObject.glFramebufferTexture2D(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, this.framebufferTexture, 0);
         ARBFramebufferObject.glBindRenderbuffer(OpenGlHelper.field_153199_f, this.depthBuffer);
         ARBFramebufferObject.glRenderbufferStorage(OpenGlHelper.field_153199_f, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
         ARBFramebufferObject.glFramebufferRenderbuffer(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, this.depthBuffer);
         break;
      case 2:
         EXTFramebufferObject.glBindFramebufferEXT(OpenGlHelper.field_153198_e, this.framebufferObject);
         EXTFramebufferObject.glFramebufferTexture2DEXT(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, this.framebufferTexture, 0);
         EXTFramebufferObject.glBindRenderbufferEXT(OpenGlHelper.field_153199_f, this.depthBuffer);
         EXTFramebufferObject.glRenderbufferStorageEXT(OpenGlHelper.field_153199_f, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
         EXTFramebufferObject.glFramebufferRenderbufferEXT(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, this.depthBuffer);
      }

      this.framebufferClear();
      GlStateManager.func_179144_i(0);
   }

   public void setFramebufferFilter(int p_147607_1_) {
      this.framebufferFilter = p_147607_1_;
      GlStateManager.func_179144_i(this.framebufferTexture);
      GL11.glTexParameterf(3553, 10241, (float)p_147607_1_);
      GL11.glTexParameterf(3553, 10240, (float)p_147607_1_);
      GL11.glTexParameterf(3553, 10242, 10496.0F);
      GL11.glTexParameterf(3553, 10243, 10496.0F);
      GlStateManager.func_179144_i(0);
   }

   public void bindFramebufferTexture() {
      GlStateManager.func_179144_i(this.framebufferTexture);
   }

   public void unbindFramebufferTexture() {
      GlStateManager.func_179144_i(0);
   }

   public void bindFramebuffer() {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
      GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
   }

   public void clearAndBindFramebuffer() {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
      GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
      GlStateManager.func_179082_a(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
      GlStateManager.func_179151_a(1.0D);
      GL11.glClear(16640);
   }

   public void unbindFramebuffer() {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
   }

   public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_) {
      this.framebufferColor[0] = p_147604_1_;
      this.framebufferColor[1] = p_147604_2_;
      this.framebufferColor[2] = p_147604_3_;
      this.framebufferColor[3] = p_147604_4_;
   }

   public void framebufferRender(int p_147615_1_, int p_147615_2_) {
      this.framebufferRenderExt(p_147615_1_, p_147615_2_, true);
   }

   public void framebufferRenderExt(int p_178038_1_, int p_178038_2_, boolean p_178038_3_) {
      GlStateManager.func_179135_a(true, true, true, false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)p_178038_1_, (double)p_178038_2_, 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glViewport(0, 0, p_178038_1_, p_178038_2_);
      GlStateManager.func_179098_w();
      GlStateManager.func_179140_f();
      GlStateManager.func_179118_c();
      if (p_178038_3_) {
         GlStateManager.func_179084_k();
         GlStateManager.func_179142_g();
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179144_i(this.framebufferTexture);
      float f = (float)p_178038_1_;
      float f1 = (float)p_178038_2_;
      float f2 = (float)this.framebufferWidth / (float)this.framebufferTextureWidth;
      float f3 = (float)this.framebufferHeight / (float)this.framebufferTextureHeight;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      worldrenderer.func_181662_b(0.0D, (double)f1, 0.0D).func_181673_a(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      worldrenderer.func_181662_b((double)f, (double)f1, 0.0D).func_181673_a((double)f2, 0.0D).func_181669_b(255, 255, 255, 255).func_181675_d();
      worldrenderer.func_181662_b((double)f, 0.0D, 0.0D).func_181673_a((double)f2, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
      worldrenderer.func_181662_b(0.0D, 0.0D, 0.0D).func_181673_a(0.0D, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
      tessellator.func_78381_a();
      this.unbindFramebufferTexture();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179135_a(true, true, true, true);
   }

   public void framebufferClear() {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
      GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
      GlStateManager.func_179082_a(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
      GlStateManager.func_179151_a(1.0D);
      GL11.glClear(16640);
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
   }

   public void updateFramebuffer() {
      this.createBindFramebuffer(PizzaClient.mc.field_71443_c, PizzaClient.mc.field_71440_d);
   }
}
