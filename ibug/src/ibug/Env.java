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

        light1SetPosition(-8, 4, 0);
        light1IsOn = true;
        light2SetPosition(8, 4, 0);
        light2IsOn = false;

        // Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
        // w1.rotate90(1);
        // add(w1);
        // Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
        // w2.rotate90(1);
        // add(w2);
        // Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
        // add(w3);
        // Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
        // add(w4);

        // add(new Arch(new Vector3d(0, 0, -5), this));
        // add(new Arch(new Vector3d(0, 0, 5), this));
        // Arch a = new Arch(new Vector3d(5, 0, 0), this);
        // a.rotate90(1);
        // add(a);
        // Arch b = new Arch(new Vector3d(-5, 0, 0), this);
        // b.rotate90(1);
        // add(b);

        add(new Box(new Vector3d(2, 0, 0), new Vector3f(6, 1, 6), this));

        add(new Box(new Vector3d(-3, 0, 0), new Vector3f(1, 1, 1), this));

        add(new MyRobot(new Vector3d(8, 0, 0), "terminator", 36, new Point3d(-8, 0, 5)));
    }
}
