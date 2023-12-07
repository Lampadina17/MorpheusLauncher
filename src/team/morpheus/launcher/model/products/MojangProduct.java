package team.morpheus.launcher.model.products;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class MojangProduct {

    @SerializedName("latest")
    public Latest latest;

    @SerializedName("versions")
    public ArrayList<Version> versions;

    public class Latest {

        @SerializedName("release")
        public String release;

        @SerializedName("snapshot")
        public String snapshot;
    }

    public class Version {

        @SerializedName("id")
        public String id;

        @SerializedName("type")
        public String type;

        @SerializedName("url")
        public String url;

        @SerializedName("time")
        public Date time;

        @SerializedName("releaseTime")
        public Date releaseTime;
    }

    public class Game {

        @SerializedName("assetIndex")
        public AssetIndex assetIndex;

        @SerializedName("assets")
        public String assets;

        @SerializedName("complianceLevel")
        public int complianceLevel;

        @SerializedName("downloads")
        public Downloads downloads;

        @SerializedName("id")
        public String id;

        @SerializedName("inheritsFrom")
        public String inheritsFrom;

        @SerializedName("javaVersion")
        public JavaVersion javaVersion;

        @SerializedName("libraries")
        public ArrayList<Library> libraries;

        @SerializedName("mainClass")
        public String mainClass;

        @SerializedName("minimumLauncherVersion")
        public int minimumLauncherVersion;

        @SerializedName("releaseTime")
        public Date releaseTime;

        @SerializedName("time")
        public Date time;

        @SerializedName("type")
        public String type;

        @SerializedName("arguments")
        public Arguments arguments; // latest

        @SerializedName("minecraftArguments")
        public String minecraftArguments; // legacy

        public class Arguments {

            @SerializedName("game")
            public ArrayList<Object> game;

            @SerializedName("jvm")
            public ArrayList<Object> jvm;
        }

        public class Artifact {

            @SerializedName("path")
            public String path;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;
        }

        public class AssetIndex {

            @SerializedName("id")
            public String id;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("totalSize")
            public int totalSize;

            @SerializedName("url")
            public String url;
        }

        public class Client {

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;

            @SerializedName("argument")
            public String argument;

            @SerializedName("file")
            public File file;

            @SerializedName("type")
            public String type;
        }

        public class Downloads {

            @SerializedName("client")
            public Client client;

            @SerializedName("artifact")
            public Artifact artifact;

            @SerializedName("classifiers")
            public Classifiers classifiers;
        }

        public class File {

            @SerializedName("id")
            public String id;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;
        }

        public class JavaVersion {

            @SerializedName("component")
            public String component;

            @SerializedName("majorVersion")
            public int majorVersion;
        }

        public class Library {

            @SerializedName("downloads")
            public Downloads downloads;

            @SerializedName("name")
            public String name;

            @SerializedName("rules")
            public ArrayList<Rule> rules;

            @SerializedName("natives")
            public Natives natives;

            @SerializedName("url")
            public String url;
        }

        public class Rule {

            @SerializedName("action")
            public String action;

            @SerializedName("os")
            public Os os;
        }

        public class Os {

            @SerializedName("name")
            public String name;
        }

        // Legacy stuff
        public class Classifiers {

            @SerializedName("natives-linux")
            public NativesLinux natives_linux;

            @SerializedName("natives-osx")
            public NativesOsx natives_osx;

            @SerializedName("natives-macos")
            public NativesOsx natives_macos;

            @SerializedName("natives-windows")
            public NativesWindows natives_windows;

            @SerializedName("natives-windows-32")
            public NativesWindows natives_windows_32;

            @SerializedName("natives-windows-64")
            public NativesWindows natives_windows_64;
        }

        public class Natives {

            @SerializedName("linux")
            public String linux;

            @SerializedName("osx")
            public String osx;

            @SerializedName("windows")
            public String windows;
        }

        public class NativesLinux {

            @SerializedName("path")
            public String path;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;
        }

        public class NativesOsx {

            @SerializedName("path")
            public String path;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;
        }

        public class NativesWindows {

            @SerializedName("path")
            public String path;

            @SerializedName("sha1")
            public String sha1;

            @SerializedName("size")
            public int size;

            @SerializedName("url")
            public String url;
        }
    }
}
