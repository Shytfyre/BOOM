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
    float viewSize = 400f;





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
        projectionMatrix = new Matrix4f().perspective((float)Math.toRadians(60), aspect, 0.1f, 1000f);




        mapRenderer = new MapRenderer(map, tileSize, screenWidth, screenHeight);
        mapRenderer.init();
        shader = new Shader("resources/shader/shader.vert", "resources/shader/shader.frag");
        camera = new Camera(0, 0, 0);
        camera.setZ(50f);

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
        float rotation = camera.getRotation();
        float moveSpeed = 5.0f;
        float rotationSpeed = 0.002f;

        float forwardX = -(float) Math.cos(rotationSpeed - Math.PI / 2);
        float forwardY = (float) Math.sin(rotationSpeed - Math.PI / 2);
        float rightX   = -(float) Math.cos(rotationSpeed);
        float rightY   = (float) Math.sin(rotationSpeed);

        float dx = 0;
        float dy = 0;

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)){
            dx += forwardX * moveSpeed;
            dy += forwardY * moveSpeed;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_A)){
            dx -= rightX * moveSpeed;
            dy -= rightY * moveSpeed;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            dx -= forwardX * moveSpeed;
            dy -= forwardY * moveSpeed;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)){
            dx += rightX * moveSpeed;
            dy += rightY * moveSpeed;
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


