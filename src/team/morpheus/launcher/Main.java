package team.morpheus.launcher;

import lombok.Getter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import team.morpheus.launcher.instance.Vanilla;
import team.morpheus.launcher.logging.MyLogger;
import team.morpheus.launcher.model.MojangSession;
import team.morpheus.launcher.utils.OSUtils;

public class Main {

    public static final String build = "(v2.2.0 | 28_09_2024)";
    private static final MyLogger log = new MyLogger(Main.class);

    @Getter
    private static Vanilla vanilla;
    @Getter
    private static MojangSession mojangSession;

    // NOTE: it's important to set -Djava.library.path by giving natives path, else game won't start!
    public static void main(String[] args) throws Exception {
        log.info(String.format("Morpheus Launcher %s | Lampadina_17 (by-nc-sa)", build));

        Option var2 = Option.builder("v").longOpt("version").argName("version").hasArg().desc("Minecraft version to be launched").build();
        Option var3 = Option.builder("n").longOpt("minecraftUsername").argName("username").hasArg().desc("Minecraft player username (required)").build();
        Option var4 = Option.builder("t").longOpt("minecraftToken").argName("token").hasArg().desc("Minecraft player token (required)").build();
        Option var5 = Option.builder("u").longOpt("minecraftUUID").argName("uuid").hasArg().desc("Minecraft player uuid (required)").build();
        Option var7 = Option.builder("c").longOpt("forceClassPath").desc("Forces the use of classpath instead of classloader").build();
        Option var8 = Option.builder("f").longOpt("gameFolder").argName("path").hasArg().desc("Uses the user given path instead of .minecraft").build();
        Option var9 = Option.builder("x").longOpt("startOnFirstThread").desc("Starts the game on first thread (macos)").build();

        Options options = new Options();
        options.addOption(var2).addOption(var3).addOption(var4).addOption(var5).addOption(var7).addOption(var8).addOption(var9);
        CommandLine cmd = (new DefaultParser()).parse(options, args);

        String gameFolder = cmd.hasOption(var8) ? cmd.getOptionValue(var8) : OSUtils.getWorkingDirectory("minecraft").getPath();

        if (cmd.getOptionValue(var3) != null && cmd.getOptionValue(var4) != null && cmd.getOptionValue(var5) != null) {
            /* Setup minecraft session */
            mojangSession = new MojangSession(cmd.getOptionValue(var4), cmd.getOptionValue(var3), cmd.getOptionValue(var5));

            /* Select operative mode */
            if (cmd.getOptionValue(var2) != null) {
                (vanilla = new Vanilla(cmd.getOptionValue(var2), cmd.hasOption(var7), cmd.hasOption(var9))).prepareLaunch(gameFolder);
            }
        } else {
            /* Print Help */
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        System.out.println("Usage: java -Djava.library.path=<nativespath> -jar Launcher.jar [options]");
        System.out.println("\nAvailable options:");
        for (Option option : options.getOptions()) { /* Shitty workaround due to shitty bug */
            System.out.println(String.format("-%s, -%-45s %s", option.getOpt(), (option.getLongOpt() + (option.hasArg() ? " <" + option.getArgName() + ">" : "")), option.getDescription()));
        }
        System.out.println("\nCheck wiki for more details: https://morpheus-launcher.gitbook.io/home/\n");
    }

    private static void copyToClipboard(String text) {
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(text);
        clipboard.setContents(selection, null);
    }

    /* ----- VANILLA ----- */
    public static final String getVersionsURL() { /* vanilla versions list */
        return "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    }

    public static final String getAssetsURL() { /* vanilla game assets */
        return "https://resources.download.minecraft.net";
    }

    public static final String getLibrariesURL() { /* vanilla game libraries */
        return "https://libraries.minecraft.net";
    }

    /* ----- Fabric ----- */
    public static final String getFabricVersionsURL() { /* fabric versions list */
        return "https://meta.fabricmc.net/v2/versions";
    }

    /* ----- Forge ----- */
    public static final String getForgeVersionsURL() { /* forge versions list */
        return "https://files.minecraftforge.net/net/minecraftforge/forge/maven-metadata.json";
    }

    public static final String getForgeInstallerURL() { /* forge installer base url */
        return "https://maven.minecraftforge.net/net/minecraftforge/forge/";
    }

    /* ----- Optifine ----- */
    public static final String getOptifineVersionsURL() {
        return "https://morpheuslauncher.it/downloads/optifine.json";
    }

    /* ----- Morpheus ----- */
    public static final String getMorpheusAPI() {
        return "https://morpheuslauncher.it";
    }
}
