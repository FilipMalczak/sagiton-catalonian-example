package pl.sagiton.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ExampleApp implements Runnable{
    Stream<String> lineStream;
    ApplicationCommand command;
    String arg;

    private static ApplicationCommand parseCommand(String command){
        try {
            return ApplicationCommand.valueOf(command);
        } catch (IllegalArgumentException e){
            throw new IllegalStateException("Cannot parse command '"+command+"' - allowed values: "+asList(ApplicationCommand.values()));
        }
    }

    @SneakyThrows
    private static Stream<String> fileToLineStream(String path){
        return Files.lines(Paths.get(path));
    }

    public static void main(String[] args){
        if (args.length != 3){
            throw new IllegalStateException("Usage: <app> input_file_path command arg; (command can be ID or CITY, arg depends on command; see program spec in the PDF)");
        }
        String file = args[0];
        String command = args[1];
        String arg = args[2];

        new ExampleApp(fileToLineStream(file), parseCommand(command), arg).run();
    }

    @Override
    @SneakyThrows
    public void run() {
        new DataProcessor(() -> new InputFileParser().getDataSource(lineStream), command.getHandler(arg), System.out::println);
    }
}
