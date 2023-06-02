package core;

import constant.Permission;
import constant.Property;
import entity.MyMethod;
import util.ReadUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class AuditSystem {
    public static void main(String[] args) {
        Map<MyMethod, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        String appName = "pdd";
        int functions = ReadUtil.readDot(map, appName);
        ReadUtil.readXml(set, appName);
        Set<String> uses = new HashSet();
        try {
            OutputStream opt = new FileOutputStream("D:/bishe/report/" + appName + ".txt");
            OutputStreamWriter writer = new OutputStreamWriter(opt, "UTF-8");
            writer.append(appName + "安全性审计报告\r\n\r\n");
            writer.append("申请权限如下：\r\n");
            for (String permission : set) {
                if (Permission.permissionToProperty.containsKey(permission)) {
                    uses.add(Permission.permissionToProperty.get(permission));
                    writer.append(permission + ": " + Permission.permissionToMessage.get(permission) + "\r\n");
                }
            }
            writer.append("\r\n");
            Set<String> runs = new HashSet<>();
            int danger = 0;
            writer.append("获取信息的函数如下：\r\n");
            for (MyMethod m : map.keySet()) {
                String className = m.getClassName();
                String methodName = m.getMethodName().toLowerCase(Locale.ROOT);
                if (className.startsWith("android") && methodName.startsWith("get")) {
                    boolean flag = false;
                    for (String s : Property.keywordToProperty.keySet()) {
                        if (methodName.contains(s)) {
                            flag = true;
                            runs.add(Property.keywordToProperty.get(s));
                        }
                    }
                    if (flag) {
                        danger++;
                        writer.append(m + "\r\n");
                    }
                }
            }
            writer.append("危险函数占比：" + (danger * 100.0 / functions) + "%\r\n\r\n");
            writer.append("申请的权限数量：" + uses.size() + "\r\n");
            writer.append("运行时使用的权限数量：" + runs.size() + "\r\n");
            double percent = runs.size() * 1.0 / uses.size();
            if (percent >= 2) {
                writer.append("该应用风险极高，可能严重威胁系统安全！");
            } else if (percent > 1) {
                writer.append("该应用风险较高，注意小心使用。");
            } else if (percent >= 0.5) {
                writer.append("该应用行为正常。");
            } else {
                writer.append("该应用较安全，但是申请权限较多。");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
