import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Square extends Rectangle implements Snappable {

    public Square(double... vertices) {
        this(TwoDPoint.ofDoubles(vertices));
    }


    // TODO: this constructor must NOT be changed. Instead, resolve the error by adding code elsewhere.
    public Square(List<TwoDPoint> vertices) {
        super(vertices);
    }


    /**
     * Given a list of vertices assumed to be provided in a counterclockwise order in a two-dimensional plane, checks
     * whether or not they constitute a valid square.
     *
     * @param vertices the specified list of vertices in a counterclockwise order
     * @return <code>true</code> if the four vertices can form a square, <code>false</code> otherwise.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        return vertices.size() == 4 &&
                DoubleStream.of(getSideLengths()).boxed().collect(Collectors.toSet()).size() == 1;
    }

    /**
     * Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
     * the integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape
     * to a general quadrilateral, hence the return type. The only exception is when the square is positioned in a way
     * where this approximation will lead it to vanish into a single point. In that case, a call to {@link #snap()}
     * will not modify this square in any way.
     */
    @Override
    public Quadrilateral snap() {
        TwoDPoint v0 = new TwoDPoint(Math.round(this.getPosition().get(0).getX()), Math.round(this.getPosition().get(0).getY()));
        TwoDPoint v1 = new TwoDPoint(Math.round(this.getPosition().get(1).getX()), Math.round(this.getPosition().get(1).getY()));
        TwoDPoint v2 = new TwoDPoint(Math.round(this.getPosition().get(2).getX()), Math.round(this.getPosition().get(2).getY()));
        TwoDPoint v3 = new TwoDPoint(Math.round(this.getPosition().get(3).getX()), Math.round(this.getPosition().get(3).getY()));

        if (v0.getY()==v1.getY() && v0.getX() == v1.getX())
            return this;

        List<TwoDPoint> copy = new ArrayList<TwoDPoint>();
        copy.add(v0);
        copy.add(v1);
        copy.add(v2);
        copy.add(v3);
        Quadrilateral newQuadrilateralCopy = new Quadrilateral(copy);
        return newQuadrilateralCopy;
    }
    public double area() {
        return super.area();
    }

}
