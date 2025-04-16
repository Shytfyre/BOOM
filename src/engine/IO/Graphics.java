package engine.IO;


import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;


public class Graphics {

    public static int vao;
    public static int vbo;

static float [] vertices = {
        -0.5f,  0.5f,
         0.5f,  0.5f,
         0.5f, -0.5f,

         0.5f, -0.5f,
        -0.5f, -0.5f,
        -0.5f,  0.5f

};
    public static void init(){
        vao = glGenVertexArrays();
        GL30.glBindVertexArray(vao);


        vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }
}