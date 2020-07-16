
#version 300 es

layout (location = 0) in vec4 vPosition;
layout (location = 4) in vec3 normalVector;

layout (location = 1) uniform mat4 model;
layout (location = 2) uniform mat4 view;
layout (location = 3) uniform mat4 projection;

out vec3 fragNormal;
out vec3 fragPosition;

void main() {
    gl_Position = projection * view * model * vPosition;
    fragPosition = vec3(model * vPosition);
    fragNormal = normalVector;
}