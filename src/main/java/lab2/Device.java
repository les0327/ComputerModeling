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
    @Getter @Setter private double         workTime;         // total time of computing
    @Getter @Setter private List<Crossing> nextList;         // list of crossing

    public Device(String name, double tau) {
        this.name = name;
        this.tau  = tau;
        nextList = new ArrayList<>();
    }

    public void addCrossing(Crossing crossing) {
        nextList.add(crossing);
        nextList.sort(Comparator.comparing(Crossing::getProbability));
    }

    @Override
    public String toString() {
        String string = "Device{" + "name='" + name + '\'' + ", inQueue=" + inQueue + "," +
                " onProcessing=" + onProcessing + ", free=" + free + ", workTime=" + workTime + ", nextList={";
        StringBuilder sb = new StringBuilder(string);
        nextList.forEach(next -> sb.append("Device=").append(next.getNext().getName()).append(" p=").append(next.getProbability()).append(";"));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}};");
        return sb.toString();
    }
}
