package qolskyblockmod.pizzaclient.core.guioverlay;

import qolskyblockmod.pizzaclient.util.RenderUtil;

public class GuiLocation {
   public float x;
   public float y;

   public GuiLocation(int x, int y) {
      this((float)x / (float)RenderUtil.resolution.func_78328_b(), (float)y / (float)RenderUtil.resolution.func_78328_b());
   }

   public GuiLocation(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public boolean equals(GuiLocation loc) {
      return loc.x == this.x && loc.y == this.y;
   }

   public String toString() {
      return "{x=" + this.x + ", y=" + this.y + "}";
   }
}
