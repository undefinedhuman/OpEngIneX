#version 140

in vec2 passTextureCoords;

out vec4 fragColor;

uniform sampler2D texture;

void main(void) {
	fragColor = vec4(1.0);
}