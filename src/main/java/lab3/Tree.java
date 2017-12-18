package lab3;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class Tree {

    @Getter private static int index = 0;

    @Getter private Map<State, List<Crossing>> stateMap = new HashMap<>();
    private double[][] intensities;

    public void generateTree(int[][] matrix, double[][] probabilities, double[] tau) {
        int[][] stateMatrix  = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
        intensities          = Arrays.stream(probabilities).map(double[]::clone).toArray(double[][]::new);

        for (int i = 0; i < tau.length; i++)
            for (int j = 0; j < tau.length; j++)
                intensities[i][j] /= tau[i];

        State state = StateFactory.get(stateMatrix);
        generateNextStates(state);
    }

    private void generateNextStates(State state) {
        List<Crossing> crossings = generateCrossingList(state);
        stateMap.put(state, crossings);
        for (State nextState : crossings.stream().map(Crossing::getNextState).toArray(State[]::new)) {
            if (!isGenerated(nextState)) {
                generateNextStates(nextState);
            }
        }
    }

    private List<Crossing> generateCrossingList(State state) {
        Map<State, Crossing> map = new HashMap<>();
        int[][] matrix  = state.getMatrix();

        for (int i = 0; i < matrix.length; i++) {
            if (!state.isDeviceFree(i)) { // device is not free
                for (int j = 0; j < intensities[i].length; j++) {
                    if (intensities[i][j] != 0) { // crossing exists
                        int[][] devices = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
                        State.removeTaskFromDevice(i, devices); // take from device
                        State.addTaskToDevice(j, devices);      // set on another
                        State nextState = StateFactory.get(devices);
                        if (map.containsKey(nextState)) {
                            Crossing c = map.get(nextState);
                            c.addIntensity(intensities[i][j]);
                        } else {
                            Crossing crossing = new Crossing(intensities[i][j], nextState);
                            map.put(nextState, crossing);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(map.values());
    }

    private boolean isGenerated(State state) {
        return stateMap.containsKey(state);
    }
}
