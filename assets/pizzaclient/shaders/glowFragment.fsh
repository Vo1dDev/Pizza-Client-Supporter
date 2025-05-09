#version 120

uniform vec2 offset;
uniform vec3 color;
uniform bool yOffset;

uniform sampler2D textureIn;

void main() {
    float innerAlpha = texture2D(textureIn, gl_TexCoord[0].st).a * 0.35;
    innerAlpha += (texture2D(textureIn, gl_TexCoord[0].st + offset).a + texture2D(textureIn, gl_TexCoord[0].st - offset).a) * 0.233;
    innerAlpha += (texture2D(textureIn, gl_TexCoord[0].st + offset * 2.0).a + texture2D(textureIn, gl_TexCoord[0].st - offset * 2.0).a) * 0.1;
    innerAlpha += (texture2D(textureIn, gl_TexCoord[0].st + offset * 3.0).a + texture2D(textureIn, gl_TexCoord[0].st - offset * 3.0).a) * 0.02;

    //gl_FragColor = vec4(color, mix(innerAlpha, 1.0 - exp(-innerAlpha * 1.3), step(0.0, yOffset)));
    gl_FragColor = vec4(color, innerAlpha);
}