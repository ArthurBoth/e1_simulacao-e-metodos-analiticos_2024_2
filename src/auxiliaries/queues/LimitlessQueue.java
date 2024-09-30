package auxiliaries.queues;

import java.util.ArrayList;

public class LimitlessQueue extends QueueSimulation {
    ArrayList<Double> timeStatus;

    public LimitlessQueue(int serverCount, double minArrival, double maxArrival, double minService, double maxService) {
        super(serverCount, 0, minArrival, maxArrival, minService, maxService);
        timeStatus = new ArrayList<>();
    }

    @Override
    public int getCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void calcTime(double timestamp) {
        int index = timeStatus.indexOf(super.getStatus());
        double time = timeStatus.get(index) + timestamp;
        timeStatus.set(index, time);
      }

    @Override
    public DataWrapper getData() {
        DataWrapper data = new DataWrapper();
        data.setQueueTime(timeStatus.stream().mapToDouble(Double::doubleValue).toArray());
        data.setLossCount(super.getLoss());
        return data;
    }
}
