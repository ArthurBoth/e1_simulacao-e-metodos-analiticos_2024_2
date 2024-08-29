import auxiliaries.queues.QuqueSimulation;
import auxiliaries.queues.DataWrapper;

import auxiliaries.Configs;

public class App {
    public static void main(String[] args) {
        QuqueSimulation queue = new QuqueSimulation();
        DataWrapper data;

        queue.run(Configs.ITERATIONS);
        data = queue.getData();
        
        for(int i = 0; i < data.queueTimeStatus.length; i++) {
            System.out.printf("%d: %f (%f %%)%n", i, data.queueTimeStatus[i], (data.queueTimeStatus[i]/data.endTime));
        }
    }
}
