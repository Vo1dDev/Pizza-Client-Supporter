package qolskyblockmod.pizzaclient.util.remote;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class Imgur {
   public static final String id = "649f2fb48e59767";

   public static String upload(File file) {
      try {
         return upload(FileUtils.readFileToByteArray(file));
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String upload(BufferedImage image) {
      return upload(Utils.toByteArray(image, "png"));
   }

   public static String uploadScreenshot() {
      return upload(Utils.toByteArray(Utils.takeScreenshot(), "png"));
   }

   public static String upload(byte[] bytes) {
      try {
         String data = Base64.getEncoder().encodeToString(bytes);
         String params = "image=" + URLEncoder.encode(data, "UTF-8");
         HttpURLConnection connection = (HttpURLConnection)(new URL("https://api.imgur.com/3/image")).openConnection();
         connection.setDoOutput(true);
         connection.setDoInput(true);
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Authorization", "Client-ID 649f2fb48e59767");
         connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         connection.connect();
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), Charsets.UTF_8));
         writer.write(params);
         if (connection.getResponseCode() != 200) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to upload image to imgur. Imgur returned response code " + connection.getResponseCode()));
         }

         JsonObject imgurJson = (new JsonParser()).parse(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8)).getAsJsonObject();
         JsonObject dataJson = imgurJson.get("data").getAsJsonObject();
         return dataJson.get("link").getAsString();
      } catch (Exception var7) {
         var7.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to upload to imgur. See logs for more info."));
         return null;
      }
   }
}
