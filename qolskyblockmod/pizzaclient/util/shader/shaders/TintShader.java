package qolskyblockmod.pizzaclient.util.shader.shaders;

import java.awt.Color;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.render.RenderType;
import qolskyblockmod.pizzaclient.util.shader.Shader;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public class TintShader extends Shader {
   public static final TintShader instance = new TintShader();
   public final int color_location;

   public TintShader() {
      super("defaultVertex", "tintFragment");
      this.color_location = ShaderUtil.glGetUniformLocation(this.program, (CharSequence)"color");
   }

   public void applyRainbowTint(float alpha) {
      this.useShader();
      int color = Color.HSBtoRGB((float)(System.currentTimeMillis() % 3500L) / 3500.0F, PizzaClient.config.rgbBrightness, 1.0F);
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
   }

   public void applyTint(Color c) {
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
   }

   public void applyTintDefaultAlpha(Color c) {
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 0.75F);
   }

   public void applyTintSlightAlpha(Color c) {
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 0.5F);
   }

   public void applyTint(Color c, float alpha) {
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
   }

   public void applyTint(int color) {
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
   }

   public void applyTint(float r, float g, float b) {
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, r, g, b, 1.0F);
   }

   public void applyTint(float r, float g, float b, float a) {
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, r, g, b, a);
   }

   public void applyRainbowTintChams(float alpha) {
      RenderType.renderTintChams();
      this.useShader();
      int color = Color.HSBtoRGB((float)(System.currentTimeMillis() % 3500L) / 3500.0F, PizzaClient.config.rgbBrightness, 1.0F);
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
   }

   public void applyTintChams(Color c) {
      RenderType.renderTintChams();
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
   }

   public void applyTintChamsDefaultAlpha(Color c) {
      RenderType.renderTintChams();
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 0.75F);
   }

   public void applyTintChamsSlightAlpha(Color c) {
      RenderType.renderTintChams();
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 0.5F);
   }

   public void applyTintChams(Color c, float alpha) {
      RenderType.renderTintChams();
      this.useShader();
      int color = c.getRGB();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
   }

   public void applyTintChams(int color) {
      RenderType.renderTintChams();
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
   }

   public void applyTintChams(float r, float g, float b) {
      RenderType.renderTintChams();
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, r, g, b, 1.0F);
   }

   public void applyTintChams(float r, float g, float b, float a) {
      RenderType.renderTintChams();
      this.useShader();
      ShaderUtil.glUniform4f(this.color_location, r, g, b, a);
   }
}
