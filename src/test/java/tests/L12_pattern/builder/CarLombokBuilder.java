package tests.L12_pattern.builder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarLombokBuilder {
    private String name;
    private int speed;
    private int weight;
    private int acceleration;
    private int capacity;
}
