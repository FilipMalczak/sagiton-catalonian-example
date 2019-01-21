package pl.sagiton.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class InputFileParser {
    private static class ParsingStateMachine {
        private enum State {
            START,
            F1, F2;
        }

        private static final Map<State, Function<String, Visitation>> parsers;

        static {
            parsers = new HashMap<>();
            parsers.put(State.F1, FormatParsers::parseFormat1);
            parsers.put(State.F2, FormatParsers::parseFormat2);
        }

        private static final String DATA_PREFIX = "D ";

        private State state = State.START;

        private Optional<Visitation> startProcessing(String line){
            trySwitchingFormat(line);
            return Optional.empty();
        }

        private void trySwitchingFormat(String line){
            try {
                State newState = State.valueOf(line);
                makeTransitionTo(newState);
            } catch (IllegalArgumentException e){
                throw new IllegalStateException("Cannot parse '"+line+"' as a new parsing format!", e);
            }
        }

        private Optional<Visitation> process(String line){
            if (line.startsWith(DATA_PREFIX)){
                String payload = line.substring(DATA_PREFIX.length());
                return Optional.of(parse(payload));
            }
            trySwitchingFormat(line);
            return Optional.empty();
        }

        private Visitation parse(String payload){
            if (parsers.containsKey(state)){
                return parsers.get(state).apply(payload);
            } else {
                throw new IllegalStateException("Cannot find a parser for state "+state);
            }
        }

        private void makeTransitionTo(State newState){
            switch (state){
                case START: if (newState == State.START) throw new IllegalStateException("From START we can only go to F1 or F2!"); break;
                case F1: if (newState != State.F2) throw new IllegalStateException("From F1 we can only go to F2!"); break;
                case F2: if (newState != State.F1) throw new IllegalStateException("From F2 we can only go to F1!"); break;
                default: throw new IllegalStateException("It should be impossible to reach this case!");
            }
            state = newState;
        }

        /**
         *
         * @return for command lines (F1, F2) - empty; for data lines - optional of parsed visitation.
         */
        public Optional<Visitation> accept(String line){
            line = line.trim();
            switch (state) {
                case START: return startProcessing(line);
                case F1:
                case F2: return process(line);
                default: throw new IllegalStateException("It should be impossible to reach this case!");
            }
        }
    }

    public Stream<Visitation> getDataSource(Stream<String> lines){
        ParsingStateMachine stateMachine = new ParsingStateMachine();
        return lines
            .map(stateMachine::accept)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }
}
