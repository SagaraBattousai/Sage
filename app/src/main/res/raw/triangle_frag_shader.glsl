
#version 300 es

precision mediump float;

out vec4 fragColour;

layout (location = 0) uniform vec4 vColour;

void main() {
    fragColour = vColour;
}