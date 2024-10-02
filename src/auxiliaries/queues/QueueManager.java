package auxiliaries.queues;

import auxiliaries.io.ConsoleLogger;

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
            queues.add(new LimitlessQueue(serverCount, minArrival, maxArrival, minService, maxService));
        }
        else {
            queues.add(new QueueSimulation(serverCount, capacity, minArrival, maxArrival, minService, maxService));
        }
    }

    public void linkQueues(int source, int target, double chance) {
        queues.get(source).newLink(target, chance);
    }

    public void run(double firstArrival, boolean printData) {
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
                ConsoleLogger.logError("EMERGENCY BREAK");
                break;
            }
        }
        if (printData) {
            printData();
        }
    }

    private void printData() {
        for (int i = 1; i < queues.size(); i++) {
            queues.get(i).getData().printInfo(String.format("Queue %d", i), globalTime, (i == queues.size()-1));
        }
    }

    private void calcTime(Event e) {
        double now = e.scheduledFor();
        for (QueueSimulation q : queues) {
            if (q == null) continue;
            q.calcTime(now - globalTime);
        }
        globalTime = now;
    }

    private Event getRandomEvent(QueueSimulation queue) {
        ArrayList<QueueLink> links = queue.getLinks();
        double random = scheduler.getRandom();
        int index = 0;
        Event event;

        for (QueueLink l : links) {
            if (random < l.CHANCE) {
                index = l.INDEX;
                break;
            }
        }

        if (index == 0) {
            event = new Event(EventType.LEAVE);
        }
        else { 
            event = new Event(EventType.SWITCH);
        }

        event.setMinTime(queue.MIN_SERVICE);
        event.setMaxTime(queue.MAX_SERVICE);
        event.setFromQueue(queues.indexOf(queue));
        event.setToQueue(index);

        return event;
    }

    private void arrival(Event e) {
        QueueSimulation q;
        Event event;

        int index = e.toQueue();
        calcTime(e);

        q = queues.get(index);

        if (q.getStatus() < q.getCapacity()) {
            q.clientIn();
        }
        if (q.getStatus() <= q.getServers()) {
            event = getRandomEvent(q);

            scheduler.add(globalTime, event);
        } else {
            q.clientLoss();
        }

        event = q.getArrivalEvent();
        event.setToQueue(1);

        scheduler.add(globalTime, event);
    }

    private void leave(Event e) {
        QueueSimulation q;
        Event event;

        int index = e.fromQueue();
        calcTime(e);
        
        q = queues.get(index);

        q.clientOut();
        if (q.getStatus() >= q.getServers()) {
            event = q.getLeaveEvent();
            event.setFromQueue(index);

            scheduler.add(globalTime, event);
        }
    }

    private void switchQueues(Event e) {
        QueueSimulation q1;
        QueueSimulation q2;
        Event event;

        int index1 = e.fromQueue();
        int index2 = e.toQueue();
        calcTime(e);

        q1 = queues.get(index1);
        q2 = queues.get(index2);
        
        q1.clientOut();
        if (q1.getStatus() >= q1.getServers()) {
            event = getRandomEvent(q1);

            scheduler.add(globalTime, event);
        }

        if (q2.getStatus() < q2.getCapacity()) {
            q2.clientIn();
            if (q2.getStatus() <= q2.getServers()) {
                event = getRandomEvent(q2);
    
                scheduler.add(globalTime, event);
            }
        } else {
            q2.clientLoss();
        }
    }
}
