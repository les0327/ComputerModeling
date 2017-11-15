package lab2;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Crossing {
    @Getter @Setter private double probability;
    @Getter @Setter private Device next;
}
