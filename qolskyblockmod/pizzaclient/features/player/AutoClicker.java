package qolskyblockmod.pizzaclient.features.player;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class AutoClicker {
   private static boolean toggled;
   private long lastInteractTime;

   @SubscribeEvent
   public void onRenderTick(RenderWorldLastEvent event) {
      if (toggled) {
         if (PizzaClient.mc.field_71462_r == null) {
            this.use();
         } else if (GameSettings.func_100015_a(PizzaClient.keyBindings[0])) {
            toggled = false;
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "AutoClicker is now " + EnumChatFormatting.RED + "Off"));
         }
      } else if (PizzaClient.config.autoClickerHold && PizzaClient.keyBindings[0].func_151470_d()) {
         this.use();
      }

   }

   private void use() {
      if (System.currentTimeMillis() - this.lastInteractTime >= (long)PizzaClient.config.autoClickerDelay) {
         switch(PizzaClient.config.autoClickerType) {
         case 0:
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
            this.updateDelay();
            this.lastInteractTime += (long)MathUtil.clamp(MathUtil.floor(Utils.random.nextGaussian() * 20.0D), -(PizzaClient.config.autoClickerDelay / 2), PizzaClient.config.autoClickerDelay / 2);
            break;
         case 1:
            PlayerUtil.rightClick();
            this.updateDelay();
         case 2:
         default:
            break;
         case 3:
            PlayerUtil.useAbility();
            this.updateDelay();
         }
      }

   }

   private void updateDelay() {
      this.lastInteractTime = System.currentTimeMillis() - this.lastInteractTime % (long)PizzaClient.config.autoClickerDelay;
   }

   public static void toggle() {
      if (PizzaClient.config.autoClickerType == 2) {
         PlayerUtil.rightClick(PizzaClient.config.clickAmount);
         toggled = false;
      } else if (PizzaClient.config.autoClickerHold) {
         toggled = false;
      } else {
         toggled = !toggled;
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "AutoClicker is now " + Utils.getColouredBoolean(toggled)));
      }
   }
}
