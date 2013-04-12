package com.bee32.sem.makebiz.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.bee32.plover.orm.util.MixinnedWiredDataPartialContext;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.material.entity.Material;

public class BOMImportThread
        extends Thread {

    int countSaved = 0;
    int countExisted = 0;
    int countError = 0;
    boolean state = true;

    List<ErrorInfoModel> errorList;

    AtomicInteger count;

    Set<String> partsToImport = null;
    Map<String, Material> cacheMaterial;
    Chance chance;
    MixinnedWiredDataPartialContext data;

    public BOMImportThread(AtomicInteger count, MixinnedWiredDataPartialContext data,
            Map<String, Material> cacheMaterial, Set<String> partsToImport, Chance chance) {
        this.count = count;
        this.chance = chance;
        this.data = data;
        this.cacheMaterial = cacheMaterial;
        this.partsToImport = partsToImport;
    }

    @Override
    public void run() {
        countSaved = 0;
        countExisted = 0;
        countError = 0;

        errorList = new ArrayList<ErrorInfoModel>();

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
// uiLogger.warn("二次分析BOM时发生错误" + e.getMessage());
            }

        for (Part part : cacheParts) {

            synchronized (count) {
                count.addAndGet(1);
            }

            Material material = part.getTarget();
            String label = material.getLabel();
            String model = material.getModelSpec();
            Part p = data.access(Part.class).getUnique(MakebizCriteria.existingPartCheck(label, model));
            if (null == p) {
                try {
                    data.access(Part.class).saveOrUpdate(part);
                    countSaved++;
                } catch (Exception e) {
                    state = false;
                    String errorLabel = "物料:" + label + "规格:" + model;
                    ErrorInfoModel errorModel = new ErrorInfoModel(count.get(), errorLabel, e.getMessage());
                    errorList.add(errorModel);
                    countError++;
                }
            } else
                countExisted++;
        }
    }

    public int getCountSavedBom() {
        return countSaved;
    }

    public int getCountExistedBom() {
        return countExisted;
    }

    public int getCountErrorBom() {
        return countError;
    }

    public boolean isState() {
        return state;
    }

    public List<ErrorInfoModel> getErrorList() {
        return errorList;
    }

}
