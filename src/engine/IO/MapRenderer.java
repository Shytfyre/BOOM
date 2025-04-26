package engine.IO;


import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL30.*;

public class MapRenderer {
    private final Map map;
    private int vao, vbo;
    private int vertexCount;
    private final int tileSize;
    private final int screenWidth, screenHeight;
    float offsetX, offsetY;


    public MapRenderer(Map map, int tileSize, int screenWidth, int screenHeight){
        this.map = map;
        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }

public void init(){

    offsetX = (screenWidth - map.width * tileSize) / 2f;
    offsetY = (screenHeight - map.height * tileSize) / 2f;

    float[] vertexArray = generateVertexArray();
    vertexCount = vertexArray.length / 2;

    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    vbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);

    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
    glEnableVertexAttribArray(0);

    glBindBuffer(GL_ARRAY_BUFFER,0);
    glBindVertexArray(0);

}

    private float[] generateVertexArray() {
        List <Float> vertices = new ArrayList<>();

        for (int row = 0; row < map.height; row++){
            for (int col = 0; col < map.width; col++){
                if (map.mapTiles[row][col] == 1){
                    float x = col * tileSize + offsetX;
                    float y = row * tileSize + offsetY;
    
                    vertices.add(x);               vertices.add(y);
                    vertices.add(x + tileSize);    vertices.add(y);
                    vertices.add(x + tileSize);    vertices.add(y + tileSize);
    
                    vertices.add(x + tileSize);    vertices.add(y + tileSize);
                    vertices.add(x);               vertices.add(y + tileSize);
                    vertices.add(x);               vertices.add(y);
                }
            }
        }

        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++){
            vertexArray[i] = vertices.get(i);

        }
        return vertexArray;
    }

    public void render(Shader shader){
        //debugging
        //System.out.println("vertexCount = " + vertexCount);
        shader.bind();
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.unbind();

}

public void destroy(){
glDeleteBuffers(vbo);
glDeleteVertexArrays(vao);
}


}
