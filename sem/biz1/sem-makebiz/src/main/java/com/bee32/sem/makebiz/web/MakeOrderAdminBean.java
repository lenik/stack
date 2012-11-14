package com.bee32.sem.makebiz.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.free.UnexpectedException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;
    private static final Mime PDF = Mimes.application_pdf;
    private static final Mime CSV = Mimes.text_csv;

    public MakeOrderAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, MakeOrderDto.ITEM_ATTRIBUTES);
    }

    public void setChanceToApply(ChanceDto chance) {
        MakeOrderDto makeOrder = getOpenedObject();

        MakeOrder _checkSameChanceOrder = DATA(MakeOrder.class).getFirst(new Equals("chance.id", chance.getId()));
        if (_checkSameChanceOrder != null && !_checkSameChanceOrder.getId().equals(makeOrder.getId())) {
            uiLogger.error("此机会已经对应的定单!");
            return;
        }

        BEAN(MakebizService.class).chanceApplyToMakeOrder(chance, makeOrder);
    }

    public void exportToPdf() {
        MakeOrderDto makeOrder = this.getOpenedObject();
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(makeOrder.getItems());

        Map<String, Object> reportParams = new HashMap<String, Object>();
        reportParams.put("id", makeOrder.getId());
        reportParams.put("createDate", makeOrder.getCreatedDate());
        reportParams.put("owner", makeOrder.getOwnerDisplayName());
        reportParams.put("label", makeOrder.getLabel());
        reportParams.put("customer", makeOrder.getCustomer().getDisplayName());
        reportParams.put("description", makeOrder.getDescription());

        InputStream report = getClass().getClassLoader().getResourceAsStream("resources/3/15/6/3/order/report1.jrxml");
        doExport(report, reportParams, datasource, "order.pdf", PDF);
    }

    public void exportToPdfOuter() {
        MakeOrderDto makeOrder = this.getOpenedObject();
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(makeOrder.getItems());
        InputStream report = getClass().getClassLoader().getResourceAsStream("resources/3/15/6/3/order/report2.jrxml");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", makeOrder.getId());
        params.put("createDate", makeOrder.getCreatedDate());
        params.put("owner", makeOrder.getOwnerDisplayName());
        params.put("label", makeOrder.getLabel());
        params.put("customer", makeOrder.getCustomer().getDisplayName());
        params.put("description", makeOrder.getDescription());

        doExport(report, params, datasource, "order.pdf", PDF);

    }

    public void exportToCsv() {
        MakeOrderDto makeOrder = this.getOpenedObject();
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(makeOrder.getItems());
        InputStream report = getClass().getClassLoader().getResourceAsStream("resources/3/15/6/3/order/report1.jrxml");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", makeOrder.getId());
        params.put("createDate", makeOrder.getCreatedDate());
        params.put("owner", makeOrder.getOwnerDisplayName());
        params.put("label", makeOrder.getLabel());
        params.put("customer", makeOrder.getCustomer().getDisplayName());
        params.put("description", makeOrder.getDescription());

        doExport(report, params, datasource, "order.csv", CSV);
    }

    void doExport(InputStream template, Map<String, Object> params, JRDataSource datasource, String fileName,
            Mime contentType)
            throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        byte[] outputByteArray = null;
        JasperReport report;
        ServletOutputStream outputStream;
        HttpServletResponse response;

        response = (HttpServletResponse) context.getExternalContext().getResponse();

        /**
         * @see RFC 5987 2.3
         */
        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(fileName, "utf-8");
            System.out.println(encodedFilename);
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType.getContentType());
        response.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + encodedFilename + "." + contentType.getPreferredExtension());

        try {
            report = JasperCompileManager.compileReport(template);

            if (PDF.equals(contentType)) {
                outputByteArray = JasperRunManager.runReportToPdf(report, params, datasource);
            }
            if (CSV.equals(contentType)) {
                JasperPrint print = JasperFillManager.fillReport(report, params, datasource);
                JRCsvExporter exporter = new JRCsvExporter();
                ByteArrayOutputStream csvReport = new ByteArrayOutputStream();

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, csvReport);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
                exporter.exportReport();

                outputByteArray = csvReport.toByteArray();
            }

            outputStream = response.getOutputStream();
            outputStream.write(outputByteArray);
            outputStream.flush();
        } catch (JRException e) {
            uiLogger.error("无法生成报表", e);
        }
        context.responseComplete();
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    PartyDto searchParty;

    public PartyDto getSearchParty() {
        return searchParty;
    }

    public void setSearchParty(PartyDto searchParty) {
        this.searchParty = searchParty;
    }

    public void addCustomerRestriction() {
        if (searchParty != null) {
            setSearchFragment("customer", "客户为 " + searchParty.getDisplayName(), //
                    new Equals("customer.id", searchParty.getId()));
            searchParty = null;
        }
    }

    String searchPartyFuzzy;

    public String getSearchPartyFuzzy() {
        return searchPartyFuzzy;
    }

    public void setSearchPartyFuzzy(String searchPartyFuzzy) {
        this.searchPartyFuzzy = searchPartyFuzzy;
    }

    public void addCustomerRestrictionFuzzy() {
        if (!StringUtils.isEmpty(searchPartyFuzzy)) {
            setSearchFragment("customer", "客户名称包含 " + searchPartyFuzzy, //
                    MakebizCriteria.customerLike(searchPartyFuzzy));
            searchParty = null;
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MakeOrderItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", MakeOrderItemDto.class);

    public ListMBean<MakeOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
