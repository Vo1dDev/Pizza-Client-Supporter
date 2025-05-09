package qolskyblockmod.pizzaclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;
import qolskyblockmod.pizzaclient.commands.AotvTestCommand;
import qolskyblockmod.pizzaclient.commands.ArabFunnyCommand;
import qolskyblockmod.pizzaclient.commands.AutoPetCommand;
import qolskyblockmod.pizzaclient.commands.BlockAbilityCommand;
import qolskyblockmod.pizzaclient.commands.ChatSpammerCommand;
import qolskyblockmod.pizzaclient.commands.FuckCPUCommand;
import qolskyblockmod.pizzaclient.commands.ItemMacroCommand;
import qolskyblockmod.pizzaclient.commands.KuudraAuraCommand;
import qolskyblockmod.pizzaclient.commands.P4BlockCommand;
import qolskyblockmod.pizzaclient.commands.PathfindCommand;
import qolskyblockmod.pizzaclient.commands.PizzaClientGuiCommand;
import qolskyblockmod.pizzaclient.commands.SetYawCommand;
import qolskyblockmod.pizzaclient.commands.SilencerCommand;
import qolskyblockmod.pizzaclient.core.config.Config;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.core.events.WorldChangeEvent;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiManager;
import qolskyblockmod.pizzaclient.features.dungeons.ChestESP;
import qolskyblockmod.pizzaclient.features.dungeons.DungeonFeatures;
import qolskyblockmod.pizzaclient.features.dungeons.IceFillQol;
import qolskyblockmod.pizzaclient.features.dungeons.QuizAura;
import qolskyblockmod.pizzaclient.features.dungeons.SuperboomAura;
import qolskyblockmod.pizzaclient.features.dungeons.f7.FunnyDevice;
import qolskyblockmod.pizzaclient.features.dungeons.f7.FunnyTerminals;
import qolskyblockmod.pizzaclient.features.keybinds.KeybindFeatures;
import qolskyblockmod.pizzaclient.features.keybinds.auto.MacroKeybind;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.Refuel;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.MithrilMarkers;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.finder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.misc.BlockAbility;
import qolskyblockmod.pizzaclient.features.misc.GuiFeatures;
import qolskyblockmod.pizzaclient.features.misc.MonolithESP;
import qolskyblockmod.pizzaclient.features.player.AutoClicker;
import qolskyblockmod.pizzaclient.features.player.AutoJerryBox;
import qolskyblockmod.pizzaclient.features.player.AutoPowderChest;
import qolskyblockmod.pizzaclient.features.player.BurrowAura;
import qolskyblockmod.pizzaclient.features.player.HarpSolver;
import qolskyblockmod.pizzaclient.features.player.VelocityHook;
import qolskyblockmod.pizzaclient.features.skills.FunnyEnchanting;
import qolskyblockmod.pizzaclient.features.skills.MiningFeatures;
import qolskyblockmod.pizzaclient.features.skills.WorldScanner;
import qolskyblockmod.pizzaclient.features.slayers.SlayerFeatures;
import qolskyblockmod.pizzaclient.gui.UpdateGui;
import qolskyblockmod.pizzaclient.listeners.ChatListener;
import qolskyblockmod.pizzaclient.util.PacketUtil;
import qolskyblockmod.pizzaclient.util.SBInfo;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.handler.Blacklist;
import qolskyblockmod.pizzaclient.util.handler.DataLoader;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.misc.runnables.TickTask;
import qolskyblockmod.pizzaclient.util.misc.timer.TickTimer;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.IRotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

@Mod(
   name = "Pizza Client",
   modid = "pizzaclient",
   version = "1.1.3",
   acceptedMinecraftVersions = "[1.8.9]",
   clientSideOnly = true
)
@SideOnly(Side.CLIENT)
public class PizzaClient {
   public static final String MODID = "pizzaclient";
   public static final String VERSION = "1.1.3";
   public static final String NAME = "Pizza Client";
   public static final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
   public static final Minecraft mc = Minecraft.func_71410_x();
   public static final String username;
   public static final KeyBinding[] keyBindings;
   public static final String modMessage = "PizzaClient > ";
   public static final File modDir;
   public static Config config;
   public static TickTask tickTask;
   public static Locations location;
   public static JsonObject response;
   public static IRotater rotater;
   private static boolean joinSkyblock;
   private static long lastWorldChange;

   public static void onStartGame() {
      if (!modDir.exists()) {
         modDir.mkdirs();
      }

      config = new Config();
      mc.func_110432_I().func_148254_d();
   }

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      DataLoader.fetch();
      Display.setTitle("Pizza Supporter");
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      register(new GuiManager());
      register(this);
      register(new BlockAbility());
      register(new ChatListener());
      register(new GuiFeatures());
      register(new DungeonFeatures());
      register(new MiningFeatures());
      register(new FunnyTerminals());
      register(new FunnyEnchanting());
      register(new WorldScanner());
      register(new SlayerFeatures());
      register(new SBInfo());
      register(new KeybindFeatures());
      register(new ChestESP());
      register(new FunnyDevice());
      register(new AutoPowderChest());
      register(new MacroBuilder());
      register(new HarpSolver());
      register(new MonolithESP());
      register(new IceFillQol());
      register(new QuizAura());
      register(new AutoJerryBox());
      register(new SuperboomAura());
      register(new BurrowAura());
      register(new AutoClicker());
      register(new MacroKeybind());
      new MithrilMarkers();
      keyBindings[0] = new KeyBinding("AutoClicker", 45, "Pizza Client");
      keyBindings[1] = new KeyBinding("Ghost Blocks", 34, "Pizza Client");
      keyBindings[2] = new KeyBinding("ToggleSprint", 23, "Pizza Client");
      keyBindings[3] = new KeyBinding("Quick Open Config", 54, "Pizza Client");
      keyBindings[4] = new KeyBinding("Auto Item Key Toggle", 35, "Pizza Client");
      keyBindings[5] = new KeyBinding("Macro Key", 33, "Pizza Client");
      keyBindings[6] = new KeyBinding("Mithril Markers", 50, "Pizza Client");
      keyBindings[7] = new KeyBinding("Any Item With Anything", 19, "Pizza Client");
      KeyBinding[] var2 = keyBindings;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         KeyBinding key = var2[var4];
         ClientRegistry.registerKeyBinding(key);
      }

   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      registerCommand(new PizzaClientGuiCommand());
      registerCommand(new SilencerCommand());
      registerCommand(new ChatSpammerCommand());
      registerCommand(new ItemMacroCommand());
      registerCommand(new BlockAbilityCommand());
      registerCommand(new ArabFunnyCommand());
      registerCommand(new PathfindCommand());
      registerCommand(new AotvTestCommand());
      registerCommand(new AutoPetCommand());
      registerCommand(new SetYawCommand());
      registerCommand(new FuckCPUCommand());
      registerCommand(new P4BlockCommand());
      registerCommand(new KuudraAuraCommand());
   }

   public static void onTick() {
      if (tickTask != null) {
         tickTask.onTick();
      }

      if (mc.field_71441_e != null) {
         TickTimer.increment();
         MinecraftForge.EVENT_BUS.post(new TickStartEvent());
         VelocityHook.onTick();
         if (mc.field_71462_r == null) {
            if (Rotater.rotating && rotater == null) {
               Rotater.rotating = false;
            }

            ItemStack held = mc.field_71439_g.field_71071_by.func_70448_g();
            if (held != null) {
               String displayName = held.func_82833_r();
               if (config.autoJerryBox) {
                  if (displayName.contains("Jerry Box")) {
                     AutoJerryBox.useJerryBox();
                     return;
                  }

                  if (config.autoJerryBoxSwapSlot) {
                     AutoJerryBox.swapToBox();
                  }
               }

               if (config.autoSkyblock && joinSkyblock && displayName.equals("§aGame Menu §7(Right Click)")) {
                  if (!MacroBuilder.toggled) {
                     Utils.sleep(400);
                  }

                  joinSkyblock = false;
                  mc.field_71439_g.func_71165_d("/play sb");
                  return;
               }

               if (config.autoSoulcry) {
                  SlayerFeatures.useSoulCry();
               }
            } else if (config.autoJerryBox && config.autoJerryBoxSwapSlot) {
               AutoJerryBox.swapToBox();
            }
         }

      }
   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      if (System.currentTimeMillis() - lastWorldChange >= 1400L) {
         lastWorldChange = System.currentTimeMillis();
         MinecraftForge.EVENT_BUS.post(new WorldChangeEvent());
         DataLoader.fetch();
         location = Locations.NULL;
         PacketUtil.stopPackets = false;
         ChestESP.clickedBlocks.clear();
         joinSkyblock = true;
         Refuel.drillNPC = null;
         BasePathfinder.path = null;
         GuiFeatures.clicked = false;
         config.autoMacroKeyToggle = false;
         P4BlockCommand.emeraldPos = null;
      }

   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onOpenGui(GuiOpenEvent event) {
      if (event.gui instanceof GuiMainMenu) {
         if (!UpdateGui.shownGui) {
            UpdateGui.checkForUpdates();
         }

      } else {
         if (!(event.gui instanceof GuiIngameMenu) && FakeRotater.rotater != null && mc.field_71439_g != null) {
            FakeRotater.rotater.onOpenGui();
            FakeRotater.rotater = null;
         }

      }
   }

   public static void displayScreen(GuiScreen screen) {
      tickTask = () -> {
         mc.func_147108_a(screen);
         tickTask = null;
      };
   }

   public static void loadResponse() {
      response = Utils.getJson("https://gist.githubusercontent.com/PizzaboiBestLegit/e86e41c230949e51309c621548be55aa/raw/pizzaclient").getAsJsonObject();
      Blacklist.shittersOut();
   }

   public static void delayAddChatMessage(String text) {
      tickTask = () -> {
         mc.field_71439_g.func_145747_a(new ChatComponentText(text));
         tickTask = null;
      };
   }

   public static void delayAddChatMessage(ChatComponentText text) {
      tickTask = () -> {
         mc.field_71439_g.func_145747_a(text);
         tickTask = null;
      };
   }

   public static void addChatMessage(String text) {
      mc.field_71439_g.func_145747_a(new ChatComponentText(text));
   }

   public static void addChatMessage(ChatComponentText text) {
      mc.field_71439_g.func_145747_a(text);
   }

   public static void addModMessage(String text) {
      mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + text));
   }

   private static void register(Object target) {
      MinecraftForge.EVENT_BUS.register(target);
   }

   private static void registerCommand(ICommand command) {
      ClientCommandHandler.instance.func_71560_a(command);
   }

   static {
      username = mc.func_110432_I().func_111285_a();
      keyBindings = new KeyBinding[8];
      modDir = new File(new File(mc.field_71412_D, "config"), "pizzaclient");
      location = Locations.NULL;
   }
}
