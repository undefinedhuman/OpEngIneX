#version 410 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 lightRay;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat3 normalMatrix;

uniform vec3 lightPosition;

void main() {
    vec4 worldPosition = transformMatrix * vec4(position, 1);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    passTextureCoords = textureCoords * 40;

    surfaceNormal = normalMatrix * normal;
    lightRay = lightPosition - worldPosition.xyz;
}
