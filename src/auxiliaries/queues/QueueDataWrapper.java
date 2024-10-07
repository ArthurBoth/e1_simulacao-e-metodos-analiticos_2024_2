package auxiliaries.queues;

import auxiliaries.io.ConsoleLogger;

public class QueueDataWrapper {

    private double minService;        // Minimum time of service for a queue
    private double maxService;        // Maximum time of service for a queue
    private double[] queueTimeStatus; // Sum of how long the queue had [index] clients 
    private int lossCount;            // How many clients were lost due to not having queue space left

    public QueueDataWrapper() {
        minService       = -1; 
        maxService       = -1;
        lossCount        = -1;
        queueTimeStatus  = null;
    }

    public void setQueueTime(double[] queueTimeStatus) {
        this.queueTimeStatus = queueTimeStatus;
    }

    public void setMinService(double minService) {
        this.minService = minService;
    }

    public void setMaxService(double maxService) {
        this.maxService = maxService;
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
    
            ConsoleLogger.logYellow("Grupped times");
            for(int i = 0; i < queueTimeStatus.length; i++) {
                ConsoleLogger.logWhite(String.format("%02d: %.04f", i, queueTimeStatus[i]));
            }
            
            ConsoleLogger.logYellow("Clients lost");
            ConsoleLogger.logWhite(String.format("%d%n", lossCount));

            printTable(endTime);
    
            if (printEndTime) {
                ConsoleLogger.logBlue("\nTotal simulation time");
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
    
    private void printTable(double endTime) {
        int capacity = queueTimeStatus.length - 1;
        double aux;
        double ansRate;
        
        double probability;
        double population;
        double exit;
        double usage;
        double ansTime;
        
        ConsoleLogger.logWhite("=".repeat(85));
        ConsoleLogger.logYellow(" i\tProbability\tNbPacients\tFlPacients\tUsage\t\tAnsTime");
        ConsoleLogger.logWhite("-".repeat(85));
        for (int i = 0; i < queueTimeStatus.length; i++) {
            aux = 1. / ((minService + maxService) / 2);
            ansRate = Math.min(i, capacity) * aux;
            
            probability = queueTimeStatus[i] / endTime;
            population  = queueTimeStatus[i] * i;
            exit        = queueTimeStatus[i] * ansRate;
            usage       = queueTimeStatus[i] * (Math.min(i, capacity) / (double) capacity);
            ansTime     = (exit > 0) ? (population / exit) : 0;
            
            ConsoleLogger.logWhite(String.format("%02d\t%09.4f%%\t%09.4f\t%09.4f\t%09.4f\t%09.4f",
                                                     i, probability, population, exit, usage, ansTime));
        }
        ConsoleLogger.logWhite("-".repeat(85));
    }
}
