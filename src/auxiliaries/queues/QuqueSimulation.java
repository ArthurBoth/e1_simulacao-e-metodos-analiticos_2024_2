package auxiliaries.queues;

import static auxiliaries.Configs.QUEUE_CAPACITY;
import static auxiliaries.Configs.QUEUE_SERVERS;

import auxiliaries.Configs;
import auxiliaries.RNG;

public class QuqueSimulation {

    private final RNG rng;
    private final Scheduler scheduler;

    private double globalTime;
    private int currentCount;
    private int lossCount;
    private double[] timeStatus;
    
    public QuqueSimulation() {
        rng = new RNG();
        scheduler = new Scheduler(rng);

        globalTime = 0;
        currentCount = 0;
        lossCount = 0;
        timeStatus = new double[QUEUE_CAPACITY + 1]; // +1 pra incluir o '0'
    }

    public void run(int stopInCount) {
        scheduler.add(rng.nextRandom(Configs.MIN_TIME_TO_ARRIVE, Configs.MAX_TIME_TO_ARRIVE), EventType.ARRIVAL); // Primeira chegada aleatória
        rng.setStop(stopInCount);
        runLoop();
    }

    public void run(int stopInCount, double firstArrival) {
        if (firstArrival > 0) {
            rng.setStop(stopInCount);
            scheduler.add(firstArrival, EventType.ARRIVAL); // Primeira chegada predeterminada
            runLoop();
        }
        else {
            run(stopInCount);
        }
    }

    private void runLoop() {
        while (!rng.stop()) {
            Event e = scheduler.Next();

            if (e.eventType == EventType.ARRIVAL) {
                arrival(e);
            }
            else if (e.eventType == EventType.LEAVE) {
                leave(e);
            }
        }
    }

    private void arrival(Event e) {
        calcTime(e);

        if (currentCount < QUEUE_CAPACITY) {
            currentCount++;
            if (currentCount <= QUEUE_SERVERS) {
                scheduler.add(globalTime, EventType.LEAVE); // Agenda uma saída
            }
        } 
        else {
            lossCount++;
        }

        scheduler.add(globalTime, EventType.ARRIVAL);
    }

    private void leave(Event e) {
        calcTime(e);

        currentCount--;
        if (currentCount >= QUEUE_SERVERS) {
            scheduler.add(globalTime, EventType.LEAVE);
        }
    }

    private void calcTime(Event e) {
        double now = e.scheduledFor;
        timeStatus[currentCount] = (timeStatus[currentCount] + (now - globalTime));
        globalTime = now;
    }

    public DataWrapper getData() {
        DataWrapper data = new DataWrapper();

        data.setEndTime(globalTime);
        data.setLossCount(lossCount);
        data.setQueueTime(timeStatus);

        return data;
    }
}
