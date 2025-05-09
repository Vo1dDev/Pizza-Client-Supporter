package qolskyblockmod.pizzaclient.features.macros.pathfinding.path;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves.FlyMoves;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.moves.IMovement;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;

public class TPAuraPath extends PathBase {
   private Runnable runnable;

   public TPAuraPath(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public TPAuraPath(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public TPAuraPath(BetterBlockPos to) {
      super(to);
   }

   public TPAuraPath(Vec3 to) {
      super(to);
   }

   public TPAuraPath setRunnable(Runnable runnable) {
      this.runnable = runnable;
      return this;
   }

   public void onEnd() {
      if (this.runnable != null) {
         this.runnable.run();
      }

   }

   public int getSpeed() {
      return 3;
   }

   public IMovement[] getDirections() {
      return FlyMoves.values();
   }

   public void move() {
   }
}
