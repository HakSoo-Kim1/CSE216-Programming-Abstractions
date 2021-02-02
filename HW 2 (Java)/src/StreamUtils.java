import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class StreamUtils {

    public static Collection<String> capitalized (Collection<String> strings){
        return strings.stream().filter(string -> string.charAt(0) <= 90 && string.charAt(0) >= 65 ).collect(Collectors.toCollection(ArrayList::new));
    }



    public static String longest(Collection<String> strings, boolean from_start) {
        return strings.stream().reduce((first, second) -> {
            if (from_start) {
                if (first.length() >= second.length())
                    return first;
                else
                    return second;
            }
            else
            if (first.length() > second.length())
                return first;
            else
                return second;
        }).orElse(null);
    }

    public static <T extends Comparable <T>> T least (Collection <T> items, boolean from_start){
        return items.stream().reduce((first,second) -> {
            if (from_start) {
                if (first.compareTo(second) <= 0)
                    return first;
                else
                    return second;
            } else {
                if (first.compareTo(second) < 0)
                    return first;
                else
                    return second;
            }
        }).orElse(null);
    }


    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap.entrySet().stream().map( str -> (str.getKey() + " -> " + str.getValue())).collect(Collectors.toList());
    }


    public static void main(String[] args){

//            List<String> arr = new LinkedList<String>();
//        arr.add("abcsd");
//        arr.add("def");
//        arr.add("as");
//        arr.add("cs");
//        arr.add("Baaaa");
//        arr.add("Abbbb");
//        System.out.println(longest(arr,true));
//        System.out.println(capitalized(arr));
////        Collection<String> capitalized = capitalized(arr);
//
//
//
//
//        System.out.println("123123123");
//        Collection str =new ArrayList<>();
//
//        System.out.println(str);
//
//        Map<String, Integer> vehicles = new HashMap<>();
//
//        // Add some vehicles.
//        vehicles.put("BMW", 5);
//        vehicles.put("Mercedes", 3);
//        vehicles.put("Audi", 4);
//        vehicles.put("Ford", 10);
//        System.out.println(flatten(vehicles));
//        double a = 0;


    }

}
