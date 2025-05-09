package qolskyblockmod.pizzaclient.util.graphics.font.renderer;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.font.FontType;

public class CustomFontRenderer {
   public static final FontType GREYCLIFF = new FontType(new ResourceLocation("pizzaclient", "font/greycliff.ttf"), 18, false, true);
   public static final float RGB_SPEED = 3300.0F;
   public static float posX;
   public static float posY;
   public static float kerning = 8.3F;
   private static FontType fontType;

   public static float drawRainbowString(String text, float xCoord, float yCoord) {
      beginRender();
      scaleChat();
      posX = xCoord;
      posY = yCoord;
      long time = System.currentTimeMillis();

      for(int index = 0; index < text.length(); ++index) {
         char ch = text.charAt(index);
         colorRainbow(time);
         posX += fontType.renderChar(ch) - kerning;
      }

      endRender();
      return posX;
   }

   private static void colorRainbow(long time) {
      int color = Color.HSBtoRGB((float)((time - (long)((int)(posX * 11.0F - posY * 11.0F))) % 3300L) / 3300.0F, PizzaClient.config.rgbBrightness, 1.0F);
      GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F);
   }

   private static void colorRainbow() {
      int color = Color.HSBtoRGB((float)((System.currentTimeMillis() - (long)((int)(posX * 11.0F - posY))) % 3300L) / 3300.0F, PizzaClient.config.rgbBrightness, 1.0F);
      GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F);
   }

   private static void beginRender() {
      GL11.glPushMatrix();
      GlStateManager.func_179147_l();
      GlStateManager.func_179112_b(770, 771);
      GlStateManager.func_179098_w();
      fontType.bindTexture();
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
   }

   private static void scaleChat() {
      float scale = PizzaClient.mc.field_71474_y.field_96691_E;
   }

   private static void endRender() {
      GL11.glHint(3155, 4352);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179144_i(0);
   }

   public static void setFontType(FontType type) {
      fontType = type;
   }

   public static void setGreycliffFont() {
      fontType = GREYCLIFF;
   }

   public static int getHeight() {
      return (fontType.fontHeight - 8) / 2;
   }
}
