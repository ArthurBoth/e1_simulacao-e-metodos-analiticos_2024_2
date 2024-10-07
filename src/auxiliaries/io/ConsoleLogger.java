package auxiliaries.io;

public class ConsoleLogger {
    private class Colours {
        private static final String RESET  = "\u001B[0m";
        private static final String RED    = "\u001B[31m";
        private static final String GREEN  = "\u001B[32m";
        private static final String YELLOW = "\u001B[33m";
        private static final String BLUE   = "\u001B[34m";

        private Colours() {
            throw new IllegalStateException("Utility class");
        }
    }

    public static void logGreen(String message) {
        System.out.print(Colours.GREEN);
        System.out.print(message);
        System.out.println(Colours.RESET);
    }

    public static void logYellow(String message) {
        System.out.print(Colours.YELLOW);
        System.out.print(message);
        System.out.println(Colours.RESET);
    }

    public static void logBlue(String message) {
        System.out.print(Colours.BLUE);
        System.out.print(message);
        System.out.println(Colours.RESET);
    }

    public static void logWhite(String message) {
        System.out.print(Colours.RESET);
        System.out.println(message);
    }
    
    public static void logError(String message, Exception e) {
        System.out.print(Colours.RED);
        System.err.printf("ERROR: %s%n", message);

        System.out.print(Colours.YELLOW);
        e.printStackTrace();
        System.out.println(Colours.RESET);
    }

    public static void logError(String message) {
        System.out.print(Colours.RED);
        System.err.printf("ERROR: %s", message);
        System.out.println(Colours.RESET);
    }

    private ConsoleLogger() {
        throw new IllegalStateException("Utility class");
    }    
}
