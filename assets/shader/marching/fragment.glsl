#version 140

in vec3 passColor;

out vec4 fragColor;

void main(void) {
    fragColor = vec4(passColor, 1.0);
}
