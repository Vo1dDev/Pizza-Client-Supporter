package qolskyblockmod.pizzaclient.util.api;

import com.google.gson.JsonObject;

public class HypixelAPI extends APICategory {
   public HypixelAPI(JsonObject json) {
      super(json);
   }

   public static HypixelAPI getHypixelAPI() {
      return new HypixelAPI(getAPI("player").get("player").getAsJsonObject());
   }
}
