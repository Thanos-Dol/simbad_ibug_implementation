package ibug.behaviors;

import simbad.sim.LightSensor;
import simbad.sim.RangeSensorBelt;

public class Sensors {

    private RangeSensorBelt sonars;
    private RangeSensorBelt bumpers;
    private LightSensor left_light_sensor;
    private LightSensor right_light_sensor;
    private LightSensor center_light_sensor;

    public Sensors(RangeSensorBelt sonars, RangeSensorBelt bumpers,
            LightSensor left_light_sensor, LightSensor right_light_sensor, LightSensor center_light_sensor) {
        this.sonars = sonars;
        this.bumpers = bumpers;
        this.left_light_sensor = left_light_sensor;
        this.right_light_sensor = right_light_sensor;
        this.center_light_sensor = center_light_sensor;
    }

    public RangeSensorBelt get_sonars() {
        return sonars;
    }

    public RangeSensorBelt get_bumpers() {
        return bumpers;
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
