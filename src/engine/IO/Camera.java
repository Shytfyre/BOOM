package engine.IO;
import org.joml.Matrix4f;
import org.joml.Vector3f;


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

        float cameraX = x;
        float cameraY = y;
        float cameraZ = z;

        float directionX = (float) Math.cos(rotation - Math.PI / 2);
        float directionY = (float) Math.sin(rotation - Math.PI / 2);
        float directionZ = 0f;

        Vector3f eye = new Vector3f(cameraX, cameraY, cameraZ);
        Vector3f center = new Vector3f(cameraX + directionX, cameraY + directionY, cameraZ + directionZ);
        Vector3f up = new Vector3f(0f, 0f, 1f);

        view.identity();
        view.rotate(-rotation, 0, 0, 1);
        view.translate(-x, -y, 0);

        return view.lookAt(eye, center, up);
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

    public void setZ(float z) {
        this.z = z;
    }
    public float getZ() {
        return z;
    }
}