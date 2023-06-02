package util;


import entity.MyMethod;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class ReadUtil {

    /*
    public static void main(String[] args) {
        try {
            InputStream f = new FileInputStream("D:/bishe/permission.txt");
            byte[] bt = new byte[128];
            int len = 0;
            StringBuffer sb = new StringBuffer();
            while ((len = f.read(bt)) != -1) {
                sb.append(new String(bt, 0, len));
            }
            String[] ss = sb.toString().split("\n");
            int size = (ss.length + 1) / 3;
            String[] permission = new String[size];
            String[] property = new String[size];
            String[] message = new String[size];
            for (int i = 0; i < size; i++) {
                permission[i] = ss[3 * i].split("\t")[1].strip();
                property[i] = ss[3 * i].split("\t")[0].strip();
                message[i] = ss[3 * i + 1].strip();
            }
            OutputStream opt = new FileOutputStream("D:/my.txt");
            OutputStreamWriter writer = new OutputStreamWriter(opt, "UTF-8");
            for (int i = 0; i < size; i++) {
                writer.append("permissionToMessage.put(\"" + permission[i] + "\", \"" + message[i] + "\");");
                writer.append("\r\n");
                writer.append("permissionToProperty.put(\"" + permission[i] + "\", \"" + property[i] + "\");");
                writer.append("\r\n");
                //System.out.println("permissionToMessage.put(\"" + permission[i] + "\", \"" + message[i] + "\");");
                //System.out.println("permissionToProperty.put(\"" + permission[i] + "\", \"" + property[i] + "\");");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public static void readXml(Set<String> set, String appName) {
        try {
            InputStream f = new FileInputStream("D:/bishe/apk/"+appName+"/AndroidManifest.xml");
            byte[] bt = new byte[1];
            while (f.read(bt) != -1) {
                char c = (char) bt[0];
                if (c == '<') {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < 6; i++) {
                        f.read(bt);
                        c = (char) bt[0];
                        sb.append(c);
                    }
                    if (sb.toString().equals("uses-p")) {
                        for (int i = 0; i < 24; i++) {
                            f.read(bt);
                        }
                        StringBuffer pm = new StringBuffer();
                        while (f.read(bt) != -1) {
                            c = (char) bt[0];
                            if (c == '"') {
                                break;
                            }
                            pm.append(c);
                        }
                        set.add(pm.toString().substring(pm.lastIndexOf(".") + 1));
                    } else if (sb.toString().equals("applic")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readDot(Map<MyMethod, Integer> map, String appName) {
        try {
            InputStream f = new FileInputStream("D:/bishe/dot/" + appName + ".dot");
            byte[] bt = new byte[1];
            int t = 0;
            while (f.read(bt) != -1) {
                char c = (char) bt[0];
                if (c == '-') {
                    f.read(bt);
                    c = (char) bt[0];
                    if (c != '>') {
                        continue;
                    }
                    f.read(bt);
                    f.read(bt);
                    StringBuffer sb = new StringBuffer();
                    while (true) {
                        f.read(bt);
                        char s = (char) bt[0];
                        if (s == '"') {
                            break;
                        }
                        sb.append(s);
                    }
                    String[] ss = sb.toString().split(" ");
                    String className = ss[0].substring(0, ss[0].length() - 1);
                    String methodName = ss[2].substring(0, ss[2].indexOf('('));
                    MyMethod myMethod = new MyMethod(className, methodName);
                    t++;
                    map.put(myMethod, map.getOrDefault(myMethod, 0) + 1);
                }
            }
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
