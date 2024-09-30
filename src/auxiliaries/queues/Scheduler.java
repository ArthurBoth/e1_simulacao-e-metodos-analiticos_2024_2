package auxiliaries.queues;

import java.util.PriorityQueue;

import auxiliaries.RNG;

public class Scheduler {
    private RNG rng;
    private PriorityQueue<Event> eventBuffer;

    public Scheduler(RNG rng) {
        this.rng = rng;
        eventBuffer = new PriorityQueue<>(
            (e1, e2) -> ((int) ((e1.SCHEDULED_FOR - e2.SCHEDULED_FOR) * 10000))
        );
    }

    public Event next() {
        return eventBuffer.poll();
    }

    public void add(double minArrival, double maxArrival, EventType eventType) {
        eventType.setMinTime(minArrival);
        eventType.setMaxTime(maxArrival);
        add(rng.nextRandom(minArrival, maxArrival), eventType);
    }

    public void add(double globalTime, EventType eventType) {
        double eventOccurance = getOccurance(eventType);
        Event e = new Event(eventType, (globalTime + eventOccurance));

        eventBuffer.add(e);
    }

    public void firstArrival(double arrivalTime) {
        EventType type = EventType.ARRIVAL;
        type.setFromQueue(0);
        type.setToQueue(1);

        Event e = new Event(type, arrivalTime);
        eventBuffer.add(e);
    }

    private double getOccurance(EventType eventType) {
        // U(a,b) = a + [(b-a)*x]
        return (eventType.minTime() + ((eventType.maxTime() - eventType.minTime()) * rng.nextRandom()));
    }

    public boolean stop() {
        return rng.stop();
    }

    public double getRandom() {
        return rng.nextRandom(0, 1);
    }

    public boolean isEmpty() {
        return eventBuffer.isEmpty();
    }
}
