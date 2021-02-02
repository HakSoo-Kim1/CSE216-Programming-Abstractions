import java.util.*;

/**
 * The {@code SparsePolynomial} class, extending Polynomial, represents a polynomial.
 * This polynomial can only have {@code int} degrees and coefficients.
 * An object of type {@code SparsePolynomial} contains an NavigableMap data type of {@code Integer}, which represents
 * coefficients and degrees of polynomial
 *
 * @author Hak Soo Kim (111045936)
 * @version 1.0, November 30th 2020
 *
 */
public class SparsePolynomial implements  Polynomial {

    /**
     * {@code Integer} NavigableMap type polynomialMap for polynomial
     *   In NavigableMap, each key represents degree, value represents coefficient
     * {@code String} array type stringPolynomialArr for polynomial
     *   stringPolynomialArr contains {@code String} representing polynomial
     */
    private NavigableMap<Integer,Integer> polynomialMap;
    private String [] stringPolynomialArr;

    /**
     * Constructor for {@code SparsePolynomial} class
     *   Constructs a newly allocated {@code SparsePolynomial} object represented by the string.
     *   The string is converted to polynomial represented by {@code int} Map and {@code String} array.
     *   Throws an {@link IllegalArgumentException} if the string is not sufficient to be converted.
     *
     * @param str a non empty and correct format of canonical string to be converted to a {@code SparsePolynomial}.
     * @throws IllegalArgumentException if {@code str} is not correct format of canonical, or
     *   degrees and coefficients are not {@code int} in valid range , or out of boundary to handle with array or {@code int}.
     */
    public SparsePolynomial(String str) {
        if (str.length() == 0) {
            throw new IllegalArgumentException("Empty string is not allowed");
        }

        str = str.trim();
        if (str.contains(" 0")|| str.contains("+ -")  || str.startsWith("0") || str.contains("^0") || str.contains("++") || str.contains("--") || str.contains(" 0 ") || str.contains("- 0")|| str.contains("-0"))
            throw new IllegalArgumentException("not form of canonical string representation");
        str = str.replaceAll("^-x","- x");
        str = str.replaceAll("^x", "1x");
        str = str.replaceAll("^- x", "-1x");
        str = str.replace(" + ", " ");
        str = str.replace("- ", "-");
        String[] arr = str.split(" ");
        ArrayList<String> arrayList = new ArrayList(Arrays.asList(arr));
        boolean isNumber = false;
        for (int i = 0; i < arrayList.size(); i++) {
            isNumber = arrayList.get(i).matches("[-+]?\\d*\\.?\\d+");
            if (isNumber) {
                arrayList.add(i + 1, "0");
                break;
            }
        }
        StringBuilder newStr = new StringBuilder();
        for (String s : arrayList) {
            newStr.append(s).append(" ");
        }
        newStr = new StringBuilder(newStr.toString().replace(" x", " 1x"));
        newStr = new StringBuilder(newStr.toString().replace("x^", " "));
        newStr = new StringBuilder(newStr.toString().replace("x", " 1"));
        newStr = new StringBuilder(newStr.toString().replace(" - ", " -1 "));
        stringPolynomialArr = newStr.toString().split(" ");

        if (!wellFormed())
            throw new IllegalArgumentException("coefficients and degrees must always be integer and integer format");
        try{
            if (stringPolynomialArr.length >= 4) {
                for (int i = 1; i < stringPolynomialArr.length - 2; i = i + 2) {
                    if (Integer.parseInt(stringPolynomialArr[i]) <= Integer.parseInt(stringPolynomialArr[i + 2]))
                        throw new IllegalArgumentException("not form of canonical string representation");
                }
            }

            TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
            for (int i = 0; i < stringPolynomialArr.length; i = i + 2) {
                treeMap.put(Integer.valueOf(stringPolynomialArr[i + 1]), Integer.valueOf(stringPolynomialArr[i]));
            }
            polynomialMap = treeMap.descendingMap();
        }
        catch( OutOfMemoryError | NegativeArraySizeException e){
            throw new IllegalArgumentException("Your number is out of boundary to handle");
        }
        catch( NumberFormatException e){
            throw new IllegalArgumentException("given number is out of boundary or canonical format is wrong");
        }
    }


    /**
     * private constructor for {@code SparsePolynomial} class
     * constructs a newly allocated {@code SparsePolynomial} object represented by NavigableMap type of {@code Integer}.
     * @param polynomialMap a NavigableMap type of {@code Integer} to be converted to a {@code SparsePolynomial}.
     */
    private SparsePolynomial(NavigableMap<Integer,Integer> polynomialMap){
        this.polynomialMap = polynomialMap;
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    @Override
    public int degree() {
        for (Integer key : polynomialMap.keySet()) {
            if (polynomialMap.get(key) != 0){
                return key;
            }
        }
        return 0;
    }

    /**
     * Returns the coefficient corresponding to the given exponent.  Returns 0 if there is no term with that exponent
     * in the polynomial.
     *
     * @param d the exponent whose coefficient is returned.
     * @return the coefficient of the term of whose exponent is d.
     */
    @Override
    public int getCoefficient(int d) {
        if ( polynomialMap.get(d) != null)
            return polynomialMap.get(d);
        return 0;
    }

    /**
     * Checks if polynomial represents the zero constant.
     *
     * @return {@literal true} if the polynomial represents the zero constant; {@literal false} otherwise.
     */
    @Override
    public boolean isZero() {
        for (Integer key : polynomialMap.keySet()) {
            if (polynomialMap.get(key) != 0)
                return false;
        }
        return true;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *
     * @param q the non-null valid polynomial to add to <code>this</code>
     * @return <code>this + </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if {@code q} is not sufficient to add {@code this}.
     */
    @Override
    public Polynomial add(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if (q instanceof DensePolynomial){
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
            for (int i = 0; i < ((DensePolynomial) q).getPolynomialArr().length ; i++){
                if (((DensePolynomial) q).getPolynomialArr()[i] != 0){
                    addedPolynomialMap.put(i,((DensePolynomial) q).getPolynomialArr()[i]);
                }
            }

            for (Integer key : this.polynomialMap.keySet()) {
                if (addedPolynomialMap.get(key) != null){
                    addedPolynomialMap.replace(key, this.polynomialMap.get(key) + addedPolynomialMap.get(key));
                }
                else{
                    addedPolynomialMap.put(key, this.polynomialMap.get(key));
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else if (q instanceof SparsePolynomial){
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
            for (Integer key : this.polynomialMap.keySet()) {
                addedPolynomialMap.put(key, this.polynomialMap.get(key));
            }
            for (Integer key : ((SparsePolynomial) q).polynomialMap.keySet()) {
                if (addedPolynomialMap.get(key) != null){
                    addedPolynomialMap.replace(key, addedPolynomialMap.get(key) + ((SparsePolynomial) q).polynomialMap.get(key));
                }
                else {
                    addedPolynomialMap.put(key,((SparsePolynomial) q).polynomialMap.get(key));
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else {
            throw new IllegalArgumentException("Only possible between polynomials");
        }
    }

    /**
     * Returns a polynomial by multiplying the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *
     * @param q the non-null valid polynomial to multiply to <code>this</code>
     * @return <code>this * </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if {@code q} is not sufficient to multiply {@code this}.
     */
    @Override
    public Polynomial multiply(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if (q instanceof DensePolynomial) {
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();

            for (int i = 0 ; i < ((DensePolynomial) q).getPolynomialArr().length; i++){
                for (Integer key : this.getPolynomialMap().keySet()) {
                    if (addedPolynomialMap.get(i + key) == null) {
                        addedPolynomialMap.put(i + key, ((DensePolynomial) q).getPolynomialArr()[i] * this.getPolynomialMap().get(key));
                    }
                    else {
                        addedPolynomialMap.replace(i + key, ((DensePolynomial) q).getPolynomialArr()[i] * this.getPolynomialMap().get(key) + addedPolynomialMap.get(i + key));
                    }
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else if ( q instanceof SparsePolynomial){
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
            for (Integer key1 : this.getPolynomialMap().keySet()) {
                for (Integer key2 : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                    if (addedPolynomialMap.get(key1 + key2) == null) {
                        addedPolynomialMap.put(key1 + key2, ((SparsePolynomial) q).getPolynomialMap().get(key2) * this.getPolynomialMap().get(key1));
                    }
                    else{
                        addedPolynomialMap.replace((key1 + key2), ((SparsePolynomial) q).getPolynomialMap().get(key2) * this.getPolynomialMap().get(key1)  + addedPolynomialMap.get(key1 + key2));
                    }
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else {
            throw new IllegalArgumentException("Only possible between polynomials");
        }
    }

    /**
     * Returns a polynomial by subtracting the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *
     * @param q the non-null valid polynomial to subtract to <code>this</code>
     * @return <code>this - </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if {@code q} is not sufficient to subtract {@code this}.
     */
    @Override
    public Polynomial subtract(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if (q instanceof DensePolynomial){
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
            for (int i = 0; i < ((DensePolynomial) q).getPolynomialArr().length ; i++){
                if (((DensePolynomial) q).getPolynomialArr()[i] != 0){
                    addedPolynomialMap.put(i, -1 * ((DensePolynomial) q).getPolynomialArr()[i]);
                }
            }
            for (Integer key : this.polynomialMap.keySet()) {
                if (addedPolynomialMap.get(key) != null){
                    addedPolynomialMap.replace(key, this.polynomialMap.get(key) + addedPolynomialMap.get(key));
                }
                else{
                    addedPolynomialMap.put(key, this.polynomialMap.get(key));
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else if (q instanceof SparsePolynomial){
            NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
            for (Integer key : this.polynomialMap.keySet()) {
                addedPolynomialMap.put(key, this.polynomialMap.get(key));
            }
            for (Integer key : ((SparsePolynomial) q).polynomialMap.keySet()) {
                if (addedPolynomialMap.get(key) != null){
                    addedPolynomialMap.replace(key, addedPolynomialMap.get(key) - ((SparsePolynomial) q).polynomialMap.get(key));
                }
                else {
                    addedPolynomialMap.put(key,-1 * ((SparsePolynomial) q).polynomialMap.get(key));
                }
            }
            addedPolynomialMap = addedPolynomialMap.descendingMap();
            addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
            return new SparsePolynomial(addedPolynomialMap);
        }
        else {
            throw new IllegalArgumentException("Only possible between polynomials");
        }
    }

    /**
     * Returns a polynomial by negating the current instance. The current instance is not modified.
     *
     * @return negation of {@code this}
     */
    @Override
    public Polynomial minus() {
        NavigableMap<Integer,Integer> polynomialMap = new TreeMap<Integer, Integer>();
        for(Integer key: this.polynomialMap.keySet()){
            polynomialMap.put(key, this.polynomialMap.get(key) * -1);
        }
        polynomialMap = polynomialMap.descendingMap();
        return new SparsePolynomial(polynomialMap);
    }

    /**
     * Checks if the class invariant holds for the current instance.
     * Returns true if polynomial contains only integer degrees and coefficients.
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    @Override
    public boolean wellFormed() {
        for (String s : stringPolynomialArr) {
            if (s.contains(".") || s.contains("/"))
                return false;
        }
        return true;
    }

    /**
     * Returns a string representation of {@code SparsePolynomial} object.
     *
     * @return a string representation of {@code this}
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(Integer key: polynomialMap.keySet()){
            str.append(Integer.toString(polynomialMap.get(key))).append("x^").append(Integer.toString(key)).append(" + ");
        }
        if (str.toString().equals(""))
            return "0";
        str = new StringBuilder(str.substring(0, str.length() - 3));
        str = new StringBuilder(str.toString().replace("+ -", "- "));
        str = new StringBuilder(str.toString().replace("x^0", ""));
        str = new StringBuilder(str.toString().replace("^1 ", " "));
        str = new StringBuilder(str.toString().replace(" 1x", " x"));
        str = new StringBuilder(str.toString().replaceAll("^1x", "x"));
        str = new StringBuilder(str.toString().replaceAll("^-1x", "-x"));
        str = new StringBuilder(str.toString().replaceAll("\\^1$", ""));
        str = new StringBuilder(str.toString().replaceAll("^-", "- "));
        return str.toString();
    }

    /**
     * Compares equality of {@code this} with {@code o} object.
     * The result is true if and only if the argument is not null and {@code this} represents same polynomial
     * with {@code o} object.
     *
     * @param o the object to compare with
     * @return  {@literal true} if the objects are same; {@literal false} otherwise.
     */
    public boolean equals(Object o){
        if (o instanceof SparsePolynomial) {
            return ((SparsePolynomial) o).polynomialMap.equals(this.polynomialMap);
        }
        return false;
    }

    public NavigableMap<Integer, Integer> getPolynomialMap() { return polynomialMap; }

    public String[] getStringPolynomialArr() { return stringPolynomialArr; }


    public static void main(String[] args) {
    }
}
