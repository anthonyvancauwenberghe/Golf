package Game;


import org.ode4j.drawstuff.DrawStuff;
import org.ode4j.drawstuff.DrawStuff.DS_TEXTURE_NUMBER;
import org.ode4j.drawstuff.DrawStuff.dsFunctions;
import org.ode4j.math.DMatrix3;
import org.ode4j.math.DMatrix3C;
import org.ode4j.math.DQuaternion;
import org.ode4j.math.DVector3C;
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
import org.ode4j.ode.DTriMesh;
import org.ode4j.ode.DTriMeshData;
import org.ode4j.ode.DWorld;
import org.ode4j.ode.OdeHelper;
import org.ode4j.ode.DGeom.DNearCallback;
import org.ode4j.ode.DTriMesh.DTriArrayCallback;
import org.ode4j.ode.DTriMesh.DTriCallback;
import org.ode4j.ode.DTriMesh.DTriRayCallback;

public class oce2 extends dsFunctions {
    private static final double RADIUS = 0.14D;
    private static DWorld world;
    private static DSpace space;
    private static DBody sphbody;
    private static DGeom sphgeom;
    private static DJointGroup contactgroup;
    private static DTriMesh world_mesh;
    private DNearCallback nearCallback = new DNearCallback() {
        public void call(Object data, DGeom o1, DGeom o2) {
            oce2.this.nearCallback(data, o1, o2);
        }
    };
    private float[] xyz = new float[]{-8.0F, 0.0F, 5.0F};
    private float[] hpr = new float[]{0.0F, -29.5F, 0.0F};

    public oce2() {
    }

    private void nearCallback(Object data, DGeom o1, DGeom o2) {
        assert o1 != null;

        assert o2 != null;

        if(!(o1 instanceof DSpace) && !(o2 instanceof DSpace)) {
            boolean N = true;
            DContactBuffer contacts = new DContactBuffer(32);
            int n = OdeHelper.collide(o1, o2, 32, contacts.getGeomBuffer());
            if(n > 0) {
                for(int i = 0; i < n; ++i) {
                    DContact contact = contacts.get(i);
                    contact.surface.slip1 = 0.7D;
                    contact.surface.slip2 = 0.7D;
                    contact.surface.mode = 29464;
                    contact.surface.mu = 50.0D;
                    contact.surface.soft_erp = 0.96D;
                    contact.surface.soft_cfm = 0.04D;
                    DContactJoint c = OdeHelper.createContactJoint(world, contactgroup, contact);
                    c.attach(contact.geom.g1.getBody(), contact.geom.g2.getBody());
                }
            }

        } else {
            System.err.println("testing space " + o1 + " " + o2);
            OdeHelper.spaceCollide2(o1, o2, data, this.nearCallback);
        }
    }

    public void start() {
        DrawStuff.dsSetViewpoint(this.xyz, this.hpr);
    }

    private void reset_ball() {
        float sx = 0.0F;
        float sy = 3.4F;
        float sz = 7.15F;
        sy = (float)((double)sy + 0.033D);
        DQuaternion q = new DQuaternion();
        q.setIdentity();
        sphbody.setPosition((double)sx, (double)sy, (double)sz);
        sphbody.setQuaternion(q);
        sphbody.setLinearVel(0.0D, 0.0D, 0.0D);
        sphbody.setAngularVel(0.0D, 0.0D, 0.0D);
    }

    public void command(char cmd) {
        switch(cmd) {
            case ' ':
                this.reset_ball();
            default:
        }
    }

    public void step(boolean pause) {
        double simstep = 0.001D;
        double dt = DrawStuff.dsElapsedTime();
        int nrofsteps = (int)Math.ceil(dt / simstep);

        for(int spos = 0; spos < nrofsteps && !pause; ++spos) {
            OdeHelper.spaceCollide(space, Integer.valueOf(0), this.nearCallback);
            world.quickStep(simstep);
            contactgroup.empty();
        }

        DrawStuff.dsSetColor(1.0F, 1.0F, 1.0F);
        DVector3C var16 = sphbody.getPosition();
        DMatrix3C srot = sphbody.getRotation();
        DrawStuff.dsDrawSphere(var16, srot, 0.14D);
        DrawStuff.dsSetColor(0.4F, 0.7F, 0.9F);
        DrawStuff.dsSetTexture(DS_TEXTURE_NUMBER.DS_NONE);
        DVector3C pos = world_mesh.getPosition();
        DMatrix3C rot = world_mesh.getRotation();
        int numi = BasketGeom.world_indices.length;

        for(int i = 0; i < numi; i += 3) {
            int i0 = BasketGeom.world_indices[i + 0] * 3;
            int i1 = BasketGeom.world_indices[i + 1] * 3;
            int i2 = BasketGeom.world_indices[i + 2] * 3;
            DrawStuff.dsDrawTriangle(pos, rot, BasketGeom.world_vertices, i0, i1, i2, true);
        }

    }

    public static void main(String[] args) {
        (new oce2()).demo(args);
    }

    private void demo(String[] args) {
        DMass m = OdeHelper.createMass();
        DMatrix3 R = new DMatrix3();
        OdeHelper.initODE2(0);
        world = OdeHelper.createWorld();
        space = OdeHelper.createHashSpace((DSpace)null);
        contactgroup = OdeHelper.createJointGroup();
        world.setGravity(0.0D, 0.0D, -9.8D);
        world.setQuickStepNumIterations(64);
        DTriMeshData Data = OdeHelper.createTriMeshData();
        Data.build(BasketGeom.world_vertices, BasketGeom.world_indices);
        world_mesh = OdeHelper.createTriMesh(space, Data, (DTriCallback)null, (DTriArrayCallback)null, (DTriRayCallback)null);
        world_mesh.enableTC(DSphere.class, false);
        world_mesh.enableTC(DBox.class, false);
        world_mesh.setPosition(0.0D, 0.0D, 0.5D);
        R.setIdentity();
        world_mesh.setRotation(R);
        sphbody = OdeHelper.createBody(world);
        m.setSphere(1.0D, 0.14D);
        sphbody.setMass(m);
        sphgeom = OdeHelper.createSphere((DSpace)null, 0.14D);
        sphgeom.setBody(sphbody);
        this.reset_ball();
        space.add(sphgeom);
        DrawStuff.dsSimulationLoop(args, 352, 288, this);
        sphgeom.destroy();
        world_mesh.destroy();
        contactgroup.empty();
        contactgroup.destroy();
        space.destroy();
        world.destroy();
        OdeHelper.closeODE();
    }

    public void stop() {
    }
}
