package pl.sagiton.example.impl.processing;

import pl.sagiton.example.impl.model.Visitation;

import java.util.stream.Stream;

public interface VisitationHandler {
    Stream<String> handle(Stream<Visitation> visitations);
}
