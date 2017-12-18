package lab3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Crossing {

    @Getter @Setter private double intensity;
    @Getter @Setter private State nextState;

    public void addIntensity(double intensity) {
        this.intensity += intensity;
    }
}
