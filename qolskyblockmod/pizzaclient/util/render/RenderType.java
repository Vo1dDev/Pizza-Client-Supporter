package qolskyblockmod.pizzaclient.util.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.shader.Shader;
import qolskyblockmod.pizzaclient.util.shader.shaders.ColoredOutlineShader;
import qolskyblockmod.pizzaclient.util.shader.shaders.GlowShader;
import qolskyblockmod.pizzaclient.util.shader.shaders.OutlineShader;
import qolskyblockmod.pizzaclient.util.shader.shaders.TintShader;

public enum RenderType implements IRenderType {
   TINT_CHAMS {
      public void renderPost() {
         RenderUtil.endChams();
         Shader.endCurrentShader();
      }
   },
   CHAMS {
      public void renderPost() {
         RenderUtil.endChams();
      }
   };

   private static final Map<OutlineRenderer, List<Entity>> outlineRenderer = new HashMap();
   public static IRenderType renderType;
   public static final OutlineRenderer universalOutline = new OutlineRenderer();

   private RenderType() {
   }

   public static void reset() {
      renderType = null;
   }

   public static void renderChromaChams(float alpha) {
      renderType = TINT_CHAMS;
      RenderUtil.startChams();
      TintShader.instance.applyRainbowTint(alpha);
   }

   public static void renderTintChams() {
      renderType = TINT_CHAMS;
      RenderUtil.startChams();
   }

   public static void renderTintChams(Color c) {
      renderType = TINT_CHAMS;
      RenderUtil.startChams();
      TintShader.instance.applyTint(c);
   }

   public static void renderChams() {
      renderType = CHAMS;
      RenderUtil.startChams();
   }

   public static void onRender(float partialTicks) {
      RenderUtil.skipEvent = true;
      Iterator var1 = outlineRenderer.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<OutlineRenderer, List<Entity>> entry = (Entry)var1.next();
         ((OutlineRenderer)entry.getKey()).framebuffer.clearAndBindFramebuffer();
         Iterator var3 = ((List)entry.getValue()).iterator();

         while(var3.hasNext()) {
            Entity entity = (Entity)var3.next();
            RenderUtil.renderLivingEntityNoShadowNoEvent(entity, partialTicks);
         }
      }

      RenderUtil.skipEvent = false;
      PizzaClient.mc.func_147110_a().func_147610_a(true);
   }

   public static void renderOutlines() {
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, 0.0F);
      GlStateManager.func_179147_l();
      Iterator var0;
      OutlineRenderer renderer;
      if (PizzaClient.config.useGlowShader) {
         var0 = outlineRenderer.keySet().iterator();

         while(var0.hasNext()) {
            renderer = (OutlineRenderer)var0.next();
            GlStateManager.func_179144_i(renderer.framebuffer.framebufferTexture);
            Shader.framebuffer.clearAndBindFramebuffer();
            OutlineShader.instance.renderFirst();
            RenderUtil.drawQuad();
            OutlineShader.instance.renderSecond();
            RenderUtil.drawQuad();
            GlStateManager.func_179144_i(Shader.framebuffer.framebufferTexture);
            GlowShader.instance.renderFirst(renderer.renderColor);
            RenderUtil.drawQuad();
            PizzaClient.mc.func_147110_a().func_147610_a(true);
            GlowShader.instance.renderSecond();
            RenderUtil.drawQuad();
            Shader.endCurrentShader();
         }
      } else {
         var0 = outlineRenderer.keySet().iterator();

         while(var0.hasNext()) {
            renderer = (OutlineRenderer)var0.next();
            GlStateManager.func_179144_i(renderer.framebuffer.framebufferTexture);
            PizzaClient.mc.func_147110_a().func_147610_a(true);
            ColoredOutlineShader.instance.renderFirst(renderer.renderColor);
            RenderUtil.drawQuad();
            ColoredOutlineShader.instance.renderSecond();
            RenderUtil.drawQuad();
            Shader.endCurrentShader();
         }
      }

      outlineRenderer.clear();
   }

   public static void addEntity(OutlineRenderer renderer, Entity entity) {
      List<Entity> entities = (List)outlineRenderer.get(renderer);
      if (entities == null) {
         outlineRenderer.put(renderer, new ArrayList(Collections.singletonList(entity)));
      } else {
         entities.add(entity);
      }

   }

   public static void addEntity(Entity entity) {
      List<Entity> entities = (List)outlineRenderer.get(universalOutline);
      if (entities == null) {
         outlineRenderer.put(universalOutline, new ArrayList(Collections.singletonList(entity)));
      } else {
         entities.add(entity);
      }

   }

   public static boolean shouldRenderOutlines() {
      return outlineRenderer.size() != 0;
   }

   public static void addAllEntities(OutlineRenderer renderer, List<Entity> entity) {
      List<Entity> entities = (List)outlineRenderer.get(renderer);
      if (entities == null) {
         outlineRenderer.put(renderer, new ArrayList(entity));
      } else {
         entities.addAll(entity);
      }

   }

   public static void addAllEntities(OutlineRenderer renderer, Set<Entity> entity) {
      List<Entity> entities = (List)outlineRenderer.get(renderer);
      if (entities == null) {
         outlineRenderer.put(renderer, new ArrayList(entity));
      } else {
         entities.addAll(entity);
      }

   }

   public static void addAllEntities(List<Entity> entity) {
      List<Entity> entities = (List)outlineRenderer.get(universalOutline);
      if (entities == null) {
         outlineRenderer.put(universalOutline, new ArrayList(entity));
      } else {
         entities.addAll(entity);
      }

   }

   public static void addAllEntities(Set<Entity> entity) {
      List<Entity> entities = (List)outlineRenderer.get(universalOutline);
      if (entities == null) {
         outlineRenderer.put(universalOutline, new ArrayList(entity));
      } else {
         entities.addAll(entity);
      }

   }

   public static void setUniversalOutlineColor(Color c) {
      universalOutline.setColor(c);
   }

   // $FF: synthetic method
   RenderType(Object x2) {
      this();
   }
}
