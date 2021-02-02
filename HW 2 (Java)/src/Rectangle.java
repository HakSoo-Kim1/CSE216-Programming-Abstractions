import java.util.List;
import java.util.Objects;

public class Rectangle extends Quadrilateral implements SymmetricTwoDShape {

    public Rectangle(double... vertices) {
        this(TwoDPoint.ofDoubles(vertices));
    }


    public Rectangle(List<TwoDPoint> vertices) {
        super(vertices);
    }


    /**
     * The center of a rectangle is calculated to be the point of intersection of its diagonals.
     *
     * @return the center of this rectangle.
     */
    @Override
    public Point center() {
        double side1 = this.getSideLengths()[0]/2.0;
        double side2 = this.getSideLengths()[1]/2.0;
        double x = this.getPosition().get(0).getX() - side1;
        double xRound = (Math.round(x * 1000.0) / 1000.0);
        double y = this.getPosition().get(0).getY() - side2;
        double yRound = (Math.round(y * 1000.0) / 1000.0);

        TwoDPoint rectangleCenter = new TwoDPoint(xRound, yRound);
        return rectangleCenter; // TODO
    }

    @Override
    public boolean isMember(List<? extends Point> vertices) {

        if (! super.isMember(vertices))
            return false;

        for (int i = 0; i< vertices.size(); i++){
            if (! (vertices.get(i) instanceof TwoDPoint))
                return false;
        }
        if ( vertices.get(0).equals(vertices.get(1)) || vertices.get(0).equals(vertices.get(2))
                || vertices.get(0).equals(vertices.get(3)) || vertices.get(1).equals(vertices.get(2))
        || vertices.get(1).equals(vertices.get(3)) || vertices.get(2).equals(vertices.get(3))
        )
            return false;

        Double side1 = this.getSideLengths()[0];
        Double side1Round = (Math.round(side1 * 1000.0) / 1000.0);
        Double side2 = this.getSideLengths()[1];
        Double side2Round = (Math.round(side2 * 1000.0) / 1000.0);
        Double side3 = this.getSideLengths()[2];
        Double side3Round = (Math.round(side3 * 1000.0) / 1000.0);
        Double side4 = this.getSideLengths()[3];
        Double side4Round = (Math.round(side4 * 1000.0) / 1000.0);

        Double diagonol1 = Math.pow(side1 * side1 + side4 * side4 , 0.5);
        Double diagonol1Round = (Math.round(diagonol1 * 1000.0) / 1000.0);
        Double diagonol2 = Math.pow(side2 * side2 + side3 * side3 , 0.5);
        Double diagonol2Round = (Math.round(diagonol2 * 1000.0) / 1000.0);


        if (side1Round.equals(side3Round) && side2Round.equals(side4Round)) {
            if (diagonol1Round.equals(diagonol2Round));
            return true;
        }

        return false;
    }

    @Override
    public double area() {
        return super.area(); // TODO
    }
}
