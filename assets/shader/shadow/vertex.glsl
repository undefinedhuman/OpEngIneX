#version 140

in vec3 position;
in vec2 textureCoords;

out vec2 passTextureCoords;

uniform mat4 tvpMatrix;

void main(void){
	gl_Position = tvpMatrix * vec4(position, 1.0);
    passTextureCoords = textureCoords;
}