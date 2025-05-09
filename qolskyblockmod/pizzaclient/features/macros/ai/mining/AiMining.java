package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.helper.BlockPosRotationHelper;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class AiMining {
   public Set<Block> miningBlocks = new HashSet();
   public BlockPos currentBlock;
   public Vec3 blockVec;
   public static final Set<Block> mineables;
   public long lastBlockSwitch;
   private boolean fixing;
   public final List<BlockPos> hittables = new ArrayList();
   private final Map<Block, CustomBlock> customBlocks;

   public AiMining(Block block) {
      this.miningBlocks.add(block);
      this.customBlocks = new HashMap();
   }

   public AiMining(Set<Block> blocks) {
      this.miningBlocks = blocks;
      this.customBlocks = new HashMap();
   }

   public AiMining(Block block, Map<Block, CustomBlock> map) {
      this.miningBlocks.add(block);
      this.customBlocks = map;
   }

   public AiMining(Set<Block> blocks, Map<Block, CustomBlock> map) {
      this.miningBlocks = blocks;
      this.customBlocks = map;
   }

   public boolean findBlock(Block block, int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Frustum frustum = RenderUtil.setupFrustrum();
      Iterator var5 = this.hittables.iterator();

      while(var5.hasNext()) {
         BlockPos pos = (BlockPos)var5.next();
         if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == block && frustum.func_78546_a(block.func_180646_a(PizzaClient.mc.field_71441_e, pos))) {
            helper.compare(pos);
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public boolean findBlock(Block block, PropertyEnum<?> property, Enum<?> value, int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Frustum frustum = RenderUtil.setupFrustrum();
      Iterator var7 = this.hittables.iterator();

      while(var7.hasNext()) {
         BlockPos pos = (BlockPos)var7.next();
         IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         if (state.func_177230_c() == block && frustum.func_78546_a(block.func_180646_a(PizzaClient.mc.field_71441_e, pos)) && state.func_177229_b(property) == value) {
            helper.compare(pos);
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public boolean findTitanium(int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Frustum frustum = RenderUtil.setupFrustrum();
      Iterator var4 = this.hittables.iterator();

      while(var4.hasNext()) {
         BlockPos pos = (BlockPos)var4.next();
         IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         if (state.func_177230_c() == Blocks.field_150348_b && state.func_177229_b(BlockStone.field_176247_a) == EnumType.DIORITE_SMOOTH && frustum.func_78546_a(Blocks.field_150348_b.func_180646_a(PizzaClient.mc.field_71441_e, pos))) {
            helper.compare(pos);
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public boolean findBlock(Set<Block> blocks, int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Frustum frustum = RenderUtil.setupFrustrum();
      Iterator var5 = this.hittables.iterator();

      while(var5.hasNext()) {
         BlockPos pos = (BlockPos)var5.next();
         Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
         if (blocks.contains(block) && frustum.func_78546_a(block.func_180646_a(PizzaClient.mc.field_71441_e, pos))) {
            helper.compare(pos);
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public boolean findBlockNoFrustrum(Set<Block> blocks, int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Iterator var4 = this.hittables.iterator();

      while(var4.hasNext()) {
         BlockPos pos = (BlockPos)var4.next();
         if (blocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
            helper.compare(pos);
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public boolean findBlock(Block block, Block custom, CustomBlock customBlock, int rotateAmount) {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Frustum frustum = RenderUtil.setupFrustrum();
      Iterator var7 = this.hittables.iterator();

      while(var7.hasNext()) {
         BlockPos pos = (BlockPos)var7.next();
         IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         Block bl = state.func_177230_c();
         if (frustum.func_78546_a(block.func_180646_a(PizzaClient.mc.field_71441_e, pos))) {
            if (block == bl) {
               helper.compare(pos);
            } else if (bl == custom && state.func_177229_b(customBlock.property) == customBlock.value) {
               helper.compare(pos);
            }
         }
      }

      if (helper.bestPos != null) {
         this.setValues(helper.bestPos, rotateAmount);
         return true;
      } else {
         return false;
      }
   }

   public void disable() {
      this.currentBlock = null;
      this.fixing = false;
   }

   public void rotate(Vec3 vec, int rotation) {
      if (vec != null) {
         (new Rotater(vec)).antiSus((float)rotation).rotate();
      } else {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Vec returned null somehow."));
         this.currentBlock = null;
         this.onMove();
      }

   }

   public boolean onTick() {
      if (this.blockVec == null) {
         this.blockVec = PizzaClient.mc.field_71476_x.field_72307_f;
      }

      if (this.currentBlock != null && PizzaClient.mc.field_71441_e.func_180495_p(this.currentBlock).func_177230_c() == Blocks.field_150357_h) {
         this.currentBlock = null;
         return this.fixing;
      } else if (this.fixing) {
         return true;
      } else if (this.currentBlock != null && System.currentTimeMillis() - this.lastBlockSwitch >= (long)PizzaClient.config.mithrilMacroFixTime * 1000L) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Detected not mining. If you were still mining a block, change the mithril macro fix time in the config."));
         this.fixing = true;
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
         (new Thread(() -> {
            this.onMove();
            Utils.sleep(350 + Utils.random.nextInt(200));
            this.rotateToClosestHitttable();

            while(Rotater.rotating) {
               Utils.sleep(1);
            }

            Utils.sleep(500);
            this.fixing = false;
            this.lastBlockSwitch = System.currentTimeMillis();
         })).start();
         return true;
      } else {
         return false;
      }
   }

   public void onToggle() {
      this.blockVec = PizzaClient.mc.field_71476_x.field_72307_f;
      this.lastBlockSwitch = System.currentTimeMillis();
      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getMiningTool();
      this.fixing = false;
   }

   public static boolean isBlockUnmineable(Block blockIn) {
      return blockIn instanceof BlockFence || blockIn instanceof BlockStairs;
   }

   public void render() {
      if (this.currentBlock != null) {
         RenderUtil.drawFilledEsp(this.currentBlock, Color.CYAN, 0.5F);
      }

      try {
         Iterator var1 = this.hittables.iterator();

         while(var1.hasNext()) {
            BlockPos pos = (BlockPos)var1.next();
            RenderUtil.drawOutlinedEspWithFrustum(pos, Color.CYAN);
         }
      } catch (ConcurrentModificationException var3) {
      }

   }

   public void render(Color c) {
      if (this.currentBlock != null) {
         RenderUtil.drawFilledEsp(this.currentBlock, c, 0.5F);
      }

      try {
         Iterator var2 = this.hittables.iterator();

         while(var2.hasNext()) {
            BlockPos pos = (BlockPos)var2.next();
            RenderUtil.drawOutlinedEspWithFrustum(pos, c);
         }
      } catch (ConcurrentModificationException var4) {
      }

   }

   public void onMove() {
      this.hittables.clear();
      Iterator var1;
      BlockPos pos;
      if (this.customBlocks.size() == 0) {
         var1 = PlayerUtil.getPlayerBlocks().iterator();

         while(var1.hasNext()) {
            pos = (BlockPos)var1.next();
            if (this.miningBlocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()) && (double)pos.func_177956_o() != PizzaClient.mc.field_71439_g.field_70163_u - 1.0D && !Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
               this.hittables.add(pos);
            }
         }
      } else {
         var1 = PlayerUtil.getPlayerBlocks().iterator();

         while(var1.hasNext()) {
            pos = (BlockPos)var1.next();
            IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
            Block block = state.func_177230_c();
            if (this.miningBlocks.contains(block)) {
               if ((double)pos.func_177956_o() != PizzaClient.mc.field_71439_g.field_70163_u - 1.0D && !Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
                  this.hittables.add(pos);
               }
            } else if (this.customBlocks.containsKey(block)) {
               CustomBlock customBlock = (CustomBlock)this.customBlocks.get(block);
               if (state.func_177229_b(customBlock.property) == customBlock.value && (double)pos.func_177956_o() != PizzaClient.mc.field_71439_g.field_70163_u - 1.0D && !Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
                  this.hittables.add(pos);
               }
            }
         }
      }

   }

   public void setValues(BlockPos closestBlock, int rotateAmount) {
      if (closestBlock != null) {
         this.currentBlock = closestBlock;
         this.lastBlockSwitch = System.currentTimeMillis();
         this.blockVec = VecUtil.getClosestHittableToMiddle(closestBlock);
         if (this.blockVec == null) {
            this.hittables.remove(closestBlock);
         } else {
            this.rotate(this.blockVec, rotateAmount);
         }
      }
   }

   public void setValues(Vec3 closestBlock, int rotateAmount) {
      this.setValues(new BlockPos(closestBlock), rotateAmount);
   }

   private void rotateToClosestHitttable() {
      BlockPosRotationHelper helper = new BlockPosRotationHelper();
      Iterator var2 = this.hittables.iterator();

      while(var2.hasNext()) {
         BlockPos pos = (BlockPos)var2.next();
         if (!pos.equals(this.currentBlock)) {
            helper.compare(pos);
         }
      }

      if (helper.isNotNull()) {
         this.currentBlock = helper.bestPos;
         (new Rotater(VecUtil.getClosestHittableToMiddle(helper.bestPos))).antiSus((float)PizzaClient.config.mithrilRotateAmount).rotate();
      } else {
         float rand = MathUtil.randomFloat(3.0F);
         float pitch = MathUtil.randomFloat(3.0F);
         (new Rotater(60.0F + rand, pitch)).rotate();

         while(Rotater.rotating) {
            Utils.sleep(1);
         }

         Utils.sleep(50 + Utils.random.nextInt(50));
         (new Rotater(-(60.0F + rand), -pitch)).rotate();
      }
   }

   public void update() {
      this.lastBlockSwitch = System.currentTimeMillis();
      this.fixing = false;
      this.onMove();
   }

   static {
      mineables = new HashSet(Arrays.asList(Blocks.field_150365_q, Blocks.field_150366_p, Blocks.field_150450_ax, Blocks.field_150352_o, Blocks.field_150482_ag, Blocks.field_150348_b, Blocks.field_150369_x, Blocks.field_150412_bA));
   }
}
