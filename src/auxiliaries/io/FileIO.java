package auxiliaries.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliaries.fileData.FileDataWrapper;

public class FileIO {
    private class StringConstants {
        public static final String ERROR_MSG    = "An error occurred. ";
        public static final String WHEN_READING = "(reading)";
        public static final String WHEN_WRITING = "(writing)";
        public static final String EMPTY_FILE   = "The file is empty.";

        private StringConstants() {
            throw new IllegalStateException("Utility class");
        }
    }

    public static FileDataWrapper read(String path) {
        /*
    public String content;
    public int arrivalsIndex;
    public int queuesIndex;
    public int networksIndex;
    public int rndnumbersIndex;
    public int numbersPerSeedIndex;
    public int seedsIndex;
         */
        FileDataWrapper returnValue;
        int index = 0;
        int[] indexes = {-1, -1, -1, -1, -1, -1};
        StringBuilder content = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "Not null";

            // Skip comment headers
            while ((line.charAt(0) != '!') && ((line) != null)) {
                line = bufferedReader.readLine();
            }

            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\\s", "");
                index++;
                content.append(line);
                content.append(System.lineSeparator());

                switch (line) {
                    case "arrivals:"          -> {indexes[0] = index;}
                    case "queues:"            -> {indexes[1] = index;}
                    case "network:"           -> {indexes[2] = index;}
                    case "rndnumbers:"        -> {indexes[3] = index;}
                    case "rndnumbersPerSeed:" -> {indexes[4] = index;}
                    case "seeds:"             -> {indexes[5] = index;}
                }
            }

            bufferedReader.close();

        } catch (IOException e) {
            ConsoleLogger.logError(StringConstants.ERROR_MSG + StringConstants.WHEN_READING, e);
            return null;
        }
        if ((content.length() == 0)) {
            ConsoleLogger.logGreen(StringConstants.EMPTY_FILE);
            return null;
        }

        returnValue = new FileDataWrapper(
                        content.toString(), indexes[0], indexes[1], indexes[2], 
                        indexes[3], indexes[4], indexes[5]);

        return returnValue;
    }

    public static void writeLine(String path, String line) {
        try {
            FileWriter fileWriter = new FileWriter(path, true);

            fileWriter.write(line);

            fileWriter.close();
            
        } catch (IOException e) {
            ConsoleLogger.logError(StringConstants.ERROR_MSG + StringConstants.WHEN_WRITING, e);
        }
    }
    
}
