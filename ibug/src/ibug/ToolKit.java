package ibug;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import simbad.sim.Agent;

public class ToolKit {
    public static double wrapToPi(double a) {
        if (a > Math.PI)
            return a - Math.PI * 2;
        if (a <= -Math.PI)
            return a + Math.PI * 2;
        return a;
    }

    public static double get_angle(Agent terminator) {
        double angle = 0;
        double msin;
        double mcos;
        Transform3D m_Transform3D = new Transform3D();
        terminator.getRotationTransform(m_Transform3D);
        Matrix3d m1 = new Matrix3d();
        m_Transform3D.get(m1);
        msin = m1.getElement(2, 0);
        mcos = m1.getElement(0, 0);
        if (msin < 0) {
            angle = Math.acos(mcos);
        } else {
            if (mcos < 0) {
                angle = 2 * Math.PI - Math.acos(mcos);
            } else {
                angle = -Math.asin(msin);
            }
        }
        while (angle < 0)
            angle += Math.PI * 2;
        return angle;
    }

    public static Point3d get_local_coords(Agent terminator, Point3d p) {
        Point3d a = new Point3d();
        Point3d r = new Point3d();
        double th = get_angle(terminator);
        double x, y, z;
        terminator.getCoords(r);
        x = p.getX() - r.x;
        z = -p.getZ() + r.z;
        a.setX(x * Math.cos(th) + z * Math.sin(th));
        a.setZ(z * Math.cos(th) - x * Math.sin(th));
        a.setY(p.y);
        return a;
    }
}
