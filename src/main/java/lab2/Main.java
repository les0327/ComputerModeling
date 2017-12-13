package lab2;

public class Main {

    private static final int STEPS = 100000;

    public static void main(String[] args) {
        Device[] devices = new Device[10];
        devices[0] = new Device("ЦП", 0.01);
        devices[1] = new Device("NB", 0.2);
        devices[2] = new Device("SB", 0.5);
        devices[3] = new Device("ОП", 0.1);
        devices[4] = new Device("ВП", 0.03);
        devices[5] = new Device("АП", 0.03);
        devices[6] = new Device("МА", 1);
        devices[7] = new Device("КД", 2);
        devices[8] = new Device("ISA", 1);
        devices[9] = new Device("COM", 1);

        devices[0].addCrossing(new Crossing(1, devices[1]));   // ЦП -> NB

        devices[1].addCrossing(new Crossing(0.1, devices[2])); // NB -> SB
        devices[1].addCrossing(new Crossing(0.4, devices[3])); // NB -> ОП
        devices[1].addCrossing(new Crossing(0.5, devices[0])); // NB -> ЦП

        devices[2].addCrossing(new Crossing(0.5, devices[1]));  // SB -> NB
        devices[2].addCrossing(new Crossing(0.01, devices[8])); // SB -> ISA
        devices[2].addCrossing(new Crossing(0.02, devices[4])); // SB -> ВП
        devices[2].addCrossing(new Crossing(0.02, devices[5])); // SB -> АП
        devices[2].addCrossing(new Crossing(0.05, devices[6])); // SB -> МА
        devices[2].addCrossing(new Crossing(0.4, devices[7]));  // SB -> КД

        devices[3].addCrossing(new Crossing(1, devices[1]));    // ОП -> NB
        devices[4].addCrossing(new Crossing(1, devices[0]));    // ВП -> ЦП
        devices[5].addCrossing(new Crossing(1, devices[0]));    // АП -> ЦП
        devices[6].addCrossing(new Crossing(1, devices[2]));    // МА -> SB
        devices[7].addCrossing(new Crossing(1, devices[2]));    // КД -> SB

        devices[8].addCrossing(new Crossing(0.5, devices[2]));  // ISA -> SB
        devices[8].addCrossing(new Crossing(0.5, devices[9]));  // ISA -> COM

        devices[9].addCrossing(new Crossing(1, devices[8]));    // COM -> ISA

        Scheme scheme = new Scheme(devices);
        for (int i = 0; i < 100; i++) {
            scheme.addTaskToDevice(devices[0]);
        }
        scheme.model(STEPS);

    }
}
