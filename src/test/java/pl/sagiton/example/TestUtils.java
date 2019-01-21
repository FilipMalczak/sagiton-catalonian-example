package pl.sagiton.example;

import lombok.SneakyThrows;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    @SneakyThrows
    public static Stream<String> getLines(String resourceName){
        URI resourceUri = TestUtils.class.getResource(resourceName).toURI();
        return Files.lines(Paths.get(resourceUri));
    }

    public static <T> void assertEqualsUnordered(Collection<T> expected, Collection<T> actual){
        assertEquals(expected.size(), actual.size());
        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
    }
}
