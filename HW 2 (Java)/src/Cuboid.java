import java.util.List;
import java.util.ArrayList;

// TODO : a missing interface method must be implemented in this class to make it compile. This must be in terms of volume().
public class Cuboid implements ThreeDShape {


    private final ThreeDPoint[] vertices = new ThreeDPoint[8];

    /**
     * Creates a cuboid out of the list of vertices. It is expected that the vertices are provided in
     * the order as shown in the figure given in the homework document (from v0 to v7).
     *
     * @param vertices the specified list of vertices in three-dimensional space.
     */
    public Cuboid(List<ThreeDPoint> vertices) {
        if (vertices.size() != 8)
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                    this.getClass().getName()));
        int n = 0;
        for (ThreeDPoint p : vertices) this.vertices[n++] = p;
    }

    @Override
    public double volume() {
        double xMax = vertices[0].getX();
        double xMin = vertices[0].getX();
        double yMax = vertices[0].getY();
        double yMin = vertices[0].getY();
        double zMax = vertices[0].getZ();
        double zMin = vertices[0].getZ();
        double b = 0;
        double l = 0;
        double h = 0;
        for (int i = 0; i < 8; i ++) {
            if (xMax < vertices[i].getX()) {
                xMax = vertices[i].getX();
            }
            if (xMin > vertices[i].getX()) {
                xMin = vertices[i].getX();
            }
            if (yMax < vertices[i].getY()) {
                yMax = vertices[i].getY();
            }
            if (yMin > vertices[i].getY()) {
                yMin = vertices[i].getY();
            }
            if (zMax < vertices[i].getZ()) {
                zMax = vertices[i].getZ();
            }
            if (zMin > vertices[i].getZ()) {
                zMin = vertices[i].getZ();
            }
        }
            b = xMax - xMin;
            l = yMax - yMin;
            h = zMax - zMin;

        return b * l * h;
    }

    public static Cuboid random(){
        double l = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);
        double w = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);
        double h = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);

        double x1 = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);
        double y1 = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);
        double z1 = ((double)Math.round( (Math.random()*100.0) * 1000.0) / 1000.0);

        ThreeDPoint v0 = new ThreeDPoint(x1, y1, z1);
        ThreeDPoint v1 = new ThreeDPoint(x1 + l, y1, z1);
        ThreeDPoint v2 = new ThreeDPoint(x1 + l, y1 + h, z1);
        ThreeDPoint v3 = new ThreeDPoint(x1, y1 + h, z1);
        ThreeDPoint v4 = new ThreeDPoint(x1, y1 + h, z1 + w);
        ThreeDPoint v5 = new ThreeDPoint(x1, y1, z1 + w);
        ThreeDPoint v6 = new ThreeDPoint(x1 + l, y1, z1 + w);
        ThreeDPoint v7 = new ThreeDPoint(x1 + l, y1 + h, z1 + w);
        ArrayList<ThreeDPoint> vertices =  new ArrayList<ThreeDPoint>();
        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        vertices.add(v7);
        Cuboid randomCuboid = new Cuboid(vertices);
        return randomCuboid;
    }

    @Override
    public ThreeDPoint center() {
        double x = 0;
        double y = 0;
        double z = 0;
        for (int i = 0; i < 8; i++){
            x += vertices[i].getX();
            y += vertices[i].getY();
            z += vertices[i].getZ();
        }

        ThreeDPoint cuboidCenter = new ThreeDPoint((x/8.0),(y/8.0),(z/8.0));
        return cuboidCenter; // TODO
    }

    @Override
    public int compareTo(ThreeDShape o) {
        return (int) (this.volume() - o.volume());
    }

    public ThreeDPoint[] getVertices() {
        return vertices;
    }

    public double surfaceArea(){
        double xMax = vertices[0].getX();
        double xMin = vertices[0].getX();
        double yMax = vertices[0].getY();
        double yMin = vertices[0].getY();
        double zMax = vertices[0].getZ();
        double zMin = vertices[0].getZ();
        double b = 0;
        double l = 0;
        double h = 0;
        for (int i = 0; i < 8; i ++) {
            if (xMax < vertices[i].getX()) {
                xMax = vertices[i].getX();
            }
            if (xMin > vertices[i].getX()) {
                xMin = vertices[i].getX();
            }
            if (yMax < vertices[i].getY()) {
                yMax = vertices[i].getY();
            }
            if (yMin > vertices[i].getY()) {
                yMin = vertices[i].getY();
            }
            if (zMax < vertices[i].getZ()) {
                zMax = vertices[i].getZ();
            }
            if (zMin > vertices[i].getZ()) {
                zMin = vertices[i].getZ();
            }
        }
            b = xMax - xMin;
            l = yMax - yMin;
            h = zMax - zMin;

        return 2 * b * l + 2 * l * h + 2 * b * h;
    }



}
