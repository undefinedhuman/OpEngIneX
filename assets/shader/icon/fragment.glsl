#version 140

in vec2 passTextureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {
    fragColor = texture(textureSampler, passTextureCoords);
}
