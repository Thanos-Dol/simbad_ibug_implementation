package ibug.behaviors.actualBehaviors;

import ibug.MyRobot;
import ibug.behaviors.Behavior;
import ibug.behaviors.Sensors;
import ibug.behaviors.Velocities;
import simbad.sim.Agent;

public class Align extends Behavior {

    MyRobot terminator;
    double STABLE_ROTATIONAL = 0.2;
    double THRESHOLD_CONSTANT = 1e-2;
    double K = 1e3;

    public Align(Sensors sensors, MyRobot terminoid) {
        super(sensors);
        this.terminator = terminoid;
    }

    @Override
    public Velocities act() {
        double llum = get_sensors().get_left_light_sensor().getLux();
        double rlum = get_sensors().get_right_light_sensor().getLux();
        double clum = get_sensors().get_center_light_sensor().getLux();

        // double new_rotational = llum > rlum ? 1 : -1;
        // double new_rotational = Math.abs(llum - rlum) < THRESHOLD ? 0.2 : (llum -
        // rlum) * 1e4;

        double new_rotational = llum > rlum ? STABLE_ROTATIONAL : -STABLE_ROTATIONAL;
        // double new_rotational = K * ((llum == rlum && ((llum + rlum) / 2 < clum)) ?
        // -0.1 : llum - rlum);

        return new Velocities(0, new_rotational);
    }

    @Override
    public boolean is_active() {
        double llum = get_sensors().get_left_light_sensor().getLux();
        double rlum = get_sensors().get_right_light_sensor().getLux();
        double clum = get_sensors().get_center_light_sensor().getLux();

        if (clum > 0.081) {
            terminator.set_sensor_block(false);
            return false;
        }

        if (Math.abs(llum - rlum) < (clum * THRESHOLD_CONSTANT) && (clum < (llum + rlum) / 2)) {
            terminator.set_sensor_block(false);
            return false;
        }
        return true;
    }
}
