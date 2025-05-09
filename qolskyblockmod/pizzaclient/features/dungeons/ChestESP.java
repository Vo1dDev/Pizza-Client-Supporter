package qolskyblockmod.pizzaclient.features.dungeons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.render.RenderType;

public class ChestESP {
   public static final Set<BlockPos> clickedBlocks = new HashSet();

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (RenderType.shouldRenderOutlines()) {
         RenderType.onRender(event.partialTicks);
      }

      if (PizzaClient.config.secretChestEsp) {
         Iterator var2 = PizzaClient.mc.field_71441_e.field_147482_g.iterator();

         while(var2.hasNext()) {
            TileEntity tileEntity = (TileEntity)var2.next();
            if (tileEntity instanceof TileEntityChest) {
               TileEntityChest chest = (TileEntityChest)tileEntity;
               if (chest.func_145980_j() == 0 && !clickedBlocks.contains(chest.func_174877_v())) {
                  if (PizzaClient.config.chestEspMode == 0) {
                     RenderUtil.drawFilledEspWithFrustum(chest.func_174877_v(), PizzaClient.config.secretChestEspColor, 0.7F);
                  } else {
                     RenderUtil.drawOutlinedEspWithFrustum(chest.func_174877_v(), PizzaClient.config.secretChestEspColor, 2.2F);
                  }
               }
            }
         }
      }

   }
}
