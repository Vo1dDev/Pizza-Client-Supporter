package qolskyblockmod.pizzaclient.commands;

import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class ChatSpammerCommand extends CommandBase implements ICommand {
   private final AtomicBoolean chatSpammer = new AtomicBoolean();

   public String func_71517_b() {
      return "chatspammer";
   }

   public String func_71518_a(ICommandSender sender) {
      return EnumChatFormatting.RED + "Usages: /chatspammer add [name] or /chatspammer to toggle";
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      EntityPlayerSP player = (EntityPlayerSP)sender;
      switch(args.length) {
      case 0:
         this.chatSpammer.set(!this.chatSpammer.get());
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Chat Spammer is now " + Utils.getColouredBoolean(this.chatSpammer.get())));
         if (this.chatSpammer.get()) {
            if (PizzaClient.config.chatSpammerName == null) {
               player.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "The name must be an valid ign!"));
               return;
            }

            player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Started the chat spammer for " + EnumChatFormatting.GOLD + PizzaClient.config.chatSpammerName));
            (new Thread(() -> {
               while(this.chatSpammer.get()) {
                  if (PizzaClient.mc.field_71462_r == null) {
                     Utils.sleep(PizzaClient.config.chatSpammerDelay + Utils.betterRandom(4));
                     PizzaClient.mc.field_71439_g.func_71165_d("/p " + PizzaClient.config.chatSpammerName);
                     Utils.sleep(PizzaClient.config.chatSpammerDelay + Utils.betterRandom(4));
                     PizzaClient.mc.field_71439_g.func_71165_d("/p disband");
                  }
               }

            })).start();
         } else {
            player.func_71165_d("/p disband");
         }
         break;
      case 2:
         if (args[0].equalsIgnoreCase("add")) {
            if (args[1] == null) {
               player.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "You must enter a name!"));
               return;
            }

            PizzaClient.config.chatSpammerName = args[1];
            player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "The name has been set to " + EnumChatFormatting.GOLD + PizzaClient.config.chatSpammerName));
            return;
         }

         player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
         break;
      default:
         player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
      }

   }
}
