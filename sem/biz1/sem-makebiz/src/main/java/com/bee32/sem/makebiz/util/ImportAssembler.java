package com.bee32.sem.makebiz.util;

import java.util.ArrayList;
import java.util.List;

public class ImportAssembler {

    static String regex = "(?=([\\d]))(?<=([A-Z\\[Φ]))";

    public static Object[] convert(String[] str, String prefix, boolean ispart) {

        List<String> materials = new ArrayList<String>();
        String module = str[1];
        String[] split = module.split(regex);
        String pattern = split[0];
        String partModule = split[1];
        String[] items;
        String materialString = null;

        String part_material = prefix + "-" + str[0] + "," + str[1] + "*" + str[2] + ",g,p," + str[4] + "," + str[6];
        materials.add(part_material);

        String part = null;

        int partCount = 0;
        String type = "钢板";
        switch (pattern) {
        case "PL":
            items = partModule.split("\\*");
            materialString = materialAssemblerPL(str[7], type, items[0]);
            materials.add(materialString);

            part = partAssemblerPL(prefix, type, items[0], str);
            partCount = 1;
            break;
        case "H":
            items = partModule.split("\\*");
            List<String> hs = materialAssemblerH(prefix, str, partModule);
            String pl1 = materialAssemblerPL(str[7], type, items[2]);
            String pl2 = materialAssemblerPL(str[7], type, items[3]);
            materials.add(pl1);
            materials.add(pl2);
            materials.addAll(hs);

            part = partAssemblerH(prefix, type, partModule, str, ispart);
            partCount = 3;
            break;
        case "HN":
        case "HM":
        case "HW":
        case "HB":
        case "HC":
            type = "成品H型钢";
            materialString = materialAssemblerComm(str[7], type, partModule, "H型钢");
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "T":
            type = "T型钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "BOX":
            items = partModule.split("\\*");
            List<String> box = materialAssemblerBox(prefix, type, str);

            materials.addAll(box);
            materials.add(materialAssemblerPL(str[7], type, items[2]));
            materials.add(materialAssemblerPL(str[7], type, items[3]));

            part = partAsemblerBox(prefix, type, partModule, str, ispart);
            partCount = 3;
            break;
        case "I":
            type = "工字钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "[":
        case "C":
            type = "槽钢";
            String m;
            if (partModule.indexOf("#") >= 0)
                m = partModule.split("#")[0];
            else
                m = partModule;
            m = m + "号";
            materialString = str[7] + type + "," + m + ",k,r";
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, m, str);
            partCount = 1;
            break;
        case "CC":
            type = "C型钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "ZZ":
            type = "Z型钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "L":
            type = "角钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "TUB":
        case "口":
            type = "方管";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "PIP":
        case "Φ":
            type = "钢管";
            materialString = materialAssemblerComm(str[7], type, partModule, "无缝管");
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "D":
            type = "圆钢";
            materialString = materialAssemblerComm(str[7], type, partModule, type);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "SD":
            type = "栓钉";
            materialString = materialAssemblerComm(str[7], type, partModule, "r");
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        default:
            System.out.println("----没有匹配的材料类型----");
        }

        Object[] result = { materials, part, partCount };
        return result;
    }

    static String materialAssemblerPL(String label, String type, String module) {
        String category = null;
        if (label.indexOf("235") >= 0)
            category = "Q235钢板";
        if (label.indexOf("345") >= 0)
            category = "Q345钢板";
        if (label.indexOf("GJ") >= 0)
            return label + "高强度钢板" + module + "mm,k,高强度钢板";
        // 物料名称，规格，单位
        else
            return label + type + "," + module + "mm,k," + category;
    }

    static String materialAssemblerComm(String prefix, String label, String module, String category) {
        // 物料名称，规格，单位
        return prefix + label + "," + module + ",k," + category;
    }

    static String materialAssemblerSD(String prefix, String label, String modle) {
        return prefix + label + "," + modle + ",g,r";
    }

    static String partAssemblerPL(String prefix, String type, String thick, String[] strings) {

        String partLabel = prefix + "-" + strings[0];
        String rawLabeL = strings[7] + type;
        String modelSpec = strings[1] + "*" + strings[2];
        return partLabel + modelSpec + "," + strings[3] + ",p,{" + rawLabeL + thick + "mm," + strings[4] + ",r}";
    }

    static String partAssemblerComm(String prefix, String type, String partModule, String[] strings) {
        String partLabel = prefix + "-" + strings[0];
        String rawLabel = strings[7] + type;
        String partModel = strings[1] + "*" + strings[2];
        return partLabel + partModel + "," + strings[3] + ",p,{" + rawLabel + partModule + "," + strings[4] + ",r}";
    }

    static String partAssemblerH(String prefix, String type, String partModule, String[] strings, boolean part) {

        String partLabel = prefix + "-" + strings[0];
        String flangeLabel = partLabel + "-翼缘";
        String websLabel = partLabel + "-腹版";
        String[] moduleItems = partModule.split("\\*");

        String flangeModule = "PL" + moduleItems[3] + "*" + moduleItems[1];
        String webModule;
        if (moduleItems[0].indexOf("~") >= 0) {
            String[] items = moduleItems[0].split("~");
            int i = Integer.parseInt(items[0]);
            int j = Integer.parseInt(items[1]);

            int k = Integer.parseInt(moduleItems[3]); // 翼缘厚
            int l = i - k * 2;
            int m = j - k * 2;
            webModule = "PL" + moduleItems[2] + "*" + l + "~" + m;
        } else {
            int i = Integer.parseInt(moduleItems[0]); // 截面高
            int k = Integer.parseInt(moduleItems[3]);
            int m = i - k * 2;
            webModule = "PL" + moduleItems[2] + "*" + m;
        }

        String flange = flangeLabel + flangeModule + ",2,p,{" + strings[7] + type + moduleItems[3] + "mm,1,r}";
        String web = websLabel + webModule + ",1,p,{" + strings[7] + type + moduleItems[2] + "mm,1,r}";

        String partModel = strings[1] + "*" + strings[2];
        String result;
        if (part)
            result = partLabel + partModel + "," + strings[3] + ",p,{" + flange + ";" + web + "}";
        else
            result = partLabel + partModel + "," + strings[3] + ",p,{" + flange + "/" + web + "}";

        return result;
    }

    static String partAsemblerBox(String prefix, String type, String partModule, String[] strings, boolean part) {

        String partLabel = prefix + "-" + strings[0];
        String flangeLabel = partLabel + "-翼缘";
        String websLabel = partLabel + "-腹版";
        String[] moduleItems = partModule.split("\\*");

        String flangeModule = "PL" + moduleItems[3] + "*" + moduleItems[1];
        int i = Integer.parseInt(moduleItems[0]); // 截面高
        int k = Integer.parseInt(moduleItems[3]);
        int m = i - k * 2;
        String webModule = "PL" + moduleItems[2] + "*" + m;

        String flange = flangeLabel + flangeModule + ",2,p,{" + strings[7] + type + moduleItems[3] + "mm,1,r}";
        String web = websLabel + webModule + ",2,p,{" + strings[7] + type + moduleItems[2] + "mm,1,r}";

        String partModel = strings[1] + "*" + strings[2];
        String result;
        if (part)
            result = partLabel + partModel + "," + strings[3] + ",p,{" + flange + ";" + web + "}";
        else
            result = partLabel + partModel + "," + strings[3] + ",p,{" + flange + "/" + web + "}";

        return result;
    }

    static List<String> materialAssemblerH(String prefix, String[] strings, String module) {
        List<String> result = new ArrayList<String>();
        String[] split = module.split("\\*");
        String first = split[0];

        String flangeLabel = prefix + "-" + strings[0] + "-翼缘";
        String webLabel = prefix + "-" + strings[0] + "-腹版";

        String flangeModule = "PL" + split[3] + "*" + split[1];

        String webModule;
        if (first.indexOf("~") >= 0) {
            String[] split2 = first.split("~");
            int i = Integer.parseInt(split2[0]);
            int j = Integer.parseInt(split2[1]);
            int k = Integer.parseInt(split[3]);
            int l = i - k * 2;
            int m = j - k * 2;
            webModule = "PL" + split[2] + "*" + l + "~" + m;
        } else {
            int i = Integer.parseInt(split[0]);
            int j = Integer.parseInt(split[3]);
            int k = i - j * 2;
            webModule = "PL" + split[2] + "*" + k;
        }
        result.add(flangeLabel + "," + flangeModule + ",g,p");
        result.add(webLabel + "," + webModule + ",g,p");
// result.add(prefix + "-" + strings[0] + "," + strings[1] + ",g");

        return result;
    }

    static List<String> materialAssemblerBox(String prefix, String type, String[] strings) {
        List<String> result = new ArrayList<String>();
        String[] pattern = strings[1].split(regex);
        String module = pattern[1];
        String[] split = module.split("\\*");
        int i = Integer.parseInt(split[0]);
        int j = Integer.parseInt(split[3]);
        int k = i - j * 2;
// result.add(prefix + "-" + strings[0] + "," + strings[1] + ",g");
        result.add(prefix + "-" + strings[0] + "-腹版,PL" + split[2] + "*" + k + ",g,p");
        result.add(prefix + "-" + strings[0] + "-翼缘,PL" + split[3] + "*" + split[1] + ",g,p");
        result.add(materialAssemblerPL(strings[7], type, split[2]));
        result.add(materialAssemblerPL(strings[7], type, split[3]));
        return result;
    }

}
