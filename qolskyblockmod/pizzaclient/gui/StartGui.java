package qolskyblockmod.pizzaclient.gui;

import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.Config;
import qolskyblockmod.pizzaclient.gui.components.BetterButton;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.font.renderer.FontUtil;
import qolskyblockmod.pizzaclient.util.shader.ChromaShader;
import qolskyblockmod.pizzaclient.util.shader.shaders.BlurShader;
import qolskyblockmod.pizzaclient.util.shader.shaders.TexturedChromaShader;

public class StartGui extends GuiScreen {
   public boolean func_73868_f() {
      return false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(new BetterButton(0, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 100, 190, 20, "Config"));
      this.field_146292_n.add(new BetterButton(1, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 125, 190, 20, "Edit Button Locations"));
      this.field_146292_n.add(new BetterButton(2, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 150, 190, 20, "Send Commands List"));
      this.field_146292_n.add(new BetterButton(3, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 175, 190, 20, "Join the discord!"));
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      PlayerUtil.walkInInventory();
      BlurShader.renderBlurryBackground();
      GL11.glScalef(10.0F, 10.0F, 0.0F);
      ChromaShader.setSize(90.0F);
      TexturedChromaShader.instance.applyShader();
      FontUtil.drawCenteredStringNoEvent("PizzaClient", (int)((float)this.field_146294_l / 2.0F / 10.0F), (int)(((float)this.field_146295_m / 4.0F - 75.0F) / 10.0F), 65535);
      ChromaShader.endShaderResetSize();
      GL11.glScalef(0.1F, 0.1F, 0.0F);
      Iterator var4 = this.field_146292_n.iterator();

      while(var4.hasNext()) {
         GuiButton button = (GuiButton)var4.next();
         button.func_146112_a(this.field_146297_k, mouseX, mouseY);
      }

   }

   protected void func_146284_a(GuiButton button) {
      switch(button.field_146127_k) {
      case 0:
         this.field_146297_k.func_147108_a(PizzaClient.config.gui());
         break;
      case 1:
         this.field_146297_k.func_147108_a(new LocationEditGui());
         break;
      case 2:
         Config.sendCommandsList();
         this.field_146297_k.field_71439_g.func_71053_j();
         break;
      case 3:
         Utils.openUrl("https://discord.gg/NWeacCr3B8");
      }

   }
}
