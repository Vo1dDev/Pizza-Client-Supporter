package qolskyblockmod.pizzaclient.util.handler;

public class ThreadHandler {
   public static Thread thread = new Thread();

   public static boolean isDead() {
      return !thread.isAlive();
   }

   public static boolean isAlive() {
      return thread.isAlive();
   }

   public static void enqueue(Runnable runnable) {
      thread = new Thread(runnable);
      thread.start();
   }

   public static void enqueueIfDead(Runnable runnable) {
      if (!thread.isAlive()) {
         thread = new Thread(runnable);
         thread.start();
      }

   }
}
