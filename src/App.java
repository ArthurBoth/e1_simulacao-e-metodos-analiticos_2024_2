import auxiliaries.RNG;
import auxiliaries.queues.Scheduler;
import auxiliaries.queues.QueueManager;

import auxiliaries.Configs;

public class App {
    public static void main(String[] args) {
        RNG rng = new RNG();
        rng.setStop(Configs.ITERATIONS);

        Scheduler scheduler = new Scheduler(rng);
        QueueManager manager = new QueueManager(scheduler);
        
        manager.run(Configs.FIRST_ARRIVAL);
    }
}
