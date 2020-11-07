#version 400 core

in vec3 position;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat3 normalMatrix;

void main() {
    vec4 worldPosition = transformMatrix * vec4(position, 1);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
}
