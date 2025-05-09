package qolskyblockmod.pizzaclient.util.misc.timer;

public interface TimerEnum {
   Timer timer = new Timer();

   int getDelay();

   default boolean timePassed() {
      return timer.timePassed(this.getDelay());
   }
}
