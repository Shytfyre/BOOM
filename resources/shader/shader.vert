#version 330 core

layout(location = 0) in vec2 aPos;

uniform float u_screenWidth;
uniform float u_screenHeight;

void main() {
    float x = (aPos.x / u_screenWidth) * 2.0 - 1.0;
    float y = (aPos.y / u_screenHeight) * 2.0 - 1.0;
    gl_Position = vec4(x, -y, 0.0, 1.0);
}
