#version 410 core

in vec3 position;

uniform mat4 tvpMatrix;

void main(void){
	gl_Position = tvpMatrix * vec4(position, 1.0);
}