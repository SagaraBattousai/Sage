
#version 300 es

precision mediump float;

out vec4 fragColour;

uniform vec4 vColour;

void main() {
    fragColour = vColour;
}