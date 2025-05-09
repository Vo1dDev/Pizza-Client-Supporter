package qolskyblockmod.pizzaclient.util.shader.util;

import java.io.BufferedInputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class ShaderUtil {
   public static final boolean ARB_SHADERS;
   public static final int framebufferType;

   public static void glUniform1f(int location, float v0) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1fARB(location, v0);
      } else {
         GL20.glUniform1f(location, v0);
      }

   }

   public static void glUniform1i(int location, int v0) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1iARB(location, v0);
      } else {
         GL20.glUniform1i(location, v0);
      }

   }

   public static void glUniform1f(int location, double v0) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1fARB(location, (float)v0);
      } else {
         GL20.glUniform1f(location, (float)v0);
      }

   }

   public static void glUniform1(int location, FloatBuffer buffer) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1ARB(location, buffer);
      } else {
         GL20.glUniform1(location, buffer);
      }

   }

   public static void glUniform3f(int location, float v0, float v1, float v2) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform3fARB(location, v0, v1, v2);
      } else {
         GL20.glUniform3f(location, v0, v1, v2);
      }

   }

   public static void glUniform4f(int location, float f, float f1, float f2, float f3) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform4fARB(location, f, f1, f2, f3);
      } else {
         GL20.glUniform4f(location, f, f1, f2, f3);
      }

   }

   public static void glUniform2f(int location, float v0, float v1) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform2fARB(location, v0, v1);
      } else {
         GL20.glUniform2f(location, v0, v1);
      }

   }

   public static void glUniformBool(int location, int value) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1iARB(location, value);
      } else {
         GL20.glUniform1i(location, value);
      }

   }

   public static void glValidateProgram(int program) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glValidateProgramARB(program);
      } else {
         GL20.glValidateProgram(program);
      }

   }

   public static int glGetUniformLocation(int program, ByteBuffer buffer) {
      return ARB_SHADERS ? ARBShaderObjects.glGetUniformLocationARB(program, buffer) : GL20.glGetUniformLocation(program, buffer);
   }

   public static int glGetUniformLocation(int programObj, CharSequence name) {
      return ARB_SHADERS ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
   }

   public static int getUniformBool(boolean bool) {
      return bool ? 1 : 0;
   }

   public static int loadVertex(String vertex) {
      int shaderID;
      try {
         byte[] bytes = Utils.toByteArray(new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(new ResourceLocation("pizzaclient", "shaders/" + vertex + ".vsh")).func_110527_b()));
         ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
         buffer.put(bytes);
         buffer.position(0);
         shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153209_q);
         OpenGlHelper.func_153169_a(shaderID, buffer);
         OpenGlHelper.func_153170_c(shaderID);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException(var4);
      }

      if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
         throw new RuntimeException("Failed to load shader vertex " + vertex);
      } else {
         return shaderID;
      }
   }

   public static int loadFragment(String fragment) {
      int shaderID;
      try {
         ResourceLocation resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + fragment + ".fsh");
         BufferedInputStream bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
         byte[] bytes = Utils.toByteArray(bufferedInputStream);
         ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
         buffer.put(bytes);
         buffer.position(0);
         shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153210_r);
         OpenGlHelper.func_153169_a(shaderID, buffer);
         OpenGlHelper.func_153170_c(shaderID);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new RuntimeException(var6);
      }

      if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
         throw new RuntimeException("Failed to load shader fragment " + fragment);
      } else {
         return shaderID;
      }
   }

   static {
      ContextCapabilities contextcapabilities = GLContext.getCapabilities();
      ARB_SHADERS = !contextcapabilities.OpenGL21;
      if (contextcapabilities.OpenGL30) {
         framebufferType = 0;
      } else if (contextcapabilities.GL_ARB_framebuffer_object) {
         framebufferType = 1;
      } else if (contextcapabilities.GL_EXT_framebuffer_object) {
         framebufferType = 2;
      } else {
         framebufferType = -1;
      }

   }
}
