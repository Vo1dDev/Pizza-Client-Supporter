package qolskyblockmod.pizzaclient.util.exceptions;

import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class APIException extends RuntimeException {
   public APIException(String cause) {
      super("An error occured when reading the api. Cause: " + cause + " See logs for more info.");
   }

   public void printStackTrace() {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + this.getMessage()));
      super.printStackTrace();
   }
}
