package qolskyblockmod.pizzaclient.util.render;

import java.nio.FloatBuffer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import qolskyblockmod.pizzaclient.util.RenderUtil;

public class RenderModel {
   public final int vaoId = this.createVAO();
   public final int vertexCount;

   public RenderModel(float... positions) {
      this.storeData(positions);
      GL30.glBindVertexArray(0);
      this.vertexCount = positions.length / 3;
   }

   private int createVAO() {
      int id = GL30.glGenVertexArrays();
      GL30.glBindVertexArray(id);
      return id;
   }

   private void storeData(float[] data) {
      GL15.glBindBuffer(34962, GL15.glGenBuffers());
      FloatBuffer buffer = RenderUtil.createFloatBuffer(data);
      GL15.glBufferData(34962, buffer, 35044);
      GL20.glVertexAttribPointer(0, 3, 5126, false, 0, 0L);
      GL15.glBindBuffer(34962, 0);
   }

   public void render() {
      GL11.glScalef((float)RenderUtil.resolution.func_78326_a(), (float)RenderUtil.resolution.func_78328_b(), 1.0F);
      GL11.glEnableClientState(32884);
      GL30.glBindVertexArray(this.vaoId);
      GL20.glEnableVertexAttribArray(0);
      GL11.glDrawArrays(7, 0, this.vertexCount);
      GL20.glDisableVertexAttribArray(0);
      GL11.glDisableClientState(32884);
      GL30.glBindVertexArray(0);
      GL11.glScalef(1.0F / (float)RenderUtil.resolution.func_78326_a(), 1.0F / (float)RenderUtil.resolution.func_78328_b(), 1.0F);
   }

   public void render(ScaledResolution resolution) {
      GL11.glScalef((float)resolution.func_78326_a(), (float)resolution.func_78328_b(), 1.0F);
      GL11.glEnableClientState(32884);
      GL30.glBindVertexArray(this.vaoId);
      GL20.glEnableVertexAttribArray(0);
      GL11.glDrawArrays(7, 0, this.vertexCount);
      GL20.glDisableVertexAttribArray(0);
      GL11.glDisableClientState(32884);
      GL30.glBindVertexArray(0);
      GL11.glScalef(1.0F / (float)resolution.func_78326_a(), 1.0F / (float)resolution.func_78328_b(), 1.0F);
   }
}
