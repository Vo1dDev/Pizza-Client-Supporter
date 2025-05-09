package qolskyblockmod.pizzaclient.mixins.mixin.server;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S08PacketPlayerPosLook.EnumFlags;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.failsafes.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroState;
import qolskyblockmod.pizzaclient.features.player.AutoPowderChest;
import qolskyblockmod.pizzaclient.features.player.VelocityHook;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.SBInfo;

@Mixin(
   value = {NetHandlerPlayClient.class},
   priority = 500
)
public abstract class MixinReceivedPackets {
   @Inject(
      method = {"handleParticles"},
      at = {@At("HEAD")}
   )
   private void onParticle(S2APacketParticles packetIn, CallbackInfo ci) {
      if (PizzaClient.mc.field_71462_r == null && PizzaClient.config.powderChestMacro && packetIn.func_179749_a() == EnumParticleTypes.CRIT) {
         AutoPowderChest.onParticle(packetIn);
      }

   }

   @Inject(
      method = {"handlePlayerPosLook"},
      at = {@At("HEAD")}
   )
   private void onPacketPosLook(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
      if (MacroBuilder.toggled && MacroBuilder.state == MacroState.ACTIVE) {
         float yaw = packetIn.func_148931_f();
         if (packetIn.func_179834_f().contains(EnumFlags.Y_ROT)) {
            yaw += PizzaClient.mc.field_71439_g.field_70177_z;
         }

         if (PlayerUtil.getPositionEyes().func_72436_e(new Vec3(packetIn.func_148932_c(), packetIn.func_148928_d(), packetIn.func_148933_e())) < 16.0D) {
            if (PizzaClient.config.rotationFailsafe) {
               float diffYaw = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70177_z - yaw);
               if (diffYaw > 10.0F) {
                  Failsafes.onPacketPosLook(diffYaw);
               }
            }
         } else {
            MacroBuilder.updatePosition();
            Failsafes.onChangePosition();
         }
      }

   }

   @Inject(
      method = {"handleExplosion"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S27PacketExplosion;func_149149_c()F"
)},
      cancellable = true
   )
   private void modifyExplosionKB(S27PacketExplosion packetIn, CallbackInfo ci) {
      if (PizzaClient.config.antiKb && SBInfo.inSkyblock) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"handleEntityVelocity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelKB(S12PacketEntityVelocity packetIn, CallbackInfo ci) {
      if (packetIn.func_149412_c() == PizzaClient.mc.field_71439_g.func_145782_y() && SBInfo.inSkyblock) {
         if (this.shouldKeepKB()) {
            if (PizzaClient.config.increasedVelocity) {
               VelocityHook.setupVelocity();
            }
         } else if (PizzaClient.config.antiKb) {
            ci.cancel();
         }
      }

   }

   private boolean shouldKeepKB() {
      if (PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150353_l) {
         return true;
      } else {
         ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
         if (held == null) {
            return false;
         } else {
            String name = held.func_82833_r();
            return name.contains("Leaping Sword") || name.contains("Bonzo's Staff") || name.contains("Jerry-chine");
         }
      }
   }
}
