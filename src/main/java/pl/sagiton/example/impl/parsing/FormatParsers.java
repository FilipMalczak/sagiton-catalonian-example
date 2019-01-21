package pl.sagiton.example.impl.parsing;

import pl.sagiton.example.impl.model.Visitation;

public class FormatParsers {
    //todo could be refactored to reduce code duplication
    static Visitation parseFormat1(String payload){
        String[] parts = payload.split(",");
        if (parts.length != 3){
            throw new IllegalStateException("Wrong number of payload parts! Expected 3 comma-separated segments, got '"+payload+"'");
        }
        //todo some validation of ID format
        return Visitation.of(parts[0], parts[1], parts[2]);
    }

    static Visitation parseFormat2(String payload){
        String[] parts = payload.split(" ; ");
        if (parts.length != 3){
            throw new IllegalStateException("Wrong number of payload parts! Expected 3 comma-separated segments, got '"+payload+"'");
        }
        //todo some validation of ID format
        return Visitation.of(parts[0], parts[1], parts[2].replaceAll("[-]", ""));
    }
}
