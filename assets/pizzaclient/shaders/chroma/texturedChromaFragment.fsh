#version 120

uniform float chromaSize;
uniform float timeOffset;
uniform float saturation;

uniform sampler2D outTexture;

varying vec2 outTextureCoords;
varying vec4 outColor;

vec3 hsb2rgb_smooth(vec3 c) {
    vec3 rgb = clamp(abs(mod(c.x * 6.0 + vec3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
    rgb = rgb * rgb * (3.0 - 2.0 * rgb);
    return c.z * mix(vec3(1.0), rgb, c.y);
}

void main() {
    vec4 originalColor = texture2D(outTexture, outTextureCoords) * outColor;
    float hue = mod(((gl_FragCoord.x - gl_FragCoord.y) / chromaSize) - timeOffset, 1.0);
    gl_FragColor = vec4(hsb2rgb_smooth(vec3(hue, saturation, 1.0)), originalColor.a);
}