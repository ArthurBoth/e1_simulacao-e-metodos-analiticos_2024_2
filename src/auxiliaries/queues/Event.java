package auxiliaries.queues;

public class Event {
    public final EventType eventType;
    public final double scheduledFor;

    public Event(EventType eventType, double scheduledFor) {
        this.eventType = eventType;
        this.scheduledFor = scheduledFor;
    }
}
