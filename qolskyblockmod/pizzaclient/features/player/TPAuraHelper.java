package qolskyblockmod.pizzaclient.features.player;

import java.util.List;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.TPAuraPath;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.NoMovementPathfinder;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class TPAuraHelper {
   public static final int FLY_DURATION = 1300;
   public static TPAuraPath path;
   private static long lastFlyDisable;

   public static boolean isFlyActive() {
      return System.currentTimeMillis() - lastFlyDisable < 1300L;
   }

   public static void update() {
      lastFlyDisable = System.currentTimeMillis();
   }

   public static void teleport() {
      if (path.moves.size() == 0) {
         path.onEnd();
         disable();
      } else {
         List<BlockPos> subList = path.moves.subList(0, MathUtil.min(path.moves.size(), path.getSpeed()));
         BlockPos pos = (BlockPos)subList.get(subList.size() - 1);
         PizzaClient.mc.field_71439_g.func_70107_b((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5D);
         subList.clear();
      }
   }

   public static void runPathfinder(BetterBlockPos pos) {
      (new Thread(() -> {
         path = (TPAuraPath)(new NoMovementPathfinder(new TPAuraPath(Utils.getClosestInRange(pos)))).calculateAndGetPath();
      })).start();
   }

   public static void runPathfinder(BetterBlockPos pos, Runnable runnable) {
      (new Thread(() -> {
         path = (TPAuraPath)(new NoMovementPathfinder((new TPAuraPath(Utils.getClosestInRange(pos))).setRunnable(runnable))).calculateAndGetPath();
      })).start();
   }

   public static void disable() {
      Pathfinding.unregister();
      BasePathfinder.path = null;
      path = null;
      PizzaClient.mc.field_71439_g.field_70159_w = PizzaClient.mc.field_71439_g.field_70179_y = 0.0D;
   }
}
