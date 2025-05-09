package qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;

public abstract class MiningBot extends PathBase {
   public MiningBot(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public MiningBot(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public MiningBot(BetterBlockPos to) {
      super(to);
   }

   public MiningBot(Vec3 to) {
      super(to);
   }
}
