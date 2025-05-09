package qolskyblockmod.pizzaclient.util.misc.timer;

public class TickTimer {
   public static int ticks;

   public static void reset() {
      ticks = 0;
   }

   public static void increment() {
      ++ticks;
   }
}
