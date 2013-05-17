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
import com.bee32.sem.asset.dto.CreditNoteDto;
import com.bee32.sem.asset.entity.CreditNote;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(FundFlow.class)
public class CreditNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    private static final Mime PDF = Mimes.application_pdf;

    public CreditNoteAdminBean() {
        super(CreditNote.class, CreditNoteDto.class, 0);
    }

    public void exportToPdf()
            throws FxrQueryException, IOException {
        CreditNoteDto note = this.getOpenedObject();
        List<String> dummyData = new ArrayList<String>();
        dummyData.add("");

        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(dummyData);
        InputStream report = getClass().getClassLoader().getResourceAsStream(
                "resources/3/15/3/3/creditNote/report1.jrxml");

        Map<String, Object> reportParams = new HashMap<String, Object>();
        reportParams.put("id", note.getId());
        reportParams.put("createDate", note.getCreatedDate());
        reportParams.put("beginTime", note.getBeginTime());
        reportParams.put("operator", note.getOperator().getDisplayName());
        reportParams.put("party", note.getParty().getDisplayName());
        reportParams.put("chance", note.getChance().getSubject());
        reportParams.put("value", note.getNativeValue());
        reportParams.put("description", note.getDescription());
        reportParams.put("text", note.getText());
        doExport(report, reportParams, datasource, "creditNote", PDF);
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
