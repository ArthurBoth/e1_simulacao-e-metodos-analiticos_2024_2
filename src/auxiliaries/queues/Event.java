package auxiliaries.queues;

public class Event {
    public final EventType EVENT_TYPE;
    public final double SCHEDULED_FOR;

    public Event(EventType eventType, double scheduledFor) {
        EVENT_TYPE      = eventType;
        SCHEDULED_FOR   = scheduledFor;
    }
}
