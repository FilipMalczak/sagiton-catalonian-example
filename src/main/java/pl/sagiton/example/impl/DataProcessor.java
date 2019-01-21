package pl.sagiton.example.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import pl.sagiton.example.impl.model.Visitation;
import pl.sagiton.example.impl.processing.VisitationHandler;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataProcessor implements Runnable {
    Supplier<Stream<Visitation>> dataSource;
    VisitationHandler handler;
    Consumer<String> dataSink;

    @Override
    public void run() {
        try (Stream<Visitation> visitations = dataSource.get()) {
            handler.handle(visitations.distinct()).forEach(dataSink);
        }
    }
}
