package auxiliaries.queues;

import java.util.PriorityQueue;

import auxiliaries.RNG;

public class Scheduler {
    private RNG rng;
    private PriorityQueue<Event> eventBuffer;

    public Scheduler(RNG rng) {
        this.rng = rng;
        eventBuffer = new PriorityQueue<>(
            (e1, e2) -> ((int) ((e1.scheduledFor() - e2.scheduledFor()) * 10000))
        );
    }

    public Event next() {
        return eventBuffer.poll();
    }

    public void add(double minArrival, double maxArrival, Event event) {
        event.setMinTime(minArrival);
        event.setMaxTime(maxArrival);
        add(rng.nextRandom(minArrival, maxArrival), event);
    }

    public void add(double globalTime, Event event) {
        double eventOccurance = getOccurance(event);
        event.setScheduledFor(globalTime + eventOccurance);

        eventBuffer.add(event);
    }

    public void firstArrival(double arrivalTime) {
        Event event = new Event(EventType.ARRIVAL);
        event.setFromQueue(0);
        event.setToQueue(1);

        event.setScheduledFor(arrivalTime);
        eventBuffer.add(event);
    }

    private double getOccurance(Event event) {
        // U(a,b) = a + [(b-a)*x]
        return (event.minTime() + ((event.maxTime() - event.minTime()) * rng.nextRandom()));
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
