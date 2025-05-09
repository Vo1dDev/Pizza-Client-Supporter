package qolskyblockmod.pizzaclient.mixins.mixin.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({BlockLever.class})
public abstract class MixinLever extends Block {
   public MixinLever(Material blockMaterialIn, MapColor blockMapColorIn) {
      super(blockMaterialIn, blockMapColorIn);
   }

   @Inject(
      method = {"setBlockBoundsBasedOnState"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void changeLeverBox(IBlockAccess worldIn, BlockPos pos, CallbackInfo ci) {
      if (PizzaClient.config.biggerSecretBlocks) {
         this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         ci.cancel();
      }

   }
}
