package qolskyblockmod.pizzaclient.core.guioverlay;

import qolskyblockmod.pizzaclient.util.RenderUtil;

public abstract class GuiElement {
   public GuiLocation pos;

   public GuiElement(GuiLocation location) {
      this.pos = (GuiLocation)GuiManager.GUIPOSITIONS.getOrDefault(this.getName(), location);
   }

   public abstract String getName();

   public abstract void render();

   public abstract void demoRender();

   public abstract boolean isToggled();

   public void setPos(float x, float y) {
      this.pos.x = x;
      this.pos.y = y;
   }

   public float getActualX() {
      return (float)RenderUtil.resolution.func_78326_a() * this.pos.x;
   }

   public float getActualY() {
      return (float)RenderUtil.resolution.func_78328_b() * this.pos.y;
   }

   public abstract int getHeight();

   public abstract int getWidth();
}
