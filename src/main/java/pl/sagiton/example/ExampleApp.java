package pl.sagiton.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import pl.sagiton.example.impl.ApplicationCommand;
import pl.sagiton.example.impl.DataProcessor;
import pl.sagiton.example.impl.model.Visitation;
import pl.sagiton.example.impl.parsing.DataSourceFactory;
import pl.sagiton.example.impl.processing.VisitationHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ExampleApp{
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

    private static DataProcessor prepareProcessor(String path, String command, String arg){
        Stream<String> lineStream = fileToLineStream(path);
        Supplier<Stream<Visitation>> dataSource = () -> DataSourceFactory.getDataSource(lineStream);
        ApplicationCommand cmd = parseCommand(command);
        VisitationHandler handler = cmd.getHandler(arg);
        Consumer<String> sink = System.out::println;
        return new DataProcessor(dataSource, handler, sink);
    }

    public static void main(String[] args){
        if (args.length != 3){
            throw new IllegalStateException("Usage: <app> input_file_path command arg; (command can be ID or CITY, arg depends on command; see program spec in the PDF)");
        }
        String path = args[0];
        String command = args[1];
        String arg = args[2];

        prepareProcessor(path, command, arg).run();
    }
}
