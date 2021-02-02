public interface Polynomial {
    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    int degree();

    /**
     * Returns the coefficient corresponding to the given exponent.  Returns 0 if there is no term with that exponent
     * in the polynomial.
     *
     * @param d the exponent whose coefficient is returned.
     * @return the coefficient of the term of whose exponent is d.
     */
    int getCoefficient(int d);

    /**
     * @return true if the polynomial represents the zero constant
     */
    boolean isZero();

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *
     * @param q the non-null polynomial to add to <code>this</code>
     * @return <code>this + </code>q
     * @throws NullPointerException if q is null
     */
    Polynomial add(Polynomial q);
/**
 * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
 * parameter are modified.
 *   Returns a {@code DensePolynomial} object if {@code q} is instance of {@code DensePolynomial} or {@code SparsePolynomial}
 *   without negative degrees.
 * Throws an {@link IllegalArgumentException} if {@code q} is not an instance of {@code DensePolynomial} or {@code SparsePolynomial} or
 * instance of {@code SparsePolynomial} contains at least one negative sign.
 * Also throw an {@link IllegalArgumentException} if result of {@code this} + {@code q} is out of boundary to handle,
 * which means result polynomial cannot be represented by array type.
 *
 * @param q the non-null polynomial to add to <code>this</code>
 * @return <code>this + </code>q
 * @throws NullPointerException if q is null
 * @throws IllegalArgumentException if result of {@code this} + {@code q} is out of boundary to handle or
 * {@code q} is not sufficient to add {@code this}.
 */


    /**
     * Returns a polynomial by multiplying the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the polynomial to multiply with <code>this</code>
     * @return <code>this * </code>q
     * @throws NullPointerException if q is null
     */
    Polynomial multiply(Polynomial q);

    /**
     * Returns a  polynomial by subtracting the parameter from the current instance. Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the non-null polynomial to subtract from <code>this</code>
     * @return <code>this - </code>q
     * @throws NullPointerException if q is null
     */
    Polynomial subtract(Polynomial q);

    /**
     * Returns a polynomial by negating the current instance. The current instance is not modified.
     *
     * @return -this
     */
    Polynomial minus();

    /**
     * Checks if the class invariant holds for the current instance.
     *
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    boolean wellFormed();
}
