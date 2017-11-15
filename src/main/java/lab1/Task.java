package lab1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
class Task {
    @Getter @Setter private double inTime;
    @Getter @Setter private double solveTime;
    @Getter @Setter private double onSolving;
    @Getter @Setter private double outTime;
    @Getter @Setter private double solvedTime;

    Task(double enterTime, double solveTime) {
        this.inTime = enterTime;
        this.solveTime = solveTime;
        this.solvedTime = 0;
    }
}
