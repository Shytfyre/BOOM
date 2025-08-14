package engine.IO;

public class CollisionTest {

/* 3 vectors in front of camera to detect camera-wall intersection

        float vectorStart = camera.position
        float vectorLength = ??

   the side-vectors need to be positioned with a certain degree to allow for corner detection

        vector1 = camera.position //straight
        vector2 = baseAngle - 0.785f //left - degree can be changed
        vector3 =  baseAngle + 0.785f //right - degree can be changed

   now the vectors need to check for wall-intersections

        In Input/Movement Class:

            while(vector1, vector2, vector 3 != wallVertex) {
                (Movement as usual) }

        Outside the Movement Class I'd need to disable/modify the movement, so I think I'd need to make movement a stricter object so that I can change it better from outside

            if(vector1, vector2, vector 3 == wallVertex){
                (Movement in cardinal point of the vector in collision) == 0 }


        Detection mechanism:

            check points on the vector (tip, half-way point) to see if they are in the same tile as the camera
            if not = check which tile it's in and determine if it's a wall or not

            if (sensorTip.getTile() != camera.getTile()){
                if (sensorTip.getTile() == 1){
                    block movement/sliding}}




   */








}
