package logger;

/**
 * Created by Gaetan on 18/05/2017.
 * Logger
 */
public class Logger {

    // By default, display everything
    public static int logLevelToDisplay = 0;

    public static void log(Object message) {
        log(message.toString());
    }

    public static void log(String message) {
        log(message, 0);
    }

    public static void log(String message, int level) {
        if (level >= logLevelToDisplay) {
            println(message);
        }
    }

    public static void log(String message, int level, boolean newLine) {
        if (level >= logLevelToDisplay) {
            if (newLine) {
                println(message);
            } else {
                print(message);
            }
        }
    }

    private static void print(String message) {
        System.out.print(message);
    }

    private static void println(String message) {
        System.out.println(message);
    }

}
