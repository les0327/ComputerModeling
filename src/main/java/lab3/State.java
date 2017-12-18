package lab3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@AllArgsConstructor
public class State{

    @Getter @Setter private int index;
    @Getter @Setter private int[][] matrix; // first index - device; second: 0 - tasks in queue, 1 - on processor, 2 - is Free

    public static void addTaskToDevice(int i, int[][] devices) {
        if (devices[i][2] == 1) {
            devices[i][1]++; // set task on processor
            devices[i][2] = 0; // set not free
        } else {
            devices[i][0]++; // add task to queue
        }
    }

    public static void removeTaskFromDevice(int i, int[][] devices) {
        devices[i][1]--; // remove task from processor
        if (devices[i][0] != 0) { // if queue is not empty
            devices[i][0]--; // take from queue
            devices[i][1]++; // set on processor
        } else {
            devices[i][2] = 1; // if queue is empty - set free
        }
    }

    public void addTaskToDevice(int i) {
        if (isDeviceFree(i)) {
            matrix[i][1]++; // set task on processor
            matrix[i][2] = 0; // set not free
        } else {
            matrix[i][0]++; // add task to queue
        }
    }

    public void removeTaskFromDevice(int i) {
        matrix[i][1]--; // remove task from processor
        if (matrix[i][0] != 0) { // if queue is not empty
            matrix[i][0]--; // take from queue
            matrix[i][1]++; // set on processor
        } else {
            matrix[i][2] = 1; // if queue is empty - set free
        }
    }

    public boolean isDeviceFree(int i) {
        return matrix[i][2] == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.deepEquals(matrix, state.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
