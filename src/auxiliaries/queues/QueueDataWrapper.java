package auxiliaries.queues;

import java.io.StringReader;

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
                ConsoleLogger.logBlue("Total simulation time");
                ConsoleLogger.logWhite(String.format("%.04f%n%n", endTime));
                System.out.println();
                printTable(endTime);
            } else {
                System.out.println();
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

        ConsoleLogger.logWhite("=================================================================================================");
        ConsoleLogger.logYellow("Probability\t\tNbPacients\t\tFlPacients\t\tUsage\t\tAnsTime");
        ConsoleLogger.logWhite("=================================================================================================");
        for (int i = 0; i < queueTimeStatus.length; i++) {
            aux = 1. / ((minService + maxService) / 2);
            ansRate = Math.min(i, capacity) * aux;
            
            probability = queueTimeStatus[i] / endTime;
            population  = queueTimeStatus[i] * i;
            exit        = queueTimeStatus[i] * ansRate;
            usage       = queueTimeStatus[i] * (Math.min(i, capacity) / capacity);
            ansTime     = population / exit;


            ConsoleLogger.logWhite(formatLine(probability, population, exit, usage, ansTime));
        }
    }

    private String formatLine(double probability, double population, double exit, double usage, double ansTime) {
        StringBuilder builder = new StringBuilder();
        String prob = String.format("%.04f", probability);
        String pop  = String.format("%.04f", population);
        String ex   = String.format("%.04f", exit);
        String usg  = String.format("%.04f", usage);
        String ans  = String.format("%.04f", ansTime);

        builder.append(prob);
        for (int i = 0; i < ((prob.length() / 8) + 3); i++) builder.append("\t");
        builder.append(pop);
        for (int i = 0; i < ((pop.length() / 8) + 1); i++) builder.append("\t");
        builder.append(ex);
        for (int i = 0; i < ((ex.length() / 8) + 1); i++) builder.append("\t");
        builder.append(usg);
        for (int i = 0; i < ((usg.length() / 8) + 1); i++) builder.append("\t");
        builder.append(ans);
        for (int i = 0; i < ((ans.length() / 8) + 1); i++) builder.append("\t");

        return builder.toString();
    }
}
