package auxiliaries.queues;

import java.util.ArrayList;

public class QueueManager {
    private final Scheduler scheduler;

    private double globalTime;
    private ArrayList<QueueSimulation> queues;
    
    public QueueManager(Scheduler scheduler) {
        this.scheduler = scheduler;
        queues = new ArrayList<>();
        queues.add(null);
    }

    public void newQueue(int serverCount, int capacity, double minArrival, double maxArrival, double minService, double maxService) {
        if (capacity <= 0) {
            queues.add(new QueueSimulation(serverCount, capacity, minArrival, maxArrival, minService, maxService));
        }
        else {
            queues.add(new LimitlessQueue(serverCount, minArrival, maxArrival, minService, maxService));
        }
    }

    public void linkQueues(int source, int target, double chance) {
        queues.get(source).newLink(target, chance);
    }

    public void run(double firstArrival) {
        scheduler.firstArrival(firstArrival);
        while (!scheduler.stop()) {
            Event e = scheduler.next();

            switch (e.EVENT_TYPE) {
                case EventType.ARRIVAL:
                    arrival(e);
                    break;
                case EventType.LEAVE:
                    leave(e);
                    break;
                case EventType.SWITCH:
                    switchQueues(e);
                    break;
                default:
            }

            if (scheduler.isEmpty()) {
                System.out.println("EMERGENCY BREAK");
                break;
            }
        }
        printData();
    }

    private void printData() {
        for (int i = 1; i < queues.size(); i++) {
            queues.get(i).getData().printInfo(String.format("Fila %d", i), globalTime, (i == queues.size()-1));
        }
    }

    private void calcTime(Event e) {
        double now = e.SCHEDULED_FOR;
        for (QueueSimulation q : queues) {
            if (q == null) continue;
            q.calcTime(now - globalTime);
        }
        globalTime = now;
    }

    private EventType setIndexforEvent(Event e, QueueSimulation queue) {
        ArrayList<QueueLink> links = queue.getLinks();
        double random = scheduler.getRandom();
        int index = 0;
        EventType eventType;

        for (QueueLink l : links) {
            if (random < l.CHANCE) {
                index = l.INDEX;
                break;
            }
        }

        if (index == 0) {
            eventType = EventType.LEAVE;
        }
        else { 
            eventType = EventType.SWITCH;
        }

        eventType.setMinTime(queue.MIN_SERVICE);
        eventType.setMaxTime(queue.MAX_SERVICE);
        eventType.setFromQueue(queues.indexOf(queue));
        eventType.setToQueue(index);

        return eventType;
    }

    private void arrival(Event e) {
        calcTime(e);
        int index = e.EVENT_TYPE.toQueue();

        if (index < 1) return; // index == 0 -> Não entra na fila

        if (queues.get(index).getStatus() < queues.get(index).getCapacity()) {
            queues.get(index).clientIn();
        }
        if (queues.get(index).getStatus() <= queues.get(index).getServers()) {
            EventType eventType = setIndexforEvent(e, queues.get(index));

            scheduler.add(globalTime, eventType);
        } else {
            queues.get(index).clientLoss();
        }

        scheduler.add(globalTime, queues.get(index).getArrivalEvent());
    }

    private void leave(Event e) {
        calcTime(e);
        int index = e.EVENT_TYPE.fromQueue();

        queues.get(index).clientOut();
        if (queues.get(index).getStatus() >= queues.get(index).getServers()) {
            scheduler.add(globalTime, queues.get(index).getLeaveEvent());
        }
    }

    private void switchQueues(Event e) {
        calcTime(e);
        int index1 = e.EVENT_TYPE.fromQueue();
        int index2 = e.EVENT_TYPE.toQueue();

        if (index1 < 0) return; // index == 0 -> Sai da fila
        
        queues.get(index1).clientOut();
        if (queues.get(index1).getStatus() >= queues.get(index1).getServers()) {
            EventType eventType = setIndexforEvent(e, queues.get(index1));

            scheduler.add(globalTime, eventType);
        }
        
        if (index2 < 1) return; // index == 0 -> Sai da fila

        if (queues.get(index2).getStatus() < queues.get(index2).getCapacity()) {
            queues.get(index2).clientIn();
            if (queues.get(index2).getStatus() <= queues.get(index2).getServers()) {
                scheduler.add(globalTime, queues.get(index2).getLeaveEvent());
            }
        } else {
            queues.get(index2).clientLoss();
        }
    }
}
