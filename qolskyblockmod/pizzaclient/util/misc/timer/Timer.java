package qolskyblockmod.pizzaclient.util.misc.timer;

public class Timer {
   private long lastInteractTime;

   public boolean timePassed(int time) {
      if (System.currentTimeMillis() - this.lastInteractTime >= (long)time) {
         this.updateTimer();
         return true;
      } else {
         return false;
      }
   }

   public static boolean timePassed(long lastInteractTime, int time) {
      return System.currentTimeMillis() - lastInteractTime >= (long)time;
   }

   public void updateTimer() {
      this.lastInteractTime = System.currentTimeMillis();
   }
}
