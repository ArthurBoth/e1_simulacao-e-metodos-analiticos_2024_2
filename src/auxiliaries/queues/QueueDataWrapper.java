package auxiliaries.queues;

import auxiliaries.io.ConsoleLogger;

public class QueueDataWrapper {
    
    private double[] queueTimeStatus; // Sum of how long the queue had [index] clients 
    private int lossCount;            // how many clients were lost due to not having queue space left

    public QueueDataWrapper() {
        lossCount        = -1;
        queueTimeStatus  = null;
    }

    public void setQueueTime(double[] queueTimeStatus) {
        this.queueTimeStatus = queueTimeStatus;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }
    
    public void printInfo(String header, double endTime) {
        printInfo(header, endTime, false);
    }
    
    public void printInfo(String header, double endTime, boolean printEndTime) {
        if (validNumbers(endTime)) {
            ConsoleLogger.logGreen(header);

            ConsoleLogger.logYellow("Probability distribution");
            for(int i = 0; i < queueTimeStatus.length; i++) {
                ConsoleLogger.logWhite(String.format("%d: %.04f %%", i, (queueTimeStatus[i]/endTime)));
            }
    
            ConsoleLogger.logYellow("Grupped times");
            for(int i = 0; i < queueTimeStatus.length; i++) {
                ConsoleLogger.logWhite(String.format("%d: %.04f", i, queueTimeStatus[i]));
            }
            
            ConsoleLogger.logYellow("Clients lost");
            ConsoleLogger.logWhite(String.format("%d%n", lossCount));
    
            if (printEndTime) {
                ConsoleLogger.logYellow("Total simulation time");
                ConsoleLogger.logWhite(String.format("%.04f%n%n", endTime));
            } else {
                ConsoleLogger.logWhite("");
            }
        }
    }
    
    private boolean validNumbers(double endTime) {
        return (lossCount >= 0) &&
               (endTime   >  0) &&
               (queueTimeStatus != null);
    }
}
