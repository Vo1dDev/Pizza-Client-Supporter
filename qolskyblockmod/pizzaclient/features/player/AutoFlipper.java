package qolskyblockmod.pizzaclient.features.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.core.events.WorldChangeEvent;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class AutoFlipper {
   private static final long[] lastUpdates = new long[100];
   private static final Set<String> filter = new HashSet(Arrays.asList("TRAVEL_SCROLL", "COSMETIC", "DUNGEON_PASS", "ARROW_POISON", "PET_ITEM"));
   public static final Set<String> checked = new HashSet();
   public static final Map<String, String> itemIds = new HashMap();
   private JsonObject neuResponse;
   private Thread thread = new Thread();

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.autoFlipper) {
         if (!this.thread.isAlive()) {
            if (itemIds.size() == 0) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Trying to load in items..."));
               this.thread = new Thread(AutoFlipper::loadIds, "AutoFlipper");
               this.thread.start();
            } else {
               this.flipper();
            }
         }

      }
   }

   private void flipper() {
      this.thread = new Thread(() -> {
         this.neuResponse = Utils.getJson("https://api.skytils.gg/api/auctions/lowestbins").getAsJsonObject();
         JsonObject auctionPage = Utils.getJson("https://api.hypixel.net/skyblock/auctions?page=0").getAsJsonObject();
         int pageAmount = auctionPage.get("totalPages").getAsInt() - 1;
         long lastUpdated = auctionPage.get("lastUpdated").getAsLong();
         if (lastUpdated != lastUpdates[0]) {
            lastUpdates[0] = lastUpdated;
            this.getFlips(auctionPage);
         }

         for(int i = 1; i < pageAmount; ++i) {
            JsonObject page = Utils.getJson("https://api.hypixel.net/skyblock/auctions?page=" + i).getAsJsonObject();
            long update = page.get("lastUpdated").getAsLong();
            if (update != lastUpdates[i]) {
               lastUpdates[i] = update;
               this.getFlips(auctionPage);
            }
         }

      });
      this.thread.start();
   }

   public static void loadIds() {
      List<String> containsList = new ArrayList(Arrays.asList("_SACK", "_TUXEDO_BOOTS", "_TUXEDO_LEGGINGS"));
      List<String> startsWithList = new ArrayList(Arrays.asList("WAND_OF", "SALMON_", "GHOST_BOOTS", "PET_ITEM", "FARM_ARMOR", "PURE_MITHRIL", "STARRED_", "PERFECT_", "BOUNCY_", "BEASTMASTER_CREST", "SKELETON_LORD_"));
      Set<String> equalsList = new HashSet(Arrays.asList("MITHRIL_COAT", "STEEL_CHESTPLATE", "BOSS_SPIRIT_BOW", "END_LEGGINGS", "END_BOOTS", "END_HELMET", "END_CHESTPLATE"));
      Iterator var3 = Utils.getJson("https://api.hypixel.net/resources/skyblock/items").getAsJsonObject().get("items").getAsJsonArray().iterator();

      while(true) {
         while(true) {
            label53:
            while(true) {
               JsonObject item;
               String id;
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var3.hasNext()) {
                                 return;
                              }

                              JsonElement jsons = (JsonElement)var3.next();
                              item = jsons.getAsJsonObject();
                           } while(!item.has("name"));
                        } while(!item.has("id"));
                     } while(!item.has("category"));
                  } while(filter.contains(item.get("category").getAsString()));

                  id = item.get("id").getAsString();
               } while(equalsList.contains(id));

               Iterator var7 = containsList.iterator();

               String s;
               while(var7.hasNext()) {
                  s = (String)var7.next();
                  if (id.contains(s)) {
                     continue label53;
                  }
               }

               var7 = startsWithList.iterator();

               while(var7.hasNext()) {
                  s = (String)var7.next();
                  if (id.startsWith(s)) {
                     continue label53;
                  }
               }

               itemIds.put(item.get("name").getAsString(), item.get("id").getAsString());
            }
         }
      }
   }

   private int getDemand(String id) {
      return -1;
   }

   private double getAvgPrice(String id) {
      try {
         if (this.neuResponse.has(id)) {
            return this.neuResponse.get(id).getAsDouble();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return -1.0D;
   }

   private void getFlips(JsonObject auctionPage) {
      Iterator var2 = auctionPage.get("auctions").getAsJsonArray().iterator();

      while(true) {
         while(true) {
            JsonObject o;
            String uuid;
            do {
               do {
                  do {
                     do {
                        if (!var2.hasNext()) {
                           return;
                        }

                        JsonElement item = (JsonElement)var2.next();
                        o = item.getAsJsonObject();
                        uuid = o.get("uuid").getAsString();
                     } while(checked.contains(uuid));
                  } while(!o.has("bin"));
               } while(!o.get("bin").getAsBoolean());
            } while(o.get("claimed").getAsBoolean());

            String name = o.get("item_name").getAsString();
            Iterator var7 = itemIds.entrySet().iterator();

            while(var7.hasNext()) {
               Entry<String, String> items = (Entry)var7.next();
               if (name.contains((CharSequence)items.getKey())) {
                  double startingBid = o.get("starting_bid").getAsDouble();
                  double avgPrice = this.getAvgPrice((String)items.getValue());
                  if (startingBid < avgPrice) {
                     double profit = avgPrice * 0.9800000190734863D - startingBid;
                     if (profit >= (double)((long)PizzaClient.config.autoFlipperLowestPrice * 1000000L)) {
                        checked.add(uuid);
                        int demand = this.getDemand((String)items.getValue());
                        Utils.playOrbSound();
                        PizzaClient.mc.field_71439_g.func_145747_a((new ChatComponentText("PizzaClient > " + EnumChatFormatting.YELLOW + (String)items.getValue() + EnumChatFormatting.GREEN + " +$" + Utils.formatValue((double)MathUtil.floor_long(profit)) + "\n" + EnumChatFormatting.GOLD + "Sales: " + EnumChatFormatting.GREEN + (demand == -1 ? "unknown\n" : demand + "/day\n") + EnumChatFormatting.GOLD + "Avg Price: " + EnumChatFormatting.GREEN + Utils.formatValue(avgPrice) + "\n" + EnumChatFormatting.GOLD + "Price: " + EnumChatFormatting.GREEN + Utils.formatValue((double)((long)startingBid)) + "\n")).func_150255_a((new ChatStyle()).func_150241_a(new ClickEvent(Action.RUN_COMMAND, "/viewauction " + uuid))));
                        if (PizzaClient.mc.field_71462_r == null) {
                           PizzaClient.mc.field_71439_g.func_71165_d("/viewauction " + uuid);
                        }
                     }
                  }
                  break;
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onWorldChange(WorldChangeEvent event) {
      checked.clear();
   }
}
