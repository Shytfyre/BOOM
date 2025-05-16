package Game;


import engine.IO.*;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
//debugging shader path
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.nio.file.Path;
// import java.io.IOException;



public class Main implements Runnable {
    public Thread game;
    public Window window;
    private Shader shader;
    private Map map;
    private Camera camera;
    private Matrix4f projectionMatrix;
    private MapRenderer mapRenderer;
    public final int WIDTH = 1500, HEIGHT = 800;
    public final String TITLE = "BOOM";




    public void start() {
        game = new Thread(this, TITLE);
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, TITLE);
        window.setBackgroundColor(255, 255, 255); //white ; 0xFFFFFF in hex
        window.create();
        Graphics.init();
        MapLoader loader = new MapLoader("resources/maps/map.json");
        map = loader.getMap();
        int screenWidth = window.getWidth();
        int screenHeight = window.getHeight();
        int tileSizeW = screenWidth / map.width;
        int tileSizeH = screenHeight / map.height;
        int tileSize = Math.min(tileSizeW, tileSizeH);
        float aspect = (float) screenWidth / screenHeight;
        //projectionMatrix = new Matrix4f().ortho(-aspect, aspect, -1f, 1f, -1f, 1f);
        projectionMatrix = new Matrix4f().ortho(0, screenWidth, screenHeight, 0, -1, 1);



        mapRenderer = new MapRenderer(map, tileSize, screenWidth, screenHeight);
        mapRenderer.init();
        shader = new Shader("resources/shader/shader.vert", "resources/shader/shader.frag");
        camera = new Camera(0, 0, 0);


        // debugging
        // System.out.println("Looking in: " + Paths.get("resources/shader/shader.vert").toAbsolutePath());
        //try {
           // Path path = Paths.get("./resources/shader/shader.vert");
           // String source = Files.readString(path);

            //System.out.println("Vertex shader:\n" + source);

        //} catch (IOException e) {
           // e.printStackTrace();
        //}
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            render();
            update();

            if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                System.out.println("Game has been closed.");
                return;
            }
        }
        destroy();
    }


    private void update() {
        Input.update();

        float moveSpeed = 5.0f;
        float rotationSpeed = 0.002f;
        float dx = 0;
        float dy = 0;

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)){
            dx += (float) Math.cos(camera.getRotation()) * moveSpeed;
            dy += (float) Math.sin(camera.getRotation()) * moveSpeed;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_A)){
            dx += (float) Math.sin(camera.getRotation()) * moveSpeed;
            dy += (float) Math.cos(camera.getRotation()) * moveSpeed;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)){
            dx -= (float) Math.sin(camera.getRotation()) * moveSpeed;
            dy -= (float) Math.cos(camera.getRotation()) * moveSpeed;
        }


        camera.move(dx, dy);

        double centerX = window.getWidth() / 2;
        double centerY = window.getHeight() / 2;
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();

        float deltaX = (float) Input.getDeltaX();
        float deltaY = (float) Input.getDeltaY();

        camera.rotate(-deltaX * rotationSpeed);

        long handle = window.getWindow();

        Input.setCursorPosition(handle, (int)centerX, (int)centerY);

        window.update();

         if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
         System.out.println("X: " + Input.getMouseX() + "| Y: " + Input.getMouseY());
         }

         double scrollY = Input.getScrollY();
            if (scrollY != 0) {
                if (scrollY > 0) {
                    System.out.println("Scroll up" + scrollY);
                }
                else {
                    System.out.println("Scroll down" + scrollY);
                }
                Input.resetScroll();
            }
        }

    private void render() {
        mapRenderer.render(shader, camera, projectionMatrix);
        window.swapBuffers();

    }

    public void destroy() {
        shader.destroy();
        window.destroy();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}


