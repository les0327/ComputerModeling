package lab3;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StateFactory {

    @Getter private static Set<State> states = new HashSet<>();
    @Getter private static int index = 0;

    private static boolean exists(int[][] matrix) {
        return states.contains(new State(-1, matrix));
    }

    public static State get(int[][] matrix) {
        State buf = new State(-1, matrix);
        State result;
        if (exists(matrix))
            for (State state : states) {
                result = state;
                if (result.equals(buf))
                    return result;
            }

        result = new State(index++, Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new));
        states.add(result);
        return result;
    }

    /**
     * @param n - devices
     * @param k - tasks
     * @return (k+n-1)!/(k!(n-1)!)
     */
    public static long theoreticSize(int n, int k) {
        int numerator    = 1;
        int denominator1 = 1;
        int denominator2 = 1;
        for (int i = 2; i <= k + n - 1; i++)
            numerator *= i;
        for (int i = 2; i <= k; i++)
            denominator1 *= i;
        for (int i = 2; i <= n - 1; i++)
            denominator2 *= i;

        return numerator / denominator1 / denominator2;
    }
}
