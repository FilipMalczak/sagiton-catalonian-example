package pl.sagiton.example.impl.parsing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sagiton.example.impl.model.Visitation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static pl.sagiton.example.TestUtils.getLines;

class DataSourceFactoryTest {
    @Test
    @DisplayName("Empty input should be parsed to empty result")
    public void empty(){
        Stream<String> lines = Stream.empty();
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(emptyList(), result);
    }

    @Test
    @DisplayName("Input consisting only of first format should parse properly")
    public void firstFormatOnly(){
        Stream<String> lines = getLines("/f1only.txt");
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(
            asList(
                Visitation.of("Erica Burns", "BARCELONA", "93654902Y"),
                Visitation.of("Lucy Mcgee", "LONDON", "51011156P"),
                Visitation.of("Mitchell Newton", "SAN FRANCISCO", "25384390A"),
                Visitation.of("Margarita Richards", "LAS VEGAS", "09877359D")
            ),
            result
        );
    }

    @Test
    @DisplayName("Input consisting only of second format should parse properly")
    public void secondFormatOnly(){
        Stream<String> lines = getLines("/f2only.txt");
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(
            asList(
                Visitation.of("Mitchell Newton", "LAS VEGAS", "25384390A"),
                Visitation.of("Margarita Richards", "NEW YORK", "09877359D"),
                Visitation.of("Rhonda Hopkins", "BARCELONA", "54315871Z")
            ),
            result
        );
    }

    //todo
    //f1 then f2
    //f2 then f1
    //f1 f2 f1 f2

}