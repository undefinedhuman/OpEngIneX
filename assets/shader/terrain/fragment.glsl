#version 410 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 lightRay;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float ambientValue;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(lightRay);

    float dot = dot(unitNormal, unitLight);
    float brightness = max(dot, ambientValue);
    vec3  diffuse = brightness * lightColor;

    out_Color = vec4(diffuse, 1) * texture(textureSampler, passTextureCoords);
}
