package qolskyblockmod.pizzaclient.features.dungeons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.Utils;

public class ThreeWeirdosAura {
   public static final List<String> correctAnswers = new ArrayList();

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
   }

   public static void loadSolutions() {
      if (correctAnswers.size() == 0) {
         JsonArray creditsToSkytils = Utils.getJson("https://cdn.jsdelivr.net/gh/Skytils/SkytilsMod-Data@main/solvers/threeweirdos.json").getAsJsonArray();
         Iterator var1 = creditsToSkytils.iterator();

         while(var1.hasNext()) {
            JsonElement s = (JsonElement)var1.next();
            correctAnswers.add(s.getAsString());
         }
      }

   }
}
