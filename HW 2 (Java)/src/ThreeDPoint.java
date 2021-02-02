/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {
    private double x;
    private double y;
    private double z;
    private double [] coordinates = new double [3];

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // TODO
    }
    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
        return coordinates;
    }


    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
//    public void setX(double x){
//        this.x = x;
//    }
//    public void setY(double y){
//        this.y = y;
//    }
//    public void setZ(double z){
//        this.z = z;
//    }
    public String toString(){
        return x +" " +  y +" "+ z + "     ";
    }

}
