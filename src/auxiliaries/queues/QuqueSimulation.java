package auxiliaries.queues;

public class QuqueSimulation {
    private final int serverCount;
    private final int capacity;

    private int currentCount;
    private int lossCount;
    private double[] timeStatus;
    
    public final double minArrival;
    public final double maxArrival;
    public final double minService;
    public final double maxService;
    
    public QuqueSimulation(int serverCount, int capacity, double minArrival, double maxArrival, double minService, double maxService) {
        this.serverCount = serverCount;
        this.capacity    = capacity;
        this.minArrival  = minArrival;
        this.maxArrival  = maxArrival;
        this.minService  = minService;
        this.maxService  = maxService;

        currentCount = 0;
        lossCount    = 0;
        timeStatus   = new double[capacity + 1]; // +1 pra incluir o '0'
    }
    
    public int getStatus() {
        return currentCount;
    }

    public int getCapacity() {
        return capacity;
    } 

    public int getServers() {
        return serverCount;
    }

    public int getLoss() {
        return lossCount;
    }

    public void clientLoss() {
        lossCount++;
    }

    public void clientIn() {
        currentCount++;
    }

    public void clientOut() {
        currentCount--;
    }

    public void calcTime(double timestamp) {
      timeStatus[currentCount] += timestamp;
    }

    public EventType getArrivalEvent() {
        EventType eventType = EventType.ARRIVAL;
        eventType.setMintime(minArrival);
        eventType.setMaxtime(maxArrival);

        return eventType;
    }

    public EventType getLeaveEvent() {
        EventType eventType = EventType.LEAVE;
        eventType.setMintime(minService);
        eventType.setMaxtime(maxService);

        return eventType;
    }
}
