package auxiliaries.queues;

import java.util.PriorityQueue;
import java.util.Comparator;

import auxiliaries.RNG;

public class Scheduler {
    private RNG rng;
    private PriorityQueue<Event> eventBuffer;

    public Scheduler(RNG rng) {
        this.rng = rng;
        eventBuffer = new PriorityQueue<>(
            new Comparator<Event>() {
                public int compare(Event e1, Event e2){
                    return ((int) e1.scheduledFor - (int) e2.scheduledFor);
                }
            }
        );
    }

    public Event Next() {
        return eventBuffer.poll();
    }

    public void add(double globalTime, EventType eventType) {
        double eventOccurance = getOccurance(eventType);
        Event e = new Event(eventType, (globalTime + eventOccurance));

        eventBuffer.add(e);
    }

    private double getOccurance(EventType eventType) {
        // U(a,b) = a + [(b-a)*x]
        return (eventType.minTime + ((eventType.maxTime - eventType.minTime) * rng.nextRandom()));
    }
}
