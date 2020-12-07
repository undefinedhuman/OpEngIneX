#version 140

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(void) {
    fragColor = texture(textureSampler, textureCoords);
}
