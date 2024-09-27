package auxiliaries.queues;

import static auxiliaries.Configs.ANSI_GREEN;
import static auxiliaries.Configs.ANSI_YELLOW;
import static auxiliaries.Configs.ANSI_RESET;

public class DataWrapper {
    
    private double[] queueTimeStatus;    // status de quanto tempo haviam aquele numero de clientes na fila
    private int lossCount;               // quantos clientes não entraram na fila porque ela estava cheia

    public DataWrapper() {
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
            System.out.printf("%s%s%s%n", ANSI_GREEN, header, ANSI_RESET);

            System.out.printf("%sDistribuição de probabilidades%s%n", ANSI_YELLOW, ANSI_RESET);
            for(int i = 0; i < queueTimeStatus.length; i++) {
                    System.out.printf("%d: %.04f %%%n", i, (queueTimeStatus[i]/endTime));
                }
    
            System.out.printf("%n%sTempos acumulados%s%n", ANSI_YELLOW, ANSI_RESET);
            for(int i = 0; i < queueTimeStatus.length; i++) {
                    System.out.printf("%d: %.04f%n", i, queueTimeStatus[i]);
                }
                
            System.out.printf("%n%sNúmero de perda de clientes%s%n", ANSI_YELLOW, ANSI_RESET);
            System.out.printf("%d%n", lossCount);
    
            if (printEndTime) {
                System.out.printf("%n%sTempo global da simulação%s%n", ANSI_YELLOW, ANSI_RESET);
                System.out.printf("%.04f%n%n", endTime);
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
}
