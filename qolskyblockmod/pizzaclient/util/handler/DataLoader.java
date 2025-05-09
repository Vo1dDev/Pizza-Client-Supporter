package qolskyblockmod.pizzaclient.util.handler;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.dungeons.QuizAura;
import qolskyblockmod.pizzaclient.features.dungeons.ThreeWeirdosAura;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;

public class DataLoader {
   private static Thread fetcher = new Thread();
   private static boolean allLoaded;

   private static boolean isDead() {
      return !fetcher.isAlive();
   }

   private static void loadData() {
      if (!fetcher.isAlive()) {
         fetcher = new Thread(() -> {
            try {
               PizzaClient.loadResponse();
               RainbowString.updateList();
               QuizAura.loadSolutions();
               ThreeWeirdosAura.loadSolutions();
               allLoaded = true;
            } catch (Exception var1) {
               var1.printStackTrace();
            }

         });
         fetcher.start();
      }

   }

   private static void reload() {
      if (!fetcher.isAlive()) {
         fetcher = new Thread(() -> {
            try {
               RainbowString.updateList();
            } catch (Exception var1) {
               var1.printStackTrace();
            }

         });
         fetcher.start();
      }

   }

   public static void fetch() {
      if (!allLoaded) {
         loadData();
      } else {
         reload();
      }

   }
}
