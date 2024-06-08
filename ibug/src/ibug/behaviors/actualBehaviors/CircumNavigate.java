package ibug.behaviors.actualBehaviors;

import java.util.LinkedList;
import java.util.Queue;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import ibug.behaviors.Behavior;
import ibug.behaviors.Sensors;
import ibug.behaviors.Velocities;
import ibug.MyRobot;
import ibug.ToolKit;

import simbad.demo.LightSearchDemo;
import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.LightSensor;

public class CircumNavigate extends Behavior {
    Point3d goal;
    MyRobot terminator;
    boolean CLOCKWISE;

    private LinkedList<Double> last_lum_reads;
    private int num_of_last_lum_reads;

    int count;

    static double K1 = 5;
    static double K2 = 0.8;
    static double K3 = 1;
    static double SAFETY = 0.8;

    public boolean time_to_leave() {
        if (last_lum_reads.size() < num_of_last_lum_reads) {
            return false;
        }

        for (int i = 0; i < (num_of_last_lum_reads - 1) / 2; ++i) {
            if (last_lum_reads.get(i) > last_lum_reads.get(i + 1)) { // ! will it become noticeably faster if I returned
                                                                     // the whole array and worked on the array instead?
                return false;
            }
        }
        for (int i = (num_of_last_lum_reads - 1) / 2; i < num_of_last_lum_reads - 1; ++i) {
            if (last_lum_reads.get(i) < last_lum_reads.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public Point3d get_sensed_point(int sonar) {
        RangeSensorBelt sonars = this.get_sensors().get_sonars();

        double v;
        if (sonars.hasHit(sonar))
            v = terminator.getRadius() + sonars.getMeasurement(sonar);
        else
            v = terminator.getRadius() + sonars.getMaxRange();
        double x = v * Math.cos(sonars.getSensorAngle(sonar));
        double z = v * Math.sin(sonars.getSensorAngle(sonar));
        return new Point3d(x, 0, z);
    }

    public CircumNavigate(Sensors sensors, MyRobot terminoid, Point3d goal, int num_of_last_lum_reads,
            boolean CLOCKWISE) {
        super(sensors);
        this.terminator = terminoid;
        this.goal = goal;
        this.CLOCKWISE = CLOCKWISE;
        this.num_of_last_lum_reads = num_of_last_lum_reads;

        // completed = true;

        this.last_lum_reads = new LinkedList<Double>();
        count = 0;
    }

    public Velocities act() {
        RangeSensorBelt sonars = get_sensors().get_sonars();
        LightSensor center_light_sensor = get_sensors().get_center_light_sensor();

        int min;
        min = 0;
        for (int i = 1; i < sonars.getNumSensors(); i++)
            if (sonars.getMeasurement(i) < sonars.getMeasurement(min))
                min = i;
        Point3d p = get_sensed_point(min);
        double d = p.distance(new Point3d(0, 0, 0));
        Vector3d v;
        v = CLOCKWISE ? new Vector3d(-p.z, 0, p.x) : new Vector3d(p.z, 0, -p.x);
        double phLin = Math.atan2(v.z, v.x);
        double phRot = Math.atan(K3 * (d - SAFETY));
        if (CLOCKWISE)
            phRot = -phRot;
        double phRef = ToolKit.wrapToPi(phLin + phRot);

        if (count >= 10) {
            if (last_lum_reads.size() == num_of_last_lum_reads) {
                last_lum_reads.poll(); // ! double check that poll() works in linked list the same way as in queue
            }

            last_lum_reads.offer(center_light_sensor.getLux()); // ! double check that offer() works in linked list the
                                                                // same
                                                                // way as in queue
        }

        count += 1;

        return new Velocities(K2 * Math.cos(phRef), K1 * phRef);
    }

    public boolean is_active() {
        if (terminator.get_sensor_block()) {
            return false;
        }

        if (terminator.get_circum_navigating()) {
            if (time_to_leave()) {
                terminator.set_circum_navigating(false);
                terminator.set_sensor_block(true);
                last_lum_reads.clear();
                return false;
            }

            return true;
        }

        RangeSensorBelt sonars = get_sensors().get_sonars();

        int i, j;
        i = 7; // k - 2, k = n/4
        j = 0; // for 2k - 3 checks
        for (j = 0; j < 15; ++j) { // ! this will change and become a function of the number of sonars I have
            if (sonars.getMeasurement(i) < 0.7) {
                if (i > 0) {
                    this.CLOCKWISE = false;
                } else {
                    this.CLOCKWISE = true;
                }

                terminator.set_circum_navigating(true);
                count = 0;
                return true;
            }

            i -= 1;
            if (i < 0) {
                i = sonars.getNumSensors() - 1; // ? make sure this returns number of sensors on the belt
            }
        }

        return false;

    }

    // public boolean is_active() {
    // if (!completed) {
    // Point3d r = new Point3d();
    // terminator.getCoords(r);
    // double f = Math.atan2(r.z - goal.z, goal.x - r.x);
    // double dist = r.distance(goal);

    // if (hit_point.distance(r) > 1) {
    // circleCompleted = true;
    // }
    // if (circleCompleted && dist < initial_dist && Math.abs(f - fToGoal) < 0.05) {
    // completed = true;
    // return false;
    // }
    // return true;
    // }
    // RangeSensorBelt sonars = get_sensors().get_sonars();
    // Point3d lg = ToolKit.get_local_coords(terminator, goal);
    // double f2g = Math.atan2(lg.z, lg.x), minDist = Double.POSITIVE_INFINITY,
    // phRef;
    // for (int i = 0; i < sonars.getNumSensors(); i++) {
    // double ph = ToolKit.wrapToPi(sonars.getSensorAngle(i));
    // if (Math.abs(ph - f2g) <= Math.PI / 2) {
    // minDist = minDist < sonars.getMeasurement(i) ? minDist :
    // sonars.getMeasurement(i);
    // }
    // }
    // if (minDist <= 0.5) {
    // completed = false;
    // Point3d r = new Point3d();
    // terminator.getCoords(r);
    // fToGoal = Math.atan2(r.z - goal.z, goal.x - r.x);
    // initial_dist = r.distance(goal);
    // circleCompleted = false;
    // hit_point = r;
    // return true;
    // }
    // return false;
    // }
}
