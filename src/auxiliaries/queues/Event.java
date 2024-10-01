package auxiliaries.queues;

public class Event {
    public final EventType EVENT_TYPE;

    private double scheduledFor;
    private double minTime;
    private double maxTime;
    private int fromQueue;
    private int toQueue;

    public Event(EventType eventType) {
        EVENT_TYPE      = eventType;
    }

    public void setScheduledFor(double scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public void setFromQueue(int fromQueue) {
        this.fromQueue = fromQueue;
    }

    public void setToQueue(int toQueue) {
        this.toQueue = toQueue;
    }

    public double scheduledFor() {
        return scheduledFor;
    }

    public double minTime() {
        return minTime;
    }

    public double maxTime() {
        return maxTime;
    }

    public int fromQueue() {
        return fromQueue;
    }

    public int toQueue() {
        return toQueue;
    }
}
