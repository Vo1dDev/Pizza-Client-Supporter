package qolskyblockmod.pizzaclient.util.graphics.font.renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.mixins.mixin.accessor.AccessorFontRenderer;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class FontUtil {
   public static final int[] CHAR_WIDTH = new int[256];
   public static final float RGB_SPEED = 3000.0F;
   public static final AccessorFontRenderer fontRenderer;
   public static final int[] colorCode;
   public static float posX;
   public static float posY;

   public static void drawCenteredString(String text, float x, float y, int color) {
      PizzaClient.mc.field_71466_p.func_175065_a(text, x - (float)PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0F, y, color, false);
   }

   public static void drawCenteredStringNoEvent(String text, float x, float y, int color) {
      renderString(text, (int)(x - (float)PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0F), (int)y, color, false);
   }

   public static void drawCenteredStringNoEvent(String text, int x, int y, int color) {
      renderString(text, (int)((float)x - (float)PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0F), y, color, false);
   }

   public static void drawString(String text, float x, float y, int color) {
      PizzaClient.mc.field_71466_p.func_175065_a(text, x, y, color, false);
   }

   public static void drawBackground(int width, int height) {
      Gui.func_73734_a(0, 0, width, height, 1509949440);
   }

   public static void drawBackground(int width, int height, int alpha) {
      Gui.func_73734_a(0, 0, width, height, (new Color(0, 0, 0, alpha)).getRGB());
   }

   public static void drawBackground(float width, float height) {
      Gui.func_73734_a(0, 0, (int)width, (int)height, 1509949440);
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color);
   }

   public static void drawRect(double left, double top, double right, double bottom, Color color) {
      Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color.getRGB());
   }

   public static void renderChar(char ch) {
      if (ch != 160) {
         if (ch != ' ') {
            renderDefaultChar(ch);
         }
      }
   }

   public static void renderCharNoReturn(char ch) {
      if (ch != 160) {
         if (ch != ' ') {
            renderDefaultCharNoReturn(ch);
         }
      }
   }

   public static float renderDefaultChar(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      int l = CHAR_WIDTH[ch];
      float f = (float)l - 0.01F;
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(posX, posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(posX, posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(posX + f - 1.0F, posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(posX + f - 1.0F, posY + 7.99F, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static void renderDefaultCharNoReturn(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      float f = (float)CHAR_WIDTH[ch] - 0.01F;
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(posX, posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(posX, posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(posX + f - 1.0F, posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(posX + f - 1.0F, posY + 7.99F, 0.0F);
      GL11.glEnd();
   }

   public static int getStringWidth(char[] chars) {
      int i = 0;
      boolean flag = false;

      for(int j = 0; j < chars.length; ++j) {
         char c0 = chars[j];
         int k = getCharWidth(c0);
         if (k < 0 && j < chars.length - 1) {
            ++j;
            c0 = chars[j];
            if (c0 != 'l' && c0 != 'L') {
               if (c0 == 'r' || c0 == 'R') {
                  flag = false;
               }
            } else {
               flag = true;
            }

            k = 0;
         }

         i += k;
         if (flag && k > 0) {
            ++i;
         }
      }

      return i;
   }

   public static int getRainbowStringWidth(char[] chars) {
      int i = 0;
      boolean flag = false;

      for(int j = 1; j < chars.length; ++j) {
         char c0 = chars[j];
         int k = getCharWidth(c0);
         if (k < 0 && j < chars.length - 1) {
            ++j;
            c0 = chars[j];
            if (c0 != 'l' && c0 != 'L') {
               if (c0 == 'r' || c0 == 'R') {
                  flag = false;
               }
            } else {
               flag = true;
            }

            k = 0;
         }

         i += k;
         if (flag && k > 0) {
            ++i;
         }
      }

      return i;
   }

   public static int getCharWidth(char ch) {
      if (ch == ' ') {
         return 4;
      } else {
         return ch > 255 ? fontRenderer.getGlyphWidths()[ch] : CHAR_WIDTH[ch];
      }
   }

   public static void bindUnicodeTexture(int page) {
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getUnicodePageLocation(page));
   }

   public static float renderShadowedString(String text, int x, int y, int color) {
      GlStateManager.func_179141_d();
      fontRenderer.invokeResetStyles();
      return (float)MathUtil.max(fontRenderer.invokeRenderString(text, (float)x + 1.0F, (float)y + 1.0F, color, true), fontRenderer.invokeRenderString(text, (float)x, (float)y, color, false));
   }

   public static float renderString(String text, int x, int y, int color, boolean shadow) {
      GlStateManager.func_179141_d();
      fontRenderer.invokeResetStyles();
      return shadow ? (float)MathUtil.max(fontRenderer.invokeRenderString(text, (float)x + 1.0F, (float)y + 1.0F, color, true), fontRenderer.invokeRenderString(text, (float)x, (float)y, color, false)) : (float)fontRenderer.invokeRenderString(text, (float)x, (float)y, color, false);
   }

   public static void drawRainbowText(char[] input, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = (float)x;
      posY = (float)y;
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      char[] var5 = input;
      int var6 = input.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         char ch = var5[var7];
         posX += renderRainbowChar(ch, time, 0, true);
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179118_c();
      GlStateManager.func_179117_G();
   }

   public static void drawRainbowText(String text, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = (float)x;
      posY = (float)y;
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);

      for(int i = 0; i < text.length(); ++i) {
         posX += renderRainbowChar(text.charAt(i), time, 0, true);
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179118_c();
      GlStateManager.func_179117_G();
   }

   public static float renderRainbowChar(int ch, long time, int rgb, boolean shadow) {
      if (ch != 32 && ch != 160) {
         if (ch > 255) {
            return 0.0F;
         } else {
            int l = CHAR_WIDTH[ch];
            float width = (float)l - 0.01F;
            long y = (long)(posY * 11.0F);
            long position = time - ((long)(posX * 11.0F) - y);
            int color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
            float red = (float)(color >> 16 & 255) / 255.0F;
            float blue = (float)(color >> 8 & 255) / 255.0F;
            float green = (float)(color & 255) / 255.0F;
            position = time - ((long)((posX + (float)l) * 11.0F) - y);
            color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
            float red2 = (float)(color >> 16 & 255) / 255.0F;
            float blue2 = (float)(color >> 8 & 255) / 255.0F;
            float green2 = (float)(color & 255) / 255.0F;
            int i = ch % 16 * 8;
            int j = ch / 16 * 8;
            if (shadow) {
               ++posX;
               ++posY;
               GL11.glColor4f(red / 4.0F, green / 4.0F, blue / 4.0F, 1.0F);
               GL11.glBegin(5);
               GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f(posX, posY, 0.0F);
               GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f(posX, posY + 7.99F, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f(posX + width - 1.0F, posY, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f(posX + width - 1.0F, posY + 7.99F, 0.0F);
               GL11.glEnd();
               --posX;
               --posY;
            }

            GL11.glBegin(7);
            GL11.glColor4f(red, green, blue, 1.0F);
            GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f(posX, posY, 0.0F);
            GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f(posX, posY + 7.99F, 0.0F);
            GL11.glColor4f(red2, green2, blue2, 1.0F);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f(posX + width - 1.0F, posY + 7.99F, 0.0F);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f(posX + width - 1.0F, posY, 0.0F);
            GL11.glEnd();
            return (float)l;
         }
      } else {
         return 4.0F;
      }
   }

   public static int generateTextureID(ResourceLocation location) {
      SimpleTexture texture = new SimpleTexture(location);
      PizzaClient.mc.field_71446_o.func_110579_a(location, texture);
      return texture.func_110552_b();
   }

   static {
      fontRenderer = (AccessorFontRenderer)PizzaClient.mc.field_71466_p;
      colorCode = fontRenderer.getColorCodes();

      BufferedImage bufferedimage;
      try {
         bufferedimage = Utils.readBufferedImage(new ResourceLocation("textures/font/ascii.png"));
      } catch (IOException var15) {
         throw new RuntimeException(var15);
      }

      int h = bufferedimage.getWidth();
      int j = bufferedimage.getHeight();
      int[] aint = new int[h * j];
      bufferedimage.getRGB(0, 0, h, j, aint, 0, h);
      int k = j / 16;
      int l = h / 16;
      float f = 8.0F / (float)l;

      for(int j1 = 0; j1 < 256; ++j1) {
         int k1 = j1 % 16;
         int l1 = j1 / 16;
         if (j1 == 32) {
            CHAR_WIDTH[j1] = 4;
         }

         int i2;
         for(i2 = l - 1; i2 >= 0; --i2) {
            int j2 = k1 * l + i2;
            boolean flag = true;

            for(int k2 = 0; k2 < k; ++k2) {
               int l2 = (l1 * l + k2) * h;
               if ((aint[j2 + l2] >> 24 & 255) != 0) {
                  flag = false;
                  break;
               }
            }

            if (!flag) {
               break;
            }
         }

         ++i2;
         CHAR_WIDTH[j1] = (int)(0.5D + (double)((float)i2 * f)) + 1;
      }

   }
}
