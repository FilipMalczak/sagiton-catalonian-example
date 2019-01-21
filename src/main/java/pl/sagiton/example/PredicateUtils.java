package pl.sagiton.example;

import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateUtils {
    public static <T, V> Predicate<T> isEqual(Function<T, V> extract, V expected){
        return t -> extract.apply(t).equals(expected);
    }
}
