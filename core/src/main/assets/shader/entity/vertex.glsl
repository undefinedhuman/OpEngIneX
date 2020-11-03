#version 410 core

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

const float PI = 3.1415926535897932384626433832795;

void main() {
    vec4 newPos = vec4(position.x, position.y, position.z, 1);
    newPos.x += sin(time) * 0.1 * position.y;
    vec4 worldPosition = transformMatrix * newPos;
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    passTextureCoords = textureCoords;

    surfaceNormal = normalMatrix * normal;
    lightRay = lightPosition - worldPosition.xyz;
    cameraRay = cameraPosition - worldPosition.xyz;
}
