package tests.Lesson14_restassured.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseEmployee {
    private String status;
    private String message;
    private Data data;
}
