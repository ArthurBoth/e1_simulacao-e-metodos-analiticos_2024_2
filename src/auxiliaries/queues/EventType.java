package auxiliaries.queues;

public enum EventType {
    ARRIVAL, LEAVE, SWITCH;

    private double minTime;
    private double maxTime;
    private int fromQueue;
    private int toQueue;

    private EventType() {
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
