package team.morpheus.launcher.utils;

import team.morpheus.launcher.Main;
import team.morpheus.launcher.logging.MyLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String readJsonFromConnection(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream()));
        String line, output = "";
        while ((line = br.readLine()) != null) {
            output += (line + "\n");
        }
        br.close();
        return output;
    }

    public static HttpURLConnection makePostRequest(URL url, HashMap<String, String> params) throws IOException {
        String urlParameters = getDataString(params);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        return conn;
    }

    public static String makeGetRequest(URL url) throws IOException {
        HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
        httpurlconnection.setRequestMethod("GET");
        httpurlconnection.setRequestProperty("User-Agent", String.format("Morpheus Launcher (%s)", Main.build));
        httpurlconnection.setUseCaches(false);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
        StringBuilder stringbuilder = new StringBuilder();
        String s;

        while ((s = bufferedreader.readLine()) != null) {
            stringbuilder.append(s);
            stringbuilder.append('\r');
        }

        bufferedreader.close();
        return stringbuilder.toString();
    }

    private static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.displayName()));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.displayName()));
        }
        return result.toString();
    }

    public static void downloadAndUnzipNatives(URL source, File targetPath, MyLogger log) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(source.openStream());
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    String fileName = zipEntry.getName().replace("\\", "/");
                    String[] fileSplit = fileName.split("/");
                    File newFile = new File(targetPath.getPath(), fileSplit[Math.max(0, fileSplit.length - 1)]);

                    boolean isNativeFile = newFile.getPath().endsWith(".dll") || newFile.getPath().endsWith(".dylib") || newFile.getPath().endsWith(".jnilib") || newFile.getPath().endsWith(".so");
                    if (isNativeFile) {
                        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, length);
                        }
                        fileOutputStream.close();
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
