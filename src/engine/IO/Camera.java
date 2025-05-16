package engine.IO;
import org.joml.Matrix4f;


public class Camera {

    private float x;
    private float y;
    private float z;
    private float rotation;
    private float fov;
    private float near;
    private float far;
    private float aspectRatio;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;


    public Camera(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }




    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void rotate(float dr) {
        rotation += dr;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f view = new Matrix4f();
        view.identity();
        view.rotate(-rotation, 0, 0, 1);
        view.translate(-x, -y, 0);
        return view;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getRotation(){
        return rotation;
    }
}