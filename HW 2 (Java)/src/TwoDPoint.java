import java.util.List;
import java.util.ArrayList;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point {
    private double x;
    private double y;
    private double [] coordinates = new double [2];

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates; // TODO
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {
        if (coordinates.length % 2 != 0){
            throw new IllegalArgumentException();
        }
        else {
            List<TwoDPoint> lst = new ArrayList<TwoDPoint>();
            for (int i = 0; i < coordinates.length; i = i + 2) {
                TwoDPoint temp = new TwoDPoint(coordinates[i], coordinates[i+1]);
                lst.add(temp);
            }
            return lst;
        }
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

    public boolean equals(Object another){
        if ( ! (another instanceof TwoDPoint))
            return false;
        Double x1 = this.getX();
        Double y1 = this.getY();
        Double x2 = ((TwoDPoint) another).getX();
        Double y2 = ((TwoDPoint) another).getY();
        if (x1.equals(x2) && y1.equals(y2))
            return true;
        else return false;
    }




}
