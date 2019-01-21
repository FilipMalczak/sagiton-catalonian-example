package pl.sagiton.example;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import pl.sagiton.example.impl.ApplicationCommand;
import pl.sagiton.example.impl.DataProcessor;
import pl.sagiton.example.impl.parsing.DataSourceFactory;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.sagiton.example.TestUtils.assertEqualsUnordered;
import static pl.sagiton.example.TestUtils.getLines;

class DataProcessorTests {


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

    //todo
    //empty input (both modes)
    //repetitions are removed (both modes)
}