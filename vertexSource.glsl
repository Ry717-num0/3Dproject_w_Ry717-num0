#version 140

#extension GL_ARB_explicit_attrib_location : enable

#extension GL_ARB_separate_shader_objects : enable

layout(location = 0) in vec3 vertexPosition_modelspace;
void main(){
    gl_Position.xyz = position;
    gl_Position.w = 1.0;
}


