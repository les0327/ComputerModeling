package lab2;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Device {

    @Getter @Setter private String         name;             // name
    @Getter @Setter private double         tau;              // time of computing 1 task
    @Getter @Setter private int            inQueue;          // count of tasks in queue
    @Getter @Setter private double         timeOut;          // time
    @Getter @Setter private int            onProcessing;     // count of tasks on processing
    @Getter @Setter private boolean        free;             // is free?
    @Getter @Setter private int            tasks;
    @Getter @Setter private double         workTime;         // total time of computing
    @Getter @Setter private List<Crossing> nextList;         // list of crossing

    public Device(String name, double tau) {
        this.name = name;
        this.tau  = tau;
        this.free = true;
        nextList = new ArrayList<>();
    }

    public void addCrossing(Crossing crossing) {
        nextList.add(crossing);
        nextList.sort(Comparator.comparing(Crossing::getProbability));
    }

    public Device next(double p) {
        double sum = 0;
        for (int i = 0; i < nextList.size() - 1; i++) {
            sum += nextList.get(i).getProbability();
            if (p < sum) {
                return nextList.get(i).getNext();
            }
        }
        return nextList.get(nextList.size() - 1).getNext();
    }

    @Override
    public String toString() {
        String string = "Device{" + "name='" + name + '\'' + ", inQueue=" + inQueue + "," + ", timeOut=" + timeOut +
                " onProcessing=" + onProcessing + ", free=" + free + ", tasks=" + tasks + ", workTime=" + workTime + ", nextList={";
        StringBuilder sb = new StringBuilder(string);
        nextList.forEach(next -> sb.append("Device=").append(next.getNext().getName()).append(" p=").append(next.getProbability()).append(";"));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}};");
        return sb.toString();
    }
}
