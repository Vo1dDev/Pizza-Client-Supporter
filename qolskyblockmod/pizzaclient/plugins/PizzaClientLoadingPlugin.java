package qolskyblockmod.pizzaclient.plugins;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@Name(" ")
public class PizzaClientLoadingPlugin implements IFMLLoadingPlugin {
   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }

   static {
      MixinBootstrap.init();
      Mixins.addConfiguration("mixins.pizzaclient.json");
      MixinEnvironment.getCurrentEnvironment().setObfuscationContext("searge");
   }
}
