package ibug.behaviors;

public abstract class Behavior {
    private Sensors sensors;

    private static final int ROTATION_COUNT = 20;

    protected static final double TRANSLATIONAL_VELOCITY = 0.4;

    public Behavior(Sensors sensors) {
        this.sensors = sensors;
    }

    public abstract Velocities act();

    public abstract boolean is_active();

    protected Sensors get_sensors() {
        return sensors;
    }

    public static int get_rotation_count() {
        return ROTATION_COUNT;
    }
}
