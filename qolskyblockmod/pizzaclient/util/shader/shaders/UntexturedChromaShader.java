package qolskyblockmod.pizzaclient.util.shader.shaders;

import qolskyblockmod.pizzaclient.util.shader.ChromaShader;

public class UntexturedChromaShader extends ChromaShader {
   public static final UntexturedChromaShader instance = new UntexturedChromaShader();

   public UntexturedChromaShader() {
      super("chroma/chromaVertex", "chroma/chromaFragment");
   }
}
