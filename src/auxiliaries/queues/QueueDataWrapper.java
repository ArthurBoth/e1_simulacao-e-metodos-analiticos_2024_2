package auxiliaries.queues;

import auxiliaries.io.ConsoleLogger;
import auxiliaries.io.FileIO;

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
                System.out.println();
            }
        }
    }

    public void writeInfo(String header, double endTime, boolean writeEndTime) {
        StringBuilder builder;
        if (validNumbers(endTime)) {
            builder = new StringBuilder(String.format("%s%n", header));
            
            builder.append(String.format("Probability distribution%n"));
            for(int i = 0; i < queueTimeStatus.length; i++) {
                builder.append(String.format("%d: %.04f %%%n", i, (queueTimeStatus[i]/endTime)));
            }
    
            builder.append(String.format("Grupped times%n"));
            for(int i = 0; i < queueTimeStatus.length; i++) {
                builder.append(String.format("%d: %.04f%n", i, queueTimeStatus[i]));
            }
            
            builder.append(String.format("Clients lost%n"));
            builder.append(String.format("%d%n", lossCount));

            if (writeEndTime) {
                builder.append(String.format("Total simulation time%n"));
                builder.append(String.format("%.04f%n%n", endTime));
            }
            else {
                builder.append(System.lineSeparator());
            }

            FileIO.writeLine("_Output File_.txt", builder.toString());
        }
    }
    
    private boolean validNumbers(double endTime) {
        return (lossCount >= 0) &&
               (endTime   >  0) &&
               (queueTimeStatus != null);
    }
}
