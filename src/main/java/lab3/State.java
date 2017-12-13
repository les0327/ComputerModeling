package lab3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@AllArgsConstructor
public class State {

    @Getter @Setter private int[][] matrix; // first index - device; second: 0 - tasks in queue, 1 - on processor, 2 - is Free

    public void addTaskToDevice(int i) {
        if (isDeviceFree(i)) {
            matrix[i][1] += 1; // set task on processor
        } else {
            matrix[i][0] += 1; // add task to queue
        }
    }

    private boolean isDeviceFree(int i) {
        return matrix[i][2] == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(matrix, state.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }
}
