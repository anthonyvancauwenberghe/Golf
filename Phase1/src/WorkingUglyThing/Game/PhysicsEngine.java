package WorkingUglyThing.Game;

import java.util.ArrayList;

/**
 * Created by tony and Markus on 16/03/2016.
 */
public class PhysicsEngine {
    private Course course;
    public boolean atLeastOneBallMoving;



    private ArrayList<Ball> balls = new ArrayList<>(2);




    public static boolean enable3D = Config.ENABLED3D;

    public void init(Course course, ArrayList<Ball> balls) {
        this.course = course;
        this.balls.clear();
        this.balls = balls;
        atLeastOneBallMoving = false;
    }

    public void init(ArrayList<Player> players, Course course) {
        this.course = course;
        this.balls.clear();

        for (int i = 0; i < players.size(); i++) {
            balls.add(players.get(i).getBall());
        }
        atLeastOneBallMoving = false;
    }


    public void processPhysics(double elapsedTime) {
        atLeastOneBallMoving = false;



        Type[][][] playfield = course.getPlayfield();
        Coordinate [][] normals = course.getSurfaceNormals();
        for (int i = 0; i < balls.size(); i++) {


            Ball b = balls.get(i);
            if (!b.inPlay||b.pregame) continue;
            gravity(b);

            hover(b,elapsedTime,playfield,normals,course.getDimension());
            collide(b,elapsedTime,playfield,normals,course.getDimension());
            checkborder(b);
            accelerate(b,elapsedTime);
            resetA(b);


            //check for Collision with different balls
            for (int j = i+1; j < balls.size(); j++) {
                //check for collision between two balls
                Ball bd = balls.get(j);
                if (!bd.inPlay||bd.pregame) continue;
                ballCollision(b,bd);
            }

            inertia(b,elapsedTime);
            //b.checkBallStopped();
            atLeastOneBallMoving = !b.checkBallStopped();

            checkIfInHole(b);

        }
    }

    private void checkIfInHole(Ball b) {
        Hole h = course.getHole();
        double dx =b.x-h.x;
        double dy =b.y-h.y;
        double dz =b.z-h.z;

        if ((h.isBallIntersectingHole(b))&&b.getSpeed()<0.2){
            b.setInHole(true);
        }

    }
    private void checkborder(Ball b){
        int height = Game.course.getHeight();
        int width = Game.course.getWidth();

        double dx = Math.abs(b.getX()-b.getPreviousX());
        double dy = Math.abs(b.getY()-b.getPreviousY());
        double dz = Math.abs(b.getZ()-b.getPreviousZ());



        if(b.getX()+b.getRadius()>width-1){
            System.out.println("out of X border: " + b.getX() + " speedX: " +b.getaX());
            b.x = width-1-b.getRadius();
            b.previousX=b.x+dx;

        }

        if(b.getX()-b.getRadius()<=0){
            System.out.println("out of X border: " + b.getX() + " speedX: " +b.getaX());
            b.x = b.getRadius()+1;
            b.previousX=b.x-dx;
        }

        if(b.getY()+b.getRadius()>height-1){
            System.out.println("out of X border: " + b.getX() + " speedX: " +b.getaX());
            b.y = height-1-b.getRadius();
            b.previousY=b.y+dy;
        }

        if(b.getY()-b.getRadius()<=0){
            System.out.println("out of X border: " + b.getX() + " speedX: " +b.getaX());
            b.y = 1+b.getRadius();
            b.previousY=b.y-dy;
        }



    }

    private void collide(Ball b,double elapsedTime, Type[][][] playfield,  Coordinate[][] normals, int[] dimension) {
        double normalX = 0;
        double normalY = 0;
        double normalZ = 0;
        int count = 0;
        double g = 1*Config.GRAVITY_FORCE;
        double BounceFriction = 0;
        int[][] surfaceBall = b.getSurfacePoints();
        int l = surfaceBall.length;

        double vOld = b.getSpeed();

        for (int i = 0; i < l; i++) {
            int x = surfaceBall[i][0];
            int y = surfaceBall[i][1];
            int z = surfaceBall[i][2];
            boolean skipCheck = false;
            if (y<0||x<0||z<0||x>=dimension[0]||y>=dimension[1]||z>=dimension[2]) {
                skipCheck = true;
                if (x < 0) {
                    //normalX += 1;

                }

                if (y < 0) {
                    //normalY += 1;

                }
                if (z < 0) {
                   // normalZ += 1;

                }

                if (x >= dimension[0]) {
                   // normalX -= 1;

                }
                if (y >= dimension[1]) {
                   // normalY -= 1;

                }
                if (z >= dimension[2]) {
                    //aZ -= 1;
                   // count--;
                    //count++;
                }
               // BounceFriction+=Type.Grass.getBounceDampness();
                skipCheck = true;
               // count++;
            }

            if (!skipCheck){
                Type t = playfield[x][y][z];
                if (t!= Type.Empty) {
                    BounceFriction += t.getBounceDampness();

                     Coordinate c = normals[x][y];

                    //Coordinate c = course.getNormal(x, y, z);
                    if (!Double.isNaN(c.getX())){
                        normalX += c.getX();
                    normalY += c.getY();
                    normalZ += c.getZ();
                    count++;

                    }
                }

            }
        }

        if (count!=0){
            normalX/=count;
            normalY/=count;
            normalZ/=count;
            BounceFriction/=count;
            //addedFriction = (1-addedFriction*elapsedTime);
            double dx =  b.x -b.previousX;
            double dy = b.y - b.previousY ;
            double dz = b.z - b.previousZ ;

            // Project velocity onto the normal, multiply by 2, and subtract it from velocity

            // project velocity onto the normal using dot product
            double scalarProjection = dx * normalX + dy * normalY + dz *normalZ;
           // if (scalarProjection<0){

            //
            double dxNew = dx -  normalX * scalarProjection * 2;
            double dyNew = dy -  normalY * scalarProjection * 2;
            double dzNew = dz -  normalZ * scalarProjection * 2;
           // if (Math.sqrt(dxNew*dxNew+dyNew*dyNew+dzNew*dzNew)< Math.sqrt(dx*dx+dy*dy+dz*dz)) {
            BounceFriction=0;
               b.previousX = b.x - dxNew * (1 - BounceFriction * elapsedTime);
               b.previousY = b.y - dyNew * (1 - BounceFriction * elapsedTime);
               b.previousZ = b.z - dzNew * (1 - BounceFriction * elapsedTime);

                //System.out.println("woop");
           // }
           // }
            //friction should come in here




        }else{





        }
        double air= Config.AIR_FRICTION;

        double speed = b.getSpeed();
        double drag = air * (speed * speed);

        double nx = drag*b.getSpeedX()/speed;
        double ny = drag*b.getSpeedY()/speed;
        double nz = drag*b.getSpeedZ()/speed;



        b.aX-=nx;
        b.aY-=ny;
        b.aZ-=nz;
    }

    private void hover(Ball b,double elapsedTime, Type[][][] playfield, Coordinate[][] normals, int[] dimension) {
        double aX = 0;
        double aY = 0;
        double aZ = 0;
        int count = 0;

        double g = 1*Config.GRAVITY_FORCE;
        double addedFriction = 0;
        int[][] surfaceBall = b.getSurfacePointsBig();
        int l = surfaceBall.length;
        for (int i = 0; i < l; i++) {
            int x = surfaceBall[i][0];
            int y = surfaceBall[i][1];
            int z = surfaceBall[i][2];
            boolean skipCheck = false;
            if (y<0||x<0||z<0||x>=dimension[0]||y>=dimension[1]||z>=dimension[2]) {
                skipCheck = true;
                /*if (x < 0) {
                    aX += 1;
                }

                if (y < 0) {
                    aY += 1;

                }
                if (z < 0) {
                    aZ += Config.UPPush;

                }

                if (x >= dimension[0]) {
                    aX -= 1;

                }
                if (y >= dimension[1]) {
                    aY -= 1;

                }
                if (z >= dimension[2]) {
                    //aZ -= 1;
                    count--;
                    //count++;
                }*/
                //addedFriction+=Type.Grass.getFriction();
                skipCheck = true;
                //count++;

            }

            if (!skipCheck){
                Type t = playfield[x][y][z];
                 if (t!= Type.Empty) {
                     addedFriction+=t.getFriction();

                     Coordinate c = normals[x][y];
                     aX += c.getX();
                     aY += c.getY();
                     aZ += c.getZ();
                     count++;

                 }else{

                 }

            }
        }

       if (count!=0){

           aX/=count;
           aY/=count;
           aZ/=count;
           addedFriction/=count;


           //Vector3 planeOrigin;
           //Vector3 planeNormal;
           //Vector3 point;

           //Vector3 v = point - planeOrigin;
           //Vector3 d = Vector3.Project(v, planeNormal.normalized);
           //Vector3 projectedPoint = point - d;

           double dx = b.previousX - b.x;
           double dy = b.previousY - b.y;
           double dz = b.previousZ - b.z;
           addedFriction = (1-addedFriction*elapsedTime);
           b.previousX = b.x +  dx*addedFriction;
           b.previousY = b.y +  dy*addedFriction;
           b.previousZ = b.z +  dz*addedFriction;

           b.aX+=aX*Config.UPPush*g;
           b.aY+=aY*Config.UPPush*g;
           b.aZ+=aZ*Config.UPPush*g;
           //friction should come in here

       }



    }

    private void accelerate(Ball b, double elapsedTime) {
        double e = elapsedTime*elapsedTime;
        b.x+=b.aX*e;
        b.y+=b.aY*e;
        b.z+=b.aZ*e;

    }


    private void ballCollision(Ball b, Ball bd) {

        double dx = b.getX()-bd.getX();
        double dy = b.getY()-bd.getY();
        double dz = b.getZ()-bd.getZ();

        double slength = dx*dx+dy*dy+dz*dz;
        double length = Math.sqrt(slength);
        double target = b.getRadius()+bd.getRadius();

        //if spheres are closer then radii combined
        if(length<target){
            // record previous velocity
            double v1x = b.x - b.previousX;
            double v1y = b.y - b.previousY;
            double v1z = b.z - b.previousZ;

            double v2x = bd.x - bd.previousX;
            double v2y = bd.y - bd.previousY;
            double v2z = bd.z - bd.previousZ;

            // resolve the body overlap conflict
            double factor = (length-target)/length;
            b.x -= dx*factor*0.5;
            b.y -= dy*factor*0.5;
            b.z -= dy*factor*0.5;

            bd.x += dx*factor*0.5;
            bd.y += dy*factor*0.5;
            bd.z += dz*factor*0.5;

            // compute the projected component factors
            double f1 = (Config.DAMPING*(dx*v1x+dy*v1y+dz*v1z))/slength;
            double f2 = (Config.DAMPING*(dx*v2x+dy*v2y+dz*v2z))/slength;


            // swap the projected components
            v1x += f2*dx-f1*dx;
            v2x += f1*dx-f2*dx;
            v1y += f2*dy-f1*dy;
            v2y += f1*dy-f2*dy;
            v1z += f2*dy-f1*dy;
            v2z += f1*dz-f2*dz;

            // the previous position is adjusted
            // to represent the new velocity
            b.previousX = b.x - v1x;
            b.previousY = b.y - v1y;
            b.previousZ = b.z - v1z;

            bd.previousX = bd.x - v2x;
            bd.previousY = bd.y - v2y;
            bd.previousZ = bd.z - v2z;
        }

    }

    private void inertia(Ball b, double elapsedTime) {
        double x = b.getX()*2-b.getPreviousX();
        double y = b.getY()*2-b.getPreviousY();
        double z = b.getZ()*2-b.getPreviousZ();
        b.previousX = b.x;
        b.previousY = b.y;
        b.previousZ = b.z;
        b.x = x;
        b.y = y;
        b.z = z;

    }

    private void resetA(Ball b) {
        b.setaZ(0);
        b.setaX(0);
        b.setaY(0);
    }

    private void gravity(Ball b) {

            b.setaZ(-Config.GRAVITY_FORCE);

    }


    public double calculateAngle(double speedX, double speedY) {
        double angle;
        angle = Math.atan(speedY / speedX);
        return angle;
    }

    /*
    private void checkIfObjectIsBetweenBallAndFutureBall(int coordinateX, int coordinateY, int futureXCoordinate, int futureYCoordinate, double v, double angle, double radius) {
        int collisionX=-6666666;
        int collisionY=-6666666;
        ArrayList<int[]> pp = getPointsBetween(coordinateX,coordinateY,futureXCoordinate,futureYCoordinate);
        Point2D p = new Point();
        boolean collisionHappened = false;

        int ss = pp.size();
        loop:for (int i = 0; i < ss; i++) {
            if (course.getTile(pp.get(i)[0],pp.get(i)[1],0)== Game.Type.OBJECT){
                collisionX=pp.get(i)[0];
                collisionY= pp.get(i)[1];
                collisionHappened = true;
                break loop;
            }
        }

        if (collisionHappened){
            for (int i = 0; i < 1000; i++) {
                System.out.println("Coolision happened at: " + collisionX +" " + collisionY);
            }
        }


    }


*/



/*
    private void checkForCollision(Ball b, Coordinate newPosition) {
        ArrayList<Coordinate> checkThose = Coordinate.getPxelBetweenToPoints(b.getCoordinate(),newPosition);
        int indexOfLastFree = checkThose.size()-1;

        Coordinate collisionCoordinate=null;
        Coordinate lastFreeCoordinate = b.getCoordinate();

        ;
        for (int j = 0; j < checkThose.size(); j++) {
            Coordinate c = checkThose.get(j);
            Type positionType = course.getType(newPosition);
            if (positionType != Type.Empty){
                collisionCoordinate = c;
                if (j > 0)  lastFreeCoordinate = checkThose.get(j-1);
                else lastFreeCoordinate = checkThose.get(0);

                indexOfLastFree = j-1;
                break;
            }

        }
        if (collisionCoordinate==null) {
            b.setPosition(newPosition);
            return;
        }
        if (indexOfLastFree>=0) b.setPosition(lastFreeCoordinate);

        //updateSpeed
        try {

            Coordinate normal = Coordinate.getNormal(course, collisionCoordinate);

            double projection = b.getSpeedX()*normal.getX()+b.getSpeedY()*normal.getY()+b.getSpeedZ()*normal.getZ();
            projection *=2;
            double newSpeedX = -normal.getX()*projection;
            double newSpeedY = -normal.getY()*projection;
            double newSpeedZ = -normal.getZ()*projection;

            b.speedX = newSpeedX;
            b.speedY = newSpeedY;
            b.speedZ = newSpeedZ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
*/
    /*
    public void checkColission() {
        double angle = calculateAngle(ball.getSpeedX(), ball.getSpeedY());
        double coordinateX, coordinateY, futureXCoordinate, futureYCoordinate;
        Type nextBallCoordinateType = null;

        if (Math.abs(ball.getSpeedX()) > Math.abs(ball.getSpeedY())) {
            System.out.println("SpeedX bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedX()); i += 5) {
                Game.dp.repaint();
                coordinateX = i;
                coordinateY = (i * Math.tan(angle));
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {

                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {

                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate - ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ());
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        ball.getCoordinate().setX(futureXCoordinate);
                        ball.getCoordinate().setX(futureYCoordinate);
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();

                        break forloop;
                    }
                }

            }

        } else {
            System.out.println("SpeedY bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedY()); i += 1) {
                Game.dp.repaint();
                coordinateX = (i / Math.tan(angle));
                coordinateY = i;
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {
                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {
                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate + ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ());
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();
                        break forloop;
                    }
                }

            }
        }
    }
*/



    public void processHole(Ball ball) {

        Type ballCoordinateType = null;
        Coordinate b = ball.getCoordinate();
        double ballSpeed = ball.getSpeed();
        Hole h = course.getHole();

        /*********************************/
        /** Process Hole **/
        /*********************************/
        //t = (2*Rh - R)/vf
        //Rh = radius of hole
        //R = radius ball
        //        vf = speed of ball when it reaches the hole
        //g*t^2/2 > R
        //g = gravity = 9.81
        //or expressed in vf: vf < (2Rh - R)(g / 2R)^1/2


        if (Math.abs(b.getX() - h.getX()) <= ball.getRadius() + h.radius && Math.abs(b.getY() - h.getY()) <= ball.getRadius() + h.radius) {
            double distance = Math.sqrt((b.getX() - h.getX()) * (b.getX() - h.getX()) + (b.getY() - h.getY()) * (b.getY() - h.getY()));

            if (distance + ball.getRadius() < +h.radius) {
                //inAir
                if (ballSpeed < (2 * ball.radius - h.radius) * Math.sqrt(10 / 2 * h.radius)) {
                    ball.previousX = ball.x;
                    ball.previousZ = ball.z;
                    ball.previousY = ball.y;
                    ball.setInHole(true);
                }
            } else if (distance <= ball.getRadius() + h.radius) {
                Coordinate c = new Coordinate(h.getX() - b.getX(), h.getY() - b.getY(), h.getZ() - b.getZ());
                double factor = (1 - distance / (ball.getRadius() + h.radius)) * h.getFriction();
                //ball.redirect(c, factor);


            }

        }


    }
}
