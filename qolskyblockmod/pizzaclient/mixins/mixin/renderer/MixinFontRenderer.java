package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import java.util.Locale;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.custom.CustomString;
import qolskyblockmod.pizzaclient.util.graphics.custom.ModMessageString;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.CustomStringRenderer;

@Mixin(
   value = {FontRenderer.class},
   priority = 999
)
public class MixinFontRenderer {
   @Inject(
      method = {"drawString(Ljava/lang/String;FFIZ)I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderRainbowString(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
      if (text != null && text.length() != 0) {
         if (PizzaClient.mc.field_71441_e != null) {
            int index = RainbowString.rgbList.getIndex(text.hashCode());
            if (index == -1) {
               if (text.startsWith("§rPizzaClient > ")) {
                  ModMessageString.addToList(text);
                  CustomStringRenderer.setAlpha(color);
                  cir.setReturnValue(((CustomString)RainbowString.rgbList.get(text.hashCode())).render(text, (int)x, (int)y, color, shadow));
                  return;
               }

               String cleaned = text.replace("'", " '").replace(":", " : ");
               String[] strings = cleaned.split(" ");
               if (strings.length == 0) {
                  String lowerCase = StringUtils.func_76338_a(text).toLowerCase(Locale.ROOT);
                  if (RainbowString.rgbNames.containsKey(lowerCase)) {
                     try {
                        new RainbowString(text, cleaned, lowerCase);
                     } catch (Exception var16) {
                        PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Caught and logged an exception when setting up rgb name. Please report this. String size: 0, String to render: " + text + ", cleaned: " + cleaned + ", lowercase: " + lowerCase));
                        var16.printStackTrace();
                        return;
                     }

                     CustomStringRenderer.setAlpha(color);
                     cir.setReturnValue(((CustomString)RainbowString.rgbList.get(text.hashCode())).render(text, (int)x, (int)y, color, shadow));
                     return;
                  }
               } else {
                  String[] var19 = strings;
                  int var11 = strings.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String s = var19[var12];
                     if (s.length() > 2) {
                        String lowerCase = StringUtils.func_76338_a(s).toLowerCase(Locale.ROOT);
                        if (RainbowString.rgbNames.containsKey(lowerCase)) {
                           try {
                              new RainbowString(text, s, lowerCase);
                           } catch (Exception var17) {
                              PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Caught and logged an exception when setting up rgb name. Please report this. String to render: " + text + ", cleaned: " + cleaned + ", lowercase: " + lowerCase));
                              var17.printStackTrace();
                              return;
                           }

                           CustomStringRenderer.setAlpha(color);
                           cir.setReturnValue(((CustomString)RainbowString.rgbList.get(text.hashCode())).render(text, (int)x, (int)y, color, shadow));
                           return;
                        }
                     }
                  }
               }

               RainbowString.rgbList.put(text.hashCode(), (Object)null);
               return;
            }

            CustomString custom = (CustomString)RainbowString.rgbList.getFromIndex(index);
            if (custom != null) {
               CustomStringRenderer.setAlpha(color);
               cir.setReturnValue(custom.render(text, (int)x, (int)y, color, shadow));
            }
         }

      } else {
         cir.setReturnValue((int)x);
      }
   }
}
