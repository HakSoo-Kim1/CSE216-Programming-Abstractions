import java.util.*;


public class Ordering {

    static class XLocationComparator implements Comparator<TwoDShape> {
        @Override
        public int compare(TwoDShape o1, TwoDShape o2) {
            double o1X = 0;
            double o2X = 0;
            if (o1 instanceof Circle) {
                o1X = ((Circle) o1).center().coordinates()[0] - ((Circle) o1).getRadius();
            } else if (o1 instanceof Quadrilateral) {
                o1X = ((Quadrilateral) o1).getPosition().get(0).getX();
                for (int i = 0; i < 4; i++) {
                    if (o1X > ((Quadrilateral) o1).getPosition().get(i).getX())
                        o1X = ((Quadrilateral) o1).getPosition().get(i).getX();
                }
            }
            else {
                throw new IllegalStateException();
            }

            if (o2 instanceof Circle) {
                o2X = ((Circle) o2).center().coordinates()[0] - ((Circle) o2).getRadius();
            } else if (o2 instanceof Quadrilateral) {
                o2X = ((Quadrilateral) o2).getPosition().get(0).getX();
                for (int i = 0; i < 4; i++) {
                    if (o2X > ((Quadrilateral) o2).getPosition().get(i).getX())
                        o2X = ((Quadrilateral) o2).getPosition().get(i).getX();
                }
            }
            else {
                throw new IllegalStateException();
            }
            return Double.compare(o1X, o2X);
        }

    }

    static class AreaComparator implements Comparator<SymmetricTwoDShape> {
        @Override
        public int compare(SymmetricTwoDShape o1, SymmetricTwoDShape o2) {
            double o1Area = o1.area();
            double o2Area = o2.area();
            return Double.compare(o1Area, o2Area);
        }
    }

    static class SurfaceAreaComparator implements Comparator<ThreeDShape> {
        @Override public int compare(ThreeDShape o1, ThreeDShape o2) {
            double o1SurfaceArea = 0;
            double o2SurfaceArea = 0;

            if (o1 instanceof Cuboid){
                o1SurfaceArea = ((Cuboid) o1).surfaceArea();
            } else if (o1 instanceof Sphere){
                o1SurfaceArea = ((Sphere) o1).surfaceArea();
            }
            else {
                throw new IllegalStateException();
            }
            if (o2 instanceof Cuboid){
                o2SurfaceArea = ((Cuboid) o2).surfaceArea();
            } else if (o2 instanceof Sphere){
                o2SurfaceArea = ((Sphere) o2).surfaceArea();
            }
            else {
                throw new IllegalStateException();
            }
            return Double.compare(o1SurfaceArea, o2SurfaceArea);
        }
    }

    // TODO: there's a lot wrong with this method. correct it so that it can work properly with generics.
    static  <T> void  copy(Collection<? extends T> src, Collection<T> dest) {
        dest.addAll(src);
    }


//    static void copy(Collection source, Collection destination) {
//        destination.addAll(source);
//    }


    public static void main(String[] args) {
//

        List<TwoDPoint>          vertice          = new ArrayList<>();
        TwoDPoint v1 = new TwoDPoint(3.8,3.7);
        TwoDPoint v2 = new TwoDPoint(1.7,3.7);
        TwoDPoint v3 = new TwoDPoint(1.7,1.6);
        TwoDPoint v4 = new TwoDPoint(3.8,1.6);
        vertice.add(v1);
        vertice.add(v2);
        vertice.add(v3);
        vertice.add(v4);
        Square a = new Square(vertice);
       Point b =  a.center();
        Quadrilateral c ;
        Cuboid.random();
        c = a.snap();

//        List<SymmetricTwoDShape> symmetricshapes = new ArrayList<>();
//
//        copy(symmetricshapes, shapes); // note-1 //
//
//
//        List<Number> numbers = new ArrayList<>();
//        numbers.add(123);
//        List<Double> doubles = new ArrayList<>();
//        doubles.add(456.7);
//        List<String> strings = new ArrayList<>();
//        strings.add("asd");
////        copy(strings,doubles);
////        doubles.add(123);
//
//        Set<Square>        squares = new HashSet<>();
//        Set<Quadrilateral> quads   = new LinkedHashSet<>();
//
//        copy(doubles, numbers); // note-2 //
//        copy(squares, quads);   // note-3 //
//
//

//        List<ThreeDShape>        threedshapes    = new ArrayList<>();
//        List<ThreeDPoint> vertices = new ArrayList<>();
//        vertices.add(new ThreeDPoint(0,0,0));
//        vertices.add(new ThreeDPoint(-5.5,0,0));
//        vertices.add(new ThreeDPoint(-5.5,0,5.5));
//        vertices.add(new ThreeDPoint(0,-5.5,0));
//        vertices.add(new ThreeDPoint(0,0,5.5));
//        vertices.add(new ThreeDPoint(-5.5,0,5.5));
//        vertices.add(new ThreeDPoint(0,-5.5,5.5));
//        vertices.add(new ThreeDPoint(-5.5,-5.5,5.5));
//        threedshapes.add(new Sphere(0,0,0,10));
//        threedshapes.add(new Cuboid(vertices));
//        Collections.sort(threedshapes);
//        threedshapes.sort(new SurfaceAreaComparator());
//
//        threedshapes.add(Cuboid.random());
//        threedshapes.add(Sphere.random());
//
//        /*
//         * uncomment the following block and fill in the "..." constructors to create actual instances. If your
//         * implementations are correct, then the code should compile and yield the expected results of the various
//         * shapes being ordered by their smallest x-coordinate, area, volume, surface area, etc. */
//
//        /*
//        symmetricshapes.add(new Rectangle(...));
//        symmetricshapes.add(new Square(...));
//        symmetricshapes.add(new Circle(...));
//
//                shapes.add(new Quadrilateral(new ArrayList<>()));
//
//         */
//        symmetricshapes.add(new Square(TwoDPoint.ofDoubles(0,0,-5,0,-5,-5,0,-5)));
//        symmetricshapes.add(new Rectangle(TwoDPoint.ofDoubles(0,0,-1.5,0,-1.5,-1.1,0,-1.1)));
//        symmetricshapes.add(new Circle(0,0,1.1));
//        symmetricshapes.sort(new AreaComparator());
//
//        List<TwoDShape>          shapes          = new ArrayList<>();
//        shapes.add(new Circle(0,0,2) );
//        List<TwoDPoint> a1 = new ArrayList<>();
//        a1.add(new TwoDPoint(0,0));
//        a1.add(new TwoDPoint(-4.5,0));
//        a1.add(new TwoDPoint(-4.5,-2.2));
//        a1.add(new TwoDPoint(0,-2.2));
//        shapes.add(new Rectangle(a1));
//
//
//        shapes.sort(new XLocationComparator());
//        shapes.sort(new AreaComparator());
//        symmetricshapes.sort(new XLocationComparator());

//        List<TwoDPoint> a2 = new ArrayList<>();
//        a2.add(new TwoDPoint(0,0));
//        a2.add(new TwoDPoint(0,-0.4));
//        a2.add(new TwoDPoint(-0.4,-0.4));
//        a2.add(new TwoDPoint(0,-0.4));
//        Square sq = new Square(a2);
//        Quadrilateral qued = sq.snap();
//        shapes.add(new Square(a2));

//
//
//        // sorting 3d shapes according to various criteria
//
//        copy(symmetricshapes, shapes); // note-1 //
//
////        Collections.sort(threedshapes);
////        threedshapes.sort(new SurfaceAreaComparator());
//
//        /*
//         * if your changes to copy() are correct, uncommenting the following block will also work as expected note that
//         * copy() should work for the line commented with 'note-1' while at the same time also working with the lines
//         * commented with 'note-2' and 'note-3'. */
//
//
//        List<Number> numbers = new ArrayList<>();
//        List<Double> doubles = new ArrayList<>();
//        Set<Square>        squares = new HashSet<>();
//        Set<Quadrilateral> quads   = new LinkedHashSet<>();
//
//        copy(doubles, numbers); // note-2 //
//        copy(squares, quads);   // note-3 //
//        System.out.println("DONE");
//        System.out.println(Arrays.asList(Cuboid.random().getVertices()));
//        System.out.println(Sphere.random());



    }
}
