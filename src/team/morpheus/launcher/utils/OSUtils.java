package team.morpheus.launcher.utils;

import java.io.File;

public class OSUtils {

    public static native String getHWID();

    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    public static File getWorkingDirectory(String applicationName) {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform().ordinal()) {
            case 0:
            case 1:
                workingDirectory = new File(userHome, '.' + applicationName + '/');
                break;
            case 2:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, '.' + applicationName + '/');
                }
                break;
            case 3:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, applicationName + '/');
        }

        if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        } else {
            return workingDirectory;
        }
    }

    public static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.windows;
        } else if (osName.contains("mac")) {
            return OS.macos;
        } else if (osName.contains("solaris")) {
            return OS.solaris;
        } else if (osName.contains("sunos")) {
            return OS.solaris;
        } else if (osName.contains("linux")) {
            return OS.linux;
        } else {
            return osName.contains("unix") ? OS.linux : OS.unknown;
        }
    }

    public enum OS {
        linux, solaris, windows, macos, unknown
    }
}