package qolskyblockmod.pizzaclient.util.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ResizeWindowEvent;
import qolskyblockmod.pizzaclient.util.render.FastFramebuffer;
import qolskyblockmod.pizzaclient.util.shader.util.ShaderUtil;

public abstract class Shader {
   public final int program;
   public static final FastFramebuffer framebuffer;

   public Shader(String vertex, String fragment) {
      this.program = OpenGlHelper.func_153183_d();
      if (vertex != null) {
         OpenGlHelper.func_153178_b(this.program, ShaderUtil.loadVertex(vertex));
      }

      OpenGlHelper.func_153178_b(this.program, ShaderUtil.loadFragment(fragment));
      OpenGlHelper.func_153179_f(this.program);
      ShaderUtil.glValidateProgram(this.program);
      this.bindAttributes();
   }

   public Shader(String location) {
      this(location, location);
   }

   public static void endCurrentShader() {
      if (ShaderUtil.ARB_SHADERS) {
         ARBShaderObjects.glUseProgramObjectARB(0);
      } else {
         GL20.glUseProgram(0);
      }

   }

   public final void bindAttribute(int attribute, String name) {
      GL20.glBindAttribLocation(this.program, attribute, name);
   }

   public void bindAttributes() {
   }

   public int getUniformLocation(String name) {
      return ShaderUtil.ARB_SHADERS ? ARBShaderObjects.glGetUniformLocationARB(this.program, name) : GL20.glGetUniformLocation(this.program, name);
   }

   public void useShader() {
      if (ShaderUtil.ARB_SHADERS) {
         ARBShaderObjects.glUseProgramObjectARB(this.program);
      } else {
         GL20.glUseProgram(this.program);
      }

   }

   public void endShader() {
      if (ShaderUtil.ARB_SHADERS) {
         ARBShaderObjects.glUseProgramObjectARB(0);
      } else {
         GL20.glUseProgram(0);
      }

   }

   public static void updateFramebuffer() {
      if (framebuffer.framebufferWidth != PizzaClient.mc.field_71443_c || framebuffer.framebufferHeight != PizzaClient.mc.field_71440_d) {
         framebuffer.updateFramebuffer();
         MinecraftForge.EVENT_BUS.post(new ResizeWindowEvent());
      }

   }

   static {
      framebuffer = new FastFramebuffer(PizzaClient.mc.field_71440_d, PizzaClient.mc.field_71443_c);
   }
}
