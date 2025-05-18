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


    public Camera(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = 0f;
    }




    public void move(float dx, float dz) {
        this.x += dx;
        this.z += dz;
    }

    public void rotate(float dr) {
        rotation += dr;
    }

    public Matrix4f getViewMatrix() {

        float cameraX = x;
        float cameraY = y;
        float cameraZ = z;

        Vector3f eye = new Vector3f(cameraX, cameraY, cameraZ);

        float directionX = (float)Math.cos(rotation);
        float directionY = 0f;
        float directionZ = (float)Math.sin(rotation);

        Vector3f center = new Vector3f(cameraX + directionX, cameraY + directionY, cameraZ + directionZ);
        Vector3f up = new Vector3f(0f, 1f, 0f);

        return new Matrix4f().lookAt(eye, center, up);

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

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public void setZ(float z) {
        this.z = z;
    }
    public float getZ() {
        return z;
    }
    public void setY(float y) {
        this.y = y;
    }
}