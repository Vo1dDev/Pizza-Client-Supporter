package qolskyblockmod.pizzaclient.mixins.mixin.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({BlockCocoa.class})
public abstract class MixinCocoaBean extends Block {
   public MixinCocoaBean(Material blockMaterialIn, MapColor blockMapColorIn) {
      super(blockMaterialIn, blockMapColorIn);
   }

   @Inject(
      method = {"getCollisionBoundingBox"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void fixBoxWhenColliding(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<AxisAlignedBB> cir) {
      if (PizzaClient.config.cocoaBeanSize) {
         IBlockState iblockstate = worldIn.func_180495_p(pos);
         int i = (Integer)iblockstate.func_177229_b(BlockCocoa.field_176501_a);
         int j = 4 + i * 2;
         int k = 5 + i * 2;
         float f = (float)j / 2.0F;
         switch((EnumFacing)iblockstate.func_177229_b(BlockCocoa.field_176387_N)) {
         case SOUTH:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + (8.0F - f) / 16.0F), (double)((float)pos.func_177956_o() + (12.0F - (float)k) / 16.0F), (double)((float)pos.func_177952_p() + (15.0F - (float)j) / 16.0F), (double)((float)pos.func_177958_n() + (8.0F + f) / 16.0F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + 0.9375F)));
            return;
         case NORTH:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + (8.0F - f) / 16.0F), (double)((float)pos.func_177956_o() + (12.0F - (float)k) / 16.0F), (double)((float)pos.func_177952_p() + 0.0625F), (double)((float)pos.func_177958_n() + (8.0F + f) / 16.0F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + (1.0F + (float)j) / 16.0F)));
            return;
         case WEST:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.0625F), (double)((float)pos.func_177956_o() + (12.0F - (float)k) / 16.0F), (double)((float)pos.func_177952_p() + (8.0F - f) / 16.0F), (double)((float)pos.func_177958_n() + (1.0F + (float)j) / 16.0F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + (8.0F + f) / 16.0F)));
            return;
         case EAST:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + (15.0F - (float)j) / 16.0F), (double)((float)pos.func_177956_o() + (12.0F - (float)k) / 16.0F), (double)((float)pos.func_177952_p() + (8.0F - f) / 16.0F), (double)((float)pos.func_177958_n() + 0.9375F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + (8.0F + f) / 16.0F)));
         }
      }

   }

   @Inject(
      method = {"setBlockBoundsBasedOnState"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void increaseBlockBox(IBlockAccess worldIn, BlockPos pos, CallbackInfo ci) {
      if (PizzaClient.config.cocoaBeanSize) {
         if ((Integer)worldIn.func_180495_p(pos).func_177229_b(BlockCocoa.field_176501_a) < 2) {
            this.func_149676_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         } else {
            this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         }

         ci.cancel();
      }

   }
}
