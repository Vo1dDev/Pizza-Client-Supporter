package qolskyblockmod.pizzaclient.util.render;

import java.awt.Color;
import net.minecraft.entity.Entity;

public class OutlineRenderer {
   public final FastFramebuffer framebuffer = new FastFramebuffer();
   public int renderColor;

   public OutlineRenderer(Color color) {
      this.renderColor = color.getRGB();
   }

   public OutlineRenderer(int color) {
      this.renderColor = color;
   }

   public OutlineRenderer() {
   }

   public void setColor(Color c) {
      this.renderColor = c.getRGB();
   }

   public void setColor(int color) {
      this.renderColor = color;
   }

   public void updateFramebuffer() {
      this.framebuffer.updateFramebuffer();
   }

   public void addEntity(Entity entity) {
      RenderType.addEntity(this, entity);
   }
}
