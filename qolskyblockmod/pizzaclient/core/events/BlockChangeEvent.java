package qolskyblockmod.pizzaclient.core.events;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class BlockChangeEvent extends Event {
   public final IBlockState oldState;
   public final IBlockState currentState;
   public final BlockPos pos;

   public BlockChangeEvent(IBlockState oldState, IBlockState currentState, BlockPos pos) {
      this.currentState = currentState;
      this.oldState = oldState;
      this.pos = pos;
   }

   public Block getOldBlock() {
      return this.oldState.func_177230_c();
   }

   public Block getNewBlock() {
      return this.currentState.func_177230_c();
   }
}
