package qolskyblockmod.pizzaclient.util.api;

import com.google.gson.JsonArray;

public class MojangAPI {
   public static String getNameByUUID(String uuid) {
      JsonArray arr = APICategory.getJson("https://api.mojang.com/user/profiles/" + uuid + "/names").getAsJsonArray();
      return arr.get(arr.size() - 1).getAsJsonObject().get("name").getAsString();
   }

   public static JsonArray getNameHistoryByUUID(String uuid) {
      return APICategory.getJson("https://api.mojang.com/user/profiles/" + uuid + "/names").getAsJsonArray();
   }

   public static String getUUIDByName(String name) {
      return APICategory.getJson("https://api.mojang.com/users/profiles/minecraft/" + name).getAsJsonObject().get("id").getAsString();
   }

   public static JsonArray getNameHistoryByName(String name) {
      String uuid = APICategory.getJson("https://api.mojang.com/users/profiles/minecraft/" + name).getAsJsonObject().get("id").getAsString();
      return APICategory.getJson("https://api.mojang.com/user/profiles/" + uuid + "/names").getAsJsonArray();
   }
}
