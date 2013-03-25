package com.bee32.sem.makebiz.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategories;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.world.thing.Units;

public class MaterialBatchImportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;
    static String regex = "(?=([\\d]))(?<=([A-Z\\[Φ]))";

    ChanceDto target;
    boolean upload = false;
    boolean analysis = false;
    String uploadedFileName;
    UploadedFile file;

    Set<String> materials_toImport;
    Set<String> parts_toImport;

    public void doImport() {
        if (target == null) {
            uiLogger.warn("请选择对应销售机会");
            return;
        }

        Map<String, Material> cacheMaterial = new HashMap<String, Material>();
        List<Part> parts = new ArrayList<Part>();

        Units units = BEAN(Units.class);
        MaterialCategories category = BEAN(MaterialCategories.class);
        // TODO import materials
        if (null != materials_toImport && materials_toImport.size() > 0)
            for (String material : materials_toImport) {
                String[] split = material.split(",");
                String materialLabel = split[0];
                String materialModule = split[1];
                String materialUnit = split[2];
                Material mate = new Material();
                mate.setLabel(materialLabel);
                mate.setModelSpec(materialModule);
                switch (materialUnit) {
                case "g":
                    mate.setUnit(units.PIECE);
                case "k":
                    mate.setUnit(units.KILOGRAM);
                default:
                    mate.setUnit(units.PIECE);
                }
                mate.setCategory(category.rawMaterial);

                cacheMaterial.put(materialLabel + materialModule, mate);
            }

        // TODO 导入BOM
        if (null != parts_toImport && parts_toImport.size() > 0)
            for (String partString : parts_toImport) {
                int index = partString.indexOf("{");
                String part = partString.substring(0, index);

                String[] partItems = part.split(",");
                String string = partItems[0];
                Material m = cacheMaterial.get(string);

                Part parent = new Part();
                parent.setTarget(m);
                parent.setCategory(category.component);

                String children = partString.substring(index + 1);
                String[] split = children.split("/");

                List<PartItem> ch = new ArrayList<PartItem>();
                for (String child : split) {

                    int cIndex = child.indexOf("{");
                    String cPart = child.substring(0, cIndex);
                    String cchinred = child.substring(cIndex + 1);
                    String[] strings = cPart.split(",");
                    String key = strings[0];
                    BigDecimal quantity = new BigDecimal(strings[1]);

                    Part firstChild = new Part();
                    firstChild.setTarget(cacheMaterial.get(key));
                    MaterialCategory cate = category.rawMaterial;
// switch (strings[2]) {
// case "c":
// cate = MaterialCategories.component;
// case "p":
// cate = MaterialCategories.part;
// case "r":
// cate = MaterialCategories.rawMaterial;
// default:
// cate = MaterialCategories.rawMaterial;
// }
                    firstChild.setCategory(cate);

                    PartItem item = new PartItem();
                    item.setPart(firstChild);
                    item.setQuantity(quantity);
                    item.setParent(parent);

                    parts.add(firstChild);

                    ch.add(item);

                    if (cchinred.indexOf(";") <= 0) {
                        cchinred = cchinred.replace("}", "");
                        String[] cSplit = cchinred.split(",");
                        BigDecimal cquantity = new BigDecimal(cSplit[1]);

                        PartItem cItem = new PartItem();
                        Material cMaterial = cacheMaterial.get(cSplit[0]);
                        cItem.setMaterial(cMaterial);
                        cItem.setQuantity(cquantity);
                        cItem.setParent(firstChild);

                        firstChild.setChildren(Arrays.asList(cItem));
                        parts.add(firstChild);
                    } else {
                        List<PartItem> ch2 = new ArrayList<PartItem>();
                        String[] split2 = cchinred.split(";");
                        for (String s : split2) {
                            int dIndex = s.indexOf("{");
                            String dPart = s.substring(0, dIndex);
                            String[] dSplit = dPart.split(",");
                            Material dMaterial = cacheMaterial.get(dSplit[0]);
                            BigDecimal dquantity = new BigDecimal(dSplit[1]);

                            PartItem dItem1 = new PartItem();
                            Part dPart_ = new Part();
                            dPart_.setTarget(dMaterial);
                            dPart_.setCategory(category.rawMaterial);

                            dItem1.setQuantity(dquantity);
                            dItem1.setParent(firstChild);

                            PartItem dItem2 = new PartItem();
                            // TODO
                            String dchildren = s.substring(dIndex + 1);
                            dchildren = dchildren.replace("}", "");

                            String[] dcSplit = dchildren.split(",");
                            Material dcMaterial = cacheMaterial.get(dcSplit[0]);
                            BigDecimal dcquantity = new BigDecimal(dcSplit[1]);
                            dItem2.setQuantity(dcquantity);
                            dItem2.setMaterial(dcMaterial);
                            dItem2.setParent(dPart_);
                            dPart_.setChildren(Arrays.asList(dItem2));

                            dItem1.setPart(dPart_);
                            ch2.add(dItem1);
                            parts.add(dPart_);
                        }
                        firstChild.setChildren(ch2);
                    }
                }
                parent.setChildren(ch);
                parts.add(parent);
            }

        DATA(Material.class).saveAll(cacheMaterial.values());
        DATA(Part.class).saveAll(parts);
        uiLogger.info("导入物料成功");
    }

    public void doAnalysis()
            throws IOException {

        String globalPrefix = String.valueOf(target.getId());
        InputStream inputstream = file.getInputstream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputstream, "gbk");
        analysis = true;

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Set<String> materials = new HashSet<String>(); // materials
        Set<String> parts = new HashSet<String>(); // parts
        Map<String, String> temp_parts = new HashMap<String, String>(); // tmp_parts

        List<String> prefixs = new ArrayList<String>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.indexOf("材料表") >= 0)
                continue;
            if (line.indexOf("编号") >= 0) {
                prefixs.clear();
                parts.addAll(temp_parts.values());
                temp_parts.clear();
                continue;
            }
            if (line.length() <= 10)
                continue;

            String[] split = line.split(",");
            if (split[2].length() == 0) {// 是构件
                String prefix = globalPrefix + "-" + split[0];
                prefixs.add(prefix);
                String compMaterial = prefix + "," + split[1] + "," + "g" + "," + split[4] + "," + split[6];
                materials.add(compMaterial);

                // TODO part string
                String string_part = prefix + split[1] + ",c,{";
                temp_parts.put(globalPrefix + "-" + split[0], string_part);

            } else {// 是零件
                for (String pre : prefixs) {
                    Object[] convertedArray = convertMaterial(split, pre);
                    List<String> result = (List<String>) convertedArray[0];
                    String convertPart = (String) convertedArray[1];

                    materials.addAll(result);

                    String temp_part_string = temp_parts.get(pre);
                    temp_part_string = temp_part_string + convertPart + "/";
                    temp_parts.put(pre, temp_part_string);
                }
            }
        }

        bufferedReader.close();

        if (temp_parts.values().size() > 0)
            parts.addAll(temp_parts.values());

        materials_toImport = materials;
        parts_toImport = parts;

        for (String s : materials_toImport) {
            System.out.println(s);
        }
        for (String s : parts_toImport) {
            System.out.println(s);
        }
    }

    public void handleFileUpload(FileUploadEvent event)
            throws IOException {
        file = event.getFile();
        uploadedFileName = file.getFileName();
        upload = true;
        uiLogger.info("上传文件" + uploadedFileName + "成功");
        uiLogger.info("该文件导入后不会被保存");
    }

    static Object[] convertMaterial(String[] str, String prefix) {

        List<String> materials = new ArrayList<String>();
        String module = str[1];
        String[] split = module.split(regex);
        String pattern = split[0];
        String partModule = split[1];
        String[] items;
        String materialString = null;

        String part_material = prefix + "-" + str[0] + "," + str[1] + ",g," + str[3] + "," + str[4] + "," + str[6];
        materials.add(part_material);

        String part = null;

        String type = "钢板";
        switch (pattern) {
        case "PL":
            items = partModule.split("\\*");
            materialString = materialAssemblerPL(str[7], type, items[0]);
            materials.add(materialString);

            part = partAssemblerPL(prefix, type, items[0], str);
            break;
        case "H":
            items = partModule.split("\\*");
            List<String> hs = materialAssemblerH(prefix, str, partModule);
            String pl1 = materialAssemblerPL(str[7], type, items[2]);
            String pl2 = materialAssemblerPL(str[7], type, items[3]);
            materials.add(pl1);
            materials.add(pl2);
            materials.addAll(hs);

            part = partAssemblerH(prefix, type, partModule, str);
            break;
        case "HN":
        case "HM":
        case "HW":
        case "HB":
        case "HC":
            type = "成品H型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "T":
            type = "T型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "BOX":
            items = partModule.split("\\*");
            materialAssemblerBox(prefix, type, str);
            materials.add(materialAssemblerPL(str[7], type, items[2]));
            materials.add(materialAssemblerPL(str[7], type, items[3]));

            part = partAsemblerBox(prefix, type, partModule, str);
            break;
        case "I":
            type = "工字钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "[":
        case "C":
            type = "槽钢";
            String m;
            if (partModule.indexOf("#") >= 0)
                m = partModule.split("#")[0];
            else
                m = partModule;
            materialString = str[7] + type + "," + m + "号,k";
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "CC":
            type = "C型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "ZZ":
            type = "Z型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "L":
            type = "角铁";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "TUB":
        case "口":
            type = "方管";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "PIP":
        case "Φ":
            type = "钢管";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "D":
            type = "圆钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        case "SD":
            type = "栓钉";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);
            part = partAssemblerComm(prefix, type, partModule, str);
            break;
        default:
            System.out.println("----没有匹配的材料类型----");
        }

        Object[] result = { materials, part };
        return result;
    }

    static String materialAssemblerPL(String label, String type, String module) {
        // 物料名称，规格，单位
        return label + type + "," + module + "mm, k";
    }

    static String materialAssemblerComm(String prefix, String label, String module) {
        // 物料名称，规格，单位
        return prefix + label + "," + module + ",k";
    }

    static String partAssemblerPL(String prefix, String type, String thick, String[] strings) {

        String partLabel = prefix + "-" + strings[0];
        String rawLabeL = strings[7] + type;
        return partLabel + strings[1] + "," + strings[3] + ",p,{" + rawLabeL + thick + "mm," + strings[3] + ",r}";
    }

    static String partAssemblerComm(String prefix, String type, String partModule, String[] strings) {
        String partLabel = prefix + "-" + strings[0];
        String rawLabel = strings[7] + type;
        return partLabel + strings[1] + "," + strings[3] + ",p,{" + rawLabel + partModule + "," + strings[3] + ",r}";
    }

    static String partAssemblerH(String prefix, String type, String partModule, String[] strings) {

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

        String result = partLabel + "," + strings[3] + ",p,{" + flange + ";" + web + "}";

        return result;
    }

    static String partAsemblerBox(String prefix, String type, String partModule, String[] strings) {

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

        String result = partLabel + "," + strings[3] + ",p,{" + flange + ";" + web + "}";

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
        result.add(flangeLabel + "," + flangeModule + ",g");
        result.add(webLabel + "," + webModule + ",g");

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
        result.add(prefix + "-" + strings[0] + "-腹版" + "," + split[2] + "*" + k + ",k");
        result.add(prefix + "-" + strings[0] + "-翼缘" + "," + split[3] + "*" + split[1] + ",k");
        result.add(materialAssemblerPL(strings[7], type, split[2]));
        result.add(materialAssemblerPL(strings[7], type, split[3]));
        return result;
    }

    public ChanceDto getTarget() {
        return target;
    }

    public void setTarget(ChanceDto target) {
        this.target = target;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public boolean isAnalysis() {
        return analysis;
    }

    public void setAnalysis(boolean analysis) {
        this.analysis = analysis;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }
}