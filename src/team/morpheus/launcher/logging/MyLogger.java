package team.morpheus.launcher.logging;

public class MyLogger {

    public Class clazz;

    public MyLogger(Class clazz) {
        this.clazz = clazz;
    }

    public void info(String message) {
        printLog(LogLevel.INFO, message);
    }

    public void warn(String message) {
        printLog(LogLevel.WARN, message);
    }

    public void error(String message) {
        printLog(LogLevel.ERROR, message);
    }

    public void debug(String message) {
        printLog(LogLevel.DEBUG, message);
    }

    public void printLog(LogLevel type, String message) {
        System.out.println(String.format("[%s]: %s", type.name(), message));
    }
}
