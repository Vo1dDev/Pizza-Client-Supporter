package qolskyblockmod.pizzaclient.commands;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.features.misc.BlockAbility;
import qolskyblockmod.pizzaclient.util.ItemUtil;

public class BlockAbilityCommand extends CommandBase {
   public String func_71517_b() {
      return "blockability";
   }

   public List<String> func_71514_a() {
      return Collections.singletonList("disableability");
   }

   public String func_71518_a(ICommandSender sender) {
      return "/blockability [clearall] or [list]";
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      EntityPlayerSP player = (EntityPlayerSP)sender;
      if (args.length == 0) {
         ItemStack item = player.field_71071_by.func_70448_g();
         if (item == null) {
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "Hold the item with the ability that you want to block."));
         } else if (!ItemUtil.hasRightClickAbility(item)) {
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "That item isn't valid."));
         } else {
            String itemId = ItemUtil.getSkyBlockItemID(item);
            if (BlockAbility.blockedItems.contains(itemId)) {
               BlockAbility.blockedItems.remove(itemId);
               BlockAbility.writeSave();
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Removed the ability blocker on " + EnumChatFormatting.GOLD + itemId));
            } else {
               BlockAbility.blockedItems.add(itemId);
               BlockAbility.writeSave();
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Added the ability blocker for " + EnumChatFormatting.GOLD + itemId));
            }

         }
      } else {
         String subcommand = args[0].toLowerCase();
         byte var6 = -1;
         switch(subcommand.hashCode()) {
         case 3322014:
            if (subcommand.equals("list")) {
               var6 = 1;
            }
            break;
         case 790299700:
            if (subcommand.equals("clearall")) {
               var6 = 0;
            }
         }

         switch(var6) {
         case 0:
            BlockAbility.blockedItems.clear();
            BlockAbility.writeSave();
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "Cleared all custom ability blocks."));
            break;
         case 1:
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Blocked Abilities list: " + EnumChatFormatting.AQUA + BlockAbility.blockedItems));
            break;
         default:
            player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
         }

      }
   }
}
