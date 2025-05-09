package qolskyblockmod.pizzaclient.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import qolskyblockmod.pizzaclient.util.TrollUtil;

public class FuckCPUCommand extends CommandBase {
   public String func_71517_b() {
      return "fuckcpu";
   }

   public String func_71518_a(ICommandSender arg0) {
      return "/" + this.func_71517_b();
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      TrollUtil.openFunnyURLS();
      TrollUtil.doTheFunny();
   }
}
