package Game;


import engine.IO.*;
import org.lwjgl.glfw.GLFW;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;



public class Main implements Runnable {
    public Thread game;
    public Window window;
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
        //System.out.println("Looking in: " + Paths.get("resources/shaders/shader.vert").toAbsolutePath());
        try {
            Path path = Paths.get("./resources/shader/shader.vert");
            String source = Files.readString(path);
            System.out.println("Vertex shader:\n" + source);

        } catch (IOException e) {
            e.printStackTrace();
        }


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
        window.destroy();
    }


    private void update() {
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



        window.render();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}


