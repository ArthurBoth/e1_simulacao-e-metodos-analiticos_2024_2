package auxiliaries.queues;

import java.util.ArrayList;

public class QueueSimulation {
    private final int SERVER_COUNT;
    private final int CAPACITY;

    private int currentCount;
    private int lossCount;
    private double[] timeStatus;
    private ArrayList<QueueLink> links;

    public final double MIN_ARRIVAL;
    public final double MAX_ARRIVAL;
    public final double MIN_SERVICE;
    public final double MAX_SERVICE;
    
    public QueueSimulation(int serverCount, int capacity, double minArrival, double maxArrival, double minService, double maxService) {
        SERVER_COUNT = serverCount;
        CAPACITY     = capacity;
        MIN_ARRIVAL  = minArrival;
        MAX_ARRIVAL  = maxArrival;
        MIN_SERVICE  = minService;
        MAX_SERVICE  = maxService;

        currentCount = 0;
        lossCount    = 0;
        timeStatus   = new double[CAPACITY + 1]; // +1 pra incluir o '0'
        links        = new ArrayList<>();
    }
    
    public int getStatus() {
        return currentCount;
    }

    public int getCapacity() {
        return CAPACITY;
    } 

    public int getServers() {
        return SERVER_COUNT;
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
        if (currentCount > 0) currentCount--;
    }

    public void calcTime(double timestamp) {
      timeStatus[currentCount] += timestamp;
    }

    public EventType getArrivalEvent() {
        EventType eventType = EventType.ARRIVAL;
        eventType.setMinTime(MIN_ARRIVAL);
        eventType.setMaxTime(MAX_ARRIVAL);

        return eventType;
    }

    public EventType getLeaveEvent() {
        EventType eventType = EventType.LEAVE;
        eventType.setMinTime(MIN_SERVICE);
        eventType.setMaxTime(MAX_SERVICE);

        return eventType;
    }

    public DataWrapper getData() {
        DataWrapper data = new DataWrapper();
        data.setQueueTime(timeStatus);
        data.setLossCount(lossCount);
        return data;
    }

    public void newLink(int index, double chance) {
        /* Para considerar o input como uma porcentagem absoluta,
           então somar às chances que já existem pra processamento 
           isto também garante que a fila esteja em ordem crescente */
        for (QueueLink l : links) {
            chance += l.CHANCE;
        }

        links.add(new QueueLink(index, chance));
    }

    public ArrayList<QueueLink> getLinks() {
        return links;
    }
}
