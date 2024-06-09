package ibug.behaviors;

public abstract class Behavior {
    private Sensors sensors;

    public Behavior(Sensors sensors) {
        this.sensors = sensors;
    }

    public abstract Velocities act();

    public abstract boolean is_active();

    protected Sensors get_sensors() {
        return sensors;
    }
}
