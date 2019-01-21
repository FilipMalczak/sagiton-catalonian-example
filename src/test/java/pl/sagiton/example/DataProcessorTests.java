package pl.sagiton.example;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import pl.sagiton.example.impl.ApplicationCommand;
import pl.sagiton.example.impl.DataProcessor;
import pl.sagiton.example.impl.parsing.InputFileParser;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataProcessorTests {
    @SneakyThrows
    private Stream<String> getLines(String resourceName){
        URI resourceUri = this.getClass().getResource(resourceName).toURI();
        return Files.lines(Paths.get(resourceUri));
    }

    private static <T> void assertEqualsUnordered(Collection<T> expected, Collection<T> actual){
        assertEquals(expected.size(), actual.size());
        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
    }

    @Test
    @SneakyThrows
    public void cartagenaCity(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new InputFileParser().getDataSource(getLines("/input1.txt")),
            ApplicationCommand.CITY.getHandler("CARTAGENA"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(
            asList(
                "Russell Pope,69429384C"
            ),
            result
        );
    }

    @Test
    @SneakyThrows
    public void shelleyPaneId(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new InputFileParser().getDataSource(getLines("/input1.txt")),
            ApplicationCommand.ID.getHandler("54808168L"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(
            asList(
                "MADRID",
                "BARCELONA",
                "OVIEDO"
            ),
            result
        );
    }
}