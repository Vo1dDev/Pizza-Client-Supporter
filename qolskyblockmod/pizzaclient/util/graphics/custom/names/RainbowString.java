package qolskyblockmod.pizzaclient.util.graphics.custom.names;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.collections.fastutil.maps.IntObjectOpenHashMap;
import qolskyblockmod.pizzaclient.util.graphics.custom.CustomString;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.CustomStringRenderer;

public class RainbowString implements CustomString {
   public String stringBefore;
   public char[] rainbowChars;
   public String stringAfter;
   public static final Map<String, char[]> rgbNames = new HashMap();
   public static final IntObjectOpenHashMap<CustomString> rgbList = new IntObjectOpenHashMap(2048, 0.7F);
   private static Thread fetcher = new Thread(() -> {
   });

   public RainbowString(String msg, String rainbowMessage, String lowerCase) {
      this.rainbowChars = (char[])rgbNames.get(lowerCase);
      if (this.rainbowChars[0] == 167) {
         new NonRGBString(msg, rainbowMessage, lowerCase);
      } else {
         int index = msg.indexOf(rainbowMessage);
         this.stringBefore = msg.substring(0, index);
         if (this.rainbowChars[1] == '[') {
            if (this.stringBefore.indexOf(93) == -1) {
               this.replaceRankInChars();
            } else if (this.stringBefore.indexOf(58) == -1 && (this.stringBefore.contains("[VIP") || this.stringBefore.contains("[MVP"))) {
               this.stringBefore = this.stringBefore.split("\\[")[0];
            }
         }

         String colorCode = Utils.getColorCode(rainbowMessage);
         this.stringAfter = colorCode != null ? colorCode + msg.substring(index + rainbowMessage.length()) : msg.substring(index + rainbowMessage.length());
         rgbList.put(msg.hashCode(), this);
      }
   }

   private void replaceRankInChars() {
      for(int i = 2; i < this.rainbowChars.length; ++i) {
         char ch = this.rainbowChars[i];
         if (ch == ']') {
            int from = i + 2;
            int newLength = this.rainbowChars.length - from;
            char[] copy = new char[newLength + 1];
            System.arraycopy(this.rainbowChars, from, copy, 1, newLength);
            copy[0] = this.rainbowChars[0];
            this.rainbowChars = copy;
            break;
         }
      }

   }

   public int render(String text, int x, int y, int color, boolean shadow) {
      return PizzaClient.mc.field_71466_p.func_175065_a(this.stringAfter, CustomStringRenderer.drawRainbowName(this.rainbowChars, PizzaClient.mc.field_71466_p.func_175065_a(this.stringBefore, (float)x, (float)y, color, shadow), y, shadow), (float)y, color, shadow);
   }

   public static void updateList() {
      if (!fetcher.isAlive()) {
         fetcher = new Thread(() -> {
            JsonObject respose = Utils.getJson("https://gist.githubusercontent.com/PizzaboiBestLegit/c65896b963454b679eb68a29435ccb19/raw/gistfile1.txt").getAsJsonObject();
            rgbNames.clear();
            Iterator var1 = respose.entrySet().iterator();

            while(var1.hasNext()) {
               Entry<String, JsonElement> jsons = (Entry)var1.next();
               rgbNames.put(((String)jsons.getKey()).toLowerCase(Locale.ROOT), ((JsonElement)jsons.getValue()).getAsString().replace("Â", "").toCharArray());
            }

         });
         fetcher.start();
      }

   }
}
