
#version 300 es

layout (location = 0) in vec4 vPosition;
layout (location = 1) uniform mat4 uMVPMatrix;

void main() {
    gl_Position = uMVPMatrix * vPosition;
}