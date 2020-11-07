#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 lightRay;
out vec3 cameraRay;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat3 normalMatrix;
uniform float time;

uniform vec3 lightPosition;
uniform vec3 cameraPosition;

void main() {
    vec3 newPos = vec3(position.x + sin(time) * 0.1 * position.y, position.y, position.z);
    vec4 worldPosition = transformMatrix * vec4(newPos, 1);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    passTextureCoords = textureCoords;

    surfaceNormal = normalMatrix * vec3(0.0, 1.0, 0.0);
    lightRay = lightPosition - worldPosition.xyz;
    cameraRay = cameraPosition - worldPosition.xyz;
}
