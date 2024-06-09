package ibug;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.RobotFactory;

import ibug.behaviors.Behavior;
import ibug.behaviors.Sensors;
import ibug.behaviors.Velocities;
import ibug.behaviors.actualBehaviors.*;

public class MyRobot extends Agent {

    static boolean CLOCKWISE = false;
    static double K1 = 5;
    static double K2 = 0.8;
    static double K3 = 1;
    double SAFETY = 0.8;

    private Sensors sensors;

    private Behavior[] behaviors;
    private int current_behaviour_index;
    private boolean[][] subsumption_matrix;

    private boolean sensor_block;

    public boolean get_sensor_block() {
        return sensor_block;
    }

    public void set_sensor_block(boolean sensor_block_value) {
        this.sensor_block = sensor_block_value;
    }

    private boolean circum_navigating;

    public boolean get_circum_navigating() {
        return circum_navigating;
    }

    public void set_circum_navigating(boolean circum_navigating_value) {
        this.circum_navigating = circum_navigating_value;
    }

    public MyRobot(Vector3d position, String name, int num_of_sensors) {
        super(position, name);

        sensors = new Sensors(RobotFactory.addSonarBeltSensor(this, num_of_sensors),
                RobotFactory.addLightSensor(this, new Vector3d(0.4, 0.3, -0.4), 0, "left light sensor"),
                RobotFactory.addLightSensor(this, new Vector3d(0.4, 0.3, 0.4), 0, "right light sensor"),
                RobotFactory.addLightSensor(this, new Vector3d(0, 0.3, 0), 0, "right light sensor"));

        behaviors = new Behavior[3];

        behaviors[0] = new CircumNavigate(
                sensors,
                this,
                7,
                CLOCKWISE);

        behaviors[1] = new Align(
                sensors,
                this);

        behaviors[2] = new MoveToTarget(
                this,
                sensors);

        boolean[][] temp = {
                { false, true, true },
                { false, false, true },
                { false, false, false }
        };

        subsumption_matrix = temp;
    }

    public void initBehavior() {
        current_behaviour_index = 0;
        setTranslationalVelocity(0.0);
        setRotationalVelocity(0.0);
        sensor_block = false;
        circum_navigating = false;
    }

    public void performBehavior() {
        boolean behaviour_active_status[] = new boolean[behaviors.length];

        for (int i = 0; i < behaviour_active_status.length; ++i) {
            behaviour_active_status[i] = behaviors[i].is_active();
        }

        boolean found_next_behaviour = false;
        while (!found_next_behaviour) {
            boolean current_behaviour = behaviour_active_status[current_behaviour_index];
            if (current_behaviour) {
                for (int i = 0; i < subsumption_matrix.length; ++i) {
                    if (behaviour_active_status[i] && subsumption_matrix[i][current_behaviour_index]) {
                        current_behaviour = false;
                        break;
                    }
                }
            }

            if (current_behaviour) {
                Velocities velocities_update = behaviors[current_behaviour_index].act();

                this.setTranslationalVelocity(velocities_update.get_translational_velocity());
                this.setRotationalVelocity(velocities_update.get_rotational_velocity());

                current_behaviour_index = 0;
                found_next_behaviour = true;
            }

            if (!found_next_behaviour) {
                current_behaviour_index = (current_behaviour_index + 1) % subsumption_matrix.length;
            }
        }
    }
}
