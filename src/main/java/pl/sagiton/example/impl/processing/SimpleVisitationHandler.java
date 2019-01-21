package pl.sagiton.example.impl.processing;

import lombok.Value;
import pl.sagiton.example.impl.model.Visitation;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Value
public class SimpleVisitationHandler implements VisitationHandler {
    Predicate<Visitation> filter;
    Function<Visitation, String> renderer;

    @Override
    public Stream<String> handle(Stream<Visitation> visitations) {
        return visitations
            .filter(filter)
            .map(renderer);
    }
}
