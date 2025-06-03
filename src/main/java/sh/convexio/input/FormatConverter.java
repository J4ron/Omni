package sh.convexio.input;

/**
 *Interface for converting objects of type S to type T and back.
 *
 * @param <S>
 * @param <T>
 */
public interface FormatConverter<S, T> {
    /**
     *Converts an object from source type S to target type T.
     *
     * @param source the source object to convert
     * @return converted target object
     */
    T convertTo(S source);

    /**
     *Converts an Object from target type T back to source type S.
     *
     * @param target the target object to convert back
     * @return converted source objekt
     */
    S convertFrom(T target);
}