package ibug;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Point3d;

import simbad.sim.Arch;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;
import simbad.sim.Line;
import simbad.sim.Box;

public class Env extends EnvironmentDescription {

    Env() {

        light1SetPosition(-8, 2, 0);
        light1IsOn = true;
        light2SetPosition(8, 2, 0);
        light2IsOn = false;

        add(new Box(new Vector3d(1.4, 0, 0), new Vector3f(6, 1, 6), this));

        add(new Box(new Vector3d(-3, 0, 0), new Vector3f(1, 1, 1), this));

        add(new Box(new Vector3d(-5, 0, 0), new Vector3f(1, 1, 1), this));

        add(new MyRobot(new Vector3d(8, 0, 0), "terminator", 36));
    }
}
