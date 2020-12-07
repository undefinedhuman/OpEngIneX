#version 410 core

in vec2 position;

out vec2 textureCoords;
out vec4 positionInCameraSpace;
out vec4 positionInClipSpace;
out vec3 toCameraRay;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat3 normalMatrix;

uniform vec3 cameraPosition;

const float TEXTURE_TILING = 4;

void main() {
    vec4 positionInWorldSpace = transformMatrix * vec4(position.x, 0.0, position.y, 1.0);
    positionInCameraSpace = viewMatrix * positionInWorldSpace;
    positionInClipSpace = projectionMatrix * positionInCameraSpace;
    gl_Position = positionInClipSpace;
    textureCoords = vec2(position / 2.0 + 0.5) * TEXTURE_TILING;
    toCameraRay = cameraPosition - positionInWorldSpace.xyz;
}
