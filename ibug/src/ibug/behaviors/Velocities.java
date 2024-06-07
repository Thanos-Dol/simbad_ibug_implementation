package ibug.behaviors;

public class Velocities {
    private double translational_velocity;
    private double rotational_velocity;

    public Velocities(double translational_velocity, double rotational_velocity) {
        this.translational_velocity = translational_velocity;
        this.rotational_velocity = rotational_velocity;
    }

    public double get_translational_velocity() {
        return translational_velocity;
    }

    public double get_rotational_velocity() {
        return rotational_velocity;
    }
}
