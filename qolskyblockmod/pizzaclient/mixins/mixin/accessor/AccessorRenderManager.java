package qolskyblockmod.pizzaclient.mixins.mixin.accessor;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderManager.class})
public interface AccessorRenderManager {
   @Accessor("renderOutlines")
   boolean getRenderOutlines();
}
