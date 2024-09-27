package auxiliaries.queues;

import java.util.PriorityQueue;

import auxiliaries.RNG;

public class Scheduler {
    private RNG rng;
    private PriorityQueue<Event> eventBuffer;

    public Scheduler(RNG rng) {
        this.rng = rng;
        eventBuffer = new PriorityQueue<>(
            (e1, e2) -> ((int) e1.scheduledFor - (int) e2.scheduledFor)
        );
    }

    public Event next() {
        return eventBuffer.poll();
    }

    public void add(double minArrival, double maxArrival, EventType eventType) {
        eventType.setMintime(minArrival);
        eventType.setMaxtime(maxArrival);
        add(rng.nextRandom(minArrival, maxArrival), eventType);
    }

    public void add(double globalTime, EventType eventType) {
        double eventOccurance = getOccurance(eventType);
        Event e = new Event(eventType, (globalTime + eventOccurance));

        eventBuffer.add(e);
    }

    public void firstArrival(double arrivalTime) {
        Event e = new Event(EventType.ARRIVAL, arrivalTime);
        eventBuffer.add(e);
    }

    private double getOccurance(EventType eventType) {
        // U(a,b) = a + [(b-a)*x]
        return (eventType.minTime() + ((eventType.maxTime() - eventType.minTime()) * rng.nextRandom()));
    }

    public boolean stop() {
        return rng.stop();
    }
}
