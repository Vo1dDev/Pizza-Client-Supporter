#version 120

uniform vec2 offset;

uniform sampler2D texture;

void main() {
    float innerAlpha = texture2D(texture, gl_TexCoord[0].xy).a;
    if (innerAlpha != 0.0) {
        gl_FragColor.a = 0;
    } else {
        innerAlpha += texture2D(texture, gl_TexCoord[0].xy + offset).a + texture2D(texture, gl_TexCoord[0].xy - offset).a;
        innerAlpha += texture2D(texture, gl_TexCoord[0].xy + offset * 2.0).a + texture2D(texture, gl_TexCoord[0].xy - offset * 2.0).a;
        gl_FragColor.a = innerAlpha;
    }
}