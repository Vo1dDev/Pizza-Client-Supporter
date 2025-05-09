package qolskyblockmod.pizzaclient.features.keybinds.auto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.Utils;

public class MacroKeybind extends ConfigFile {
   public static final Map<String, CustomKeybind> itemMacros = new HashMap();
   public static final File configFile;

   public MacroKeybind() {
      super(configFile);
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.autoMacroKeyToggle && PizzaClient.mc.field_71462_r == null) {
         for(int i = 0; i < 8; ++i) {
            ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (item != null) {
               String displayName = StringUtils.func_76338_a(item.func_82833_r()).toLowerCase();
               Iterator var5 = itemMacros.entrySet().iterator();

               while(var5.hasNext()) {
                  Entry<String, CustomKeybind> entry = (Entry)var5.next();
                  if (displayName.contains((CharSequence)entry.getKey())) {
                     CustomKeybind keybind = (CustomKeybind)entry.getValue();
                     if (System.currentTimeMillis() - keybind.lastSwitch >= (long)keybind.delay) {
                        keybind.useItemAndUpdate(i);
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   public static void updateToggle() {
      PizzaClient.config.autoMacroKeyToggle = !PizzaClient.config.autoMacroKeyToggle;
      Utils.addToggleMessage("Auto Item Keybind", PizzaClient.config.autoMacroKeyToggle);
   }

   public void read(FileReader in) {
      JsonObject file = (JsonObject)PizzaClient.gson.fromJson(in, JsonObject.class);
      if (file == null) {
         write(new JsonObject(), configFile);
      } else {
         Iterator var3 = file.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<String, JsonElement> e = (Entry)var3.next();
            JsonObject value = ((JsonElement)e.getValue()).getAsJsonObject();
            itemMacros.put(e.getKey(), new CustomKeybind(value.get("delay").getAsInt(), KeybindAction.getActionFromString(value.get("actionType").getAsString())));
         }

      }
   }

   public static void saveConfig() {
      JsonObject data = new JsonObject();
      Iterator var1 = itemMacros.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<String, CustomKeybind> entry = (Entry)var1.next();
         JsonObject obj = new JsonObject();
         CustomKeybind value = (CustomKeybind)entry.getValue();
         obj.addProperty("delay", value.delay);
         obj.addProperty("actionType", KeybindAction.getString(value.actionType));
         data.add((String)entry.getKey(), obj);
      }

      write(data, configFile);
   }

   static {
      configFile = new File(PizzaClient.modDir, "automacrokeys.json");
   }
}
