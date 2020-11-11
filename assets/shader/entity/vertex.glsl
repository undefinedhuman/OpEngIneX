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

uniform float windFrequency;
uniform float windGustsDistance;
uniform float windStrength;
uniform float windFactor;
uniform vec2 windDirection;

uniform float time;

uniform vec3 lightPosition;
uniform vec3 cameraPosition;

const float PI = 3.1415926535897932384626433832795;

void main() {
    vec4 worldPosition = transformMatrix * vec4(position.x, position.y, position.z, 1);

    float globalWind = sin(PI * time * windFrequency + (worldPosition.x + worldPosition.z) * 0.8 * windGustsDistance);
    float localWind = sin(PI * time * windFrequency + (worldPosition.x + worldPosition.z) * 2 * windGustsDistance) * 0.3;

    worldPosition.x += (globalWind + localWind) * windStrength * windDirection.x * position.y * windFactor;
    worldPosition.z -= (globalWind - localWind) * windStrength/2 * windDirection.y * position.y * windFactor;

    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    passTextureCoords = textureCoords;

    surfaceNormal = normalMatrix * normal;
    lightRay = lightPosition - worldPosition.xyz;
    cameraRay = cameraPosition - worldPosition.xyz;
}
