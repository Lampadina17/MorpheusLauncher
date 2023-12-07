package team.morpheus.launcher.model.products;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MorpheusProduct {

    @SerializedName("data")
    public Data data;

    public class Data {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("version")
        public String version;

        @SerializedName("gameversion")
        public String gameversion;

        @SerializedName("sha256")
        public String sha256;

        @SerializedName("mainclass")
        public String mainclass;

        @SerializedName("libraries")
        public ArrayList<Library> libraries;
    }

    public class Library {

        @SerializedName("name")
        public String name;

        @SerializedName("sha256")
        public String sha256;
    }
}
