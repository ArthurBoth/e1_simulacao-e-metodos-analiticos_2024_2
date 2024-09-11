import auxiliaries.RNG;
import auxiliaries.queues.Scheduler;
import auxiliaries.queues.QueueManager;
import auxiliaries.queues.DataWrapper;

import auxiliaries.Configs;

public class App {
    public static void main(String[] args) {
        RNG rng = new RNG();
        rng.setStop(Configs.ITERATIONS);

        Scheduler scheduler = new Scheduler(rng);
        QueueManager manager = new QueueManager(scheduler);
        
        DataWrapper data;

        queue.run(Configs.ITERATIONS, Configs.FIRST_ARRIVAL);
        data = queue.getData();

        data.printInfo();
    }
}
