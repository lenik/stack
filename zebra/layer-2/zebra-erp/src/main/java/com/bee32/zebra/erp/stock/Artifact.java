package com.bee32.zebra.erp.stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.CoEntity;

public class Artifact
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    public static final int N_SKU_CODE = 30;
    public static final int N_BAR_CODE = 30;
    public static final int N_UOM_PROPERTY = 20;
    public static final int N_SPEC = 100;
    public static final int N_COLOR = 20;
    public static final int N_CAUTION = 100;

    private int id;
    private ArtifactCategory category;
    private String skuCode;
    private String barCode;

    private UOM uom = UOMs.PIECE;
    private String uomProperty = "数量";
    private Map<UOM, Double> convMap = new HashMap<UOM, Double>();
    private int decimalDigits = 2;

    private String spec;
    private String color;
    private String caution;

    private final Dim3d bbox = new Dim3d();
    private double weight;
    private double netWeight;

    private SupplyMethod supplyMethod = SupplyMethod.BUY;
    private int supplyDelay; // in days
    private List<PlaceOption> placeOpts = new ArrayList<PlaceOption>();

    private List<FabStepDef> fabProc = new ArrayList<FabStepDef>();

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OfGroup(OaGroups.Classification.class)
    public ArtifactCategory getCategory() {
        return category;
    }

    public void setCategory(ArtifactCategory category) {
        this.category = category;
    }

    public void setCategoryId(int categoryId) {
        (this.category = new ArtifactCategory()).setId(categoryId);
    }

    @OfGroup(OaGroups.Identity.class)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * @placeholder 输入条形码
     */
    @OfGroup({ OaGroups.Identity.class, OaGroups.Packaging.class })
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @OfGroup(OaGroups.Packaging.class)
    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }

    @OfGroup(OaGroups.Packaging.class)
    public void setUomId(int uomId) {
        (this.uom = new UOM()).setId(uomId);
    }

    /**
     * @placeholder 输入衡量单位的用途属性，如"长度"、"重量"
     */
    @OfGroup(OaGroups.Packaging.class)
    public String getUomProperty() {
        return uomProperty;
    }

    public void setUomProperty(String uomProperty) {
        this.uomProperty = uomProperty;
    }

    @OfGroup(OaGroups.Packaging.class)
    public Map<UOM, Double> getConvMap() {
        return convMap;
    }

    public void setConvMap(Map<UOM, Double> convMap) {
        if (convMap == null)
            throw new NullPointerException("convMap");
        this.convMap = convMap;
    }

    @OfGroup(OaGroups.Setting.class)
    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    /**
     * @placeholder 输入规格/型号
     */
    @OfGroup(OaGroups.Identity.class)
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * @placeholder 输入颜色名称
     */
    @OfGroup(OaGroups.ColorManagement.class)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @placeholder 输入警告信息，如危险品、易碎、易爆炸等
     */
    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    @OfGroup(OaGroups.Packaging.class)
    public Dim3d getBbox() {
        return bbox;
    }

    @OfGroup(OaGroups.Packaging.class)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @OfGroup(OaGroups.Packaging.class)
    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    @OfGroup(OaGroups.Schedule.class)
    public SupplyMethod getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(SupplyMethod supplyMethod) {
        if (supplyMethod == null)
            throw new NullPointerException("supplyMethod");
        this.supplyMethod = supplyMethod;
    }

    @OfGroup(OaGroups.Schedule.class)
    public int getSupplyDelay() {
        return supplyDelay;
    }

    public void setSupplyDelay(int supplyDelay) {
        this.supplyDelay = supplyDelay;
    }

    @OfGroup(OaGroups.Setting.class)
    public List<PlaceOption> getPlaceOpts() {
        return placeOpts;
    }

    public void setPlaceOpts(List<PlaceOption> placeOpts) {
        if (placeOpts == null)
            throw new NullPointerException("placeOpts");
        this.placeOpts = placeOpts;
    }

    public List<FabStepDef> getFabProc() {
        return fabProc;
    }

    public void setFabProc(List<FabStepDef> fabProc) {
        this.fabProc = fabProc;
    }

    public static class PlaceOption {

        Artifact artifact;
        Place place;
        boolean locked;
        String status;

        double reservation;
        int checkPeriod;
        long checkExpires;

        public Artifact getArtifact() {
            return artifact;
        }

        public void setArtifact(Artifact artifact) {
            this.artifact = artifact;
        }

        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        /**
         * @label 永久库位
         */
        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        /**
         * @label 推荐库位状态
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * 安全库存
         * 
         * @label 库存安全保留量
         */
        @OfGroup(OaGroups.Schedule.class)
        public double getReservation() {
            return reservation;
        }

        public void setReservation(double reservation) {
            this.reservation = reservation;
        }

        /**
         * @label 盘点周期
         */
        @OfGroup(OaGroups.Schedule.class)
        public int getCheckPeriod() {
            return checkPeriod;
        }

        public void setCheckPeriod(int checkPeriod) {
            this.checkPeriod = checkPeriod;
        }

        /**
         * @label 下次盘点时间
         */
        @OfGroup(OaGroups.Schedule.class)
        public long getCheckExpires() {
            return checkExpires;
        }

        public void setCheckExpires(long checkExpires) {
            this.checkExpires = checkExpires;
        }

    }

    public static class SalePrice {

        Date date;
        double price;

    }

}
