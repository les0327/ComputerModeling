package lab3;

public class Main {

    public static void main(String[] args) {
        lab2.Main.main(null);
        System.out.println();
        String[] devices = {"ЦП", "NB", "SB", "ОП", "ВП", "АП", "МА", "КД", "ISA", "COM"};
        int[][] startStateMatrix = {
                {0, 1, 0}, // ЦП
                {0, 1, 0}, // NB
                {0, 1, 0}, // SB
                {0, 0, 1}, // ОП
                {0, 0, 1}, // ВП
                {0, 0, 1}, // АП
                {0, 0, 1}, // МА
                {0, 0, 1}, // КД
                {0, 0, 1}, // ISA
                {0, 0, 1}, // COM
        };
        double[] tau = {0.01, 0.2, 0.5, 0.1, 0.03, 0.03, 1, 2, 1, 1,};
        double[][] probabilities = {
                //      ЦП     NB    SB    ОП     ВП     АП     МА    КД   ISA    COM
                /*ЦП*/  {0,     1,    0,    0,     0,     0,     0,    0,     0,    0},
                /*NB*/  {0.5,   0,  0.1,  0.4,     0,     0,     0,    0,     0,    0},
                /*SB*/  {0,   0.5,    0,    0,  0.02,  0.02,  0.05,  0.4,  0.01,    0},
                /*ОП*/  {0,     1,    0,    0,     0,     0,     0,    0,     0,    0},
                /*ВП*/  {1,     0,    0,    0,     0,     0,     0,    0,     0,    0},
                /*АП*/  {1,     0,    0,    0,     0,     0,     0,    0,     0,    0},
                /*МА*/  {0,     0,    1,    0,     0,     0,     0,    0,     0,    0},
                /*КД*/  {0,     0,    1,    0,     0,     0,     0,    0,     0,    0},
                /*ISA*/ {0,     0,  0.5,    0,     0,     0,     0,    0,     0,  0.5},
                /*COM*/ {0,     0,    0,    0,     0,     0,     0,    0,     1,    0}
        };

        Tree tree = new Tree();
        tree.generateTree(startStateMatrix, probabilities, tau);
        System.out.println("Lab 3\n");
//        System.out.println(StateFactory.getStates().size());
//        System.out.println(StateFactory.theoreticSize(10, 3));

        Process p = new Process(tree.getStateMap());
        double[] ro = p.calculateRo();

        for (int i = 0; i < ro.length; i++) {
            System.out.printf("%s - %4.2f%%%n", devices[i], ro[i] * 100);
        }
    }
}
