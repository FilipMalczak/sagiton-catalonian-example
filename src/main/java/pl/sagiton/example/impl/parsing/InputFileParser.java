package pl.sagiton.example.impl.parsing;

import pl.sagiton.example.impl.model.Visitation;

import java.util.Optional;
import java.util.stream.Stream;

public class InputFileParser {
    public static Stream<Visitation> getDataSource(Stream<String> lines){
        ParsingStateMachine stateMachine = new ParsingStateMachine();
        return lines
            .map(stateMachine::accept)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }
}

