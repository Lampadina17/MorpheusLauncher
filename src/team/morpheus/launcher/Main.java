package team.morpheus.launcher;

import lombok.Getter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import team.morpheus.launcher.instance.Morpheus;
import team.morpheus.launcher.instance.Vanilla;
import team.morpheus.launcher.logging.MyLogger;
import team.morpheus.launcher.model.MojangSession;
import team.morpheus.launcher.model.MorpheusSession;
import team.morpheus.launcher.utils.OSUtils;

import javax.swing.*;
import java.io.File;

public class Main {

    public static final String build = "(Ver 1.0) - 07_12_2023";
    private static final MyLogger log = new MyLogger(Main.class);
    @Getter
    private static Morpheus morpheus;
    @Getter
    private static Vanilla vanilla;
    @Getter
    private static MojangSession mojangSession;

    // NOTE: it's important to set -Djava.library.path by giving natives path, else game won't start!
    public static void main(String[] args) throws Exception {
        log.info(String.format("Morpheus Launcher %s | Lampadina_17 (cc by-nc-sa) 2023", build));

        /* Morpheus product's related arguments, most people can ignore this */
        Option var0 = Option.builder("a").longOpt("accessToken").argName("token").hasArg().desc("").build();
        Option var1 = Option.builder("p").longOpt("productID").argName("productid").hasArg().desc("").build();
        Option var6 = Option.builder("h").longOpt("hwid").argName("showpopup").hasArg().optionalArg(true).desc("Prints hardware-id into a message dialog").build();

        /* Vanilla related arguments */
        Option var2 = Option.builder("v").longOpt("version").argName("version").hasArg().desc("Minecraft version to be launched").build();
        Option var3 = Option.builder("n").longOpt("minecraftUsername").argName("username").hasArg().desc("Minecraft player username (required)").build();
        Option var4 = Option.builder("t").longOpt("minecraftToken").argName("token").hasArg().desc("Minecraft player token (required)").build();
        Option var5 = Option.builder("u").longOpt("minecraftUUID").argName("uuid").hasArg().desc("Minecraft player uuid (required)").build();

        Options options = new Options();
        options.addOption(var0).addOption(var1).addOption(var2).addOption(var3).addOption(var4).addOption(var5).addOption(var6);
        CommandLine cmd = (new DefaultParser()).parse(options, args);

        if (cmd.getOptionValue(var3) != null && cmd.getOptionValue(var4) != null && cmd.getOptionValue(var5) != null) {
            /* Setup minecraft session */
            mojangSession = new MojangSession(cmd.getOptionValue(var4), cmd.getOptionValue(var3), cmd.getOptionValue(var5));

            /* Select operative mode */
            if (cmd.getOptionValue(var2) != null) {
                (vanilla = new Vanilla(cmd.getOptionValue(var2))).prepareLaunch();
            } else if (cmd.getOptionValue(var0) != null && cmd.getOptionValue(var1) != null) {
                loadNativeLib();
                (morpheus = new Morpheus(new MorpheusSession(cmd.getOptionValue(var0), cmd.getOptionValue(var1), OSUtils.getHWID()))).prepareLaunch();
            }
        } else {
            if (cmd.hasOption(var6)) {
                loadNativeLib();
                /* Print HWID */
                String hwid = OSUtils.getHWID();
                log.info(String.format("HWID: %s", hwid));
                String showPopupValue = cmd.getOptionValue(var6);
                if (showPopupValue != null && showPopupValue.equalsIgnoreCase("true")) {
                    copyToClipboard(hwid);
                    JOptionPane.showMessageDialog(null, "Hwid has successfully copied to your clipboard!");
                }
                System.exit(0);
            } else {
                /* Print Help */
                printHelp(options);
            }
        }
    }

    private static void printHelp(Options options) {
        System.out.println("Usage: java -Djava.library.path=<nativespath> -jar Launcher.jar [options]");
        System.out.println("\nAvailable options:");
        for (Option option : options.getOptions()) { /* Shitty workaround due to shitty bug */
            System.out.println(String.format("-%s, -%-45s %s", option.getOpt(), (option.getLongOpt() + (option.hasArg() ? " <" + option.getArgName() + ">" : "")), option.getDescription()));
        }
        System.out.println("\nNotes:\n* While premium players can give to 'minecraftToken' argument their Minecraft session token, sp players will instead have to give 0 as the value.\n");
    }

    private static void copyToClipboard(String text) {
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(text);
        clipboard.setContents(selection, null);
    }

    /* Loads morpheusguard dynamic library only when needed, for most users this is useless */
    public static void loadNativeLib() {
        String nativelibpath = OSUtils.getWorkingDirectory("morpheus").getPath() + File.separator;
        switch (OSUtils.getPlatform()) {
            case windows:
                nativelibpath += "morpheus_guard.dll";
                break;
            case macos:
                nativelibpath += "libmorpheus_guard.dylib";
                break;
            case linux:
                nativelibpath += "libmorpheus_guard.so";
                break;
        }
        System.load(nativelibpath);
    }

    /* Vanilla version list */
    public static final String getVersionsURL() {
        return "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    }

    /* Vanilla assets */
    public static final String getAssetsURL() {
        return "https://resources.download.minecraft.net";
    }

    public static final String getLibrariesURL() {
        return "https://libraries.minecraft.net";
    }

    /* Fabric */
    public static final String getFabricVersionsURL() {
        return "https://meta.fabricmc.net/v2/versions";
    }

    /* Morpheus */
    public static final String getMorpheusAPI() {
        return "https://morpheuslauncher.it";
    }
}
