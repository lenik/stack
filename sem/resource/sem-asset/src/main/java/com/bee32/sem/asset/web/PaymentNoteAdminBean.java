package com.bee32.sem.asset.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.free.UnexpectedException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.asset.service.AssetService;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(FundFlow.class)
public class PaymentNoteAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private static final Mime PDF = Mimes.application_pdf;

    PersonDto whoPay;

    public PaymentNoteAdminBean() {
        super(PaymentNote.class, PaymentNoteDto.class, 0);
    }

    public PersonDto getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(PersonDto whoPay) {
        this.whoPay = whoPay;
    }

    /**
     * 显示付款对话框时，把付款人copy到临时变量中
     */
    public void showPayDialog() {
        PaymentNoteDto note = getOpenedObject();
        whoPay = note.getWhoPay();
    }

    public void pay() {
        if (whoPay.isNull()) {
            uiLogger.error("没有付款人");
            return;
        }

        PersonDto whoPayOld = null;
        PaymentNoteDto note = getOpenedObject();
        try {
            whoPayOld = note.getWhoPay(); // 保存原来的付款人
            note.setWhoPay(whoPay);
            BEAN(AssetService.class).pay(note);
            uiLogger.info("付款成功");
        } catch (Exception e) {
            note.setWhoPay(whoPayOld); // 如果出错，则还原旧的付款人
            uiLogger.error("付款错误", e);
        }
    }

    public void exportToPdf()
            throws FxrQueryException, IOException {
        PaymentNoteDto note = this.getOpenedObject();
        List<String> dummyData = new ArrayList<String>();
        dummyData.add("");

        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(dummyData);
        InputStream report = getClass().getClassLoader().getResourceAsStream(
                "resources/3/15/3/3/paymentNote/report1.jrxml");

        Map<String, Object> reportParams = new HashMap<String, Object>();
        reportParams.put("id", note.getId());
        reportParams.put("createDate", note.getCreatedDate());
        reportParams.put("beginTime", note.getBeginTime());
        reportParams.put("operator", note.getOperator().getDisplayName());
        reportParams.put("party", note.getParty().getDisplayName());
        reportParams.put("chance", note.getChance().getSubject());
        reportParams.put("value", note.getNativeValue());
        reportParams.put("whoPay", note.getWhoPay().getDisplayName());
        reportParams.put("description", note.getDescription());
        reportParams.put("text", note.getText());
        doExport(report, reportParams, datasource, "paymentNote", PDF);
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
            outputByteArray = JasperRunManager.runReportToPdf(report, params, datasource);
            outputStream = response.getOutputStream();
            outputStream.write(outputByteArray);
            outputStream.flush();
        } catch (JRException e) {
            uiLogger.error("无法生成报表", e);
        }
        context.responseComplete();
    }

}
