package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import qolskyblockmod.pizzaclient.PizzaClient;

public class MutableBlockPosNode extends BlockPos {
   public int field_177962_a;
   public int field_177960_b;
   public int field_177961_c;
   public double cost = 1000000.0D;

   public MutableBlockPosNode() {
      super(0, 0, 0);
   }

   public MutableBlockPosNode(int x, int y, int z) {
      super(0, 0, 0);
      this.field_177962_a = x;
      this.field_177960_b = y;
      this.field_177961_c = z;
   }

   public boolean isBlockLoaded() {
      return !PizzaClient.mc.field_71441_e.func_72863_F().func_73154_d(this.field_177962_a >> 4, this.field_177961_c >> 4).func_76621_g();
   }

   public Block getBlock() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this).func_177230_c();
   }

   public IBlockState getBlockState() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this);
   }

   public MutableBlockPosNode up() {
      return new MutableBlockPosNode(this.field_177962_a, this.field_177960_b + 1, this.field_177961_c);
   }

   public MutableBlockPosNode up(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a, this.field_177960_b + amt, this.field_177961_c);
   }

   public MutableBlockPosNode down() {
      return new MutableBlockPosNode(this.field_177962_a, this.field_177960_b - 1, this.field_177961_c);
   }

   public MutableBlockPosNode down(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a, this.field_177960_b - amt, this.field_177961_c);
   }

   public MutableBlockPosNode offset(EnumFacing dir) {
      Vec3i vec = dir.func_176730_m();
      return new MutableBlockPosNode(this.field_177962_a + dir.func_82601_c(), this.field_177960_b + vec.func_177956_o(), this.field_177961_c + vec.func_177952_p());
   }

   public MutableBlockPosNode offset(EnumFacing dir, int dist) {
      if (dist == 0) {
         return this;
      } else {
         Vec3i vec = dir.func_176730_m();
         return new MutableBlockPosNode(this.field_177962_a + vec.func_177958_n() * dist, this.field_177960_b + vec.func_177956_o() * dist, this.field_177961_c + vec.func_177952_p() * dist);
      }
   }

   public MutableBlockPosNode north() {
      return new MutableBlockPosNode(this.field_177962_a, this.field_177960_b, this.field_177961_c - 1);
   }

   public MutableBlockPosNode north(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a, this.field_177960_b, this.field_177961_c - amt);
   }

   public MutableBlockPosNode south() {
      return new MutableBlockPosNode(this.field_177962_a, this.field_177960_b, this.field_177961_c + 1);
   }

   public MutableBlockPosNode south(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a, this.field_177960_b, this.field_177961_c + amt);
   }

   public MutableBlockPosNode east() {
      return new MutableBlockPosNode(this.field_177962_a + 1, this.field_177960_b, this.field_177961_c);
   }

   public MutableBlockPosNode east(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a + amt, this.field_177960_b, this.field_177961_c);
   }

   public MutableBlockPosNode west() {
      return new MutableBlockPosNode(this.field_177962_a - 1, this.field_177960_b, this.field_177961_c);
   }

   public MutableBlockPosNode west(int amt) {
      return amt == 0 ? this : new MutableBlockPosNode(this.field_177962_a - amt, this.field_177960_b, this.field_177961_c);
   }

   public int hashCode() {
      return (int)(482263L * (751913L * (long)this.field_177962_a + (long)this.field_177960_b) + (long)this.field_177961_c);
   }

   public int func_177958_n() {
      return this.field_177962_a;
   }

   public int func_177956_o() {
      return this.field_177960_b;
   }

   public int func_177952_p() {
      return this.field_177961_c;
   }

   public void resetValues() {
      this.field_177962_a = this.field_177960_b = this.field_177961_c = 0;
      this.cost = 1000000.0D;
   }
}
