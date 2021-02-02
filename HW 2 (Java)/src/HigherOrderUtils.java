import java.util.function.BiFunction;
import java.util.List;
import java.util.Arrays;

import java.util.function.Function;

public class HigherOrderUtils {

    private interface NamedBiFunction <T,U,R>  extends BiFunction<T, U, R>{
        String name();
    }

    public static NamedBiFunction <Double , Double, Double> add = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double first, Double second) {
            return first + second;
        }

        @Override
        public String name() {
            return "add";
        }
    };




    public static NamedBiFunction <Double , Double, Double> subtract = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double first, Double second) {
            return first - second;
        }

        @Override
        public String name() {
            return "diff";
        }
    };


    public static NamedBiFunction <Double , Double, Double> multiply = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double first, Double second) {
            return first * second;
        }

        @Override
        public String name() {
            return "mult";
        }
    };

    public static NamedBiFunction <Double , Double, Double> divide = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double first, Double second) {
            if (second == 0.0)
                throw new ArithmeticException();
            return first / second;
        }

        @Override
        public String name() {
            return "div";
        }
    };

    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions){
        if (args.size() - 1 != bifunctions.size())
            throw new IllegalArgumentException();

        for (int i = 0; i < bifunctions.size(); i++)
           args.set(i + 1, bifunctions.get(i).apply(args.get(i),args.get(i + 1))) ;

        return args.get(args.size()-1);
    }

    public static class FuntionComposition<T, U, R> {
        public BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = (first, second) -> first.andThen(second);
    }

    public static void main(String[] args){

//        Function<Character, Integer> function1 = (value) -> Integer.valueOf(value);
//        Function<Integer, String> function2 = (value) -> "Your character value is " + value;
//        FuntionComposition<Character,Integer,String> f = new FuntionComposition();
//        Function<Character,String> functionCombined= f.composition.apply(function1,function2);
//        System.out.println(functionCombined.apply('2'));



//        Function< Integer,Integer > a =  function1.andThen(function2);
////        FuntionComposition<Integer,Integer,Integer>;
//        System.out.println(a.apply(3));
//        Function<Integer,Integer> functionCombined = FunctionComposition
//        FuntionComposition< Integer, Integer,Integer > a =  function1.andThen(function2);
//        Function<Integer, Integer> hey =




//        List<String> str = Arrays.asList("A","a", "B", "b", "C");
//        List<NamedBiFunction<String, String, String>> h = Arrays.asList(asd, asd, asd, asd,asd);
//        System.out.println(zip(str, h));

//        List<Double> nums = Arrays.asList(1.0,1.3, 1.5, 2.2, 3.1);
//        List<NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(add, multiply, add, divide);
//        System.out.println(zip(nums, bfs));
//        System.out.println(nums);

    }
}
