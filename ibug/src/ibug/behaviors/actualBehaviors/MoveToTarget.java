package ibug.behaviors.actualBehaviors;

import ibug.behaviors.Behavior;
import ibug.behaviors.Sensors;
import ibug.behaviors.Velocities;
import ibug.MyRobot;

public class MoveToTarget extends Behavior {

    MyRobot terminator;
    static double K = 0.5;

    public MoveToTarget(MyRobot terminoid, Sensors sensors) {
        super(sensors);
        this.terminator = terminoid;
    }

    @Override
    public boolean is_active() {
        return true;
    }

    @Override
    public Velocities act() {

        double clum = get_sensors().get_center_light_sensor().getLux();
        double new_velocity = clum > 0.08 ? 0.0 : K; // 0.0864
        return new Velocities(new_velocity, 0.0);
    }
}
