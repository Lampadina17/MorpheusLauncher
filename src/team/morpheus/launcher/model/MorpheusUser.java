package team.morpheus.launcher.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MorpheusUser {

    @SerializedName("data")
    public Data data;

    public class Data {

        @SerializedName("id")
        public String id;

        @SerializedName("username")
        public String username;

        @SerializedName("hwid")
        public String hwid;

        @SerializedName("products")
        public ArrayList<String> products;
    }
}