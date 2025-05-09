package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.render.RenderType;

@Mixin(
   value = {RendererLivingEntity.class},
   priority = 900
)
public abstract class MixinRendererLiving<T extends EntityLivingBase> extends Render<T> {
   @Shadow
   protected ModelBase field_77045_g;
   @Shadow
   protected boolean field_177098_i;

   @Shadow
   protected abstract float func_77044_a(T var1, float var2);

   @Shadow
   protected abstract void func_77039_a(T var1, double var2, double var4, double var6);

   @Shadow
   protected abstract void func_77043_a(T var1, float var2, float var3, float var4);

   @Shadow
   protected abstract void func_77041_b(T var1, float var2);

   @Shadow
   protected abstract boolean func_177088_c(T var1);

   @Shadow
   protected abstract void func_77036_a(T var1, float var2, float var3, float var4, float var5, float var6, float var7);

   @Shadow
   protected abstract boolean func_177090_c(T var1, float var2);

   @Shadow
   protected abstract void func_177093_a(T var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

   protected MixinRendererLiving(RenderManager renderManager) {
      super(renderManager);
   }

   @Inject(
      method = {"doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;popMatrix()V"
)}
   )
   private void renderEntityOutlinesPost(CallbackInfo ci) {
      if (RenderType.renderType != null) {
         RenderType.renderType.renderPost();
         RenderType.reset();
      }

   }

   @Inject(
      method = {"doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelIfNoEvent(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
      if (RenderUtil.skipEvent) {
         GL11.glPushMatrix();
         GlStateManager.func_179129_p();
         this.field_77045_g.field_78095_p = entity.func_70678_g(partialTicks);
         boolean shouldSit = entity.func_70115_ae() && entity.field_70154_o != null && entity.field_70154_o.shouldRiderSit();
         this.field_77045_g.field_78093_q = shouldSit;
         this.field_77045_g.field_78091_s = entity.func_70631_g_();
         float f = this.interpolateRotation(entity.field_70760_ar, entity.field_70761_aq, partialTicks);
         float f1 = this.interpolateRotation(entity.field_70758_at, entity.field_70759_as, partialTicks);
         float f2 = f1 - f;
         float f8;
         if (shouldSit && entity.field_70154_o instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity.field_70154_o;
            f = this.interpolateRotation(entitylivingbase.field_70760_ar, entitylivingbase.field_70761_aq, partialTicks);
            f2 = f1 - f;
            f8 = MathHelper.func_76142_g(f2);
            if (f8 < -85.0F) {
               f8 = -85.0F;
            }

            if (f8 >= 85.0F) {
               f8 = 85.0F;
            }

            f = f1 - f8;
            if (f8 * f8 > 2500.0F) {
               f += f8 * 0.2F;
            }
         }

         float f7 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
         this.func_77039_a(entity, x, y, z);
         f8 = this.func_77044_a(entity, partialTicks);
         this.func_77043_a(entity, f8, f, partialTicks);
         GlStateManager.func_179091_B();
         GL11.glScalef(-1.0F, -1.0F, 1.0F);
         this.func_77041_b(entity, partialTicks);
         GL11.glTranslatef(0.0F, -1.5078125F, 0.0F);
         float f5 = entity.field_70722_aY + (entity.field_70721_aZ - entity.field_70722_aY) * partialTicks;
         float f6 = entity.field_70754_ba - entity.field_70721_aZ * (1.0F - partialTicks);
         if (entity.func_70631_g_()) {
            f6 *= 3.0F;
         }

         if (f5 > 1.0F) {
            f5 = 1.0F;
         }

         GlStateManager.func_179141_d();
         this.field_77045_g.func_78086_a(entity, f6, f5, partialTicks);
         this.field_77045_g.func_78087_a(f6, f5, f8, f2, f7, 0.0625F, entity);
         boolean flag;
         if (this.field_177098_i) {
            flag = this.func_177088_c(entity);
            this.func_77036_a(entity, f6, f5, f8, f2, f7, 0.0625F);
            if (flag) {
               this.unsetScoreTeamColor();
            }
         } else {
            flag = this.func_177090_c(entity, partialTicks);
            this.func_77036_a(entity, f6, f5, f8, f2, f7, 0.0625F);
            if (flag) {
               this.unsetBrightness();
            }

            GlStateManager.func_179132_a(true);
            if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v()) {
               this.func_177093_a(entity, f6, f5, partialTicks, f8, f2, f7, 0.0625F);
            }
         }

         GlStateManager.func_179101_C();
         GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
         GlStateManager.func_179098_w();
         GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
         GlStateManager.func_179089_o();
         GL11.glPopMatrix();
         ci.cancel();
      }

   }

   protected float interpolateRotation(float par1, float par2, float par3) {
      float f;
      for(f = par2 - par1; f < -180.0F; f += 360.0F) {
      }

      while(f >= 180.0F) {
         f -= 360.0F;
      }

      return par1 + par3 * f;
   }

   protected void unsetScoreTeamColor() {
      GlStateManager.func_179145_e();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GlStateManager.func_179098_w();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179098_w();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   protected void unsetBrightness() {
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GlStateManager.func_179098_w();
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176086_J, 770);
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
      GlStateManager.func_179090_x();
      GlStateManager.func_179144_i(0);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }
}
