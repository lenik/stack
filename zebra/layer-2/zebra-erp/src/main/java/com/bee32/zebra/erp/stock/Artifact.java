package com.bee32.zebra.erp.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.erp.stock.Warehouse.Cell;
import com.tinylily.model.base.CoEntity;
import com.tinylily.model.base.CoNode;

/**
 * 定义生产过程或供销中使用的制品（成品或半成品）。<br>
 * 也叫SKU（库存保管单元）比如一件衬衫，按颜色、尺码可以定义一系列物料。
 * 
 * @label 物料
 * 
 * @rel artcat: 管理物料分类
 * @rel uom: 管理计量单位
 * @rel purchase-request: 采购入库
 * @rel stock-check: 库存盘点
 * 
 * @see <a href="http://www.bensino.com/news/sInstitute/2/2012/1212/40641.html">SKU不是越多越好</a>
 * @see <a href="http://www.360doc.com/content/08/0109/14/25392_958214.shtml">物料编码规划</a>
 * @see <a href="http://baike.baidu.com/view/395868.htm">什么是物料清单（BOM）</a>
 * @see <a href="http://book.ebusinessreview.cn/bookpartinfo-71335.html">几种不同的 BOM 拓扑结构</a>
 */
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
    private Category category;
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
    private List<WarehouseOption> wOpts = new ArrayList<WarehouseOption>();
    private List<PreferredCell> preferredCells = new ArrayList<PreferredCell>();

    private List<FabStepDef> fabProc = new ArrayList<FabStepDef>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryId(int categoryId) {
        (this.category = new Category()).setId(categoryId);
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }

    public void setUomId(int uomId) {
        (this.uom = new UOM()).setId(uomId);
    }

    public String getUomProperty() {
        return uomProperty;
    }

    public void setUomProperty(String uomProperty) {
        this.uomProperty = uomProperty;
    }

    public Map<UOM, Double> getConvMap() {
        return convMap;
    }

    public void setConvMap(Map<UOM, Double> convMap) {
        if (convMap == null)
            throw new NullPointerException("convMap");
        this.convMap = convMap;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public int getSupplyDelay() {
        return supplyDelay;
    }

    public void setSupplyDelay(int supplyDelay) {
        this.supplyDelay = supplyDelay;
    }

    public Dim3d getBbox() {
        return bbox;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public SupplyMethod getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(SupplyMethod supplyMethod) {
        if (supplyMethod == null)
            throw new NullPointerException("supplyMethod");
        this.supplyMethod = supplyMethod;
    }

    public List<WarehouseOption> getWarehouseOpts() {
        return wOpts;
    }

    public void setWarehouseOpts(List<WarehouseOption> wOpts) {
        if (wOpts == null)
            throw new NullPointerException("wOpts");
        this.wOpts = wOpts;
    }

    public List<PreferredCell> getPreferredCells() {
        return preferredCells;
    }

    public void setPreferredCells(List<PreferredCell> preferredCells) {
        if (preferredCells == null)
            throw new NullPointerException("preferredCells");
        this.preferredCells = preferredCells;
    }

    public List<FabStepDef> getFabProc() {
        return fabProc;
    }

    public void setFabProc(List<FabStepDef> fabProc) {
        this.fabProc = fabProc;
    }

    public static class Category
            extends CoNode<Category> {

        private static final long serialVersionUID = 1L;

        public static final int N_SKU_CODE_FORMAT = 100;
        public static final int N_BAR_CODE_FORMAT = 100;

        private int id;
        private String skuCodeFormat;
        private String barCodeFormat;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSkuCodeFormat() {
            return skuCodeFormat;
        }

        public void setSkuCodeFormat(String skuCodeFormat) {
            this.skuCodeFormat = skuCodeFormat;
        }

        public String getBarCodeFormat() {
            return barCodeFormat;
        }

        public void setBarCodeFormat(String barCodeFormat) {
            this.barCodeFormat = barCodeFormat;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

    }

    public static class WarehouseOption {

        Artifact artifact;
        Warehouse warehouse;
        double reservation;
        int checkPeriod;
        long checkExpires;

        public Artifact getArtifact() {
            return artifact;
        }

        public void setArtifact(Artifact artifact) {
            this.artifact = artifact;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        /** aka. safety stock. */
        public double getReservation() {
            return reservation;
        }

        public void setReservation(double reservation) {
            this.reservation = reservation;
        }

        public int getCheckPeriod() {
            return checkPeriod;
        }

        public void setCheckPeriod(int checkPeriod) {
            this.checkPeriod = checkPeriod;
        }

        public long getCheckExpires() {
            return checkExpires;
        }

        public void setCheckExpires(long checkExpires) {
            this.checkExpires = checkExpires;
        }

    }

    public static class PreferredCell
            implements Serializable {

        private static final long serialVersionUID = 1L;

        public static final int N_STATUS = 100;

        private Artifact artifact;
        private Cell cell;
        private boolean locked;
        private String status;

        public Artifact getArtifact() {
            return artifact;
        }

        public void setArtifact(Artifact artifact) {
            this.artifact = artifact;
        }

        public Cell getCell() {
            return cell;
        }

        public void setCell(Cell cell) {
            this.cell = cell;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public static class SalePrice {

        Date date;
        double price;

    }

}
