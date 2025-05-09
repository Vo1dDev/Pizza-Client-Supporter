package qolskyblockmod.pizzaclient.util.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;

public class SkyblockAPI extends APICategory {
   public SkyblockAPI(JsonObject json) {
      super(json);
   }

   public static SkyblockAPI getLatestProfileSkyblockAPI() {
      JsonArray profiles = getAPI("skyblock/profiles").get("profiles").getAsJsonArray();
      long lastSave = 0L;
      JsonObject currentProfile = null;
      Iterator var4 = profiles.iterator();

      while(var4.hasNext()) {
         JsonElement element = (JsonElement)var4.next();
         JsonObject obj = element.getAsJsonObject().get("members").getAsJsonObject().get(getNonDashedUUID()).getAsJsonObject();
         long save = obj.get("last_save").getAsLong();
         if (lastSave < save) {
            currentProfile = obj;
            lastSave = save;
         }
      }

      return new SkyblockAPI(currentProfile);
   }

   public JsonArray getBurrows() {
      return this.json.get("griffin").getAsJsonObject().get("burrows").getAsJsonArray();
   }

   public long getPurse() {
      return 0L;
   }
}
