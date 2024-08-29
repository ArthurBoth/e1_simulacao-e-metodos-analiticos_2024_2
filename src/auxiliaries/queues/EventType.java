package auxiliaries.queues;

import static auxiliaries.Configs.MIN_TIME_TO_ARRIVE;
import static auxiliaries.Configs.MAX_TIME_TO_ARRIVE;
import static auxiliaries.Configs.MIN_TIME_TO_LEAVE;
import static auxiliaries.Configs.MAX_TIME_TO_LEAVE;

public enum EventType {
    ARRIVAL(MIN_TIME_TO_ARRIVE, MAX_TIME_TO_ARRIVE), LEAVE(MIN_TIME_TO_LEAVE, MAX_TIME_TO_LEAVE);

    public final double minTime;
    public final double maxTime;

    private EventType(double minTime, double maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }
}
