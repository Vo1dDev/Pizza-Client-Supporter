#version 120

uniform vec2 offset;

uniform sampler2D outTexture;

void main() {
    vec3 blr = texture2D(outTexture, gl_TexCoord[0].st).rgb * 0.09973557;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - offset).rgb) * 0.096667;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 2.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 2.0 * offset).rgb) * 0.08801633;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 3.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 3.0 * offset).rgb) * 0.075284354;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 4.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 4.0 * offset).rgb) * 0.0604927;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 5.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 5.0 * offset).rgb) * 0.045662273;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 6.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 6.0 * offset).rgb) * 0.0323794;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 7.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 7.0 * offset).rgb) * 0.02156933;
    blr += (texture2D(outTexture, gl_TexCoord[0].st + 8.0 * offset).rgb + texture2D(outTexture, gl_TexCoord[0].st - 8.0 * offset).rgb) * 0.01349774;

    gl_FragColor = vec4(blr, 1.0);
}