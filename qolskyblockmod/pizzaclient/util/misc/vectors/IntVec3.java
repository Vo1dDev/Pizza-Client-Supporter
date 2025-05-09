package qolskyblockmod.pizzaclient.util.misc.vectors;

import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.Utils;

public class IntVec3 {
   public final int x;
   public final int y;
   public final int z;

   public IntVec3(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public IntVec3(double x, double y, double z) {
      this.x = (int)x;
      this.y = (int)y;
      this.z = (int)z;
   }

   public boolean isBlockLoaded() {
      return this.x >= -30000000 && this.z >= -30000000 && this.x < 30000000 && this.z < 30000000 && this.y >= 0 && this.y < 256 && Utils.isChunkLoaded(this.x >> 4, this.z >> 4);
   }

   public double distanceToSq(BetterBlockPos pos) {
      double d0 = (double)(this.x - pos.field_177962_a);
      double d1 = (double)(this.y - pos.field_177960_b);
      double d2 = (double)(this.z - pos.field_177961_c);
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public int hashCode() {
      return (int)(482263L * (751913L * (long)this.x + (long)this.y) + (long)this.z);
   }

   public boolean equals(Object obj) {
      IntVec3 other = (IntVec3)obj;
      return this.x == other.x && this.y == other.y && this.z == other.z;
   }

   public boolean equals(IntVec3 other) {
      return this.x == other.x && this.y == other.y && this.z == other.z;
   }
}
