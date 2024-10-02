import auxiliaries.RNG;
import auxiliaries.queues.Scheduler;
import auxiliaries.queues.QueueManager;

public class App {
    // Simulation configs
    public static final int ITERATIONS       = 100000;
    public static final double FIRST_ARRIVAL = 2;
    public static void main(String[] args) {

        RNG rng = new RNG();
        rng.setStop(ITERATIONS);

        Scheduler scheduler = new Scheduler(rng);
        QueueManager manager = new QueueManager(scheduler);
        
        manager.newQueue(1, 0, 2, 4, 1, 2);
        manager.newQueue(2, 5, 0, 0, 4, 8);
        manager.newQueue(2, 10, 0, 0, 5, 15);

        manager.linkQueues(1, 2, 0.8);
        manager.linkQueues(1, 3, 0.2);
        manager.linkQueues(2, 1, 0.3);
        manager.linkQueues(2, 0, 0.2);
        manager.linkQueues(2, 2, 0.5);
        manager.linkQueues(3, 3, 0.7);
        manager.linkQueues(3, 3, 0.3);

        manager.run(FIRST_ARRIVAL, true);


    }
}