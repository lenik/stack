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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.primefaces.event.FileUploadEvent;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.makebiz.util.ImportAssembler;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategories;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.util.MaterialCriteria;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.Units;

public class MaterialBatchImportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;
    static Pattern emptyLinePattern = Pattern.compile("[0-9]");

    ChanceDto target;
    boolean upload = false;
    boolean analysis = false;
    boolean importedMaterial = false;
    boolean importedBom = false;
    boolean importedOrder = false;

    String uploadedFileName;
    private File tempFile;

    Set<String> partsToImport = null;

    int materialSize; // 物料总数
    int compSize; // 构件总数
    int partSize; // 零件总数

    int countSavedMaterial = 0;
    int countExistedMaterial = 0;
    int countErrorMaterial = 0;

    int countSavedBom = 0;
    int countExistedBom = 0;
    int countErrorBom = 0;

    private Map<String, Material> cacheMaterial = null;

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
                cacheMaterial.put(label + modelSpec, toImport);
            }
        }

        importedMaterial = true;
        uiLogger.info("已有" + countExistedMaterial + "个物料已存在 <未导入>");
        uiLogger.info("本次导入" + countSavedMaterial + "个物料");

    }

    // 导入bom
    public void importBom() {

        boolean state = true;
        countSavedBom = 0;
        countExistedBom = 0;
        countErrorBom = 0;

        Chance chance = target.unmarshal();

        // 由特定的格式转化成BOM
        List<Part> cacheParts = new ArrayList<Part>();
        if (null != partsToImport)
            try {
                for (String partString : partsToImport) {
                    int index = partString.indexOf("{");
                    String materialPattern = partString.substring(0, index);
                    String[] partItems = materialPattern.split(",");
                    String string = partItems[0];

                    Material material = cacheMaterial.get(string);
                    if (null == material)
                        throw new NullPointerException("target of partLevel1 is null");

                    Part partLevel1 = new Part();
                    partLevel1.setTarget(material);
                    partLevel1.setChance(chance);

                    List<PartItem> level1PartItems = new ArrayList<PartItem>();
                    String children = partString.substring(index + 1);

                    if (children.indexOf("/") <= 0) {
                        PartItem item = new PartItem();
                        children = children.replace("/", "").replace("}", "");
                        String[] first = children.split(",");
                        Material m = cacheMaterial.get(first[0]);
                        if (null == m)
                            throw new NullPointerException("material of partItemLevel1 is null");
                        double quantity = Double.parseDouble(first[1]);
                        item.setParent(partLevel1);
                        item.setMaterial(m);
                        item.setQuantity(quantity);
                        level1PartItems.add(item);
                    } else {

                        String[] split = children.split("/");

                        for (String child : split) {

                            int cIndex = child.indexOf("{");
                            String cPart = child.substring(0, cIndex);
                            String cchildren = child.substring(cIndex + 1);
                            String[] strings = cPart.split(",");
                            String key = strings[0];
                            BigDecimal quantity = new BigDecimal(strings[1]);

                            Part partLevel2 = new Part();
                            Material targetPartLevel2 = cacheMaterial.get(key);
                            if (null == targetPartLevel2)
                                throw new NullPointerException("target of partLevel2 is null");
                            partLevel2.setTarget(targetPartLevel2);

                            PartItem partItemLevel1 = new PartItem();
                            partItemLevel1.setPart(partLevel2);
                            partItemLevel1.setQuantity(quantity);
                            partItemLevel1.setParent(partLevel1);

                            List<PartItem> level2PartItems = new ArrayList<PartItem>();

                            if (cchildren.indexOf(";") <= 0) {
                                cchildren = cchildren.replace("}", "");
                                String[] cSplit = cchildren.split(",");
                                double quantityString = Double.parseDouble(cSplit[1]);
                                BigDecimal cquantity = new BigDecimal(quantityString);
                                Material partItemLevel3 = cacheMaterial.get(cSplit[0]);
                                if (null == partItemLevel3)
                                    throw new NullPointerException("material of partItemLevel2 is null");

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
                                        throw new NullPointerException("target of partLevel3 is null");
                                    BigDecimal dquantity = new BigDecimal(dSplit[1]);

                                    Part partLevel3 = new Part();
                                    partLevel3.setTarget(targetPartLevel3);

                                    String dchildren = s.substring(dIndex + 1);
                                    dchildren = dchildren.replace("}", "");

                                    String[] dcSplit = dchildren.split(",");
                                    Material partItemLevel3Material = cacheMaterial.get(dcSplit[0]);
                                    if (null == partItemLevel3Material)
                                        throw new NullPointerException("material of partItemLevel3 is null");

                                    BigDecimal itemquantity = new BigDecimal(dcSplit[1]);
                                    PartItem partItemLevel3 = new PartItem();
                                    partItemLevel3.setQuantity(itemquantity);
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
                    }
                    partLevel1.setChildren(level1PartItems);
                    cacheParts.add(partLevel1);
                }
            } catch (Exception e) {
                state = false;
                uiLogger.warn("二次分析BOM时发生错误" + e.getMessage());
            }
        if (state) {
            for (Part part : cacheParts) {
                Material material = part.getTarget();
                String label = material.getLabel();
                String module = material.getModelSpec();
                IEntityAccessService<Part, Integer> partService = DATA(Part.class);
                Part p = partService.getUnique(MakebizCriteria.existingPartCheck(label, module));
                if (null == p) {
                    try {
                        partService.saveOrUpdate(part);
                        countSavedBom++;
                    } catch (Exception e) {
                        countErrorBom++;
                        uiLogger.warn("导入BOM时发生错误" + e.getMessage());
                    }
                } else
                    countExistedBom++;
            }
            importedBom = true;
            uiLogger.info("本次BOM导入完毕");
        } else {
            importedBom = false;
            uiLogger.warn("发生错误，导入未开始执行");
        }
    }

    public void importMakeOrder() {
        MakeOrder order = new MakeOrder();

        Chance chance = target.unmarshal();
        target = DTOs.marshal(ChanceDto.class, ChanceDto.PARTIES, chance);
        ChancePartyDto chanceParty = target.getParties().get(0);
        Party party = chanceParty.getParty().unmarshal();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        List<MakeOrderItem> items = new ArrayList<MakeOrderItem>();

        if (null != partsToImport)
            for (String partString : partsToImport) {
                int index = partString.indexOf("{");
                String materialPattern = partString.substring(0, index);
                String[] partItems = materialPattern.split(",");
                String string = partItems[0];

                Material material = cacheMaterial.get(string);
                if (null == material)
                    throw new NullPointerException("material of MakeOrderItem is null");

                MakeOrderItem orderItem = DATA(MakeOrderItem.class).getUnique(
                        MakebizCriteria.exsitingMakeOrderItemCheck(material.getLabel(), material.getModelSpec()));
                if (null == orderItem) {
                    orderItem = new MakeOrderItem();
                    BigDecimal quantity = new BigDecimal(partItems[2]);
                    orderItem.setQuantity(quantity);
                    orderItem.setMaterial(material);
                    orderItem.setParent(order);
                    items.add(orderItem);
                }
            }
        order.setItems(items);
        order.setLabel(year + "年" + month + "月" + day + "日生产订单(导入)");

        order.setCustomer(party);
        order.setChance(chance);

        if (order.getItems().size() > 0) {
            try {
                DATA(MakeOrder.class).saveOrUpdate(order);
                importedOrder = true;
            } catch (Exception e) {
                importedOrder = false;
                uiLogger.warn("导入生产订单时发生错误，导入失败。");
            }
        } else {
            uiLogger.info("没有需要导入的生产订单。");
        }

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
                    String material = prefix + "," + split[1] + ",g,c," + split[4] + "," + split[6];
                    materials.add(material);

                    // part string
                    String string_part = prefix + split[1] + ",c," + split[3] + "{";
                    temp_parts.put(globalPrefix + "-" + split[0], string_part);

                } else {// 是零件

                    if (prefixs.size() > 0)
                        for (String pre : prefixs) {
                            Object[] convertedArray = ImportAssembler.convert(split, pre, true);
                            List<String> resultMaterials = (List<String>) convertedArray[0];
                            String resultPart = (String) convertedArray[1];
                            partCount = partCount + (int) convertedArray[2];

                            materials.addAll(resultMaterials);

                            String temp_part_string = temp_parts.get(pre);
                            temp_part_string = temp_part_string + resultPart + "/";
                            temp_parts.put(pre, temp_part_string);
                        }
                    else { // 散件 ==>> 构件
                           // TODO
                        Object[] convert = ImportAssembler.convert(split, globalPrefix, false);
                        List<String> resultMaterials = (List<String>) convert[0];
                        String resultPart = (String) convert[1];

                        materials.addAll(resultMaterials);
                        parts.add(resultPart);
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

                MaterialCategory cate = null;

                switch (materialCategory) {
                case "c":
                    cate = category.component;
                    break;
                case "p":
                    cate = category.part;
                    break;
                case "r":
                    cate = category.rawMaterial;
                    break;
                default:
                    cate = DATA(MaterialCategory.class).getUnique(MaterialCriteria.equalsLabel(materialCategory));
                    if (null == cate)
                        cate = category.rawMaterial;
                }

                mate.setCategory(cate);

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
        importedOrder = false;

        materialSize = 0;
        compSize = 0;
        partSize = 0;
        cacheMaterial = null;
        partsToImport = null;

        uiLogger.info("上传文件" + uploadedFileName + "成功");
        uiLogger.info("该文件导入后不会被保存");
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

    public boolean isImportedOrder() {
        return importedOrder;
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