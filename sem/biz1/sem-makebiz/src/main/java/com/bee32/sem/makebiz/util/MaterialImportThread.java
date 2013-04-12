package com.bee32.sem.makebiz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.MixinnedWiredDataPartialContext;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.world.thing.UnitConv;

public class MaterialImportThread
        extends Thread {

    int countSaved = 0;
    int countExisted = 0;
    int countError = 0;

    List<ErrorInfoModel> errorList;

    AtomicInteger count;
    StringBuffer saving;
    boolean state;

    Map<String, Material> cacheMaterial;
    MixinnedWiredDataPartialContext data;

    public MaterialImportThread(AtomicInteger count, Map<String, Material> cache, MixinnedWiredDataPartialContext data) {
        this.count = count;
        this.cacheMaterial = cache;
        this.data = data;
    }

    @Override
    public void run() {

        errorList = new ArrayList<ErrorInfoModel>();

        for (Material mate : cacheMaterial.values()) {

            synchronized (count) {
                count.addAndGet(1);
            }

            String label = mate.getLabel();
            String modelSpec = mate.getModelSpec();
            IEntityAccessService<Material, Long> materialService = data.access(Material.class);
            Material toImport = materialService.getUnique(MakebizCriteria.existingMaterialCheck(label, modelSpec));
            if (null == toImport) {
                try {
                    UnitConv unitConv = mate.getUnitConv();
                    if (null != unitConv) {
                        data.access(UnitConv.class).saveOrUpdate(unitConv);
                    }
                    materialService.saveOrUpdate(mate);
                    String savingModel = "正在保存物料" + label + ",规格型号" + modelSpec;
                    saving = new StringBuffer(savingModel);
                    countSaved++;
                } catch (Exception e) {
                    String errorLabel = "物料" + label + "规格:" + modelSpec;
                    ErrorInfoModel errorModel = new ErrorInfoModel(count.get(), errorLabel, e.getMessage());
                    errorList.add(errorModel);
                    countError++;
                }
            } else {
                countExisted++;
                cacheMaterial.put(label + modelSpec, toImport);
            }

        }
        state = true;
    }

    public int getCountSavedMaterial() {
        return countSaved;
    }

    public int getCountExistedMaterial() {
        return countExisted;
    }

    public int getCountErrorMaterial() {
        return countError;
    }

    public StringBuffer getSaving() {
// if(null == saving)
// return new StringBuffer("");
        return saving;
    }

    public boolean isState() {
        return state;
    }

    public List<ErrorInfoModel> getErrorList() {
        if (null == errorList)
            return new ArrayList<ErrorInfoModel>();
        return errorList;
    }

}
