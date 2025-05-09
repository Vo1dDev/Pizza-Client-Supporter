package qolskyblockmod.pizzaclient.util.shader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.timer.TickTimer;
import qolskyblockmod.pizzaclient.util.shader.uniform.FloatUniform;
import qolskyblockmod.pizzaclient.util.shader.uniform.IUniform;

public abstract class ChromaShader extends Shader {
   public final List<IUniform> uniforms = new ArrayList();
   private static float chromaSize = 35.0F;

   public ChromaShader(String vertex, String fragment) {
      super(vertex, fragment);
      this.registerUniforms();
   }

   public ChromaShader(String location) {
      super(location, location);
      this.registerUniforms();
   }

   private void registerUniforms() {
      this.uniforms.add(new FloatUniform(this.program, "chromaSize", () -> {
         return chromaSize * ((float)PizzaClient.mc.field_71443_c / 100.0F);
      }));
      this.uniforms.add(new FloatUniform(this.program, "timeOffset", () -> {
         return ((float)TickTimer.ticks + Utils.getPartialTicks()) / 65.0F;
      }));
      this.uniforms.add(new FloatUniform(this.program, "saturation", () -> {
         return PizzaClient.config.rgbBrightness;
      }));
   }

   public void applyShader() {
      this.useShader();
      Iterator var1 = this.uniforms.iterator();

      while(var1.hasNext()) {
         IUniform uniform = (IUniform)var1.next();
         uniform.update();
      }

   }

   public static void endShaderResetSize() {
      endCurrentShader();
      chromaSize = 35.0F;
   }

   public static void setSize(float size) {
      chromaSize = size;
   }

   public static void scaleSize(float scale) {
      chromaSize = 35.0F * scale;
   }

   public static void resetSize() {
      chromaSize = 35.0F;
   }
}
