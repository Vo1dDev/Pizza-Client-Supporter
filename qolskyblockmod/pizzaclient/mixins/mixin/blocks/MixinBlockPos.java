package qolskyblockmod.pizzaclient.mixins.mixin.blocks;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({BlockPos.class})
public class MixinBlockPos extends Vec3i {
   public MixinBlockPos(int xIn, int yIn, int zIn) {
      super(xIn, yIn, zIn);
   }

   @Overwrite
   public BlockPos func_177984_a() {
      return new BlockPos(this.func_177958_n(), this.func_177956_o() + 1, this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177981_b(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n(), this.func_177956_o() + offset, this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177977_b() {
      return new BlockPos(this.func_177958_n(), this.func_177956_o() - 1, this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177979_c(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n(), this.func_177956_o() - offset, this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177978_c() {
      return new BlockPos(this.func_177958_n(), this.func_177956_o(), this.func_177952_p() - 1);
   }

   @Overwrite
   public BlockPos func_177964_d(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n(), this.func_177956_o(), this.func_177952_p() - offset);
   }

   @Overwrite
   public BlockPos func_177968_d() {
      return new BlockPos(this.func_177958_n(), this.func_177956_o(), this.func_177952_p() + 1);
   }

   @Overwrite
   public BlockPos func_177970_e(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n(), this.func_177956_o(), this.func_177952_p() + offset);
   }

   @Overwrite
   public BlockPos func_177976_e() {
      return new BlockPos(this.func_177958_n() - 1, this.func_177956_o(), this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177985_f(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n() - offset, this.func_177956_o(), this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177974_f() {
      return new BlockPos(this.func_177958_n() + 1, this.func_177956_o(), this.func_177952_p());
   }

   @Overwrite
   public BlockPos func_177965_g(int offset) {
      return offset == 0 ? (BlockPos)this : new BlockPos(this.func_177958_n() + offset, this.func_177956_o(), this.func_177952_p());
   }

   @Final
   @Overwrite
   public BlockPos func_177972_a(EnumFacing direction) {
      Vec3i dir = direction.func_176730_m();
      return new BlockPos(this.func_177958_n() + dir.func_177958_n(), this.func_177956_o() + dir.func_177956_o(), this.func_177952_p() + dir.func_177952_p());
   }

   @Final
   @Overwrite
   public BlockPos func_177967_a(EnumFacing facing, int n) {
      if (n == 0) {
         return (BlockPos)this;
      } else {
         Vec3i dir = facing.func_176730_m();
         return new BlockPos(this.func_177958_n() + dir.func_177958_n() * n, this.func_177956_o() + dir.func_177956_o() * n, this.func_177952_p() + dir.func_177952_p() * n);
      }
   }
}
