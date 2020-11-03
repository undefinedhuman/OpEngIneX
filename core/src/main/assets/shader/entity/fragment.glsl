#version 410 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 lightRay;
in vec3 cameraRay;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float ambientValue;

uniform float specularStrength;
uniform float shineDamper;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(lightRay);
    vec3 unitCamera = normalize(cameraRay);

    float dot = dot(unitNormal, unitLight);
    float brightness = max(dot, ambientValue);
    vec3 diffuse = brightness * lightColor;

    vec3 reflectedLight = reflect(-unitLight, unitNormal);
    float dotCamera = pow(max(dot(unitCamera, reflectedLight), 0.0), shineDamper);
    vec3 specular = specularStrength * dotCamera * lightColor;

    out_Color = vec4((diffuse + specular), 1)  * texture(textureSampler, passTextureCoords);

}
