package qolskyblockmod.pizzaclient.mixins.mixin.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({BlockCarpet.class})
public abstract class MixinCarpet extends Block {
   public MixinCarpet(Material blockMaterialIn, MapColor blockMapColorIn) {
      super(blockMaterialIn, blockMapColorIn);
   }

   @Inject(
      method = {"setBlockBoundsFromMeta"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSetBoundingBox(CallbackInfo ci) {
      if (PizzaClient.config != null && PizzaClient.config.removeCarpets) {
         this.func_149676_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         ci.cancel();
      }

   }
}
