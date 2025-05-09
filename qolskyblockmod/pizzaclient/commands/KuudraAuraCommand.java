package qolskyblockmod.pizzaclient.commands;

import java.util.Iterator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class KuudraAuraCommand extends CommandBase {
   public String func_71517_b() {
      return "kuudra";
   }

   public String func_71518_a(ICommandSender arg0) {
      return "/" + this.func_71517_b();
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length != 1) {
         sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Usage: /kuudra [cannon/shop]"));
      }

      String var3 = args[0];
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -1367713539:
         if (var3.equals("cannon")) {
            var4 = 0;
         }
         break;
      case 3529462:
         if (var3.equals("shop")) {
            var4 = 1;
         }
      }

      Iterator var5;
      Entity entity;
      switch(var4) {
      case 0:
         var5 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         while(var5.hasNext()) {
            entity = (Entity)var5.next();
            if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("RIGHT-CLICK TO MOUNT")) {
               PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, entity);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicked cannon!"));
               return;
            }
         }

         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to find a cannon armor stand!"));
         break;
      case 1:
         var5 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         while(var5.hasNext()) {
            entity = (Entity)var5.next();
            if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("Cannon Perk Shop")) {
               PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, entity);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicked shop!"));
               return;
            }
         }

         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to find the shop armor stand!"));
         break;
      default:
         sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Usage: /kuudra [cannon/shop]"));
      }

   }
}
