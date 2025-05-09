package qolskyblockmod.pizzaclient.util.graphics.custom;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.CustomStringRenderer;

public class ModMessageString implements CustomString {
   public ModMessageString() {
   }

   public ModMessageString(String text) {
      RainbowString.rgbList.put(text.hashCode(), this);
   }

   public static void addToList(String text) {
      RainbowString.rgbList.put(text.hashCode(), new ModMessageString());
   }

   public int render(String text, int x, int y, int color, boolean shadow) {
      return PizzaClient.mc.field_71466_p.func_175065_a(text.substring(16), CustomStringRenderer.drawRainbowModMessage(x, y), (float)y, color, shadow);
   }
}
