package qolskyblockmod.pizzaclient.util.shader.shaders;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.shader.Shader;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class OutlineShader extends Shader {
   public static final OutlineShader instance = new OutlineShader();
   public final int offset_location;

   public OutlineShader() {
      super("defaultVertex", "outlineFragment");
      this.offset_location = ShaderUtil.glGetUniformLocation(this.program, (CharSequence)"offset");
   }

   public void renderFirst() {
      this.useShader();
      ShaderUtil.glUniform2f(this.offset_location, 0.0F, 1.0F / (float)PizzaClient.mc.field_71440_d);
   }

   public void renderSecond() {
      ShaderUtil.glUniform2f(this.offset_location, 1.0F / (float)PizzaClient.mc.field_71443_c, 0.0F);
   }
}
