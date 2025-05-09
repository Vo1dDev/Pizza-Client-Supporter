package qolskyblockmod.pizzaclient.util.graphics.font;

import org.lwjgl.opengl.GL11;

public class CharData {
   public int width;
   public int height;
   public int storedX;
   public int storedY;

   public float renderChar(float x, float y) {
      float renderSRCX = (float)this.storedX / 512.0F;
      float renderSRCY = (float)this.storedY / 512.0F;
      float renderSRCWidth = (float)this.width / 512.0F;
      float renderSRCHeight = (float)this.height / 512.0F;
      GL11.glBegin(4);
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x + (float)this.width), (double)y);
      GL11.glTexCoord2f(renderSRCX, renderSRCY);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x, (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x, (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)(x + (float)this.width), (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x + (float)this.width), (double)y);
      GL11.glEnd();
      return (float)this.width;
   }

   public float renderChar(float x, float y, float imgSize) {
      float renderSRCX = (float)this.storedX / imgSize;
      float renderSRCY = (float)this.storedY / imgSize;
      float renderSRCWidth = (float)this.width / imgSize;
      float renderSRCHeight = (float)this.height / imgSize;
      GL11.glBegin(4);
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x + (float)this.width), (double)y);
      GL11.glTexCoord2f(renderSRCX, renderSRCY);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x, (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x, (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)(x + (float)this.width), (double)(y + (float)this.height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x + (float)this.width), (double)y);
      GL11.glEnd();
      return (float)this.width;
   }
}
