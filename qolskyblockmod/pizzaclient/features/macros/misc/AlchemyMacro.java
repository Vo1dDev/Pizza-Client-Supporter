package qolskyblockmod.pizzaclient.features.macros.misc;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class AlchemyMacro extends Macro {
   private boolean clickedBrewingStand;
   private long lastInteractTime;
   private int nextDelay;
   private int phase;

   public void onTick() {
      if (this.lastInteractTime != 0L && System.currentTimeMillis() - this.lastInteractTime >= 15000L) {
         PizzaClient.mc.field_71439_g.func_71053_j();
         this.phase = 0;
         this.clickedBrewingStand = false;
         this.updateValues();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Detected the macro to have stopped, re-starting now!"));
      } else if (PizzaClient.mc.field_71462_r == null) {
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = 8;
         if (PizzaClient.mc.field_71476_x != null && !Rotater.rotating) {
            if (!this.facingBrewingStand()) {
               this.clickedBrewingStand = false;
               this.rotateToBrewingStand();
            } else if (!this.clickedBrewingStand) {
               KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
               this.lastInteractTime = System.currentTimeMillis() + 500L;
               this.nextDelay = Utils.random.nextInt(100);
               this.clickedBrewingStand = true;
            }

         }
      } else {
         Movement.disableMovement();
         PizzaClient.rotater = null;
         if (!PlayerUtil.isInventoryFull() && this.phase != 4) {
            this.phase = 0;
            this.clickedBrewingStand = false;
            if (!(PizzaClient.mc.field_71462_r instanceof GuiContainer)) {
               PizzaClient.mc.field_71439_g.func_71053_j();
            } else if (!(PizzaClient.mc.field_71462_r instanceof GuiInventory)) {
               if (System.currentTimeMillis() - this.lastInteractTime >= (long)(PizzaClient.config.alchemyMacroDelay + this.nextDelay)) {
                  int[] var6 = new int[]{38, 40, 42};
                  int var2 = var6.length;

                  for(int var3 = 0; var3 < var2; ++var3) {
                     int i = var6[var3];
                     ItemStack stack = ((Slot)((GuiChest)PizzaClient.mc.field_71462_r).field_147002_h.field_75151_b.get(i)).func_75211_c();
                     if (stack != null) {
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 1, PizzaClient.mc.field_71439_g);
                        this.updateValues();
                        break;
                     }
                  }
               }

            }
         } else {
            switch(this.phase) {
            case 0:
               if (System.currentTimeMillis() - this.lastInteractTime >= (long)(600 + this.nextDelay)) {
                  PizzaClient.tickTask = () -> {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Emptying inventory..."));
                     PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 89, 0, 0, PizzaClient.mc.field_71439_g);
                     this.updateValues(150);
                     PizzaClient.tickTask = null;
                     this.phase = 1;
                  };
               }

               return;
            case 1:
               if (System.currentTimeMillis() - this.lastInteractTime >= (long)(300 + this.nextDelay) && PizzaClient.mc.field_71462_r instanceof GuiContainer) {
                  ItemStack stack = ((Slot)((GuiContainer)PizzaClient.mc.field_71462_r).field_147002_h.field_75151_b.get(22)).func_75211_c();
                  if (stack != null) {
                     this.updateValues(300);
                     this.phase = 2;
                  }
               }
               break;
            case 2:
               if (System.currentTimeMillis() - this.lastInteractTime >= (long)(400 + this.nextDelay)) {
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, PizzaClient.mc.field_71439_g);
                  this.phase = 3;
                  this.updateValues();
               }
               break;
            case 3:
               if (PizzaClient.mc.field_71462_r instanceof GuiContainer && Utils.getContainerNameTrimmed().equals("Trades")) {
                  this.updateValues();
                  (new Thread(() -> {
                     try {
                        Utils.sleep(500 + Utils.random.nextInt(200));
                        List<Slot> slots = PizzaClient.mc.field_71439_g.field_71070_bA.field_75151_b;

                        for(int i = 53; i < slots.size(); ++i) {
                           this.lastInteractTime = System.currentTimeMillis();
                           Slot slot = (Slot)slots.get(i);
                           ItemStack stack = ((Slot)slots.get(i)).func_75211_c();
                           if (stack == null) {
                              Utils.sleep(10 + Utils.random.nextInt(5));
                           } else {
                              Item item = stack.func_77973_b();
                              if (item != Items.field_151068_bn && item != Items.field_151069_bo) {
                                 Utils.sleep(10 + Utils.random.nextInt(5));
                              } else {
                                 Utils.sleep(PizzaClient.config.alchemyMacroDelay + Utils.random.nextInt(110));
                                 PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot.field_75222_d, 0, 1, PizzaClient.mc.field_71439_g);
                              }
                           }
                        }

                        Utils.sleep(250 + Utils.random.nextInt(100));
                        PizzaClient.mc.field_71439_g.func_71053_j();
                        Utils.sleep(300 + Utils.random.nextInt(100));
                        this.phase = 0;
                     } catch (Exception var6) {
                        var6.printStackTrace();
                        PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Caught an exception with the alchemy macro. Please report this."));
                     }

                  })).start();
                  this.phase = 4;
               }
            }

         }
      }
   }

   private void rotateToBrewingStand() {
      Vec3 stand = this.getBrewingStand();
      if (stand == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no brewing stand."));
      } else {
         (new Rotater(stand)).setRotationAmount(17).addRandomRotateAmount().rotate();
      }
   }

   private Vec3 getBrewingStand() {
      Iterator var1 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 4.0D, PizzaClient.mc.field_71439_g.field_70163_u - 2.0D, PizzaClient.mc.field_71439_g.field_70161_v - 4.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 4.0D, PizzaClient.mc.field_71439_g.field_70163_u + 2.0D, PizzaClient.mc.field_71439_g.field_70161_v + 4.0D)).iterator();

      while(var1.hasNext()) {
         BlockPos pos = (BlockPos)var1.next();
         if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150382_bo) {
            Vec3 hittable = VecUtil.getClosestHittableToMiddle(pos);
            if (hittable != null) {
               return hittable;
            }
         }
      }

      return null;
   }

   private void updateValues() {
      this.nextDelay = Utils.random.nextInt(110);
      this.lastInteractTime = System.currentTimeMillis();
   }

   private void updateValues(int randomness) {
      this.nextDelay = Utils.random.nextInt(randomness);
      this.lastInteractTime = System.currentTimeMillis();
   }

   private boolean facingBrewingStand() {
      return PizzaClient.mc.field_71476_x != null && PizzaClient.mc.field_71476_x.func_178782_a() != null && PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c() == Blocks.field_150382_bo;
   }

   public void onToggle(boolean toggled) {
      this.addToggleMessage("Alchemy Macro");
      if (toggled) {
         PlayerUtil.setSpawnPoint();
      }

      this.clickedBrewingStand = false;
      this.phase = 0;
   }

   public void warpBack() {
      Locations.PRIVATEISLAND.warpTo();
      this.clickedBrewingStand = false;
      this.phase = 0;
   }

   public boolean applyFailsafes() {
      return true;
   }

   public boolean applyPositionFailsafe() {
      return true;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return false;
   }

   public boolean acceptGui() {
      return true;
   }

   public Locations getLocation() {
      return Locations.PRIVATEISLAND;
   }
}
