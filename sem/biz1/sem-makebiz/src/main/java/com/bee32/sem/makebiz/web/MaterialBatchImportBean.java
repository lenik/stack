package com.bee32.sem.makebiz.web;

import java.io.BufferedReader;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategories;
import com.bee32.sem.world.thing.Units;

public class MaterialBatchImportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;
    static String regex = "(?=([\\d]))(?<=([A-Z\\[Φ]))";

    ChanceDto target;
    boolean upload = false;
    boolean analysis = false;
    boolean imported = false;
    String uploadedFileName;
    UploadedFile file;

    Set<String> materials_toImport;
    Set<String> parts_toImport;

    int materialSize;
    int saveMaterialCount;
    int existMaterialCount;
    int compSize;
    int partSize;
    int savePartCount;
    int existPartCount;

    public void doImport() {
        if (target == null) {
            uiLogger.warn("请选择对应销售机会");
            return;
        }

        Map<String, Material> cacheMaterial = new HashMap<String, Material>();
        List<Part> parts = new ArrayList<Part>();

        Units units = BEAN(Units.class);
        MaterialCategories category = BEAN(MaterialCategories.class);
        // import materials
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

        IEntityAccessService<Material, Long> materialService = DATA(Material.class);

        int countMaterial_save = 0; // 存如数据库的物料 个数
        int countMaterial_exsit = 0; // 已经存在的物料 个数
        for (Material mate : cacheMaterial.values()) {
            String label = mate.getLabel();
            String module = mate.getModelSpec();
            Material m = materialService.getUnique(MakebizCriteria.existingMaterialCheck(label, module));
            if (null == m) {
                materialService.save(mate);
                countMaterial_save++;
            } else
                cacheMaterial.put(label + module, m);
            countMaterial_exsit++;
        }

        uiLogger.info(countMaterial_exsit + "个物料已存在 <未导入>");
        uiLogger.info("本次导入" + countMaterial_save + "个物料");

        // 导入BOM
        if (null != parts_toImport && parts_toImport.size() > 0)
            for (String partString : parts_toImport) {
                int index = partString.indexOf("{");
                String materialPattern = partString.substring(0, index);

                String[] partItems = materialPattern.split(",");
                String string = partItems[0];
                System.out.println("part level1 material " + string);
                Material material = cacheMaterial.get(string);
                if (null == material)
                    System.out.println("mmmmmm is nulllllllll");

                Part partLevel1 = new Part();
                partLevel1.setTarget(material);
                partLevel1.setCategory(category.component);

                String children = partString.substring(index + 1);
                String[] split = children.split("/");

                List<PartItem> level1PartItems = new ArrayList<PartItem>();
                for (String child : split) {

                    int cIndex = child.indexOf("{");
                    String cPart = child.substring(0, cIndex);
                    System.out.println("========" + cPart + "========");
                    String cchildren = child.substring(cIndex + 1);
                    String[] strings = cPart.split(",");
                    String key = strings[0];
                    System.out.println("part  level2 material" + key);
                    BigDecimal quantity = new BigDecimal(strings[1]);

                    Part partLevel2 = new Part();
                    Material material2 = cacheMaterial.get(key);
                    if (null == material2) {
                        System.out.println("|||||||  material2 is null");
                        System.out.println(key);
                    }
                    partLevel2.setTarget(material2);
                    partLevel2.setCategory(category.rawMaterial);

                    PartItem partItemLevel1 = new PartItem();
                    partItemLevel1.setPart(partLevel2);
                    partItemLevel1.setQuantity(quantity);
                    partItemLevel1.setParent(partLevel1);

                    List<PartItem> level2PartItems = new ArrayList<PartItem>();

                    if (cchildren.indexOf(";") <= 0) {
                        cchildren = cchildren.replace("}", "");
                        String[] cSplit = cchildren.split(",");
                        BigDecimal cquantity = new BigDecimal(cSplit[1]);
                        Material cMaterial = cacheMaterial.get(cSplit[0]);
                        if (null == cMaterial)
                            System.out.println("cmaterial ----------- null");

                        PartItem partItemLevel2 = new PartItem();
                        partItemLevel2.setMaterial(cMaterial);
                        partItemLevel2.setQuantity(cquantity);
                        partItemLevel2.setParent(partLevel2);

                        level2PartItems.add(partItemLevel2);
                    } else {
                        String[] split2 = cchildren.split(";");

                        for (String s : split2) {

                            System.out.println("-----------" + s);
                            int dIndex = s.indexOf("{");
                            String dPart = s.substring(0, dIndex);
                            String[] dSplit = dPart.split(",");
                            Material dMaterial = cacheMaterial.get(dSplit[0]);
                            if (null == dMaterial)
                                System.out.println("dMaterial ======== null");
                            BigDecimal dquantity = new BigDecimal(dSplit[1]);

                            Part partLevel3 = new Part();
                            partLevel3.setTarget(dMaterial);
                            partLevel3.setCategory(category.rawMaterial);

                            System.out.println("PART LEVEL 3:::" + partLevel3.getTarget().getLabel());
                            String dchildren = s.substring(dIndex + 1);
                            dchildren = dchildren.replace("}", "");

                            String[] dcSplit = dchildren.split(",");
                            Material dcMaterial = cacheMaterial.get(dcSplit[0]);
                            if (null == dcMaterial)
                                System.out.println("dcccMaterial ======== null");

                            PartItem partItemLevel3 = new PartItem();
                            partItemLevel3.setQuantity(new BigDecimal(1));
                            partItemLevel3.setParent(partLevel3);
                            partItemLevel3.setMaterial(dcMaterial);

                            List<PartItem> level3PartItems = new ArrayList<PartItem>();
                            level3PartItems.add(partItemLevel3);
                            partLevel3.setChildren(level3PartItems);

                            PartItem partItemLevel2 = new PartItem();
                            partItemLevel2.setPart(partLevel3);
                            partItemLevel2.setQuantity(dquantity);
                            partItemLevel2.setParent(partLevel2);

                            level2PartItems.add(partItemLevel2);
                            parts.add(0, partLevel3);
                        }
                    }

                    partLevel2.setChildren(level2PartItems);
                    parts.add(partLevel2);
                    level1PartItems.add(partItemLevel1);
                }
                partLevel1.setChildren(level1PartItems);
                parts.add(partLevel1);
            }

        IEntityAccessService<Part, Integer> partService = DATA(Part.class);
        int ccccc = 0;
        int countPart_save = 0;
        int countPart_exsit = 0;
//        partService.saveAll(parts);
        for (Part part : parts) {
            System.out.println(">>>>>>>>>>>" + ccccc++);

            Material material = part.getTarget();
            String label = material.getLabel();
            String module = material.getModelSpec();
            System.out.println(label);
            System.out.println(module);
            Part p = partService.getUnique(MakebizCriteria.existingPartCheck(label, module));
            if (null == p) {
                partService.saveOrUpdate(part);
                countPart_save++;
            } else
                countPart_exsit++;

        }

        savePartCount = countPart_save;
        existPartCount = countPart_exsit;
        imported = true;
        uiLogger.info("本次物料导入完毕");
    }

    public void doAnalysis()
            throws IOException {

        String globalPrefix = String.valueOf(target.getId());
        InputStream inputstream = file.getInputstream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputstream, "gbk");

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Set<String> materials = new HashSet<String>(); // materials
        Set<String> parts = new HashSet<String>(); // parts
        Map<String, String> temp_parts = new HashMap<String, String>(); // tmp_parts

        List<String> prefixs = new ArrayList<String>();

        int partCount = 0;
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
                System.out.println(line);
                String compMaterial = prefix + "," + split[1] + "," + "g" + "," + split[4] + "," + split[6];
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

        bufferedReader.close();

        if (temp_parts.values().size() > 0)
            parts.addAll(temp_parts.values());

        materials_toImport = materials;
        parts_toImport = parts;
        materialSize = materials.size();
        compSize = parts.size();
        partSize = partCount;
        analysis = true;
        uiLogger.info("解析完毕");
    }

    public void handleFileUpload(FileUploadEvent event)
            throws IOException {
        file = event.getFile();
        uploadedFileName = file.getFileName();
        upload = true;
        analysis = false;
        imported = false;
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
            materialString = str[7] + type + "," + m + ",k";
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
            materialString = materialAssemblerComm(str[7], type, partModule);
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
        result.add(flangeLabel + "," + flangeModule + ",g");
        result.add(webLabel + "," + webModule + ",g");
        result.add(prefix + "-" + strings[0] + "," + strings[1] + ",g");

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
        result.add(prefix + "-" + strings[0] + "," + strings[1] + ",g");
        result.add(prefix + "-" + strings[0] + "-腹版,PL" + split[2] + "*" + k + ",k");
        result.add(prefix + "-" + strings[0] + "-翼缘,PL" + split[3] + "*" + split[1] + ",k");
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

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public int getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(int materialSize) {
        this.materialSize = materialSize;
    }

    public int getSaveMaterialCount() {
        return saveMaterialCount;
    }

    public void setSaveMaterialCount(int saveMaterialCount) {
        this.saveMaterialCount = saveMaterialCount;
    }

    public int getExistMaterialCount() {
        return existMaterialCount;
    }

    public void setExistMaterialCount(int existMaterialCount) {
        this.existMaterialCount = existMaterialCount;
    }

    public int getCompSize() {
        return compSize;
    }

    public void setCompSize(int compSize) {
        this.compSize = compSize;
    }

    public int getPartSize() {
        return partSize;
    }

    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    public int getSavePartCount() {
        return savePartCount;
    }

    public void setSavePartCount(int savePartCount) {
        this.savePartCount = savePartCount;
    }

    public int getExistPartCount() {
        return existPartCount;
    }

    public void setExistPartCount(int existPartCount) {
        this.existPartCount = existPartCount;
    }

}