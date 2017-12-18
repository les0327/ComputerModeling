package lab3;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Process {

    @Setter private Map<State, List<Crossing>> stateMap;

    private double[] calculateP() {
        int size = stateMap.size();
        double[][] matrix = new double[size][size];

        for (State s : stateMap.keySet()) {
            int i = s.getIndex();
            for (Crossing c : stateMap.get(s)) {
                matrix[i][c.getNextState().getIndex()] = c.getIntensity();
            }
        }

        double[][] m = new double[size][size + 1];

        for (int i = 0; i < m.length + 1; i++)
            m[0][i] = 1;

        for (int i = 1; i < size; i++){
            for (int j = 0; j < m.length; j++){
                m[i][j] = -matrix[j][i - 1];
            }
            m[i][i - 1] = Arrays.stream(matrix[i-1]).sum();
        }

        for (int i = 0 ; i < m.length - 1; i++){
            for (int j = i + 1; j < m.length; j++){
                double t = m[j][i] / m[i][i];
                for (int k = 0; k < m[i].length; k++) {
                    m[j][k] -= t * m[i][k];
                }
            }
        }

        double[] probabilities = new double[size];

        for (int i = m.length - 1; i >= 0; i--){
            probabilities[i] = m[i][m.length] / m[i][i];
            if (i != m.length - 1)
                for (int j = i + 1; j < m.length; j++)
                    probabilities[i] -= m[i][j] * probabilities[j] / m[i][i];
        }

        return probabilities;
    }

    public double[] calculateRo() {
        double[] probabilities = this.calculateP();
        State[] states = stateMap.keySet().toArray(new State[0]);
        double[] ro = new double[states[0].getMatrix().length];
            for (State state : states) {
                int[][] matrix = state.getMatrix();
                for (int i = 0; i < matrix.length; i++) {
                    if (matrix[i][1] != 0) { // device is busy
                        ro[i] += probabilities[state.getIndex()];
                    }
                }
            }
        return ro;
    }
}
