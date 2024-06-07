package ibug.behaviors.actualBehaviors;

import javax.vecmath.Point3d;

import ibug.behaviors.Behavior;
import ibug.behaviors.Sensors;
import ibug.behaviors.Velocities;
import simbad.sim.Agent;
import ibug.MyRobot;
import ibug.ToolKit;

public class MoveToTarget extends Behavior {

    MyRobot terminator;
    static double K = 0.1; // ! test numbers for this one
    Point3d goal;

    public MoveToTarget(MyRobot terminoid, Sensors sensors, Point3d goal) {
        super(sensors);
        this.terminator = terminoid;
        this.goal = goal;
    }

    @Override
    public boolean is_active() {
        return true;
    }

    @Override
    public Velocities act() {

        Point3d r = new Point3d();
        terminator.getCoords(r);
        Point3d goal_local = ToolKit.get_local_coords(terminator, goal);

        return new Velocities(K * goal_local.distance(new Point3d(0, 0, 0)), 0.0);
    }
}
