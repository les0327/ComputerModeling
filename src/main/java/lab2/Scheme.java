package lab2;

import lombok.*;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

@NoArgsConstructor
public class Scheme {

    @Getter @Setter private Device[] devices;
    private double T;

    public Scheme(Device[] devices) {
        this.devices = devices;
    }

    public void model(int steps) {
        Random random = new Random();
        Device currentDevice = null;
        for (int i = 0; i < steps; i++) {
            Double  min = Double.MAX_VALUE;
            for (Device d : devices) {
                if (d.getTimeOut() < min && d.getTimeOut() != 0) {
                    min           = d.getTimeOut();
                    currentDevice = d;
                }
            }
            T = min; // set total time to moment when task will be calculated
            passTask(currentDevice, random);
        }

        System.out.println("Total tasks = " + steps);
        System.out.println("Total time  = " + T);
        Arrays.asList(devices).forEach(device -> System.out.printf("%3s(%.2f) - %4.2f%c (tasks = %d)%n",
                device.getName(), device.getTau(), device.getWorkTime()/T * 100, '%', device.getTasks()));
    }

    public void addTaskToDevice(Device device) {
        if (device.isFree()) {
            device.setOnProcessing(1);
            device.setFree(false);
            device.setTimeOut(T + device.getTau());
            device.setWorkTime(device.getWorkTime() + device.getTau());
        } else {
            device.setInQueue(device.getInQueue() + 1);
        }
    }

    private void passTask(Device device, Random r) {
        Device next = device.next(r.nextDouble());
        addTaskToDevice(next);
        device.setTasks(device.getTasks() + 1);
        if (device.getInQueue() == 0) {
            device.setTimeOut(0);
            device.setOnProcessing(0);
            device.setFree(true);
        } else {
            device.setInQueue(device.getInQueue() - 1);
            device.setTimeOut(T + device.getTau());
            device.setWorkTime(device.getWorkTime() + device.getTau());
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Scheme{\n");
        for (Device d : devices)
            sb.append('\t').append(d).append('\n');

        sb.append("};");
        return sb.toString();
    }
}
