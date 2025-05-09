package qolskyblockmod.pizzaclient.util.graphics.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.CustomFontRenderer;

public class FontType {
   public final CharData[] charData = new CharData[256];
   public final int fontTexture;
   public int fontHeight;
   public float space = 0.01F;

   public FontType(ResourceLocation location, int size) {
      this.fontTexture = this.generateTexture(getFont(location, size), 512, true, true);
   }

   public FontType(ResourceLocation location, int size, boolean antiAlias, boolean fractionalMetrics) {
      this.fontTexture = this.generateTexture(getFont(location, size), 512, antiAlias, fractionalMetrics);
   }

   public void bindTexture() {
      GlStateManager.func_179144_i(this.fontTexture);
   }

   public float renderChar(char ch) {
      return this.charData[ch].renderChar(CustomFontRenderer.posX, CustomFontRenderer.posY) + this.space;
   }

   public float getSpaceWidth() {
      return (float)this.charData[32].width;
   }

   private int generateTexture(Font font, int imgSize, boolean antiAlias, boolean fractionalMetrics) {
      BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
      Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
      graphics.setFont(font);
      graphics.setColor(new Color(255, 255, 255, 0));
      graphics.fillRect(0, 0, imgSize, imgSize);
      graphics.setColor(Color.WHITE);
      graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
      graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int charHeight = 0;
      int positionX = 0;
      int positionY = 1;

      for(int index = 0; index < this.charData.length; ++index) {
         char c = (char)index;
         CharData charData = new CharData();
         Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(c), graphics);
         charData.width = dimensions.getBounds().width + 8;
         charData.height = dimensions.getBounds().height;
         if (positionX + charData.width >= imgSize) {
            positionX = 0;
            positionY += charHeight;
            charHeight = 0;
         }

         if (charData.height > charHeight) {
            charHeight = charData.height;
         }

         charData.storedX = positionX;
         charData.storedY = positionY;
         if (charData.height > this.fontHeight) {
            this.fontHeight = charData.height;
         }

         this.charData[index] = charData;
         graphics.drawString(String.valueOf(c), positionX + 2, positionY + fontMetrics.getAscent());
         positionX += charData.width;
      }

      DynamicTexture texture = new DynamicTexture(bufferedImage);
      return texture.func_110552_b();
   }

   public static Font getFont(ResourceLocation location, int size) {
      try {
         return Font.createFont(0, PizzaClient.mc.func_110442_L().func_110536_a(location).func_110527_b()).deriveFont(0, (float)size);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }
}
