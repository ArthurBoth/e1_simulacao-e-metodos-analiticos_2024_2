package auxiliaries.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

    public static String read(String path) {
        StringBuilder content = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            bufferedReader.close();

        } catch (IOException e) {
            ConsoleLogger.logError(StringConstants.ERROR_MSG + StringConstants.WHEN_READING, e);
            return null;
        }
        if ((content.length() == 0)) {
            ConsoleLogger.logGreen(StringConstants.EMPTY_FILE);
            return "";
        }

        return content.toString();
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
