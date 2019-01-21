package pl.sagiton.example.impl.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Visitation {
    String name;
    String city;
    String id;
}
