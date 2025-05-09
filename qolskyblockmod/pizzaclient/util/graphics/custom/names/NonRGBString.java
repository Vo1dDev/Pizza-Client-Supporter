package qolskyblockmod.pizzaclient.util.graphics.custom.names;

import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.custom.CustomString;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.FontUtil;

public class NonRGBString implements CustomString {
   public String name;
   public final String oldName;
   public final boolean renderRank;
   public String stringAfter;
   public String stringBefore;

   public NonRGBString(String text, String custom, String lowerCase) {
      this.oldName = custom;
      this.name = new String((char[])RainbowString.rgbNames.get(lowerCase));
      RainbowString.rgbList.put(text.hashCode(), this);
      int index = text.indexOf(custom);
      this.stringBefore = text.substring(0, index);
      String colorCode = Utils.getColorCode(custom);
      this.stringAfter = colorCode != null ? colorCode + text.substring(index + custom.length()) : text.substring(index + custom.length());
      if (this.name.charAt(2) == '[') {
         String stripped = StringUtils.func_76338_a(text);
         if (stripped.indexOf(93) != -1 && (stripped.contains("[VIP") || stripped.contains("[MVP"))) {
            index = text.indexOf(58);
            if (!stripped.contains("] " + StringUtils.func_76338_a(this.oldName)) || index != -1 && index <= text.indexOf(lowerCase)) {
               this.name = RANK_PATTERN.matcher(this.name).replaceAll("");
               this.renderRank = false;
            } else {
               this.renderRank = true;
            }

            return;
         }
      }

      this.name = RANK_PATTERN.matcher(this.name).replaceAll("");
      this.renderRank = false;
   }

   public int render(String text, int x, int y, int color, boolean shadow) {
      return this.renderRank ? PizzaClient.mc.field_71466_p.func_175065_a(this.stringAfter, FontUtil.renderString(this.name, PizzaClient.mc.field_71466_p.func_175065_a(RANK_PATTERN.matcher(this.stringBefore).replaceAll(""), (float)x, (float)y, color, shadow), y, color, shadow), (float)y, color, shadow) : PizzaClient.mc.field_71466_p.func_175065_a(this.stringAfter, FontUtil.renderString(this.name, PizzaClient.mc.field_71466_p.func_175065_a(this.stringBefore, (float)x, (float)y, color, shadow), y, color, shadow), (float)y, color, shadow);
   }
}
