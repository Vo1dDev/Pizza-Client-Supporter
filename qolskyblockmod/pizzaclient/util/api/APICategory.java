package qolskyblockmod.pizzaclient.util.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.util.UUID;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.exceptions.APIException;

public abstract class APICategory {
   protected final JsonObject json;

   public APICategory(JsonObject json) {
      this.json = json;
   }

   public static JsonElement getJson(String endpoint) {
      try {
         HttpGet httpGet = new HttpGet(new URI(endpoint));
         httpGet.addHeader("User-Agent", "Mozilla/5.0");
         CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpGet);
         HttpEntity entity = response.getEntity();
         String json = EntityUtils.toString(entity);
         EntityUtils.consume(response.getEntity());
         return (new JsonParser()).parse(json);
      } catch (Exception var5) {
         var5.printStackTrace();
         return new JsonObject();
      }
   }

   public static JsonObject getAPI(String path) {
      if (PizzaClient.config.apiKey.length() < 10) {
         PizzaClient.mc.field_71439_g.func_145747_a((new ChatComponentText(Utils.ERROR_MESSAGE + "One of your features requires an api key! " + EnumChatFormatting.GOLD + "Click here to generate a new key!")).func_150255_a((new ChatStyle()).func_150241_a(new ClickEvent(Action.RUN_COMMAND, "/api new"))));
         throw new APIException("No API Key.");
      } else {
         JsonElement json = getJson("https://api.hypixel.net/" + path + "?key=" + PizzaClient.config.apiKey + "&uuid=" + PizzaClient.mc.field_71439_g.func_146103_bH().getId());
         if (json == null) {
            throw new APIException("Failed to get a response.");
         } else {
            JsonObject object = json.getAsJsonObject();
            if (!object.get("success").getAsBoolean()) {
               throw new APIException("Reponse returned \"false\". Report this.");
            } else {
               return object;
            }
         }
      }
   }

   public static String getKey() {
      return PizzaClient.config.apiKey;
   }

   public static String getDashedUUID() {
      return PizzaClient.mc.field_71439_g.func_146103_bH().getId().toString();
   }

   public static UUID getUUID() {
      return PizzaClient.mc.field_71439_g.func_146103_bH().getId();
   }

   public static String getNonDashedUUID() {
      return PizzaClient.mc.func_110432_I().func_148255_b();
   }
}
