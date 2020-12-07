#version 410 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 passTextureCoords;

uniform mat4 transformMatrix;

void main() {
    gl_Position = transformMatrix * vec4(position, 1);
    passTextureCoords = textureCoords;
}
