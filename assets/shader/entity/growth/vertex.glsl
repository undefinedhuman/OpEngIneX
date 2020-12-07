#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec4 positionInWorldSpace;
out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 toCameraRay;
out float fogFactor;

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

uniform float fogDensity;
uniform float fogPower;

uniform vec3 cameraPosition;

uniform vec4 clipPlane;

const float PI = 3.1415926535897932384626433832795;

void main() {
    positionInWorldSpace = transformMatrix * vec4(position.x, position.y, position.z, 1);

    gl_ClipDistance[0] = dot(positionInWorldSpace, clipPlane);

    float windSin = PI * time * windFrequency;
    float globalWind = sin(windSin + (positionInWorldSpace.x + positionInWorldSpace.z) * 0.8 * windGustsDistance);
    float localWind = sin(windSin + (positionInWorldSpace.x + positionInWorldSpace.z) * 2 * windGustsDistance) * 0.3;

    positionInWorldSpace.x += (globalWind + localWind) * windStrength * windDirection.x * position.y * windFactor;
    positionInWorldSpace.z -= (globalWind - localWind) * windStrength/2 * windDirection.y * position.y * windFactor;

    vec4 positionInCameraSpace = viewMatrix * positionInWorldSpace;

    gl_Position = projectionMatrix * positionInCameraSpace;
    passTextureCoords = textureCoords;

    surfaceNormal = normalMatrix * vec3(0, 1, 0);
    toCameraRay = cameraPosition - positionInWorldSpace.xyz;

    fogFactor = clamp(exp(-pow((length(positionInCameraSpace.xyz) * fogDensity), fogPower)), 0, 1);
}
