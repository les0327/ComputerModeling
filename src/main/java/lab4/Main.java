package lab4;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int max;
    private static int[] x;
    private static int comb;


    public static void main(String[] args) {
        bruteForce();
        System.out.printf("brute force(%d):%nmax = f(%d, %d, %d) = %d%n",comb ,x[0], x[1], x[2], max);
        dynamic();
        System.out.printf("dynamic(%d):%nmax = f(%d, %d, %d) = %d%n",comb, x[0], x[1], x[2], max);
    }

    private static void bruteForce() {
        comb = 0;
        max  = Integer.MIN_VALUE;
        x    = new int[3];
        for (int x1 = 0; x1 < 100; x1++) {
            for (int x2 = 0; x2 < 100; x2++) {
                for (int x3 = 0; x3 < 100; x3++) {
                    comb++;
                    if (isTrue(x1, x2, x3)) {
                        int result = function(x1, x2, x3);
//                        System.out.printf("f(%d, %d, %d) = %d%n", x1, x2, x3, result);
                        if (result > max) {
                            x[0] = x1;
                            x[1] = x2;
                            x[2] = x3;
                            max = result;
                        }
                    }
                }
            }
        }
    }

    private static void dynamic() {
        comb = 0;
        List<Integer> f1 = new ArrayList<>();
        List<Integer> x1 = new ArrayList<>();
        for (int i = 0; isTrue(i, 0, 0); i++) {
            comb++;
            x1.add(i);
            f1.add(f1(i));
        }
        List<Integer> f2 = new ArrayList<>();
        List<Integer> x2 = new ArrayList<>();
        for (int i = 0; i < x1.size(); i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; isTrue(i, j, 0); j++) {
                comb++;
                int result = f2(j);
                if (result > max) {
                    max = result;
                    x2.add(i, j);
                    f2.add(i, max + f1.get(i));
                }
            }
        }
        List<Integer> f3 = new ArrayList<>();
        List<Integer> x3 = new ArrayList<>();
        for (int i = 0; i < x2.size(); i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; isTrue(i, x2.get(i), j); j++) {
                comb++;
                int result = f3(j);
                if (result > max) {
                    max = result;
                    x3.add(i, j);
                    f3.add(i, max + f2.get(i));
                }
            }
        }
        max = Integer.MIN_VALUE;
        for (Integer i : f3) {
            if (i > max) {
                max = i;
            }
        }
        int index = f3.indexOf(max);
        x[0] = x1.get(index);
        x[1] = x2.get(index);
        x[2] = x3.get(index);
    }

    private static int function(int x1, int x2, int x3) {
        return f1(x1) + f2(x2) + f3(x3);
    }

    private static int f1(int x1) {
        return 2 * x1;
    }

    private static int f2(int x2) {
        return x2;
    }

    private static int f3(int x3) {
        return 3 * x3;
    }

    private static boolean isTrue(int x1, int x2, int x3) {
        return (x1 + 2 * x3 < 4) && (x3 - x2 < 1) && (x1 + x2 < 7);
    }
}
