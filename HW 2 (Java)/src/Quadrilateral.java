import java.util.Arrays;
import java.util.List;

public class Quadrilateral implements Positionable, TwoDShape {


    private final TwoDPoint[] vertices = new TwoDPoint[4];


    public Quadrilateral(double... vertices) {
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Quadrilateral(List<TwoDPoint> vertices) {
        int n = 0;
        for (TwoDPoint p : vertices) this.vertices[n++] = p;
        if (!isMember(vertices))
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                    this.getClass().getCanonicalName()));
    }


    /**
     * Given a list of four points, adds them as the four vertices of this quadrilateral in the order provided in the
     * list. This is expected to be a counterclockwise order of the four corners.
     *
     * @param points the specified list of points.
     * @throws IllegalStateException if the number of vertices provided as input is not equal to four.
     */
    @Override
    // should we throw an error when given arrayList is not consisted of 4 twoDPoint,

    public void setPosition(List<? extends Point> points) {
        if (points.size() != 4)
            throw new IllegalStateException();

        for (int i = 0; i < 4; i++) {
            if (!(points.get(i) instanceof TwoDPoint))
                throw new IllegalArgumentException();
        }

        vertices[0] = (TwoDPoint) points.get(0);
        vertices[1] = (TwoDPoint) points.get(1);
        vertices[2] = (TwoDPoint) points.get(2);
        vertices[3] = (TwoDPoint) points.get(3);
    }

    @Override
    public List<TwoDPoint> getPosition() {
        return Arrays.asList(vertices);
    }

    /**
     * @return the lengths of the four sides of the quadrilateral. Since the setter {@link Quadrilateral#setPosition(List)}
     *         expected the corners to be provided in a counterclockwise order, the side lengths are expected to be in
     *         that same order.
     */
    protected double[] getSideLengths() {
        double [] sideArr = new double [4];
        double side1 = Math.pow(Math.pow(vertices[0].getX() - vertices[1].getX(), 2) + Math.pow(vertices[0].getY() - vertices[1].getY(), 2), 0.5);
        double side1Round = (Math.round(side1 * 1000.0) / 1000.0);
        double side2 = Math.pow(Math.pow(vertices[1].getX() - vertices[2].getX(), 2) + Math.pow(vertices[1].getY() - vertices[2].getY(), 2), 0.5);
        double side2Round = (Math.round(side2 * 1000.0) / 1000.0);
        double side3 = Math.pow(Math.pow(vertices[2].getX() - vertices[3].getX(), 2) + Math.pow(vertices[2].getY() - vertices[3].getY(), 2), 0.5);
        double side3Round = (Math.round(side3 * 1000.0) / 1000.0);
        double side4 = Math.pow(Math.pow(vertices[3].getX() - vertices[0].getX(), 2) + Math.pow(vertices[3].getY() - vertices[0].getY(), 2), 0.5);
        double side4Round = (Math.round(side4 * 1000.0) / 1000.0);
        sideArr[0] = side1Round;
        sideArr[1] = side2Round;
        sideArr[2] = side3Round;
        sideArr[3] = side4Round;
        return sideArr; // TODO
    }

    @Override
    public int numSides() { return 4; }

    @Override
    public boolean isMember(List<? extends Point> vertices) { return vertices.size() == 4; }

    public double area() {
        double area = this.getSideLengths()[0] * this.getSideLengths()[1];
        return area; // TODO
    }


}
