package qolskyblockmod.pizzaclient.util.shader.shaders;

import qolskyblockmod.pizzaclient.util.shader.ChromaShader;

public class TexturedChromaShader extends ChromaShader {
   public static final TexturedChromaShader instance = new TexturedChromaShader();

   public TexturedChromaShader() {
      super("chroma/texturedVertex", "chroma/texturedChromaFragment");
   }
}
