public class Sphere implements ThreeDShape {

    private ThreeDPoint center;
    private double radius;

    public Sphere(double centerX, double centerY, double centerZ, double radius){
        if (radius < 0)
            throw new IllegalArgumentException();
        this.center = new ThreeDPoint(centerX, centerY, centerZ);
        this.radius = radius;
    }

    public void setRadius(double r) { this.radius = r; }

    public double getRadius()       { return radius; }

    public static Sphere random(){
        double x = ((double)Math.round( (Math.random()*100) * 1000) / 1000);
        double y = ((double)Math.round( (Math.random()*100) * 1000) / 1000);
        double z = ((double)Math.round( (Math.random()*100) * 1000) / 1000);
        double r = ((double)Math.round( (Math.random()*100) * 1000) / 1000);
    Sphere randomSphere = new Sphere (x, y, z, r);
    return randomSphere;
    }

    public double surfaceArea(){
        return Math.PI * 4 * Math.pow(radius, 2);
    }
    @Override
    public Point center() {
        return center;
    }

    @Override
    public double volume() {
        return (4.0/3.0) * Math.PI * (Math.pow(radius,3));
    }

    @Override
        public int compareTo(ThreeDShape o) {
            return (int) (this.volume() - o.volume());
        }

        public String toString(){
        return center.getX() + " " + center.getY() + " " + center.getZ() + getRadius();
        }
    }

