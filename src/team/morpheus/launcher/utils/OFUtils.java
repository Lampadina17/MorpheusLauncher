package team.morpheus.launcher.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OFUtils {

    public static String getOptiFineEdition(String[] ofVers) {
        if (ofVers.length <= 2) {
            return "";
        } else {
            String ofEd = "";
            for (int i = 2; i < ofVers.length; ++i) {
                if (i > 2) {
                    ofEd = ofEd + "_";
                }
                ofEd = ofEd + ofVers[i];
            }
            return ofEd;
        }
    }

    public static String getOptiFineVersion(ZipFile zipFile) throws IOException {
        ZipEntry zipEntry = zipFile.getEntry("net/optifine/Config.class");
        if (zipEntry == null) zipEntry = zipFile.getEntry("notch/net/optifine/Config.class");
        if (zipEntry == null) zipEntry = zipFile.getEntry("Config.class");
        if (zipEntry == null) zipEntry = zipFile.getEntry("VersionThread.class");
        if (zipEntry == null) {
            throw new IOException("OptiFine version not found");
        } else {
            InputStream in = zipFile.getInputStream(zipEntry);
            String ofVer = getOptiFineVersion(in);
            in.close();
            return ofVer;
        }
    }

    public static String getOptiFineVersion(InputStream in) throws IOException {
        byte[] bytes = readAll(in);
        byte[] pattern = "OptiFine_".getBytes("ASCII");
        int pos = find(bytes, pattern);
        if (pos < 0) {
            return null;
        } else {
            int startPos = pos;
            for (pos = pos; pos < bytes.length; ++pos) {
                byte b = bytes[pos];
                if (b < 32 || b > 122) {
                    break;
                }
            }
            String ver = new String(bytes, startPos, pos - startPos, "ASCII");
            return ver;
        }
    }

    public static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            int len = is.read(buf);
            if (len < 0) {
                is.close();
                byte[] bytes = baos.toByteArray();
                return bytes;
            }
            baos.write(buf, 0, len);
        }
    }

    public static int find(byte[] buf, byte[] pattern) {
        return find(buf, 0, pattern);
    }

    public static int find(byte[] buf, int index, byte[] pattern) {
        for (int i = index; i < buf.length - pattern.length; ++i) {
            boolean found = true;
            for (int pos = 0; pos < pattern.length; ++pos) {
                if (pattern[pos] != buf[i + pos]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        return -1;
    }

    public static String[] tokenize(String str, String delim) {
        List list = new ArrayList();
        StringTokenizer tok = new StringTokenizer(str, delim);
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            list.add(token);
        }
        String[] tokens = (String[]) list.toArray(new String[list.size()]);
        return tokens;
    }

    public static void copyAll(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        while (true) {
            int len = is.read(buf);
            if (len < 0) {
                return;
            }
            os.write(buf, 0, len);
        }
    }

    public static void copyFile(File fileSrc, File fileDest) throws IOException {
        if (!fileSrc.getCanonicalPath().equals(fileDest.getCanonicalPath())) {
            FileInputStream fin = new FileInputStream(fileSrc);
            FileOutputStream fout = new FileOutputStream(fileDest);
            copyAll(fin, fout);
            fout.flush();
            fin.close();
            fout.close();
        }
    }

    public static String getLaunchwrapperVersion(InputStream fin) throws IOException {
        String fileLibs = "/launchwrapper-of.txt";
        if (fin == null) {
            throw new IOException("File not found: " + fileLibs);
        } else {
            String str = readText(fin, "ASCII");
            str = str.trim();
            if (!str.matches("[0-9\\.]+")) {
                throw new IOException("Invalid launchwrapper version: " + str);
            } else {
                return str;
            }
        }
    }

    public static String readText(InputStream in, String encoding) throws IOException {
        InputStreamReader inr = new InputStreamReader(in, encoding);
        BufferedReader br = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                br.close();
                inr.close();
                in.close();
                return sb.toString();
            }
            sb.append(line);
            sb.append("\n");
        }
    }
}
