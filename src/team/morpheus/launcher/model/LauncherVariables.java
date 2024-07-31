package team.morpheus.launcher.model;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class LauncherVariables {

    private String mcVersion;
    private boolean modded;
    private boolean classPath;
    private String gamePath;
    private boolean startOnFirstThread;
}