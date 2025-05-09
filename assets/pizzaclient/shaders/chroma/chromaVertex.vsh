#version 120

out vec4 outColor;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    outColor = gl_Color;
}
