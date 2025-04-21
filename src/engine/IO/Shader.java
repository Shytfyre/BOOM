package engine.IO;

import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Shader {
    private int vertShaderID;
    private int fragShaderID;
    private String sourceVert;
    private String sourceFrag;
    private int shaderID;




    public Shader(String vertPath, String fragPath){


    //Path vertPath = Paths.get("./resources/shader/shader.vert");
    //Path fragPath = Paths.get("./resources/shader/shader.frag");

    try {
        sourceVert = Files.readString(Path.of(vertPath));
        sourceFrag = Files.readString(Path.of(fragPath));

        vertShaderID = glCreateShader(GL_VERTEX_SHADER);
        fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertShaderID, sourceVert);
        glCompileShader(vertShaderID);

        glShaderSource(fragShaderID, sourceFrag);
        glCompileShader(fragShaderID);

        System.out.println("Shader initialized successfully");


        int vertInit = glGetShaderi(vertShaderID, GL_COMPILE_STATUS);
        if (vertInit == GL_FALSE) {
            String errorMessage = glGetShaderInfoLog(vertShaderID);
            System.err.println("Vertex shader initialization error:\n" + errorMessage);
        }

        int fragInit = glGetShaderi(fragShaderID, GL_COMPILE_STATUS);
        if (fragInit == GL_FALSE) {
            String errorMessage = glGetShaderInfoLog(fragShaderID);
            System.err.println("Vertex shader initialization error:\n" + errorMessage);
        }

        shaderID = glCreateProgram();
        glAttachShader(shaderID, vertShaderID);
        glAttachShader(shaderID, fragShaderID);
        glLinkProgram(shaderID);

        int linked = glGetProgrami(shaderID, GL_LINK_STATUS);
        if (linked == GL_FALSE) {
            String errorMessage = glGetProgramInfoLog(shaderID);
            System.err.println("Shader program linking failed:\n" + errorMessage);
        }

        glDeleteShader(vertShaderID);
        glDeleteShader(fragShaderID);



    }
    catch (IOException e){
        System.out.println("Shader initialization error:\n ");
        e.printStackTrace();
    }






}

    public void bind(){
        glUseProgram(shaderID);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void destroy() {
        glDeleteProgram(shaderID);
    }
}
