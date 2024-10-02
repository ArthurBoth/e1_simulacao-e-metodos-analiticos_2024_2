package auxiliaries.queues;

import java.util.ArrayList;

public class LimitlessQueue extends QueueSimulation {
    ArrayList<Double> timeStatus;

    public LimitlessQueue(int serverCount, double minArrival, double maxArrival, double minService, double maxService) {
        super(serverCount, 0, minArrival, maxArrival, minService, maxService);

        timeStatus  = new ArrayList<>();

        timeStatus.add(0.);
    }

    @Override
    public int getCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void calcTime(double timestamp) {
        int serverCount;
        double time;

        serverCount = super.getStatus();
        fillStatus(serverCount);

        time = timeStatus.get(serverCount) + timestamp;
        timeStatus.set(serverCount, time);
      }

      private void fillStatus(int index) {
        while (timeStatus.size() <= index) {
            timeStatus.add(0.);
        }
      }

    @Override
    public QueueDataWrapper getData() {
        QueueDataWrapper data = new QueueDataWrapper();
        data.setQueueTime(timeStatus.stream().mapToDouble(Double::doubleValue).toArray());
        data.setLossCount(super.getLoss());
        return data;
    }
}
