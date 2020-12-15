#version 140

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec4 positionInWorldSpace;
out vec2 passTextureCoords;
out vec3 surfaceNormal;
out float fogFactor;
out vec4 shadowCoords;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat3 normalMatrix;

uniform mat4 shadowMapMatrix;

uniform float fogDensity;
uniform float fogPower;

uniform vec4 clipPlane;

const float TEXTURE_TILING = 40;
const float shadowDistance = 100;
const float shadowTransition = 10;

void main() {
    positionInWorldSpace = transformMatrix * vec4(position, 1);
    shadowCoords = shadowMapMatrix * positionInWorldSpace;
    vec4 positionInEyeSpace = viewMatrix * positionInWorldSpace;

    gl_ClipDistance[0] = dot(positionInWorldSpace, clipPlane);

    gl_Position = projectionMatrix * positionInEyeSpace;
    passTextureCoords = textureCoords * TEXTURE_TILING;

    surfaceNormal = normalMatrix * normal;

    float distance = length(positionInEyeSpace.xyz);
    fogFactor = clamp(exp(-pow(distance * fogDensity, fogPower)), 0, 1);

    distance = distance - (shadowDistance - shadowTransition);
    distance = distance / shadowDistance;
    shadowCoords.w = clamp(1.0-distance, 0, 1.0);
}
