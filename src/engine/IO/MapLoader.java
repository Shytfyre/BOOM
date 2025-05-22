package engine.IO;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MapLoader {
    private final Map map;

public MapLoader(String mapFilePath) {


        try {
            String json = Files.readString(Path.of(mapFilePath));
            this.map = new Gson().fromJson(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load map from: " + mapFilePath + e);
        }
}

public Map getMap(){
    return map;
}
}