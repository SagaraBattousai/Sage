#version 300 es

precision mediump float;

uniform vec4 vColour;

void main() {
    gl_FragColor = vColour;
}