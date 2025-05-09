package qolskyblockmod.pizzaclient.util.shader.shaders;

import net.minecraft.client.renderer.GlStateManager;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.shader.Shader;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class BlurShader extends Shader {
   public static final BlurShader instance = new BlurShader();
   public final int offset_Location = this.getUniformLocation("offset");

   public BlurShader() {
      super("defaultVertex", "blurFragment");
   }

   public static void renderBlurryBackground() {
      framebuffer.clearAndBindFramebuffer();
      GlStateManager.func_179144_i(PizzaClient.mc.func_147110_a().field_147617_g);
      instance.renderFirst();
      RenderUtil.drawQuad();
      GlStateManager.func_179144_i(framebuffer.framebufferTexture);
      PizzaClient.mc.func_147110_a().func_147610_a(true);
      instance.renderSecond();
      RenderUtil.drawQuad();
      endCurrentShader();
   }

   public void renderFirst() {
      this.useShader();
      ShaderUtil.glUniform2f(this.offset_Location, 0.0F, 1.0F / (float)PizzaClient.mc.field_71440_d);
   }

   public void renderSecond() {
      ShaderUtil.glUniform2f(this.offset_Location, 1.0F / (float)PizzaClient.mc.field_71443_c, 0.0F);
   }
}
