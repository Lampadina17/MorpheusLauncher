package team.morpheus.launcher.instance;

import team.morpheus.launcher.Launcher;
import team.morpheus.launcher.logging.MyLogger;
import team.morpheus.launcher.model.LauncherVariables;


public class Vanilla {

    private static final MyLogger log = new MyLogger(Vanilla.class);
    private String mcVersion;
    private boolean useclasspath;

    public Vanilla(String mcVersion, boolean useclasspath) {
        this.mcVersion = mcVersion;
        this.useclasspath = useclasspath;
    }

    public void prepareLaunch(String gamePath) throws Exception {
        boolean modded = mcVersion.toLowerCase().contains("fabric")
                || mcVersion.toLowerCase().contains("forge")
                || mcVersion.toLowerCase().contains("quilt")
                || mcVersion.toLowerCase().contains("optifine")
                || mcVersion.toLowerCase().contains("liteloader");

        log.info(String.format("Launching %s instance (%s)", !modded ? "Vanilla" : "Modded", mcVersion));
        new Launcher(new LauncherVariables(mcVersion, modded, useclasspath, gamePath));
    }
}
