package ibug.behaviors;

import simbad.sim.LightSensor;
import simbad.sim.RangeSensorBelt;

public class Sensors {

    private RangeSensorBelt sonars;
    private LightSensor left_light_sensor;
    private LightSensor right_light_sensor;
    private LightSensor center_light_sensor;

    public Sensors(RangeSensorBelt sonars, LightSensor left_light_sensor, LightSensor right_light_sensor,
            LightSensor center_light_sensor) {
        this.sonars = sonars;
        this.left_light_sensor = left_light_sensor;
        this.right_light_sensor = right_light_sensor;
        this.center_light_sensor = center_light_sensor;
    }

    public RangeSensorBelt get_sonars() {
        return sonars;
    }

    public LightSensor get_left_light_sensor() {
        return left_light_sensor;
    }

    public LightSensor get_right_light_sensor() {
        return right_light_sensor;
    }

    public LightSensor get_center_light_sensor() {
        return center_light_sensor;
    }
}
