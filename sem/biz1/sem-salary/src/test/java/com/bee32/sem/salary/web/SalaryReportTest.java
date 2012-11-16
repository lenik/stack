package com.bee32.sem.salary.web;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.JavaioFile;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.Assert;

import com.bee32.sem.salary.dto.EventBonusDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.util.PersonSalaryReportModel;

public class SalaryReportTest
        extends Assert {

    public static void main(String[] args)
            throws Exception {
        InputStream reportTemplate = SalaryReportTest.class.getClassLoader().getResourceAsStream(
                "resources/3/15/6/4/salary/report1.jrxml");

        PersonSalaryReportModel personalSalaryModel = new PersonSalaryReportModel();
        List<PersonSalaryReportModel> sourceList = new ArrayList<PersonSalaryReportModel>();

        SalaryElementDto row1 = new SalaryElementDto();
        row1.setDefCategory("cate");
        row1.setDefLabel("label");
        row1.setBonus(BigDecimal.valueOf(100));

        EventBonusDto event1 = new EventBonusDto();
        event1.setLabel("event label");
        event1.setDescription("event descriptiotn");
        event1.setBeginTime(new Date());
        event1.setEndTime(new Date());
        event1.setValue(BigDecimal.valueOf(321));

        personalSalaryModel.setElements(Arrays.asList(row1));
        personalSalaryModel.setEvents(Arrays.asList(event1));
        sourceList.add(personalSalaryModel);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "工资单");
        params.put("employeeName", "Tom");
        params.put("yearMonthString", "1000年" + "3月份");
        params.put("total", BigDecimal.valueOf(1000));
        params.put("tax", BigDecimal.valueOf(200));
        params.put("salary", BigDecimal.valueOf(3000));

//        ClassLoader loader = SalaryAdminBean.class.getClassLoader();
//        URL sub1 = loader.getResource("resources/3/15/6/4/salary/report1_subreport1.jasper");
//        URL sub2 = loader.getResource("resources/3/15/6/4/salary/report1_subreport2.jasper");
        URL dir = new URL("jar:file:///home/ztf/work/secca/stack/sem/biz1/sem-salary/target/sem-salary-0.3.2-SNAPSHOT-sources.jar!/resources/3/15/6/4/salary/");
        URL sub1 = new URL(dir,"report1_subreport1.jasper");
        URL sub2 = new URL(dir,"report1_subreport2.jasper");
        System.out.println(sub1);
        System.out.println(sub2);
        params.put("sub1", sub1);
        params.put("sub2", sub2);

        JasperReport report = JasperCompileManager.compileReport(reportTemplate);

        JRDataSource dataSource = new JRBeanCollectionDataSource(sourceList);

        byte[] pdf = JasperRunManager.runReportToPdf(report, params, dataSource);

        System.out.println("PDF Size: " + pdf.length);
        new JavaioFile("/tmp/a.pdf").forWrite().writeBytes(pdf);
    }

}
