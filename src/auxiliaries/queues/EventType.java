package auxiliaries.queues;

public enum EventType {
    ARRIVAL, LEAVE, SWITCH;

    private double minTime;
    private double maxTime;

    private EventType() {
    }

    public void setMintime(double minTime) {
        this.minTime = minTime;
    }

    public void setMaxtime(double maxTime) {
        this.maxTime = maxTime;
    }

    public double minTime() {
        return minTime;
    }

    public double maxTime() {
        return maxTime;
    }
}
