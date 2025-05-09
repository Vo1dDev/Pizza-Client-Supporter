#version 120

uniform vec4 color;

uniform sampler2D outTexture;

void main() {
    vec4 src = texture2D(outTexture, gl_TexCoord[0].st);
    gl_FragColor = vec4(mix(src.rgb, color.rgb, color.a), src.a);
}