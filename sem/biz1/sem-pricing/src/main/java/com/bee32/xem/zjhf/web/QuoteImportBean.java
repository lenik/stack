package com.bee32.xem.zjhf.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.primefaces.event.FileUploadEvent;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialAttribute;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.util.MaterialCriteria;
import com.bee32.xem.zjhf.util.ModelCombiner;

public class QuoteImportBean extends EntityViewBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    boolean upload;
    boolean analysis;
    boolean imported;

    String uploadedFileName;
    private File tmpFile;

    private static AtomicInteger progres = new AtomicInteger(0);
    private int totalMaterialCount;
    private int errorCount;
    private int existCount;
    private int saveCount;

    private List<Material> materialList;

    public void doAnalysis() {
        InputStream inputstream = null;
        InputStreamReader streamReader = null;

        Map<String, MaterialCategory> categoryCache = new HashMap<String, MaterialCategory>();

        try {
            inputstream = new FileInputStream(tmpFile);
            streamReader = new InputStreamReader(inputstream, "GBK");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }

        if (null == streamReader) {
            uiLogger.info("inputstream cant be null");
            return;
        }

        materialList = new ArrayList<Material>();

        String line = null;
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                Material material = new Material();
                List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();

                String[] split = line.split(",");
                String type = split[0];

                // Set MaterialCategory
                MaterialCategory product = DATA(MaterialCategory.class).getUnique(MaterialCriteria.equalsLabel("?????????"));
                if (null == product) {
                    product = new MaterialCategory();
                    product.setLabel("?????????");
                    try {
                        DATA(MaterialCategory.class).save(product);
                    } catch (Exception exception) {
                        uiLogger.warn("?????????????????????????????????????????????" + exception.getMessage());
                    }
                }

                MaterialCategory category = categoryCache.get(type);
                if (null == category) {
                    category = DATA(MaterialCategory.class).getUnique(MaterialCriteria.equalsLabel(type));
                    if (null == category) {
                        category = new MaterialCategory();
                        category.setLabel(type);
                        if (null != product)
                            category.setParent(product);
                        DATA(MaterialCategory.class).save(category);
                    }
                    categoryCache.put(type, category);
                }
                material.setCategory(category);

                switch (type) {
                case "??????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[5]);

                    if (split[2].length() > 0) {
                        MaterialAttribute a1 = new MaterialAttribute();
                        a1.setMaterial(material);
                        a1.setName("??????");
                        a1.setValue(split[2]);
                        attributes.add(a1);
                    }

                    if (split[3].length() > 0) {
                        MaterialAttribute a2 = new MaterialAttribute();
                        a2.setMaterial(material);
                        a2.setName("????????????");
                        a2.setValue(split[3]);
                        attributes.add(a2);
                    }

                    if (split[4].length() > 0) {
                        MaterialAttribute a3 = new MaterialAttribute();
                        a3.setMaterial(material);
                        a3.setName("????????????");
                        a3.setValue(split[4]);
                        attributes.add(a3);
                    }

                    break;


                case "??????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[6]);

                    if (split[2].length() > 0) {
                        MaterialAttribute a1 = new MaterialAttribute();
                        a1.setMaterial(material);
                        a1.setName("??????");
                        a1.setValue(split[2]);
                        attributes.add(a1);
                    }

                    if (split[3].length() > 0) {
                        MaterialAttribute a2 = new MaterialAttribute();
                        a2.setMaterial(material);
                        a2.setName("??????");
                        a2.setValue(split[3]);
                        attributes.add(a2);
                    }

                    if (split[4].length() > 0) {
                        MaterialAttribute a3 = new MaterialAttribute();
                        a3.setMaterial(material);
                        a3.setName("??????");
                        a3.setValue(split[4]);
                        attributes.add(a3);
                    }

                    if (split[5].length() > 0) {
                        MaterialAttribute a4 = new MaterialAttribute();
                        a4.setMaterial(material);
                        a4.setName("????????????");
                        a4.setValue(split[5]);
                        attributes.add(a4);
                    }

                    break;


                case "??????????????????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[4]);

                    if (split[2].length() > 0) {
                        MaterialAttribute a1 = new MaterialAttribute();
                        a1.setMaterial(material);
                        a1.setName("????????????");
                        a1.setValue(split[2]);
                        attributes.add(a1);
                    }

                    if (split[3].length() > 0) {
                        MaterialAttribute a2 = new MaterialAttribute();
                        a2.setMaterial(material);
                        a2.setName("?????????");
                        a2.setValue(split[3]);
                        attributes.add(a2);
                    }

                    break;


                case "??????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[2] + "," + split[8]);   //??????????????????????????????

                    if (split[2].length() > 0) {
                        MaterialAttribute a1 = new MaterialAttribute();
                        a1.setMaterial(material);
                        a1.setName("??????");
                        a1.setValue(split[2]);
                        attributes.add(a1);
                    }

                    if (split[3].length() > 0) {
                        MaterialAttribute a2 = new MaterialAttribute();
                        a2.setMaterial(material);
                        a2.setName("??????????????????");
                        a2.setValue(split[3]);
                        attributes.add(a2);
                    }

                    if (split[4].length() > 0) {
                        MaterialAttribute a3 = new MaterialAttribute();
                        a3.setMaterial(material);
                        a3.setName("????????????");
                        a3.setValue(split[4]);
                        attributes.add(a3);
                    }

                    if (split[5].length() > 0) {
                        MaterialAttribute a4 = new MaterialAttribute();
                        a4.setMaterial(material);
                        a4.setName("????????????");
                        a4.setValue(split[5]);
                        attributes.add(a4);
                    }

                    if (split[6].length() > 0) {
                        MaterialAttribute a5 = new MaterialAttribute();
                        a5.setMaterial(material);
                        a5.setName("??????");
                        a5.setValue(split[6]);
                        attributes.add(a5);
                    }

                    if (split[7].length() > 0) {
                        MaterialAttribute a6 = new MaterialAttribute();
                        a6.setMaterial(material);
                        a6.setName("??????");
                        a6.setValue(split[7]);
                        attributes.add(a6);
                    }

                    if (split[9].length() > 0) {
                        MaterialAttribute a7 = new MaterialAttribute();
                        a7.setMaterial(material);
                        a7.setName("??????");
                        a7.setValue(split[9]);
                        attributes.add(a7);
                    }

                    if (split[10].length() > 0) {
                        MaterialAttribute a8 = new MaterialAttribute();
                        a8.setMaterial(material);
                        a8.setName("??????");
                        a8.setValue(split[10]);
                        attributes.add(a8);
                    }

                    break;

                case "??????":
                    material.setLabel(split[3]+split[1]);   //??????????????????????????????
                    material.setModelSpec(split[4]);

                    if (split[2].length() > 0) {
                        MaterialAttribute a1 = new MaterialAttribute();
                        a1.setMaterial(material);
                        a1.setName("??????");
                        a1.setValue(split[2]);
                        attributes.add(a1);
                    }

                    break;


                case "??????":
                    material.setLabel(split[2]+split[1]);
                    material.setModelSpec(split[3]);

                    break;


                case "????????????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[2]);

                    break;


                case "??????":
                    material.setLabel(split[1]);
                    material.setModelSpec(split[2]);

                    break;


                default:
                    break;
                }

                if (attributes.size() > 0)
                    material.setAttributes(attributes);

                materialList.add(material);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            uiLogger.warn("?????????????????????????????????" + exception.getMessage());
        }

        totalMaterialCount = materialList.size();
        analysis = true;
    }

    public void progresSaveCollection() {
        saveCount = 0;
        errorCount = 0;
        existCount = 0;

        progres = new AtomicInteger(0);
        for (Material material : materialList) {
            progres.addAndGet(1);
            Material unique = DATA(Material.class).getUnique(
                    MaterialCriteria.uniqueRestriction(material.getLabel(), material.getModelSpec(), material
                            .getCategory().getId()));
            if (null == unique) {
                saveCount++;
                try {
                    DATA(Material.class).save(material);
                } catch (Exception e) {
                    errorCount++;
                    uiLogger.warn("????????????" + material.getLabel() + "?????????????????????:" + e.getMessage());
                }
            } else
                existCount++;
        }
        imported = true;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        uploadedFileName = event.getFile().getFileName();

        tmpFile = File.createTempFile(uploadedFileName, "csv");
        try (InputStream in = event.getFile().getInputstream()) {
            try (FileOutputStream out = new FileOutputStream(tmpFile)) {
                byte[] block = new byte[4096];
                int countBlock;
                while ((countBlock = in.read(block)) != -1)
                    out.write(block, 0, countBlock);
            }
        }

        upload = true;
        analysis = false;
        imported = false;
    }

    public boolean isUpload() {
        return upload;
    }

    public boolean isAnalysis() {
        return analysis;
    }

    public boolean isImported() {
        return imported;
    }

    public int getTotalMaterialCount() {
        return totalMaterialCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getExistCount() {
        return existCount;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public int getImportProgres() {

        if (totalMaterialCount == 0)
            return 0;

        int x = progres.get() * 100 / totalMaterialCount;
        if (x > 100)
            return 100;
        return x;
    }
}