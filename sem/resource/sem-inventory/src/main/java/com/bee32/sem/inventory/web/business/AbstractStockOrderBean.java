package com.bee32.sem.inventory.web.business;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.inventory.web.StockQueryDialogBean;
import com.bee32.sem.material.dto.StockWarehouseDto;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.service.PeopleService;

@ForEntity(StockOrder.class)
public abstract class AbstractStockOrderBean
        extends ScrollEntityViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject;
    StockJobStepping stepping;

    int selectedWarehouseId = -1;
    OrgDto selectedOrg;
    OrgUnitDto selectedOrgUnit;

    StreamedContent pdfFile;

    @Inject
    protected StockDictsBean dicts;

    public AbstractStockOrderBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
        String s = ctx.view.getRequest().getParameter("subject");
        subject = s == null ? null : StockOrderSubject.forValue(s);

        stepping = new StockJobFriend();
        if (configJobStepping(stepping))
            addFriend("job", stepping);
    }

    class StockJobFriend
            extends StockJobStepping {

        private static final long serialVersionUID = 1L;

        @Override
        protected StockJobDto<?> createJob(StockOrderDto initiatorOrder)
                throws InstantiationException, IllegalAccessException {
            StockJobDto<?> stockJob = super.createJob(initiatorOrder);
            initStockJob(stockJob);
            return stockJob;
        }

    }

    /**
     * @return Whether stepping is used (ie. add the job to the friend list).
     */
    protected abstract boolean configJobStepping(StockJobStepping stepping);

    public StockJobStepping getJob() {
        return stepping;
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));
        if (selectedWarehouseId != -1 || true) // get nothing if warehouse isn't selected:
            elements.add(new Equals("warehouse.id", selectedWarehouseId));
    }

    protected void initStockJob(StockJobDto<?> stockJob) {
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        this.subject = subject;
    }

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
            refreshRowCount();
            selectRow(1);
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    public OrgDto getSelectedOrg() {
        return selectedOrg;
    }

    public void setSelectedOrg(OrgDto selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    public OrgUnitDto getSelectedOrgUnit() {
        return selectedOrgUnit;
    }

    public void setSelectedOrgUnit(OrgUnitDto selectedOrgUnit) {
        this.selectedOrgUnit = selectedOrgUnit;
    }

    public StockQueryOptions getStockQueryOptions() {
        StockQueryOptions options = StockQueryDialogBean.buildQueryOptions(selectedWarehouseId);
        return options;
    }

    public void setStockQueryItemToApply(StockOrderItemDto stockQueryItem) {
        if (stockQueryItem == null) {
            uiLogger.warn("??????????????????????????????");
            return;
        }
        StockOrderItemDto orderItem = itemsMBean.getOpenedObject();
        orderItem.setMaterial(stockQueryItem.getMaterial());
        orderItem.setBatchArray(stockQueryItem.getBatchArray());
        orderItem.setPrice(stockQueryItem.getPrice());
        orderItem.setExpirationDate(stockQueryItem.getExpirationDate());
        orderItem.setLocation(stockQueryItem.getLocation());
        // itemsMBean.apply();
    }

    public void exportToPdf() {
        StockOrderDto order = this.getOpenedObject();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(order.getItems());

        ClassLoader ccl = getClass().getClassLoader(); // Thread.currentThread().getContextClassLoader();
        InputStream reportStream = ccl.getResourceAsStream("resources/3/15/3/2/stock/report1.jrxml");

        try {
            JasperReport report = JasperCompileManager.compileReport(reportStream);
            Map<String, Object> reportParams = new HashMap<String, Object>();

            IPeopleService peopleService = BEAN(PeopleService.class);
            reportParams.put("title", peopleService.getSelfOrg().getDisplayName() + subject.getDisplayName());

            reportParams.put("createDate", order.getCreatedDate());
            reportParams.put("label", order.getLabel());
            reportParams.put("party", order.getOrg().getDisplayName());
            reportParams.put("orgUnit", order.getOrgUnit().getLabel());
            reportParams.put("description", order.getDescription());
            reportParams.put("owner", order.getOwnerDisplayName());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParams, beanCollectionDataSource);
            byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);

            InputStream stream = new ByteArrayInputStream(pdfByteArray);
            pdfFile = new DefaultStreamedContent(stream, "application/pdf", "stock.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getPdfFile() {
        return pdfFile;
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    protected Class<? extends StockOrderItemDto> getItemDtoClass() {
        return StockOrderItemDto.class;
    }

    final ListMBean<? extends StockOrderItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items",
            getItemDtoClass());

    public ListMBean<? extends StockOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected StockOrderDto create() {
        if (selectedWarehouseId == -1) {
            uiLogger.error("??????????????????${tr.inventory.warehouse}!");
            return null;
        }
        if (subject == null) {
            uiLogger.error("?????????????????????????????????");
            return null;
        }
        StockOrderDto order = (StockOrderDto) super.create();
        order.setWarehouse(getSelectedWarehouse());
        order.setSubject(subject);
        return order;
    }

    @Override
    public void showEditForm() {
        for (Object s : getSelection()) {
            StockOrderDto stockOrder = (StockOrderDto) s;
            if (stockOrder.getEntityFlags().isLocked()) {
                uiLogger.error("?????????????????????????????????!");
                return;
            }
        }
        super.showEditForm();
    }

}
