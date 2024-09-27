package auxiliaries.queues;

import java.util.ArrayList;

public class QueueManager {
    public static final double FIRST_ARRIVAL      = 1.5;
    public static final double MIN_TIME_TO_ARRIVE = 1;
    public static final double MAX_TIME_TO_ARRIVE = 4;
    public static final double MIN_TIME_TO_SWITCH = 3;
    public static final double MAX_TIME_TO_SWITCH = 4;
    public static final double MIN_TIME_TO_LEAVE  = 2;
    public static final double MAX_TIME_TO_LEAVE  = 3;

    private final Scheduler scheduler;

    private double globalTime;
    private ArrayList<QuqueSimulation> queues;
    private QuqueSimulation queue1;
    private QuqueSimulation queue2;
    
    public QueueManager(Scheduler scheduler) {
        this.scheduler = scheduler;
        queues = new ArrayList<>();
        queue1 = new QuqueSimulation(2, 3,
                                     MIN_TIME_TO_ARRIVE, MAX_TIME_TO_ARRIVE,
                                     MIN_TIME_TO_SWITCH, MAX_TIME_TO_SWITCH);
        queue2 = new QuqueSimulation(1, 5,
                                     0, 0,
                                     MIN_TIME_TO_LEAVE, MAX_TIME_TO_LEAVE);
    }

    public void newQueue(int serverCount, int capacity, double minArrival, double maxArrival, double minService, double maxService) {
        queues.add(new QuqueSimulation(serverCount, capacity, minArrival, maxArrival, minService, maxService));
    }

    public void run(double firstArrival) {
        scheduler.firstArrival(firstArrival);
        while (!scheduler.stop()) {
            Event e = scheduler.next();

            switch (e.eventType) {
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
        }
        printData();
    }

    private void printData() {
        queue1.getData().printInfo("Fila 1", globalTime);
        queue2.getData().printInfo("Fila 2", globalTime, true);
    }

    private void calcTime(Event e) {
        double now = e.scheduledFor;
        queue1.calcTime(now - globalTime);
        queue2.calcTime(now - globalTime);
        globalTime = now;
    }

    private void arrival(Event e) {
        calcTime(e);

        if (queue1.getStatus() < queue1.getCapacity()) {
            queue1.clientIn();
        }
        if (queue1.getStatus() <= queue1.getServers()) {
            EventType eventType = EventType.SWITCH;
            eventType.setMintime(MIN_TIME_TO_SWITCH);
            eventType.setMaxtime(MAX_TIME_TO_SWITCH);

            scheduler.add(globalTime, EventType.SWITCH);
        } else {
            queue1.clientLoss();
        }

        scheduler.add(globalTime, queue1.getArrivalEvent());
    }

    private void leave(Event e) {
        calcTime(e);

        queue2.clientOut();
        if (queue2.getStatus() >= queue2.getServers()) {
            scheduler.add(globalTime, queue2.getLeaveEvent());
        }
    }

    private void switchQueues(Event e) {
        calcTime(e);

        queue1.clientOut();
        if (queue1.getStatus() >= queue1.getServers()) {
            EventType eventType = EventType.SWITCH;
            eventType.setMintime(0);
            eventType.setMaxtime(0);

            scheduler.add(globalTime, eventType);
        }
        if (queue2.getStatus() < queue2.getCapacity()) {
            queue2.clientIn();
            if (queue2.getStatus() <= queue2.getServers()) {
                scheduler.add(globalTime, queue2.getLeaveEvent());
            }
        } else {
            queue2.clientLoss();
        }
    }
}
