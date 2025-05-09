package qolskyblockmod.pizzaclient.features.misc;

import qolskyblockmod.pizzaclient.util.exceptions.RatException;

public class SessionProtection {
   public static String changed;
   public static boolean changedToken;

   public SessionProtection(SessionProtection a) {
      throw new RatException();
   }
}
