import auxiliaries.queues.QuqueSimulation;
import auxiliaries.queues.DataWrapper;

public class App {
    public static void main(String[] args) {
        QuqueSimulation queue = new QuqueSimulation();
        DataWrapper data;

        queue.run(100000);
        data = queue.getData();
        
        for(int i = 0; i < data.queueTimeStatus.length; i++) {
            System.out.printf("%d: %d (%d%%)%n", i, data.queueTimeStatus[i], (data.queueTimeStatus[i]/data.endTime));
        }
    }
}
