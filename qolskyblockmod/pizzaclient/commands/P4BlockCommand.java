package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;

public class P4BlockCommand extends CommandBase {
   public static BlockPos emeraldPos;

   public String func_71517_b() {
      return "p4";
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"p4block"});
   }

   public String func_71518_a(ICommandSender arg0) {
      return "/" + this.func_71517_b();
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      emeraldPos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, 63.0D, PizzaClient.mc.field_71439_g.field_70161_v);
      PizzaClient.mc.field_71441_e.func_180501_a(emeraldPos, Blocks.field_150475_bE.func_176223_P(), 3);
   }
}
