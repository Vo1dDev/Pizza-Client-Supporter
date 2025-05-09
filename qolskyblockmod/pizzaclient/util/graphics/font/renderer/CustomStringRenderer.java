package qolskyblockmod.pizzaclient.util.graphics.font.renderer;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;

public class CustomStringRenderer {
   public static final char[] modMessageChars = "PizzaClient".toCharArray();
   public static float alpha;

   public static void setAlpha(int color) {
      if ((color & -67108864) == 0) {
         alpha = (float)((color | -16777216) >> 24 & 255) / 255.0F;
      } else {
         alpha = (float)(color >> 24 & 255) / 255.0F;
      }

   }

   public static float drawUkranianChar(int ch, boolean shadow) {
      int l = FontUtil.CHAR_WIDTH[ch];
      float width = (float)l - 0.01F;
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      if (shadow) {
         ++FontUtil.posX;
         ++FontUtil.posY;
         GL11.glBegin(7);
         GL11.glColor4f(0.0F, 0.0F, 0.25F, alpha);
         GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
         GL11.glColor4f(0.25F, 0.25F, 0.05F, alpha);
         GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
         GL11.glColor4f(0.0F, 0.0F, 0.25F, alpha);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
         GL11.glEnd();
         --FontUtil.posX;
         --FontUtil.posY;
      }

      GL11.glBegin(7);
      GL11.glColor4f(0.0F, 0.0F, 1.0F, alpha);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 0.2F, alpha);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 1.0F, alpha);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static void drawUkranianString(String text, int x, int y, boolean shadow) {
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      alpha = 1.0F;
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      GL11.glShadeModel(7425);

      for(int i = 0; i < text.length(); ++i) {
         char ch = text.charAt(i);
         FontUtil.posX += drawUkranianChar(ch, shadow);
      }

      GL11.glShadeModel(7424);
   }

   public static float renderDefaultModMessageChar(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      int l = FontUtil.CHAR_WIDTH[ch];
      float f = (float)l - 0.01F;
      ++FontUtil.posX;
      ++FontUtil.posY;
      GL11.glColor4f(0.0F, 0.25F, 0.25F, alpha);
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX + f - 1.0F, FontUtil.posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX + f - 1.0F, FontUtil.posY + 7.99F, 0.0F);
      GL11.glEnd();
      --FontUtil.posX;
      --FontUtil.posY;
      GL11.glColor4f(0.0F, 1.0F, 1.0F, alpha);
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX + f - 1.0F, FontUtil.posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX + f - 1.0F, FontUtil.posY + 7.99F, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static float drawRainbowModMessage(int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      char[] var4 = modMessageChars;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char c = var4[var6];
         FontUtil.posX += renderRainbowChar(c, time, true);
      }

      GL11.glShadeModel(7424);
      FontUtil.posX += 4.0F;
      char c = 62;
      FontUtil.posX += renderDefaultModMessageChar(c);
      GlStateManager.func_179117_G();
      GlStateManager.func_179118_c();
      return FontUtil.posX + 4.0F;
   }

   public static float renderRainbowChar(int ch, long time, boolean shadow) {
      int l = FontUtil.CHAR_WIDTH[ch];
      float width = (float)l - 0.01F;
      long y = (long)(FontUtil.posY * 11.0F);
      long position = time - ((long)(FontUtil.posX * 11.0F) - y);
      int color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
      float red;
      float blue;
      float green;
      float red2;
      float blue2;
      float green2;
      switch(PizzaClient.config.modMessageColor) {
      case 0:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      case 1:
         red = 1.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = 1.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      case 2:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = 1.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = 1.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      case 3:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = 1.0F;
         position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = 1.0F;
         break;
      default:
         return drawUkranianChar(ch, shadow);
      }

      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      if (shadow) {
         ++FontUtil.posX;
         ++FontUtil.posY;
         GL11.glColor4f(red / 4.0F, green / 4.0F, blue / 4.0F, alpha);
         GL11.glBegin(5);
         GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
         GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
         GL11.glEnd();
         --FontUtil.posX;
         --FontUtil.posY;
      }

      GL11.glBegin(7);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
      GL11.glColor4f(red2, green2, blue2, alpha);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static float renderRainbowChar(int ch, long time, int rgb, boolean shadow) {
      if (ch != 32 && ch != 160) {
         if (ch > 255) {
            return 0.0F;
         } else {
            int l = FontUtil.CHAR_WIDTH[ch];
            float width = (float)l - 0.01F;
            long y = (long)(FontUtil.posY * 11.0F);
            long position = time - ((long)(FontUtil.posX * 11.0F) - y);
            int color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
            float red;
            float blue;
            float green;
            float red2;
            float blue2;
            float green2;
            switch(rgb) {
            case 0:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            case 1:
               red = 1.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = 1.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            case 2:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = 1.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = 1.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            default:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = 1.0F;
               position = time - ((long)((FontUtil.posX + (float)l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = 1.0F;
            }

            int i = ch % 16 * 8;
            int j = ch / 16 * 8;
            if (shadow) {
               ++FontUtil.posX;
               ++FontUtil.posY;
               GL11.glColor4f(red / 4.0F, green / 4.0F, blue / 4.0F, alpha);
               GL11.glBegin(5);
               GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
               GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
               GL11.glEnd();
               --FontUtil.posX;
               --FontUtil.posY;
            }

            GL11.glBegin(7);
            GL11.glColor4f(red, green, blue, alpha);
            GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f(FontUtil.posX, FontUtil.posY, 0.0F);
            GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f(FontUtil.posX, FontUtil.posY + 7.99F, 0.0F);
            GL11.glColor4f(red2, green2, blue2, alpha);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY + 7.99F, 0.0F);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f(FontUtil.posX + width - 1.0F, FontUtil.posY, 0.0F);
            GL11.glEnd();
            return (float)l;
         }
      } else {
         return 4.0F;
      }
   }

   public static void drawRainbowText(String input, float x, float y) {
      drawRainbowText(input.toCharArray(), (int)x, (int)y);
   }

   public static void drawRainbowText(String input, int x, int y) {
      drawRainbowText(input.toCharArray(), x, y);
   }

   public static void drawRainbowText(char[] input, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      int i;
      if (Character.isDigit(input[0])) {
         int color = Character.getNumericValue(input[0]);

         for(i = 1; i < input.length; ++i) {
            FontUtil.posX += renderRainbowChar(input[i], time, color, true);
         }
      } else {
         char[] var9 = input;
         i = input.length;

         for(int var7 = 0; var7 < i; ++var7) {
            char ch = var9[var7];
            FontUtil.posX += renderRainbowChar(ch, time, 0, true);
         }
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179118_c();
      GlStateManager.func_179117_G();
   }

   public static void drawRainbowText(char[] input, int x, int y, boolean shadow) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      int i;
      if (Character.isDigit(input[0])) {
         int color = Character.getNumericValue(input[0]);

         for(i = 1; i < input.length; ++i) {
            FontUtil.posX += renderRainbowChar(input[i], time, color, shadow);
         }
      } else {
         char[] var10 = input;
         i = input.length;

         for(int var8 = 0; var8 < i; ++var8) {
            char ch = var10[var8];
            FontUtil.posX += renderRainbowChar(ch, time, 0, shadow);
         }
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179117_G();
   }

   public static float drawRainbowName(char[] input, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      long time = System.currentTimeMillis();
      int color = Character.getNumericValue(input[0]);
      GL11.glShadeModel(7425);

      for(int i = 1; i < input.length; ++i) {
         FontUtil.posX += renderRainbowChar(input[i], time, color, true);
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179117_G();
      return FontUtil.posX;
   }

   public static float drawRainbowName(char[] input, int x, int y, boolean shadow) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
      FontUtil.posX = (float)x;
      FontUtil.posY = (float)y;
      long time = System.currentTimeMillis();
      int color = Character.getNumericValue(input[0]);
      GL11.glShadeModel(7425);

      for(int i = 1; i < input.length; ++i) {
         FontUtil.posX += renderRainbowChar(input[i], time, color, shadow);
      }

      GL11.glShadeModel(7424);
      GlStateManager.func_179117_G();
      return FontUtil.posX;
   }
}
