
#version 300 es

precision mediump float;

out vec4 fragColour;

layout (location = 5) uniform vec4 objectColour;
layout (location = 6) uniform vec3 lightColour;
layout (location = 7) uniform vec3 lightPosition;

in vec3 fragNormal;
in vec3 fragPosition;

in vec2 TexCoord;

layout (location = 9) uniform sampler2D ourTexture;



void main() {
    /*
    float ambiantStrength = 0.2;
    vec3 ambiant = ambiantStrength * lightColour;

    vec3 norm = normalize(fragNormal);
    vec3 lightDir = normalize(lightPosition - fragPosition);

    vec3 diffuse = max(dot(norm, lightDir), 0.0) * lightColour;


    vec3 visualColour = (ambiant + diffuse) * fragNormal;//vec3(objectColour);
    fragColour = vec4(visualColour, 1.0);
    */
    //vec3 fragNorma = (fragNormal * fragNormal);
    fragColour = texture(ourTexture, TexCoord);//vec4(0.5, 0.7, 1.0, 1.0);//vec4(fragNorma.x, fragNorma.y, fragNorma.z, 1.0);
}