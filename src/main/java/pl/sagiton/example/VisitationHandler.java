package pl.sagiton.example;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface VisitationHandler {
    Stream<String> handle(Stream<Visitation> visitations);
}
