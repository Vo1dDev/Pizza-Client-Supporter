package qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder;

import net.minecraftforge.common.MinecraftForge;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.PathBase;

public abstract class BasePathfinder {
   public static PathBase path;

   public BasePathfinder(PathBase path) {
      BasePathfinder.path = path;
      MinecraftForge.EVENT_BUS.unregister(Pathfinding.instance);
   }

   public void run() {
      this.run(true);
   }

   public void runNewThread() {
      (new Thread(this::run)).start();
   }

   public void runNewThread(boolean messages) {
      (new Thread(() -> {
         this.run(messages);
      })).start();
   }

   public void shutdown() {
      path = null;
      Pathfinding.unregister();
   }

   public static void clearCache() {
      path.checked.clear();
      path.openSet.clear();
   }

   public abstract boolean run(boolean var1);
}
