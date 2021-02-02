 import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The {@code DensePolynomial} class, extending Polynomial, represents a polynomial.
 * This polynomial can only have {@code int} degrees and coefficients. Also it excludes negative coefficients.
 * An object of type {@code DensePolynimial} contains an array of {@code int}, which represents
 * coefficients and degrees of polynomial
 *
 * @author Hak Soo Kim (111045936)
 * @version 1.0, November 30th 2020
 *
 */
public class DensePolynomial implements Polynomial{

    /**
     * {@code int} array type polynomialArr for polynomial
     *   In array, each index represents degree, value at index represents coefficient
     * {@code String} array type stringPolynomialArr for polynomial
     *   stringPolynomialArr contains {@code String} representing polynomial
     */
    private final int [] polynomialArr;
    private String [] stringPolynomialArr;

    /**
     * Constructor for {@code DensePolynomial} class
     *   Constructs a newly allocated {@code DensePolynomial} object represented by the given string.
     *   The string is converted to polynomial represented by {@code int} array and {@code String} array.
     *   Throws an {@link IllegalArgumentException} if the string is not sufficient to be converted.
     *
     * @param str a non empty and correct format of canonical string to be converted to a {@code DensePolynomial}.
     * @throws IllegalArgumentException if {@code str} is not correct format of canonical, or
     *   degrees and coefficients are not positive {@code int} in range of {@code int}, or out of boundary to handle with array or {@code int}.
     */
    public DensePolynomial (String str) throws IllegalArgumentException {
        if (str.length() == 0) {
            throw new IllegalArgumentException("Empty string is not allowed");
        }
        str = str.trim();

        if (str.contains(" 0") || str.contains("+ -") || str.startsWith("0") || str.contains("^0") || str.contains("++") || str.contains("--") || str.contains(" 0 ") || str.contains("- 0")|| str.contains("-0"))
            throw new IllegalArgumentException("not form of canonical string representation");
        str = str.replaceAll("^-x","- x");
        str = str.replaceAll("^x", "1x");
        str = str.replace(" x", " 1x");
        str = str.replace("x^", " ");
        str = str.replace("x", " 1");
        str = str.replace("- ", "-");
        str = str.replace(" + ", " ");

        stringPolynomialArr = str.split(" ");
        if (!wellFormed())
            throw new IllegalArgumentException("coefficients and degrees must always be integer and integer format");

        if (stringPolynomialArr.length % 2 == 1) {
            String[] temp = new String[stringPolynomialArr.length + 1];
            System.arraycopy(stringPolynomialArr, 0, temp, 0, stringPolynomialArr.length);
            temp[temp.length - 1] = "0";
            stringPolynomialArr = temp;
        }

        try {
            if (stringPolynomialArr.length >= 4) {
                for (int i = 1; i < stringPolynomialArr.length - 2; i = i + 2) {
                    if (Integer.parseInt(stringPolynomialArr[i]) <= Integer.parseInt(stringPolynomialArr[i + 2]))
                        throw new IllegalArgumentException("not form of canonical string representation");
                }
            }

            if ((Integer.parseInt(stringPolynomialArr[1]) < 0)) {
                polynomialArr = new int[(Math.abs(Integer.parseInt(stringPolynomialArr[1])) + 1)];
            } else {
                polynomialArr = new int[(Integer.parseInt(stringPolynomialArr[1]) + 1)];
            }

            for (int i = 0; i < stringPolynomialArr.length; i = i + 2) {
                if ((Integer.parseInt(stringPolynomialArr[i + 1]) < 0))
                    throw new IllegalArgumentException("degrees cannot be negative.");
                polynomialArr[(Integer.parseInt(stringPolynomialArr[i + 1]))] = Integer.parseInt(stringPolynomialArr[i]);
            }
        }
        catch( OutOfMemoryError | NegativeArraySizeException e){
            throw new IllegalArgumentException("Your number is out of boundary to handle");
        }
        catch( NumberFormatException e){
            throw new IllegalArgumentException("given number is out of boundary or canonical format is wrong");
        }
    }

    /**
     * private constructor for {@code DensePolynomial} class
     * constructs a newly allocated {@code DensePolynomial} object represented by array type of {@code int}.
     * @param polynomialArr an array type of {@code int} to be converted to a {@code DensePolynomial}.
     */
    private DensePolynomial (int [] polynomialArr){
        this.polynomialArr = polynomialArr;
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    @Override
    public int degree() {
        for (int i = polynomialArr.length - 1 ; i >= 0 ; i --){
            if (polynomialArr[i] != 0)
                return i;
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
        if ( d < 0 || polynomialArr.length <= d )
            return 0;
        return polynomialArr[d];
    }

    /**
     * Checks if polynomial represents the zero constant.
     *
     * @return {@literal true} if the polynomial represents the zero constant; {@literal false} otherwise.
     */
    @Override
    public boolean isZero() {
        for (int j : polynomialArr) {
            if (j != 0)
                return false;
        }
        return true;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *   Returns a {@code DensePolynomial} object if {@code q} is instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *   without negative degrees.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial} or
     * if instance of {@code SparsePolynomial} contains at least one negative sign.
     * Also throw an {@link IllegalArgumentException} if result of {@code this} + {@code q} is out of boundary to handle,
     * which means result polynomial cannot be represented by {@code int} array type.
     *
     * @param q the non-null valid polynomial to add to <code>this</code>
     * @return <code>this + </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if result of {@code this} + {@code q} is out of boundary to handle or
     * {@code q} is not sufficient to add {@code this}.
     */
    @Override
    public Polynomial add(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if ( q instanceof DensePolynomial){
            int[] addedArr;
            try {
                addedArr = new int[Math.max( ((DensePolynomial) q).polynomialArr.length, this.polynomialArr.length)];
            }
            catch(OutOfMemoryError e){
                throw new IllegalArgumentException("Your number is out of boundary to handle");
            }
            for (int i = 0; i< addedArr.length; i++ ){
                if ( i < ((DensePolynomial) q).polynomialArr.length ){
                    addedArr[i] = addedArr[i] + ((DensePolynomial) q).polynomialArr[i];
                }
                if ( i < this.polynomialArr.length){
                    addedArr[i] = addedArr[i] + this.polynomialArr[i];
                }
            }
            return new DensePolynomial(addedArr);
        }
        else if (q instanceof SparsePolynomial){
            boolean isThereNegative = false;
            int biggestInQ = q.degree();
            for (Integer key : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                if ( key < 0)
                    isThereNegative = true;
            }
            if (isThereNegative) {
                throw new IllegalArgumentException("Cannot add DensePolynomial with SparsePolynomial that has negative coefficient");
            }
            else {
                int[] addedArr;
                try {
                    addedArr = new int [ Math.max( biggestInQ + 1, this.polynomialArr.length)];
                }
                catch(OutOfMemoryError e){
                    throw new IllegalArgumentException("Your number is out of boundary to handle");
                }
                for (int i = 0; i < addedArr.length ; i++){
                    if ( i < this.polynomialArr.length){
                        addedArr[i] = addedArr[i] + this.polynomialArr[i];
                    }
                    if ( ((SparsePolynomial) q).getPolynomialMap().get(i) != null) {
                        addedArr[i] = addedArr[i] + ((SparsePolynomial) q).getPolynomialMap().get(i);
                    }
                }
                return new DensePolynomial(addedArr);
            }
        }
        else {
            throw new IllegalArgumentException("Only possible between polynomials");
        }
    }

    /**
     * Returns a polynomial by multiplying the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *   Returns a {@code DensePolynomial} object if {@code q} is instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *   without negative degrees.
     *   Returns a {@code SparsePolynomial} object if {@code q} is instance of {@code SparsePolynomial} with negative degrees.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial}.
     * Also throw an {@link IllegalArgumentException} if result of {@code this} * {@code q} is out of boundary to handle,
     * which means result polynomial cannot be represented by {@code int} array type.
     * @param q the non null valid polynomial to multiply with <code>this</code>
     * @return <code>this * </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if result of {@code this} * {@code q} is out of boundary to handle or
     * {@code q} is not sufficient to multiply {@code this}.
     *
     */
    @Override
    public Polynomial multiply(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if (q instanceof DensePolynomial) {
            int[] addedArr;
            try {
                addedArr = new int[this.polynomialArr.length + ((DensePolynomial) q).polynomialArr.length - 1];
            }
            catch(OutOfMemoryError e){
                throw new IllegalArgumentException("Your number is out of boundary to handle");
            }
            for (int i = 0; i < this.polynomialArr.length; i++) {
                for (int j = 0; j < ((DensePolynomial) q).polynomialArr.length; j++) {
                    addedArr[i + j] += this.polynomialArr[i] * ((DensePolynomial) q).polynomialArr[j];
                }
            }
            return new DensePolynomial(addedArr);
        } else if (q instanceof SparsePolynomial) {
            boolean isThereNegative = false;
            int biggestInQ = q.degree();
            for (Integer key : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                if (key < 0)
                    isThereNegative = true;
            }
            if (isThereNegative) {
                NavigableMap<Integer, Integer> addedPolynomialMap = new TreeMap<Integer, Integer>();
                for (int i = 0 ; i < this.getPolynomialArr().length; i++){
                    for (Integer key : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                        if (addedPolynomialMap.get(i + key) == null) {
                            addedPolynomialMap.put(i + key, this.polynomialArr[i] * ((SparsePolynomial) q).getPolynomialMap().get(key));
                        }
                        else {
                            addedPolynomialMap.replace(i + key, this.polynomialArr[i] * ((SparsePolynomial) q).getPolynomialMap().get(key) + addedPolynomialMap.get(i + key));
                        }
                    }
                }
                addedPolynomialMap = addedPolynomialMap.descendingMap();
                addedPolynomialMap.entrySet().removeIf(entry -> entry.getValue() == 0);
                StringBuilder str = new StringBuilder();
                for(Integer key: addedPolynomialMap.keySet()){
                    str.append(addedPolynomialMap.get(key)).append("x^").append(key).append(" + ");
                }
                if (str.length() == 0)
                    return this.add(this.minus());
                str = new StringBuilder(str.substring(0, str.length() - 3));
                str = new StringBuilder(str.toString().replace("+ -", "- "));
                str = new StringBuilder(str.toString().replace("x^0", ""));
                str = new StringBuilder(str.toString().replace("^1 ", " "));
                str = new StringBuilder(str.toString().replace(" 1x", " x"));
                str = new StringBuilder(str.toString().replaceAll("^1x", "x"));
                str = new StringBuilder(str.toString().replaceAll("\\^1$", ""));
                str = new StringBuilder(str.toString().replaceAll("^-1x", "-x"));
                str = new StringBuilder(str.toString().replaceAll("^-", "- "));

                return new SparsePolynomial(str.toString());

            } else {
                int[] addedArr;
                try {
                    addedArr = new int[this.polynomialArr.length + biggestInQ];
                }
                catch(OutOfMemoryError e){
                    throw new IllegalArgumentException("Your number is out of boundary to handle");
                }
                for (int i = 0; i < this.polynomialArr.length; i++) {
                    for (Integer key : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                        addedArr[i + key] += this.polynomialArr[i] * ((SparsePolynomial) q).getPolynomialMap().get(key);
                    }
                }
                return new DensePolynomial(addedArr);
            }
        }
        else{
            throw new IllegalArgumentException("Only possible between polynomials");
        }
    }

    /**
     * Returns a polynomial by subtracting the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *   Returns a {@code DensePolynomial} object if {@code q} is instance of {@code DensePolynomial} or {@code SparsePolynomial}
     *   without negative degrees.
     * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial} or
     * instance of {@code SparsePolynomial} contains at least one negative sign.
     * Also throw an {@link IllegalArgumentException} if result of {@code this} - {@code q} is out of boundary to handle,
     * which means result polynomial cannot be represented by {@code int} array type.
     *
     * @param q the non-null valid polynomial to subtract from <code>this</code>
     * @return <code>this - </code>q
     * @throws NullPointerException if q is null
     * @throws IllegalArgumentException if result of {@code this} - {@code q} is out of boundary to handle or
     * {@code q} is not sufficient to subtract {@code this}.
     */
    @Override
    public Polynomial subtract(Polynomial q) {
        if (q == null)
            throw new NullPointerException();
        if ( q instanceof DensePolynomial){
            int[] addedArr;
            try {
                addedArr = new int[Math.max(((DensePolynomial) q).polynomialArr.length, this.polynomialArr.length)];
            }
            catch(OutOfMemoryError e){
                throw new IllegalArgumentException("Your number is out of boundary to handle");
            }
            for (int i = 0; i< addedArr.length; i++ ){
                if ( i < ((DensePolynomial) q).polynomialArr.length ){
                    addedArr[i] = addedArr[i] - ((DensePolynomial) q).polynomialArr[i];
                }
                if ( i < this.polynomialArr.length){
                    addedArr[i] = addedArr[i] + this.polynomialArr[i];
                }
            }
            return new DensePolynomial(addedArr);
        }
        else if (q instanceof SparsePolynomial){
            boolean isThereNegative = false;
            int biggestInQ = q.degree();
            for (Integer key : ((SparsePolynomial) q).getPolynomialMap().keySet()) {
                if ( key < 0)
                    isThereNegative = true;
            }
            if (isThereNegative) {
                throw new IllegalArgumentException("Cannot subtract DensePolynomial with SparsePolynomial that has negative coefficient");
            }
            else {
                int[] addedArr;
                try {
                    addedArr = new int [ Math.max( biggestInQ + 1, this.polynomialArr.length)];
                }
                catch(OutOfMemoryError e){
                    throw new IllegalArgumentException("Your number is out of boundary to handle");
                }
                for (int i = 0; i < addedArr.length ; i++){
                    if ( i < this.polynomialArr.length){
                        addedArr[i] = addedArr[i] + this.polynomialArr[i];
                    }
                    if ( ((SparsePolynomial) q).getPolynomialMap().get(i) != null) {
                        addedArr[i] = addedArr[i] - ((SparsePolynomial) q).getPolynomialMap().get(i);
                    }
                }
                return new DensePolynomial(addedArr);
            }
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
        int [] newPolynomialArr = new int [this.polynomialArr.length];
        for (int i = 0; i < polynomialArr.length ; i++){
            newPolynomialArr[i] = polynomialArr[i] * -1;
        }

        return new DensePolynomial(newPolynomialArr);
    }

    /**
     * Returns a string representation of {@code DensePolynomial} object.
     *
     * @return a string representation of {@code this}
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = polynomialArr.length - 1; 0 <= i  ; i--){
            if (polynomialArr[i] != 0) {
                str.append(polynomialArr[i]).append("x^").append(i).append(" + ");
            }
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
     * @param o the non null object to compare with
     * @return  {@literal true} if the objects are same; {@literal false} otherwise.
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof DensePolynomial) {
            int longer = Math.max(((DensePolynomial) o).polynomialArr.length, this.polynomialArr.length);
            for (int i = 0; i < longer; i++){
                if (((DensePolynomial) o).getCoefficient(i) != this.getCoefficient(i))
                    return false;
            }
            return true;
        }
            return false;
    }

    /**
     * Checks if the class invariant holds for the current instance.
     * Returns true if polynomial contains only integer degrees and coefficients.
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    @Override
    public boolean wellFormed() {
        for (String s : stringPolynomialArr) {
            if (s.contains(".") || s.contains("/")) {
                return false;
            }
        }
        return true;
    }

    public int[] getPolynomialArr() {
        return polynomialArr;
    }
    public String[] getStringPolynomialArr() { return stringPolynomialArr; }


    public static void main(String[] args){

        Double a = new Double(4);
    }
}

