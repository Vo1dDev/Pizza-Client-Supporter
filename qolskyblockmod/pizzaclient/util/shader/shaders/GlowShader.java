package qolskyblockmod.pizzaclient.util.shader.shaders;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.shader.Shader;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class GlowShader extends Shader {
   public static final GlowShader instance = new GlowShader();
   public final int color_location;
   public final int offset_location;
   public final int yOffset_location;

   public GlowShader() {
      super("defaultVertex", "glowFragment");
      this.color_location = ShaderUtil.glGetUniformLocation(this.program, (CharSequence)"color");
      this.offset_location = ShaderUtil.glGetUniformLocation(this.program, (CharSequence)"offset");
      this.yOffset_location = ShaderUtil.glGetUniformLocation(this.program, (CharSequence)"yOffset");
   }

   public void renderFirst(int color) {
      this.useShader();
      ShaderUtil.glUniform3f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F);
      ShaderUtil.glUniform2f(this.offset_location, 1.0F / (float)PizzaClient.mc.field_71443_c, 0.0F);
      ShaderUtil.glUniform1i(this.yOffset_location, 0);
   }

   public void renderSecond() {
      ShaderUtil.glUniform2f(this.offset_location, 0.0F, 1.0F / (float)PizzaClient.mc.field_71440_d);
      ShaderUtil.glUniform1i(this.yOffset_location, 1);
   }
}
