package engine.IO;


import java.util.ArrayList;
import java.util.List;

public class MapRenderer {
    private final Map map;
    private int vao, vbo;
    private int vertexCount;
    private final int tileSize;


    public MapRenderer(Map map, int tileSize){
        this.map = map;
        this.tileSize = tileSize;

    }

public void init(){
    List <Float> vertices = new ArrayList<>();

    for (int row = 0; row < map.height; row++){
        for (int col = 0; col < map.width; col++){
            if (map.mapTiles[row][col] == 1){
                float x = col * tileSize;
                float y = row * tileSize;

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
    vertexCount = vertexArray.length / 2;
}

public void render(Shader shader){

}

public void destroy(){

}


}
