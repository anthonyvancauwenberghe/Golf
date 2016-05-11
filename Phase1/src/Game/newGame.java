//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Game;

import java.util.ArrayList;
import java.util.Iterator;
import org.ode4j.drawstuff.DrawStuff;
import org.ode4j.drawstuff.DrawStuff.DS_TEXTURE_NUMBER;
import org.ode4j.drawstuff.DrawStuff.dsFunctions;
import org.ode4j.math.DQuaternion;
import org.ode4j.ode.DBody;
import org.ode4j.ode.DBox;
import org.ode4j.ode.DContact;
import org.ode4j.ode.DContactBuffer;
import org.ode4j.ode.DContactJoint;
import org.ode4j.ode.DGeom;
import org.ode4j.ode.DJointGroup;
import org.ode4j.ode.DMass;
import org.ode4j.ode.DSpace;
import org.ode4j.ode.DSphere;
import org.ode4j.ode.DWorld;
import org.ode4j.ode.OdeHelper;
import org.ode4j.ode.OdeMath;
import org.ode4j.ode.DGeom.DNearCallback;

public class newGame extends dsFunctions {
    private static final double GRAVITY = 10.0D;
    private static final int RAMP_COUNT = 8;
    private final double rampX = 6.0D;
    private final double rampY = 0.5D;
    private final double rampZ = 0.25D;
    private final double sphereRadius = 0.25D;
    private final double maxRamp = 0.7853981633974483D;
    private final double rampInc = 0.09817477042468103D;
    private DWorld world = null;
    private DSpace space = null;
    private DJointGroup contactgroup = null;
    private double mu = 0.37D;
    private double rho = 0.1D;
    private double omega = 25.0D;
    private ArrayList<DBox> rampGeom = new ArrayList<DBox>();
    private ArrayList<DBody> sphereBody = new ArrayList<DBody>();
    private ArrayList<DSphere> sphereGeom = new ArrayList<DSphere>();
    private DNearCallback nearCallback = new DNearCallback() {
        public void call(Object data, DGeom o1, DGeom o2) {
            newGame.this.nearCallback(data, o1, o2);
        }
    };
    private static double[] xyz = new double[]{0.0D, -3.0D, 3.0D};
    private static double[] hpr = new double[]{90.0D, -15.0D, 0.0D};
    private double aX= 0.0D; //for the ramps
    private double aY= 1.0D; //for the ramps
    private double aZ= 0.0D; //for the ramps
    private double angle = Math.toRadians(45); //for the ramps

    public newGame() {
    }

    private void nearCallback(Object data, DGeom o1, DGeom o2) {
        DBody b1 = o1.getBody();
        DBody b2 = o2.getBody();
        if(b1 != null || b2 != null) {
            DContactBuffer contacts = new DContactBuffer(3);

            DContact c;
            for(Iterator numc = contacts.iterator(); numc.hasNext(); c.surface.rho = this.rho) {
                c = (DContact)numc.next();
                c.surface.mode = 29696;
                c.surface.mu = this.mu;
            }

            int var10 = OdeHelper.collide(o1, o2, 3, contacts.getGeomBuffer());
            if(var10 != 0) {
                for(int i = 0; i < var10; ++i) {
                    DContactJoint var11 = OdeHelper.createContactJoint(this.world, this.contactgroup, contacts.get(i));
                    var11.attach(b1, b2);
                }
            }

        }
    }

    public void start() {
        DrawStuff.dsSetViewpoint(xyz, hpr);
        System.out.println("Press:");
        System.out.println("\t\'[\' or \']\' to change initial angular velocity");
        System.out.println("\t\'m\' to increase sliding friction");
        System.out.println("\t\'n\' to decrease sliding friction");
        System.out.println("\t\'j\' to increase rolling friction");
        System.out.println("\t\'h\' to decrease rolling friction");
        System.out.println("\t\'r\' to reset simulation.");
    }

    private void clear() {
        if(this.contactgroup != null) {
            this.contactgroup.destroy();
        }

        if(this.space != null) {
            this.space.destroy();
        }

        if(this.world != null) {
            this.world.destroy();
        }

    }

    private void reset() {
        createWorld();

        this.clear();
        rampGeom.clear();
        sphereBody.clear();
        sphereGeom.clear();
        this.world = OdeHelper.createWorld();
        this.space = OdeHelper.createHashSpace();
        this.contactgroup = OdeHelper.createJointGroup();
        this.world.setGravity(0.0D, 0.0D, -10.0D);
        OdeHelper.createPlane(this.space, 0.0D, 0.0D, 1.0D, 0.0D);
        DMass sphereMass = OdeHelper.createMass();
        sphereMass.setSphere(1000.0D, 0.25D);

        for(int ii = 0; ii < 8; ++ii) {
            DQuaternion q = new DQuaternion();
            //double angle = Math.toRadians(45);//(double)(ii + 1) * 0.09817477042468103D;
            double cosA = Math.cos(angle);
            double sinA = Math.sin(angle);
            double rampW = 6.0D / cosA;
            double zPos = 0.5D * (sinA * rampW - cosA * 0.25D);
            double yPos = (double)ii * 1.25D * 0.5D;
            double xPos = 0.0D;
            DBox newRamp = OdeHelper.createBox(this.space, rampW, 0.5D, 0.25D);
            this.rampGeom.add(newRamp);


            OdeMath.dQFromAxisAndAngle(q, aX, aY, aZ, angle);

            newRamp.setQuaternion(q);
            newRamp.setPosition(xPos, yPos, zPos);
            xPos = -2.75D;
            zPos = sinA * rampW + 0.25D;
            //addBall();
            DBody newSphereBody = OdeHelper.createBody(this.world);
            newSphereBody.setMass(sphereMass);

            this.sphereBody.add(newSphereBody);
            double radius_SupposeTo = 0.25D; //smaller seams to make it bigger and lighter???
            DSphere newSphereGeom  = OdeHelper.createSphere(this.space, radius_SupposeTo); //smaller

            newSphereGeom.setBody(newSphereBody);
            newSphereGeom.setPosition(xPos, yPos, zPos);
            newSphereBody.setAngularVel(0.0D, this.omega, 0.0D);
            sphereGeom.add(newSphereGeom);
        }

    }

    private void createWorld() {

    }

    public void command(char cmd) {
        switch(cmd) {
            case 'H':
            case 'h':
                this.rho -= 0.02D;
                if(this.rho < 0.0D) {
                    this.rho = 0.0D;
                } break;
            case 'I':
            case 'K':
            case 'L':
            case 'O':
            case 'P':
            case 'r':
                this.angle +=0.1;
                this.reset();
                break;
            case 'Q':
            case 'S':
            case 's':
                this.aY -= 0.1;
                this.reset();
                break;
            case 'T':
            case 'U':
            case 'V':
            case 'w':
                this.aY += 0.1;
                this.reset();
                break;
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '\\':
            case '^':
            case '_':
            case '`':
            case 'a':
                this.aX -= 0.1;
                this.reset();
                break;
            case 'b':
            case 'c':
            case 'd':
                this.aZ -= 0.1;
                this.reset();
                break;
            case 'e':
                this.aZ += 0.1;
                this.reset();
                break;
            case 'f':
                this.angle -=0.1;
                this.reset();
                break;
            case 'g':
            case 'i':
            case 'k':
            case 'l':
            case 'o':
            case 'p':
            case 'q':
                this.aX += 0.1;
                this.reset();
                break;
            default:
                break;
            case 'J':
            case 'j':
                this.rho += 0.02D;
                if(this.rho > 1.0D) {
                    this.rho = 1.0D;
                }
                break;
            case 'M':
            case 'm':
                this.mu += 0.02D;
                if(this.mu > 1.0D) {
                    this.mu = 1.0D;
                }
                break;
            case 'N':
            case 'n':
                this.mu -= 0.02D;
                if(this.mu < 0.0D) {
                    this.mu = 0.0D;
                }
                break;
            case 'R':
                this.reset();
                break;
            case '[':
                --this.omega;
                break;
            case ']':
                ++this.omega;
        }

    }

    public void step(boolean pause) {
        if(!pause) {
            this.space.collide((Object)null, this.nearCallback);
            this.world.step(0.017D);
            this.contactgroup.empty();
        }

        DrawStuff.dsSetTexture(DS_TEXTURE_NUMBER.DS_GROUND);
        DrawStuff.dsSetColor(1.0D, 0.5D, 0.0D);
        ArrayList<DBox> arr$ = this.rampGeom;
        int len$ = arr$.size();

        int i$;
        for(i$ = 0; i$ < len$; ++i$) {
            DBox g = arr$.get(i$);
            DrawStuff.dsDrawBox(g.getPosition(), g.getRotation(), g.getLengths());
        }

        DrawStuff.dsSetColor(0.0F, 0.0F, 1.0F);
        ArrayList<DSphere> var6 = this.sphereGeom;
        len$ = var6.size();

        for(i$ = 0; i$ < len$; ++i$) {
            DSphere var7 = var6.get(i$);
            DrawStuff.dsDrawSphere(var7.getPosition(), var7.getRotation(), 0.25D);
        }

    }

    public static void main(String[] args) {
        (new newGame()).demo(args);
    }

    private void demo(String[] args) {
        OdeHelper.initODE2(0);
        this.reset();
        DrawStuff.dsSimulationLoop(args, 352, 288, this);
        this.clear();
        OdeHelper.closeODE();
    }

    public void stop() {
    }
}
