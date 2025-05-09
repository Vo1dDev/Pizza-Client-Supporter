package qolskyblockmod.pizzaclient.core.events;

import java.util.List;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChestBackgroundDrawnEvent extends Event {
   public final Container chest;
   public final String displayName;
   public final int chestSize;
   public final List<Slot> slots;
   public final IInventory chestInv;

   public ChestBackgroundDrawnEvent(Container chest, String displayName, int chestSize, List<Slot> slots, IInventory chestInv) {
      this.chest = chest;
      this.displayName = displayName;
      this.chestSize = chestSize;
      this.slots = slots;
      this.chestInv = chestInv;
   }
}
