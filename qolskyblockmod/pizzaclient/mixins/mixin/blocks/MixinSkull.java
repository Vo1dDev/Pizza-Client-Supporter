package qolskyblockmod.pizzaclient.mixins.mixin.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
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

@Mixin({BlockSkull.class})
public abstract class MixinSkull extends Block {
   public MixinSkull(Material blockMaterialIn, MapColor blockMapColorIn) {
      super(blockMaterialIn, blockMapColorIn);
   }

   @Inject(
      method = {"getCollisionBoundingBox"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void fixBoxWhenColliding(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<AxisAlignedBB> cir) {
      if (PizzaClient.config.biggerSecretBlocks) {
         switch((EnumFacing)worldIn.func_180495_p(pos).func_177229_b(BlockSkull.field_176418_a)) {
         case UP:
         default:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.25F), (double)pos.func_177956_o(), (double)((float)pos.func_177952_p() + 0.25F), (double)((float)pos.func_177958_n() + 0.75F), (double)((float)pos.func_177956_o() + 0.5F), (double)((float)pos.func_177952_p() + 0.75F)));
            return;
         case NORTH:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.25F), (double)((float)pos.func_177956_o() + 0.25F), (double)((float)pos.func_177952_p() + 0.5F), (double)((float)pos.func_177958_n() + 0.75F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + 1.0F)));
            return;
         case SOUTH:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.25F), (double)((float)pos.func_177956_o() + 0.25F), (double)pos.func_177952_p(), (double)((float)pos.func_177958_n() + 0.75F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + 0.5F)));
            return;
         case WEST:
            cir.setReturnValue(new AxisAlignedBB((double)((float)pos.func_177958_n() + 0.5F), (double)((float)pos.func_177956_o() + 0.25F), (double)((float)pos.func_177952_p() + 0.25F), (double)((float)pos.func_177958_n() + 1.0F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + 0.75F)));
            return;
         case EAST:
            cir.setReturnValue(new AxisAlignedBB((double)pos.func_177958_n(), (double)((float)pos.func_177956_o() + 0.25F), (double)((float)pos.func_177952_p() + 0.25F), (double)((float)pos.func_177958_n() + 0.5F), (double)((float)pos.func_177956_o() + 0.75F), (double)((float)pos.func_177952_p() + 0.75F)));
         }
      }

   }

   @Inject(
      method = {"setBlockBoundsBasedOnState"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void increaseBlockBox(IBlockAccess worldIn, BlockPos pos, CallbackInfo ci) {
      if (PizzaClient.config.biggerSecretBlocks) {
         this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         ci.cancel();
      }

   }
}
