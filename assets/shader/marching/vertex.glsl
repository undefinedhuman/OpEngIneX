#version 140

in vec3 position;

out vec3 passColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);
    passColor = vec3(position / 16);
}
