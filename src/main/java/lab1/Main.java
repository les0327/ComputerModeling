package lab1;

import java.util.*;

public class Main {
    private static final int K1 = -1, K2 = -6, K3 = -3, K4 = 0, K5 = 4;
    private static final double LENGTH = 1000;
    private static final double LAMBDA = 20;
    private static final double MU     = 20;
    private static final double T1     = 3;
    private static final double T2     = 4;
    private static double TAU          = 1 / MU;
    private static final int N         = 50;
    private static final double K      = 1;
    private static double T            = 0;
    private static ArrayList<Task> solvedTasks;

    public static void main(String[] args) {

//        simulateRR(new RRQueue(), TAU / 100);
//        solvedTasks.forEach( task -> System.out.println("RR " +task.getInTime() + " " + task.getOutTime()));
//        simulateSF(new SFQueue());
//        solvedTasks.forEach( task -> System.out.println("SF " +task.getInTime() + " " + task.getOutTime()));

        System.out.println("Максимальне значення цільвої функції досягається за значення тау: \n"
                + getMaxTau(0.01, 0.5, 0.01) + "\n");

        simulateRR(new RRQueue(), 0.01);
        System.out.println("Значення шуканих величин для СМО типу RR: ");
        printRes();
        System.out.println();

        simulateSF(new SFQueue());
        System.out.println("Значення шуканих величин для СМО типу SF: ");
        printRes();
        System.out.println();


    }

    //Print all results
    private static void printRes() {
        System.out.println("Середній час знаходження задачі в системі: " + getEVOfTime());
        System.out.println("Дисперсія часу знаходження в системі: " + getVarOfTime());
        System.out.println("Середній час реакції системи на появу задачі: " + getReactionTime());
        System.out.println("Відношення кількості оброблених задач до загальної: " + getRatio());
        System.out.println("Сумарна оцінка актуальності задач: " + getSumRelevance());
        System.out.println("Час затримки для кожної " + N + " задачі: " + getStayTimeForN());
        System.out.println("Цільова функція: " + getTarget(K1, K2, K3, K4, K5));
    }

    //Simulate the SF queue
    private static void simulateSF(SFQueue queue) {
        boolean isFree = true;
        double t1 = 0;
        double t2 = Double.MAX_VALUE;
        solvedTasks = new ArrayList<>();
        double tp;
        int k = 1;
        Task currTask = null;

        while (k < LENGTH) {
            if (k % N == 0) {
                tp = K * getStandardSolveTime();
            } else {
                tp = getStandardSolveTime();
            }
            if (t1 < t2) {
                T = t1;
                if (isFree) {
                    currTask = new Task(t1, tp);
                    currTask.setOnSolving(T);
                    t2 = T + tp;
                    currTask.setOutTime(t2);
                    isFree = false;
                } else {
                    queue.add(new Task(t1, tp));
                }
                t1 = genNextEnterTime();
                k++;
            } else {
                T = t2;
                solvedTasks.add(currTask);
                if (queue.isEmpty()) {
                    t2 = Double.MAX_VALUE;
                    isFree = true;
                } else {
                    currTask = queue.get();
                    currTask.setOnSolving(T);
                    t2 = T + currTask.getSolveTime();
                    currTask.setOutTime(t2);
                    isFree = false;
                }
            }
        }
    }

    //Simulate the RR queue
    private static void simulateRR(RRQueue queue, double tau) {
        boolean isFree = true;
        double t1 = 0;
        double t2 = Double.MAX_VALUE;
        solvedTasks = new ArrayList<>();
        double tp;
        int k = 1;
        Task currTask = null;

        while (k < LENGTH) {
            if (k % N == 0) {
                tp = K * getStandardSolveTime();
            } else {
                tp = getStandardSolveTime();
            }
            if (t1 < t2) {
                T = t1;
                if (isFree) {
                    currTask = new Task(t1, tp);
                    currTask.setOnSolving(T);
                    t2 = T + tau;
                    currTask.setSolvedTime(tau);
                    isFree = false;
                } else {
                    queue.add(new Task(t1, tp));
                }
                t1 = genNextEnterTime();
                k++;
            } else {
                T = t2;
                if (currTask.getSolvedTime() > currTask.getSolveTime()) {
                    currTask.setOutTime(t2);
                    solvedTasks.add(currTask);
                    if (queue.isEmpty()) {
                        t2 = Double.MAX_VALUE;
                        isFree = true;
                    } else {
                        currTask = queue.get();
                        if (currTask.getOnSolving() == 0)
                            currTask.setOnSolving(T);
                        t2 = T + tau;
                        currTask.setSolvedTime(tau + currTask.getSolvedTime());
                        isFree = false;
                    }
                } else {
                    queue.add(currTask);
                    currTask = queue.get();
                    if (currTask.getOnSolving() == 0)
                        currTask.setOnSolving(T);
                    t2 = T + tau;
                    currTask.setSolvedTime(tau + currTask.getSolvedTime());
                    isFree = false;
                }
            }
        }
    }


    private static double getTarget(int k1, int k2, int k3, int k4, int k5) {
        return k1 * getEVOfTime() + k2 * getVarOfTime() + k3 * getReactionTime() + k4 * getRatio() + k5 * getSumRelevance();
    }

    private static double getEVOfTime() {
        double sum = 0;
        for (Task solvedTask : solvedTasks) {
            sum += solvedTask.getOutTime() - solvedTask.getInTime();
        }
        return sum / solvedTasks.size();
    }

    private static double getVarOfTime() {
        double ev = getEVOfTime();
        double sum = 0;
        for (Task solvedTask : solvedTasks) {
            sum += Math.pow((solvedTask.getOutTime() - solvedTask.getInTime()) - ev, 2);
        }
        return sum / solvedTasks.size();
    }

    private static double getReactionTime() {
        double sum = 0;
        solvedTasks.get(0).setOnSolving(0);
        for (Task solvedTask : solvedTasks) {
            sum += solvedTask.getOnSolving() - solvedTask.getInTime();
        }
        return sum / solvedTasks.size();
    }

    private static double getRatio() {
        return (double) solvedTasks.size() / LENGTH;
    }

    //Count the total relevance of tasks
    private static double getSumRelevance() {
        double sum = 0;
        for (Task solvedTask : solvedTasks) {
            sum += getRelevance(solvedTask);
        }
        return sum / solvedTasks.size();
    }

    //Count the individual relevance for each task
    private static double getRelevance(Task t) {
        double time = t.getOutTime() - t.getInTime();
        if (time <= T1) {
            return 1;
        } else if (time >= T2) {
            return 0;
        } else {
            return (T2 - time) / (T2 - T1);
        }
    }

    //Count the stay time for each Nth task
    private static double getStayTimeForN(){
        double sum = 0;
        int n = 0;

        for (Task solvedTask : solvedTasks) {
            if(solvedTask.getSolveTime() == K * getStandardSolveTime()){
                sum += solvedTask.getOutTime() - solvedTask.getInTime();
                n++;
            }

        }
        return sum/n;
    }


    //Count the tau, which provides maximum value of target function (returns 1/mu approximately)
    private static double getMaxTau(double start, double end, double step) {
        double max = Double.NEGATIVE_INFINITY;
        double temp = 0;
        double res = 0;
        for (double tau = start; tau < end; tau += step) {
            simulateRR(new RRQueue(), tau);
            temp += getTarget(K1, K2, K3, K4, K5);
            if (temp > max) {
                max = temp;
                res = tau;
            }
        }
        return res;
    }

    //Count solve time for task
    private static double getSolveTime() {
        Random r = new Random();
        return - 1 / MU * Math.log(r.nextDouble());
    }

    private static double getStandardSolveTime() {
        return 1 / MU;
    }

    //Count the time when the next task comes into the system
    private static double genNextEnterTime() {
        Random r = new Random();
        return T - 1 / LAMBDA * Math.log(r.nextDouble());
    }
}
