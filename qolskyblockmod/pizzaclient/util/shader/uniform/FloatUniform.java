package qolskyblockmod.pizzaclient.util.shader.uniform;

import net.minecraft.client.renderer.OpenGlHelper;
import qolskyblockmod.pizzaclient.util.misc.runnables.FloatSupplier;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class FloatUniform implements IUniform {
   public final FloatSupplier supplier;
   public final int id;
   public float lastValue;

   public FloatUniform(int program, String name, FloatSupplier supplier) {
      this.id = OpenGlHelper.func_153194_a(program, name);
      this.supplier = supplier;
   }

   public void update() {
      float current = this.supplier.get();
      if (current != this.lastValue) {
         ShaderUtil.glUniform1f(this.id, current);
         this.lastValue = current;
      }

   }
}
