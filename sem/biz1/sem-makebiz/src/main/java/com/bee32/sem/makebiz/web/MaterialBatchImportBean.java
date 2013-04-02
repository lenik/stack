package com.bee32.sem.makebiz.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.primefaces.event.FileUploadEvent;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategories;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.Units;

public class MaterialBatchImportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;
    static String regex = "(?=([\\d]))(?<=([A-Z\\[Φ]))";
    static Pattern emptyLinePattern = Pattern.compile("[0-9]");

    ChanceDto target;
    boolean upload = false;
    boolean analysis = false;
    boolean importedMaterial = false;
    boolean importedBom = false;

    String uploadedFileName;
    private File tempFile;

    Set<String> partsToImport;

    int materialSize; // 物料总数
    int compSize; // 构件总数
    int partSize; // 零件总数

    int countSavedMaterial = 0;
    int countExistedMaterial = 0;
    int countErrorMaterial = 0;

    int countSavedBom = 0;
    int countExistedBom = 0;
    int countErrorBom = 0;

    private Map<String, Material> cacheMaterial;

    // 导入物料
    public void importMaterial() {

        countSavedMaterial = 0;
        countExistedMaterial = 0;
        countErrorMaterial = 0;

        for (Material mate : cacheMaterial.values()) {

            String label = mate.getLabel();
            String modelSpec = mate.getModelSpec();
            IEntityAccessService<Material, Long> materialService = DATA(Material.class);
            Material toImport = materialService.getUnique(MakebizCriteria.existingMaterialCheck(label, modelSpec));
            if (null == toImport) {
                try {
                    UnitConv unitConv = mate.getUnitConv();
                    if (null != unitConv) {
                        DATA(UnitConv.class).saveOrUpdate(unitConv);
                    }
                    materialService.saveOrUpdate(mate);
                    countSavedMaterial++;
                } catch (Exception e) {
                    uiLogger.warn("导入物料：" + label + "，规格：" + modelSpec + "时发生未知错误");
                    countErrorMaterial++;
                }
            } else {
                countExistedMaterial++;
            }
            cacheMaterial.put(label + modelSpec, toImport);
        }

        importedMaterial = true;
        uiLogger.info("已有" + countExistedMaterial + "个物料已存在 <未导入>");
        uiLogger.info("本次导入" + countSavedMaterial + "个物料");

    }

    // 导入bom
    public void importBom() {

        countSavedBom = 0;
        countExistedBom = 0;
        countErrorBom = 0;

        // 由特定的格式转化成BOM
        List<Part> cacheParts = new ArrayList<Part>();
        if (null != partsToImport)
            for (String partString : partsToImport) {
                try {
                    int index = partString.indexOf("{");
                    String materialPattern = partString.substring(0, index);

                    String[] partItems = materialPattern.split(",");
                    String string = partItems[0];
                    Material material = cacheMaterial.get(string);
                    if (null == material)
                        System.out.println("mmmmmm is nulllllllll");

                    Part partLevel1 = new Part();
                    partLevel1.setTarget(material);

                    String children = partString.substring(index + 1);
                    String[] split = children.split("/");

                    List<PartItem> level1PartItems = new ArrayList<PartItem>();
                    for (String child : split) {

                        int cIndex = child.indexOf("{");
                        String cPart = child.substring(0, cIndex);
                        String cchildren = child.substring(cIndex + 1);
                        String[] strings = cPart.split(",");
                        String key = strings[0];
                        BigDecimal quantity = new BigDecimal(strings[1]);

                        Part partLevel2 = new Part();
                        Material targetPartLevel2 = cacheMaterial.get(key);
                        if (null == targetPartLevel2) {
                            System.out.println(">>>>> target of partLevel2 is null");
                            System.out.println(key);
                        }
                        partLevel2.setTarget(targetPartLevel2);

                        PartItem partItemLevel1 = new PartItem();
                        partItemLevel1.setPart(partLevel2);
                        partItemLevel1.setQuantity(quantity);
                        partItemLevel1.setParent(partLevel1);

                        List<PartItem> level2PartItems = new ArrayList<PartItem>();

                        if (cchildren.indexOf(";") <= 0) {
                            cchildren = cchildren.replace("}", "");
                            String[] cSplit = cchildren.split(",");
                            System.out.println(cchildren);
                            BigDecimal cquantity = new BigDecimal(cSplit[1]);
                            Material partItemLevel3 = cacheMaterial.get(cSplit[0]);
                            if (null == partItemLevel3)
                                System.out.println(">>>>> material of partItemLevel2 is null");

                            PartItem partItemLevel2 = new PartItem();
                            partItemLevel2.setMaterial(partItemLevel3);
                            partItemLevel2.setQuantity(cquantity);
                            partItemLevel2.setParent(partLevel2);

                            level2PartItems.add(partItemLevel2);
                        } else {
                            String[] split2 = cchildren.split(";");

                            for (String s : split2) {

                                int dIndex = s.indexOf("{");
                                String dPart = s.substring(0, dIndex);
                                String[] dSplit = dPart.split(",");
                                Material targetPartLevel3 = cacheMaterial.get(dSplit[0]);
                                if (null == targetPartLevel3)
                                    System.out.println(">>>>> target of partLevel3 is null");
                                BigDecimal dquantity = new BigDecimal(dSplit[1]);

                                Part partLevel3 = new Part();
                                partLevel3.setTarget(targetPartLevel3);

                                String dchildren = s.substring(dIndex + 1);
                                dchildren = dchildren.replace("}", "");

                                String[] dcSplit = dchildren.split(",");
                                Material partItemLevel3Material = cacheMaterial.get(dcSplit[0]);
                                if (null == partItemLevel3Material)
                                    System.out.println(">>>>> material of partItemLevel3 is null");

// System.out.println(">>>" + dchildren);

                                PartItem partItemLevel3 = new PartItem();
                                partItemLevel3.setQuantity(new BigDecimal(1));
                                partItemLevel3.setParent(partLevel3);
                                partItemLevel3.setMaterial(partItemLevel3Material);

                                List<PartItem> level3PartItems = new ArrayList<PartItem>();
                                level3PartItems.add(partItemLevel3);
                                partLevel3.setChildren(level3PartItems);

                                PartItem partItemLevel2 = new PartItem();
                                partItemLevel2.setPart(partLevel3);
                                partItemLevel2.setQuantity(dquantity);
                                partItemLevel2.setParent(partLevel2);

                                level2PartItems.add(partItemLevel2);
                                cacheParts.add(0, partLevel3);
                            }
                        }
                        partLevel2.setChildren(level2PartItems);
                        cacheParts.add(partLevel2);
                        level1PartItems.add(partItemLevel1);
                    }
                    partLevel1.setChildren(level1PartItems);
                    cacheParts.add(partLevel1);

                } catch (Exception e) {
                    uiLogger.warn("动态生成《BOM根节点》时发生未知错误" + e.getMessage());
                }
            }

        for (Part part : cacheParts) {
            Material material = part.getTarget();
            String label = material.getLabel();
            String module = material.getModelSpec();
            IEntityAccessService<Part, Integer> partService = DATA(Part.class);
            Part p = partService.getUnique(MakebizCriteria.existingPartCheck(label, module));
            if (null == p) {
// try {
                partService.saveOrUpdate(part);
                countSavedBom++;
// } catch (Exception e) {
// countErrorBom++;
// uiLogger.warn("导入BOM:" + label + "型号：" + module + "时发生未知错误");
// }
            } else
                countExistedBom++;
        }

        importedBom = true;
        uiLogger.info("本次BOM导入完毕");
    }

    public void doAnalysis() {

        Set<String> materials = new HashSet<String>(); // materials
        Set<String> parts = new HashSet<String>(); // parts
        Map<String, String> temp_parts = new HashMap<String, String>(); // tmp_parts
        List<String> prefixs = new ArrayList<String>();

        boolean tempStatus = true; // true is the default value, if the value is false, result is
// broken
        int countLine = 0;
        String globalPrefix = String.valueOf(target.getId());
        InputStream inputstream = null;
        BufferedReader bufferedReader = null;

        // 生成特定格式的物料和bom字符集
        try {
            inputstream = new FileInputStream(tempFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream, "gbk");
            bufferedReader = new BufferedReader(inputStreamReader);
            int partCount = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                countLine++;
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
                if (!emptyLinePattern.matcher(line).find())
                    continue;
                if (line.indexOf("VALUE") >= 0) {
                    tempStatus = false;
                    uiLogger.error("解析文件" + countLine + "行时发现错误，本行记录不会进入分析结果");
                    uiLogger.info("本次解析即将停止");
                    break;
                }

                String[] split = line.split(",");
                if (split[2].length() == 0) {// 是构件
                    String prefix = globalPrefix + "-" + split[0];
                    prefixs.add(prefix);
                    String compMaterial = prefix + "," + split[1] + ",g,c," + split[4] + "," + split[6];
                    materials.add(compMaterial);

                    // part string
                    String string_part = prefix + split[1] + ",c,{";
                    temp_parts.put(globalPrefix + "-" + split[0], string_part);

                } else {// 是零件
                    for (String pre : prefixs) {
                        Object[] convertedArray = convertMaterial(split, pre);
                        List<String> result = (List<String>) convertedArray[0];
                        String convertPart = (String) convertedArray[1];
                        partCount = partCount + (int) convertedArray[2];

                        materials.addAll(result);

                        String temp_part_string = temp_parts.get(pre);
                        temp_part_string = temp_part_string + convertPart + "/";
                        temp_parts.put(pre, temp_part_string);
                    }
                }
            }

            if (temp_parts.values().size() > 0)
                parts.addAll(temp_parts.values());

            bufferedReader.close();
            partSize = partCount;

        } catch (Exception e) {
            analysis = false;
            uiLogger.warn("文件格式不正确");
            uiLogger.warn("错误发生在" + countLine + "行,请检查您的文件是否符合规范");
        }

        // 由特定的格式转化成物料
        cacheMaterial = new HashMap<String, Material>();
        Units units = BEAN(Units.class);
        MaterialCategories category = BEAN(MaterialCategories.class);

        for (String material : materials) {
            System.out.println("MM" + material);
            String materialLabel = null;
            try {
                String[] split = material.split(",");
                materialLabel = split[0];
                if (null == materialLabel)
                    materialLabel = "unknowMaterial";
                String materialModule = split[1];
                String materialUnit = split[2];
                String materialCategory = split[3];
                Material mate = new Material();
                mate.setLabel(materialLabel);
                mate.setModelSpec(materialModule);

                switch (materialCategory) {
                case "c":
                    mate.setCategory(category.component);
                    break;
                case "p":
                    mate.setCategory(category.part);
                    break;
                case "r":
                    mate.setCategory(category.rawMaterial);
                    break;
                default:
                    mate.setCategory(category.rawMaterial);
                }

                Unit unit = null;
                switch (materialUnit) {
                case "g":
                    unit = units.PIECE;
                    break;
                case "k":
                    unit = units.KILOGRAM;
                    break;
                default:
                    unit = units.PIECE;
                }
                mate.setUnit(unit);
                System.out.println("Label >>>" + materialLabel);
                System.out.println("unit >>>" + unit);

                if (split.length > 4) {
                    mate.addUnitConv(units.KILOGRAM, Double.valueOf(split[4]));
                    mate.addUnitConv(units.SQUARE_METER, Double.valueOf(split[5]));
                }

                cacheMaterial.put(materialLabel + materialModule, mate);
            } catch (Exception e) {
                tempStatus = false;
                uiLogger.warn("生成物料时" + materialLabel + "时发生错误" + e.getMessage());
            }

        }

        partsToImport = parts;

        if (tempStatus) {
            materialSize = materials.size();
            compSize = parts.size();
            analysis = true;
            tempFile.delete();
            uiLogger.info("解析完毕");
        } else {
            materialSize = 0;
            compSize = 0;
            analysis = false;
            uiLogger.info("文件解析过程中发生错误, 请检查文件格式，解析完毕");
        }
    }

    public void handleFileUpload(FileUploadEvent event)
            throws IOException {
        uploadedFileName = event.getFile().getFileName();

        tempFile = File.createTempFile(uploadedFileName, "csv");
        try (InputStream in = event.getFile().getInputstream()) {
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] block = new byte[4096];
                int countBlock;
                while ((countBlock = in.read(block)) != -1)
                    out.write(block, 0, countBlock);
            }
        }

        upload = true;
        analysis = false;
        importedMaterial = false;
        importedBom = false;

        materialSize = 0;
        compSize = 0;
        partSize = 0;

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

        String part_material = prefix + "-" + str[0] + "," + str[1] + ",g,p," + str[4] + "," + str[6];
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

            part = partAssemblerH(prefix, type, partModule, str);
            partCount = 3;
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
            partCount = 1;
            break;
        case "T":
            type = "T型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
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

            part = partAsemblerBox(prefix, type, partModule, str);
            partCount = 3;
            break;
        case "I":
            type = "工字钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
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
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "ZZ":
            type = "Z型钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "L":
            type = "角铁";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "TUB":
        case "口":
            type = "方管";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "PIP":
        case "Φ":
            type = "钢管";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "D":
            type = "圆钢";
            materialString = materialAssemblerComm(str[7], type, partModule);
            materials.add(materialString);

            part = partAssemblerComm(prefix, type, partModule, str);
            partCount = 1;
            break;
        case "SD":
            type = "栓钉";
            materialString = materialAssemblerSD(str[7], type, partModule);
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
        // 物料名称，规格，单位
        return label + type + "," + module + "mm,k,r";
    }

    static String materialAssemblerComm(String prefix, String label, String module) {
        // 物料名称，规格，单位
        return prefix + label + "," + module + ",k,r";
    }

    static String materialAssemblerSD(String prefix, String label, String modle) {
        return prefix + label + "," + modle + ",g,r";
    }

    static String partAssemblerPL(String prefix, String type, String thick, String[] strings) {

        String partLabel = prefix + "-" + strings[0];
        String rawLabeL = strings[7] + type;
        return partLabel + strings[1] + "," + strings[3] + ",p,{" + rawLabeL + thick + "mm," + strings[4] + ",r}";
    }

    static String partAssemblerComm(String prefix, String type, String partModule, String[] strings) {
        String partLabel = prefix + "-" + strings[0];
        String rawLabel = strings[7] + type;
        return partLabel + strings[1] + "," + strings[3] + ",p,{" + rawLabel + partModule + "," + strings[4] + ",r}";
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

        String result = partLabel + strings[1] + "," + strings[3] + ",p,{" + flange + ";" + web + "}";

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

        String result = partLabel + strings[1] + "," + strings[3] + ",p,{" + flange + ";" + web + "}";

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

    public ChanceDto getTarget() {
        return target;
    }

    public void setTarget(ChanceDto target) {
        this.target = target;
    }

    public boolean isUpload() {
        return upload;
    }

    public boolean isAnalysis() {
        return analysis;
    }

    public boolean isImportedMaterial() {
        return importedMaterial;
    }

    public boolean isImportedBom() {
        return importedBom;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public int getMaterialSize() {
        return materialSize;
    }

    public int getCompSize() {
        return compSize;
    }

    public int getPartSize() {
        return partSize;
    }

    public int getCountSavedMaterial() {
        return countSavedMaterial;
    }

    public int getCountExistedMaterial() {
        return countExistedMaterial;
    }

    public int getCountErrorMaterial() {
        return countErrorMaterial;
    }

    public int getCountSavedBom() {
        return countSavedBom;
    }

    public int getCountExistedBom() {
        return countExistedBom;
    }

    public int getCountErrorBom() {
        return countErrorBom;
    }

}