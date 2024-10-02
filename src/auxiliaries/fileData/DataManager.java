package auxiliaries.fileData;

import auxiliaries.io.FileIO;
import auxiliaries.queues.LimitlessQueue;
import auxiliaries.queues.QueueLink;
import auxiliaries.queues.QueueSimulation;

import java.util.Arrays;
import java.util.ArrayList;

public class DataManager {
    private double[] arrivals;
    private QueueSimulation[] queues;
    private LinkDataWrapper[] links;
    private double[] randomNumbers;
    private int ammountPerSeed;
    private double[] seeds;

    public DataManager() {
    }

    public void readFile(String filePath) {
        FileDataWrapper data = FileIO.read(filePath);
        String[] lines = data.content.split(System.lineSeparator());
        
        fillData(data, lines);
    }

    private void fillData(FileDataWrapper data, String[] lines) {
        String[] arrivalsAux      = Arrays.copyOfRange(lines, data.arrivalsIndex, data.queuesIndex);
        String[] queuesAux        = Arrays.copyOfRange(lines, data.queuesIndex, data.networksIndex);
        String[] linksAux         = Arrays.copyOfRange(lines, data.networksIndex, data.rndnumbersIndex);
        String[] randomNumbersAux = Arrays.copyOfRange(lines, data.rndnumbersIndex, data.numbersPerSeedIndex);
        String ammountPerSeedAux  = lines[data.numbersPerSeedIndex];
        String[] seedsAux         = Arrays.copyOfRange(lines, data.seedsIndex, lines.length);

        
        arrivals       = getDataDouble(arrivalsAux);
        queues         = getDataSimulations(queuesAux);
        links          = getDataLinks(linksAux);
        randomNumbers  = getDataDouble(randomNumbersAux);
        ammountPerSeed = Integer.parseInt(ammountPerSeedAux);
        seeds          = getDataDouble(seedsAux);
    }

    private double[] getDataDouble(String[] str) {
        double[] returnValue = new double[str.length];
        String[] aux;

        // Start at 1 to ignore the header
        for (int i = 1; i < str.length; i++) {
            aux = str[i].split(":");

            returnValue[i] = Double.parseDouble(aux[1]);
        }

        return returnValue;
    } 

    private QueueSimulation[] getDataSimulations(String[] str) {
        QueueSimulation[] returnValue = new QueueSimulation[str.length];
        int queueCount = 0;
        int paramsCount = 0;
        ArrayList<String> queueParams =  new ArrayList<>();
        String[] aux;

        // Start at 1 to ignore the header
        for (int i = 1; i < str.length; i++) {
            aux = str[i].split(":");

            if (!aux[1].isBlank()) {
                for (int ii = i; ((str[ii].split(":")[1].isBlank()) && (ii < str.length)); ii++) {
                    queueParams.add(str[ii]);
                    paramsCount = ii;
                }
                returnValue[queueCount++] = getQueueSimulation(queueParams);
                i += paramsCount - 1; // skip lines that were already read
            }
        }
        return returnValue;
    }

    private QueueSimulation getQueueSimulation(ArrayList<String> params) {
        String[] aux;
        int servers = 0;
        int capacity = 0;
        double minArrival = 0;
        double maxArrival = 0;
        double minService = 0;
        double maxService = 0;
        for (String s : params) {
            aux = s.split(":");
            switch (aux[0]) {
                case "servers"    -> {servers = Integer.parseInt(aux[1]);}
                case "capacity"   -> {capacity = Integer.parseInt(aux[1]);}
                case "minArrival" -> {minArrival = Double.parseDouble(aux[1]);}
                case "maxArrival" -> {maxArrival = Double.parseDouble(aux[1]);}
                case "minService" -> {minService = Double.parseDouble(aux[1]);}
                case "maxService" -> {maxService = Double.parseDouble(aux[1]);}
            }
        }

        if (capacity == 0) {
            return new LimitlessQueue(servers, minArrival, maxArrival, minService, maxService);
        }
        return new QueueSimulation(servers, capacity, minArrival, maxArrival, minService, maxService);
    }

    private LinkDataWrapper[] getDataLinks(String[] str) {
        LinkDataWrapper[] returnValue = new LinkDataWrapper[str.length];
        int linkCount = 0;
        int fromIndex = 0;
        int toIndex   = 0;
        double chance = 0;
        String[] aux;

        // Start at 1 to ignore the header
        for (int i = 1; i < str.length; i++) {
            if ((str[i].charAt(0) == '-') && (i < str.length - 2)) {
                aux = str[i].split(":");
                fromIndex = Integer.parseInt(aux[1]); 
                aux = str[i + 1].split(":");
                toIndex = Integer.parseInt(aux[1]); 
                aux = str[i + 2].split(":");
                chance = Double.parseDouble(aux[1]);

                i += 2;

                returnValue[linkCount++] = new LinkDataWrapper(fromIndex, new QueueLink(toIndex, chance));
            }
        }

        return returnValue;
    }

    public double[] arrivals() {
        return arrivals;
    }

    public QueueSimulation[] queues() {
        return queues;
    }

    public LinkDataWrapper[] links() {
        return links;
    }

    public double[] randomNumbers() {
        return randomNumbers;
    }

    public int ammountPerSeed() {
        return ammountPerSeed;
    }

    public double[] seeds() {
        return seeds;
    }
}
