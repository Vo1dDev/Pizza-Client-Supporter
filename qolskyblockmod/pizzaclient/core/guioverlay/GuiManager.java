package qolskyblockmod.pizzaclient.core.guioverlay;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.render.RenderType;

public class GuiManager extends ConfigFile {
   public static final Map<String, GuiLocation> GUIPOSITIONS = new HashMap();
   public static final List<GuiElement> elements = new ArrayList();
   public static final File configFile;

   public GuiManager() {
      super(configFile);
   }

   public static void registerElement(GuiElement e) {
      elements.add(e);
   }

   public void read(FileReader in) {
      JsonObject file = (JsonObject)PizzaClient.gson.fromJson(in, JsonObject.class);
      Iterator var3 = file.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<String, JsonElement> e = (Entry)var3.next();
         GUIPOSITIONS.put(e.getKey(), new GuiLocation(((JsonElement)e.getValue()).getAsJsonObject().get("x").getAsFloat(), ((JsonElement)e.getValue()).getAsJsonObject().get("y").getAsFloat()));
      }

   }

   public static void saveConfig() {
      Iterator var0 = elements.iterator();

      while(var0.hasNext()) {
         GuiElement e = (GuiElement)var0.next();
         GUIPOSITIONS.put(e.getName(), e.pos);
      }

      write(GUIPOSITIONS, configFile);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void renderPlayerInfo(Post event) {
      if (PizzaClient.mc.field_71441_e != null) {
         if (event.type == ElementType.HOTBAR) {
            RenderUtil.resolution = event.resolution;
            if (RenderType.shouldRenderOutlines()) {
               RenderType.renderOutlines();
            }

            if (PizzaClient.mc.field_71462_r == null) {
               Iterator var2 = elements.iterator();

               while(var2.hasNext()) {
                  GuiElement e = (GuiElement)var2.next();
                  e.render();
               }
            }
         }

      }
   }

   static {
      configFile = new File(PizzaClient.modDir, "guipositions.json");
   }
}
