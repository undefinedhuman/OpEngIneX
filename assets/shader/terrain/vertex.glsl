#version 410 core

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

void main() {
    positionInWorldSpace = transformMatrix * vec4(position, 1);
    shadowCoords = toShadowMapSpace * positionInWorldSpace;
    vec4 positionInEyeSpace = viewMatrix * positionInWorldSpace;

    gl_ClipDistance[0] = dot(positionInWorldSpace, clipPlane);

    gl_Position = projectionMatrix * positionInEyeSpace;
    passTextureCoords = textureCoords * TEXTURE_TILING;

    surfaceNormal = normalMatrix * normal;

    fogFactor = clamp(exp(-pow((length(positionInEyeSpace.xyz) * fogDensity), fogPower)), 0, 1);
}
