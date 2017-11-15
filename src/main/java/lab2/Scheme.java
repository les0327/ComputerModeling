package lab2;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Scheme {

    @Getter @Setter private Device[] devices;
    private double T;


    public void model(int steps) {
        for (int i = 0; i < steps; i++) {

        }
    }

    public void addTaskToDevice(Device device) {
        if (device.isFree()) {
            if (device.getInQueue() == 0) {
                device.setOnProcessing(1);
                device.setTimeOut(T + device.getTau());
                device.setFree(false);
                device.setWorkTime(device.getWorkTime() + device.getTau());
            } {
                device.setInQueue(device.getInQueue() + 1);
            }
        } else {
            device.setInQueue(device.getInQueue());
        }
    }

}
