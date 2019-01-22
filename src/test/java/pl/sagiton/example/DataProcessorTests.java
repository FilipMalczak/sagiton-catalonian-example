package pl.sagiton.example;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sagiton.example.impl.ApplicationCommand;
import pl.sagiton.example.impl.DataProcessor;
import pl.sagiton.example.impl.parsing.DataSourceFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.sagiton.example.TestUtils.assertEqualsUnordered;
import static pl.sagiton.example.TestUtils.getLines;

class DataProcessorTests {

    @DisplayName("Example scenario for CITY mode for CARTAGENA")
    @Test
    @SneakyThrows
    public void cartagenaCity(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/example.txt")),
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

    @DisplayName("Example scenario for ID mode for Shelley Pane")
    @Test
    @SneakyThrows
    public void shelleyPaneId(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/example.txt")),
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

    @DisplayName("Empty input file should result in no output for ID mode")
    @Test
    @SneakyThrows
    public void emptyId(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> Stream.empty(),
            ApplicationCommand.ID.getHandler("54808168L"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(emptyList(), result);
    }

    @DisplayName("Using non-existent ID should result in no output")
    @Test
    @SneakyThrows
    public void missingId(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/f1only.txt")),
            ApplicationCommand.ID.getHandler("09877359E"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(emptyList(), result);
    }

    @DisplayName("Using non-existent CITY should result in no output")
    @Test
    @SneakyThrows
    public void missingCity(){
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/f1only.txt")),
            ApplicationCommand.CITY.getHandler("CARTAGENA"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(emptyList(), result);
    }

    @DisplayName("Empty input file should result in no output for ID mode")
    @Test
    @SneakyThrows
    public void emptyCity() {
        List<String> result = new LinkedList<>();
        DataProcessor processor = new DataProcessor(
            () -> Stream.empty(),
            ApplicationCommand.ID.getHandler("54808168L"),
            result::add
        );
        processor.run();
        assertEqualsUnordered(emptyList(), result);
    }

    @ValueSource(strings = { "CARTAGENA", "BARCELONA", "LAS VEGAS", "LONDON"})
    @ParameterizedTest(name = "Duplicates are removed for CITY mode when arg={0}")
    @SneakyThrows
    public void duplicatedCity(String city){
        List<String> referenceResult = new LinkedList<>();
        List<String> duplicatedResult = new LinkedList<>();
        DataProcessor referenceProcessor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/mixed.txt")),
            ApplicationCommand.CITY.getHandler(city),
            referenceResult::add
        );
        DataProcessor duplicatedProcessor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(Stream.concat(getLines("/mixed.txt"), getLines("/mixed.txt"))),
            ApplicationCommand.CITY.getHandler(city),
            duplicatedResult::add
        );
        referenceProcessor.run();
        duplicatedProcessor.run();
        assertEqualsUnordered(referenceResult, duplicatedResult);
    }

    @ValueSource(strings = { "51011156P", "25384390A", "25384390B"})
    @ParameterizedTest(name = "Duplicates are removed for ID mode when arg={0}")
    @SneakyThrows
    public void duplicatedId(String id){
        List<String> referenceResult = new LinkedList<>();
        List<String> duplicatedResult = new LinkedList<>();
        DataProcessor referenceProcessor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(getLines("/mixed.txt")),
            ApplicationCommand.ID.getHandler(id),
            referenceResult::add
        );
        DataProcessor duplicatedProcessor = new DataProcessor(
            () -> new DataSourceFactory().getDataSource(Stream.concat(getLines("/mixed.txt"), getLines("/mixed.txt"))),
            ApplicationCommand.ID.getHandler(id),
            duplicatedResult::add
        );
        referenceProcessor.run();
        duplicatedProcessor.run();
        assertEqualsUnordered(referenceResult, duplicatedResult);
    }
}