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

    @Test
    @DisplayName("1st format before 2nd format should parse properly")
    public void f1ThenF2(){
        Stream<String> lines = Stream.concat(getLines("/f1only.txt"), getLines("/f2only.txt"));
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(
            asList(
                Visitation.of("Erica Burns", "BARCELONA", "93654902Y"),
                Visitation.of("Lucy Mcgee", "LONDON", "51011156P"),
                Visitation.of("Mitchell Newton", "SAN FRANCISCO", "25384390A"),
                Visitation.of("Margarita Richards", "LAS VEGAS", "09877359D"),
                Visitation.of("Mitchell Newton", "LAS VEGAS", "25384390A"),
                Visitation.of("Margarita Richards", "NEW YORK", "09877359D"),
                Visitation.of("Rhonda Hopkins", "BARCELONA", "54315871Z")
            ),
            result
        );
    }


    @Test
    @DisplayName("2nd format before 1st format should parse properly")
    public void f2ThenF1(){
        Stream<String> lines = Stream.concat(getLines("/f2only.txt"), getLines("/f1only.txt"));
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(
            asList(
                Visitation.of("Mitchell Newton", "LAS VEGAS", "25384390A"),
                Visitation.of("Margarita Richards", "NEW YORK", "09877359D"),
                Visitation.of("Rhonda Hopkins", "BARCELONA", "54315871Z"),
                Visitation.of("Erica Burns", "BARCELONA", "93654902Y"),
                Visitation.of("Lucy Mcgee", "LONDON", "51011156P"),
                Visitation.of("Mitchell Newton", "SAN FRANCISCO", "25384390A"),
                Visitation.of("Margarita Richards", "LAS VEGAS", "09877359D")
            ),
            result
        );
    }

    @Test
    @DisplayName("It should be possible to mix formats in the same file")
    public void mixed(){
        Stream<String> lines = getLines("/mixed.txt");
        List<Visitation> result = DataSourceFactory.getDataSource(lines).collect(Collectors.toList());
        assertEquals(
            asList(
                Visitation.of("Erica Burns", "BARCELONA", "93654902Y"),
                Visitation.of("Lucy Mcgee", "LONDON", "51011156P"),
                Visitation.of("Mitchell Newton", "LAS VEGAS", "25384390A"),
                Visitation.of("Margarita Richards", "NEW YORK", "09877359D"),
                Visitation.of("Mitchell Newton", "SAN FRANCISCO", "25384390A"),
                Visitation.of("Margarita Richards", "LAS VEGAS", "09877359D"),
                Visitation.of("Rhonda Hopkins", "BARCELONA", "54315871Z")
                ),
            result
        );
    }
}