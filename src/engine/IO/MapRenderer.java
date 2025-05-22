package engine.IO;
import org.joml.Matrix4f;
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
    private final float wallHeight = 100f;


    public MapRenderer(Map map, int tileSize, int screenWidth, int screenHeight){
        this.map = map;
        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }

public void init(){
    //Testing something for now - this is original code
    //offsetX = -(map.width * tileSize) / 2f;
    //offsetY = -(map.height * tileSize) / 2f;

    float totalWidth  = map.width  * tileSize;
    float totalHeight = map.height * tileSize;

    offsetX = -totalWidth  * 0.5f + tileSize * 0.5f;
    offsetY =  totalHeight * 0.5f - tileSize * 0.5f;


    float[] vertexArray = generateVertexArray();
    vertexCount = vertexArray.length / 3;

    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    vbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);

    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
    glEnableVertexAttribArray(0);

    glBindBuffer(GL_ARRAY_BUFFER,0);
    glBindVertexArray(0);

}



    private float[] generateVertexArray() {
        List<Float> vertices = new ArrayList<>();

        for (int row = 0; row < map.height; row++) {
            for (int col = 0; col < map.width; col++) {
                if (map.mapTiles[row][col] == 1) {
                    float x = col * tileSize + offsetX;
                    float z = -row * tileSize + offsetY;  // Z instead of Y
                    float y0 = 0f;
                    float y1 = wallHeight;

                    boolean leftEmpty   = (col == 0) || map.mapTiles[row][col - 1] == 0;
                    boolean rightEmpty  = (col == map.width - 1) || map.mapTiles[row][col + 1] == 0;
                    boolean frontEmpty  = (row == 0) || map.mapTiles[row - 1][col] == 0;
                    boolean backEmpty   = (row == map.height - 1) || map.mapTiles[row + 1][col] == 0;

                    // FRONT face (−Z)
                    if (frontEmpty) {
                        vertices.add(x);            vertices.add(y0); vertices.add(z);
                        vertices.add(x + tileSize); vertices.add(y0); vertices.add(z);
                        vertices.add(x + tileSize); vertices.add(y1); vertices.add(z);

                        vertices.add(x + tileSize); vertices.add(y1); vertices.add(z);
                        vertices.add(x);            vertices.add(y1); vertices.add(z);
                        vertices.add(x);            vertices.add(y0); vertices.add(z);
                    }

                    // BACK face (+Z)
                    if (backEmpty) {
                        float zBack = z + tileSize;
                        vertices.add(x + tileSize); vertices.add(y0); vertices.add(zBack);
                        vertices.add(x);            vertices.add(y0); vertices.add(zBack);
                        vertices.add(x);            vertices.add(y1); vertices.add(zBack);

                        vertices.add(x);            vertices.add(y1); vertices.add(zBack);
                        vertices.add(x + tileSize); vertices.add(y1); vertices.add(zBack);
                        vertices.add(x + tileSize); vertices.add(y0); vertices.add(zBack);
                    }

                    // LEFT face (−X)
                    if (leftEmpty) {
                        vertices.add(x); vertices.add(y0); vertices.add(z + tileSize);
                        vertices.add(x); vertices.add(y0); vertices.add(z);
                        vertices.add(x); vertices.add(y1); vertices.add(z);

                        vertices.add(x); vertices.add(y1); vertices.add(z);
                        vertices.add(x); vertices.add(y1); vertices.add(z + tileSize);
                        vertices.add(x); vertices.add(y0); vertices.add(z + tileSize);
                    }

                    // RIGHT face (+X)
                    if (rightEmpty) {
                        float xRight = x + tileSize;
                        vertices.add(xRight); vertices.add(y0); vertices.add(z);
                        vertices.add(xRight); vertices.add(y0); vertices.add(z + tileSize);
                        vertices.add(xRight); vertices.add(y1); vertices.add(z + tileSize);

                        vertices.add(xRight); vertices.add(y1); vertices.add(z + tileSize);
                        vertices.add(xRight); vertices.add(y1); vertices.add(z);
                        vertices.add(xRight); vertices.add(y0); vertices.add(z);
                    }
                }
            }
        }

        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexArray[i] = vertices.get(i);
        }

        return vertexArray;
    }


    public void render(Shader shader, Camera camera, Matrix4f projectionMatrix){
        //debugging
        //System.out.println("vertexCount = " + vertexCount);
        shader.bind();

        shader.setUniform("uProjection", projectionMatrix);
        shader.setUniform("uView", camera.getViewMatrix());

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
