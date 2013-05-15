package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue10A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue10B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue11A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue11B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue12A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue12B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue13A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue13B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue14A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue14B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue15A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue15B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue16A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue16B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue17A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue17B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue18A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue18B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue19A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue19B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue20A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue20B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue22A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue22B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue23A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue23B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue24A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue24B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue25A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue25B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue26A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue26B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue27A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue27B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue28A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue28B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue29A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue29B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue31A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue31B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue4A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue4B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue5A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue5B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue6A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue6B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue7A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue7B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue8A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue8B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue9A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue9B;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.service.PeopleService;

public class ProfitSheetBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionFactory sessionFactory;

    Date queryDate = new Date();

    boolean verified;

    BigDecimal v1A;
    BigDecimal v1B;

    BigDecimal v2A;
    BigDecimal v2B;

    BigDecimal v3A;
    BigDecimal v3B;

    BigDecimal v4A;
    BigDecimal v4B;

    BigDecimal v5A;
    BigDecimal v5B;

    BigDecimal v6A;
    BigDecimal v6B;

    BigDecimal v7A;
    BigDecimal v7B;

    BigDecimal v8A;
    BigDecimal v8B;

    BigDecimal v9A;
    BigDecimal v9B;

    BigDecimal v10A;
    BigDecimal v10B;

    BigDecimal v11A;
    BigDecimal v11B;

    BigDecimal v12A;
    BigDecimal v12B;

    BigDecimal v13A;
    BigDecimal v13B;

    BigDecimal v14A;
    BigDecimal v14B;

    BigDecimal v15A;
    BigDecimal v15B;

    BigDecimal v16A;
    BigDecimal v16B;

    BigDecimal v17A;
    BigDecimal v17B;

    BigDecimal v18A;
    BigDecimal v18B;

    BigDecimal v19A;
    BigDecimal v19B;

    BigDecimal v20A;
    BigDecimal v20B;

    BigDecimal v21A;
    BigDecimal v21B;

    BigDecimal v22A;
    BigDecimal v22B;

    BigDecimal v23A;
    BigDecimal v23B;

    BigDecimal v24A;
    BigDecimal v24B;

    BigDecimal v25A;
    BigDecimal v25B;

    BigDecimal v26A;
    BigDecimal v26B;

    BigDecimal v27A;
    BigDecimal v27B;

    BigDecimal v28A;
    BigDecimal v28B;

    BigDecimal v29A;
    BigDecimal v29B;

    BigDecimal v30A;
    BigDecimal v30B;

    BigDecimal v31A;
    BigDecimal v31B;

    BigDecimal v32A;
    BigDecimal v32B;

    public String getSelfOrg() {
        Org selfOrg = BEAN(PeopleService.class).getSelfOrg();

        return selfOrg.getDisplayName();
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public String getQueryDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(queryDate);
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public BigDecimal getV1A() {
        return v1A;
    }

    public BigDecimal getV1B() {
        return v1B;
    }

    public BigDecimal getV2A() {
        return v2A;
    }

    public BigDecimal getV2B() {
        return v2B;
    }

    public BigDecimal getV3A() {
        return v3A;
    }

    public BigDecimal getV3B() {
        return v3B;
    }

    public BigDecimal getV4A() {
        return v4A;
    }

    public BigDecimal getV4B() {
        return v4B;
    }

    public BigDecimal getV5A() {
        return v5A;
    }

    public BigDecimal getV5B() {
        return v5B;
    }

    public BigDecimal getV6A() {
        return v6A;
    }

    public BigDecimal getV6B() {
        return v6B;
    }

    public BigDecimal getV7A() {
        return v7A;
    }

    public BigDecimal getV7B() {
        return v7B;
    }

    public BigDecimal getV8A() {
        return v8A;
    }

    public BigDecimal getV8B() {
        return v8B;
    }

    public BigDecimal getV9A() {
        return v9A;
    }

    public BigDecimal getV9B() {
        return v9B;
    }

    public BigDecimal getV10A() {
        return v10A;
    }

    public BigDecimal getV10B() {
        return v10B;
    }

    public BigDecimal getV11A() {
        return v11A;
    }

    public BigDecimal getV11B() {
        return v11B;
    }

    public BigDecimal getV12A() {
        return v12A;
    }

    public BigDecimal getV12B() {
        return v12B;
    }

    public BigDecimal getV13A() {
        return v13A;
    }

    public BigDecimal getV13B() {
        return v13B;
    }

    public BigDecimal getV14A() {
        return v14A;
    }

    public BigDecimal getV14B() {
        return v14B;
    }

    public BigDecimal getV15A() {
        return v15A;
    }

    public BigDecimal getV15B() {
        return v15B;
    }

    public BigDecimal getV16A() {
        return v16A;
    }

    public BigDecimal getV16B() {
        return v16B;
    }

    public BigDecimal getV17A() {
        return v17A;
    }

    public BigDecimal getV17B() {
        return v17B;
    }

    public BigDecimal getV18A() {
        return v18A;
    }

    public BigDecimal getV18B() {
        return v18B;
    }

    public BigDecimal getV19A() {
        return v19A;
    }

    public BigDecimal getV19B() {
        return v19B;
    }

    public BigDecimal getV20A() {
        return v20A;
    }

    public BigDecimal getV20B() {
        return v20B;
    }

    public BigDecimal getV21A() {
        return v21A;
    }

    public BigDecimal getV21B() {
        return v21B;
    }

    public BigDecimal getV22A() {
        return v22A;
    }

    public BigDecimal getV22B() {
        return v22B;
    }

    public BigDecimal getV23A() {
        return v23A;
    }

    public BigDecimal getV23B() {
        return v23B;
    }

    public BigDecimal getV24A() {
        return v24A;
    }

    public BigDecimal getV24B() {
        return v24B;
    }

    public BigDecimal getV25A() {
        return v25A;
    }

    public BigDecimal getV25B() {
        return v25B;
    }

    public BigDecimal getV26A() {
        return v26A;
    }

    public BigDecimal getV26B() {
        return v26B;
    }

    public BigDecimal getV27A() {
        return v27A;
    }

    public BigDecimal getV27B() {
        return v27B;
    }

    public BigDecimal getV28A() {
        return v28A;
    }

    public BigDecimal getV28B() {
        return v28B;
    }

    public BigDecimal getV29A() {
        return v29A;
    }

    public BigDecimal getV29B() {
        return v29B;
    }

    public BigDecimal getV30A() {
        return v30A;
    }

    public BigDecimal getV30B() {
        return v30B;
    }

    public BigDecimal getV31A() {
        return v31A;
    }

    public BigDecimal getV31B() {
        return v31B;
    }

    public BigDecimal getV32A() {
        return v32A;
    }

    public BigDecimal getV32B() {
        return v32B;
    }

    @Transactional
    public void query() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);

        ProfitValue1A value1A = new ProfitValue1A();
        value1A.setDate(queryDate);
        value1A.setVerified(verified);
        value1A.setSession(session);
        value1A.calc();
        v1A = value1A.getValue();

        ProfitValue1B value1B = new ProfitValue1B();
        value1B.setDate(queryDate);
        value1B.setVerified(verified);
        value1B.setSession(session);
        value1B.calc();
        v1B = value1B.getValue();



        ProfitValue2A value2A = new ProfitValue2A();
        value2A.setDate(queryDate);
        value2A.setVerified(verified);
        value2A.setSession(session);
        value2A.calc();
        v2A = value2A.getValue();

        ProfitValue2B value2B = new ProfitValue2B();
        value2B.setDate(queryDate);
        value2B.setVerified(verified);
        value2B.setSession(session);
        value2B.calc();
        v2B = value2B.getValue();



        ProfitValue3A value3A = new ProfitValue3A();
        value3A.setDate(queryDate);
        value3A.setVerified(verified);
        value3A.setSession(session);
        value3A.calc();
        v3A = value3A.getValue();

        ProfitValue3B value3B = new ProfitValue3B();
        value3B.setDate(queryDate);
        value3B.setVerified(verified);
        value3B.setSession(session);
        value3B.calc();
        v3B = value3B.getValue();

        ProfitValue4A value4A = new ProfitValue4A();
        value4A.setDate(queryDate);
        value4A.setVerified(verified);
        value4A.setSession(session);
        value4A.calc();
        v4A = value4A.getValue();

        ProfitValue4B value4B = new ProfitValue4B();
        value4B.setDate(queryDate);
        value4B.setVerified(verified);
        value4B.setSession(session);
        value4B.calc();
        v4B = value4B.getValue();

        ProfitValue5A value5A = new ProfitValue5A();
        value5A.setDate(queryDate);
        value5A.setVerified(verified);
        value5A.setSession(session);
        value5A.calc();
        v5A = value5A.getValue();

        ProfitValue5B value5B = new ProfitValue5B();
        value5B.setDate(queryDate);
        value5B.setVerified(verified);
        value5B.setSession(session);
        value5B.calc();
        v5B = value5B.getValue();

        ProfitValue6A value6A = new ProfitValue6A();
        value6A.setDate(queryDate);
        value6A.setVerified(verified);
        value6A.setSession(session);
        value6A.calc();
        v6A = value6A.getValue();

        ProfitValue6B value6B = new ProfitValue6B();
        value6B.setDate(queryDate);
        value6B.setVerified(verified);
        value6B.setSession(session);
        value6B.calc();
        v6B = value6B.getValue();

        ProfitValue7A value7A = new ProfitValue7A();
        value7A.setDate(queryDate);
        value7A.setVerified(verified);
        value7A.setSession(session);
        value7A.calc();
        v7A = value7A.getValue();

        ProfitValue7B value7B = new ProfitValue7B();
        value7B.setDate(queryDate);
        value7B.setVerified(verified);
        value7B.setSession(session);
        value7B.calc();
        v7B = value7B.getValue();

        ProfitValue8A value8A = new ProfitValue8A();
        value8A.setDate(queryDate);
        value8A.setVerified(verified);
        value8A.setSession(session);
        value8A.calc();
        v8A = value8A.getValue();

        ProfitValue8B value8B = new ProfitValue8B();
        value8B.setDate(queryDate);
        value8B.setVerified(verified);
        value8B.setSession(session);
        value8B.calc();
        v8B = value8B.getValue();

        ProfitValue9A value9A = new ProfitValue9A();
        value9A.setDate(queryDate);
        value9A.setVerified(verified);
        value9A.setSession(session);
        value9A.calc();
        v9A = value9A.getValue();

        ProfitValue9B value9B = new ProfitValue9B();
        value9B.setDate(queryDate);
        value9B.setVerified(verified);
        value9B.setSession(session);
        value9B.calc();
        v9B = value9B.getValue();

        ProfitValue10A value10A = new ProfitValue10A();
        value10A.setDate(queryDate);
        value10A.setVerified(verified);
        value10A.setSession(session);
        value10A.calc();
        v10A = value10A.getValue();

        ProfitValue10B value10B = new ProfitValue10B();
        value10B.setDate(queryDate);
        value10B.setVerified(verified);
        value10B.setSession(session);
        value10B.calc();
        v10B = value10B.getValue();

        ProfitValue11A value11A = new ProfitValue11A();
        value11A.setDate(queryDate);
        value11A.setVerified(verified);
        value11A.setSession(session);
        value11A.calc();
        v11A = value11A.getValue();

        ProfitValue11B value11B = new ProfitValue11B();
        value11B.setDate(queryDate);
        value11B.setVerified(verified);
        value11B.setSession(session);
        value11B.calc();
        v11B = value11B.getValue();

        ProfitValue12A value12A = new ProfitValue12A();
        value12A.setDate(queryDate);
        value12A.setVerified(verified);
        value12A.setSession(session);
        value12A.calc();
        v12A = value12A.getValue();

        ProfitValue12B value12B = new ProfitValue12B();
        value12B.setDate(queryDate);
        value12B.setVerified(verified);
        value12B.setSession(session);
        value12B.calc();
        v12B = value12B.getValue();

        ProfitValue13A value13A = new ProfitValue13A();
        value13A.setDate(queryDate);
        value13A.setVerified(verified);
        value13A.setSession(session);
        value13A.calc();
        v13A = value13A.getValue();

        ProfitValue13B value13B = new ProfitValue13B();
        value13B.setDate(queryDate);
        value13B.setVerified(verified);
        value13B.setSession(session);
        value13B.calc();
        v13B = value13B.getValue();

        ProfitValue14A value14A = new ProfitValue14A();
        value14A.setDate(queryDate);
        value14A.setVerified(verified);
        value14A.setSession(session);
        value14A.calc();
        v14A = value14A.getValue();

        ProfitValue14B value14B = new ProfitValue14B();
        value14B.setDate(queryDate);
        value14B.setVerified(verified);
        value14B.setSession(session);
        value14B.calc();
        v14B = value14B.getValue();

        ProfitValue15A value15A = new ProfitValue15A();
        value15A.setDate(queryDate);
        value15A.setVerified(verified);
        value15A.setSession(session);
        value15A.calc();
        v15A = value15A.getValue();

        ProfitValue15B value15B = new ProfitValue15B();
        value15B.setDate(queryDate);
        value15B.setVerified(verified);
        value15B.setSession(session);
        value15B.calc();
        v15B = value15B.getValue();

        ProfitValue16A value16A = new ProfitValue16A();
        value16A.setDate(queryDate);
        value16A.setVerified(verified);
        value16A.setSession(session);
        value16A.calc();
        v16A = value16A.getValue();

        ProfitValue16B value16B = new ProfitValue16B();
        value16B.setDate(queryDate);
        value16B.setVerified(verified);
        value16B.setSession(session);
        value16B.calc();
        v16B = value16B.getValue();

        ProfitValue17A value17A = new ProfitValue17A();
        value17A.setDate(queryDate);
        value17A.setVerified(verified);
        value17A.setSession(session);
        value17A.calc();
        v17A = value17A.getValue();

        ProfitValue17B value17B = new ProfitValue17B();
        value17B.setDate(queryDate);
        value17B.setVerified(verified);
        value17B.setSession(session);
        value17B.calc();
        v17B = value17B.getValue();

        ProfitValue18A value18A = new ProfitValue18A();
        value18A.setDate(queryDate);
        value18A.setVerified(verified);
        value18A.setSession(session);
        value18A.calc();
        v18A = value18A.getValue();

        ProfitValue18B value18B = new ProfitValue18B();
        value18B.setDate(queryDate);
        value18B.setVerified(verified);
        value18B.setSession(session);
        value18B.calc();
        v18B = value18B.getValue();

        ProfitValue19A value19A = new ProfitValue19A();
        value19A.setDate(queryDate);
        value19A.setVerified(verified);
        value19A.setSession(session);
        value19A.calc();
        v19A = value19A.getValue();

        ProfitValue19B value19B = new ProfitValue19B();
        value19B.setDate(queryDate);
        value19B.setVerified(verified);
        value19B.setSession(session);
        value19B.calc();
        v19B = value19B.getValue();

        ProfitValue20A value20A = new ProfitValue20A();
        value20A.setDate(queryDate);
        value20A.setVerified(verified);
        value20A.setSession(session);
        value20A.calc();
        v20A = value20A.getValue();

        ProfitValue20B value20B = new ProfitValue20B();
        value20B.setDate(queryDate);
        value20B.setVerified(verified);
        value20B.setSession(session);
        value20B.calc();
        v20B = value20B.getValue();

        v21A = (((((v1A.subtract(v2A)).subtract(v3A)).subtract(v11A)).subtract(v14A)).subtract(v18A)).add(v20A);
        v21B = (((((v1B.subtract(v2B)).subtract(v3B)).subtract(v11B)).subtract(v14B)).subtract(v18B)).add(v20B);

        ProfitValue22A value22A = new ProfitValue22A();
        value22A.setDate(queryDate);
        value22A.setVerified(verified);
        value22A.setSession(session);
        value22A.calc();
        v22A = value22A.getValue();

        ProfitValue22B value22B = new ProfitValue22B();
        value22B.setDate(queryDate);
        value22B.setVerified(verified);
        value22B.setSession(session);
        value22B.calc();
        v22B = value22B.getValue();

        ProfitValue23A value23A = new ProfitValue23A();
        value23A.setDate(queryDate);
        value23A.setVerified(verified);
        value23A.setSession(session);
        value23A.calc();
        v23A = value23A.getValue();

        ProfitValue23B value23B = new ProfitValue23B();
        value23B.setDate(queryDate);
        value23B.setVerified(verified);
        value23B.setSession(session);
        value23B.calc();
        v23B = value23B.getValue();


        ProfitValue24A value24A = new ProfitValue24A();
        value24A.setDate(queryDate);
        value24A.setVerified(verified);
        value24A.setSession(session);
        value24A.calc();
        v24A = value24A.getValue();

        ProfitValue24B value24B = new ProfitValue24B();
        value24B.setDate(queryDate);
        value24B.setVerified(verified);
        value24B.setSession(session);
        value24B.calc();
        v24B = value24B.getValue();

        ProfitValue25A value25A = new ProfitValue25A();
        value25A.setDate(queryDate);
        value25A.setVerified(verified);
        value25A.setSession(session);
        value25A.calc();
        v25A = value25A.getValue();

        ProfitValue25B value25B = new ProfitValue25B();
        value25B.setDate(queryDate);
        value25B.setVerified(verified);
        value25B.setSession(session);
        value25B.calc();
        v25B = value25B.getValue();

        ProfitValue26A value26A = new ProfitValue26A();
        value26A.setDate(queryDate);
        value26A.setVerified(verified);
        value26A.setSession(session);
        value26A.calc();
        v26A = value26A.getValue();

        ProfitValue26B value26B = new ProfitValue26B();
        value26B.setDate(queryDate);
        value26B.setVerified(verified);
        value26B.setSession(session);
        value26B.calc();
        v26B = value26B.getValue();

        ProfitValue27A value27A = new ProfitValue27A();
        value27A.setDate(queryDate);
        value27A.setVerified(verified);
        value27A.setSession(session);
        value27A.calc();
        v27A = value27A.getValue();

        ProfitValue27B value27B = new ProfitValue27B();
        value27B.setDate(queryDate);
        value27B.setVerified(verified);
        value27B.setSession(session);
        value27B.calc();
        v27B = value27B.getValue();

        ProfitValue28A value28A = new ProfitValue28A();
        value28A.setDate(queryDate);
        value28A.setVerified(verified);
        value28A.setSession(session);
        value28A.calc();
        v28A = value28A.getValue();

        ProfitValue28B value28B = new ProfitValue28B();
        value28B.setDate(queryDate);
        value28B.setVerified(verified);
        value28B.setSession(session);
        value28B.calc();
        v28B = value28B.getValue();

        ProfitValue29A value29A = new ProfitValue29A();
        value29A.setDate(queryDate);
        value29A.setVerified(verified);
        value29A.setSession(session);
        value29A.calc();
        v29A = value29A.getValue();

        ProfitValue29B value29B = new ProfitValue29B();
        value29B.setDate(queryDate);
        value29B.setVerified(verified);
        value29B.setSession(session);
        value29B.calc();
        v29B = value29B.getValue();

        v30A = ((v21A.add(v26A)).subtract(v24A));
        v30B = ((v21B.add(v26B)).subtract(v24B));

        ProfitValue31A value31A = new ProfitValue31A();
        value31A.setDate(queryDate);
        value31A.setVerified(verified);
        value31A.setSession(session);
        value31A.calc();
        v31A = value31A.getValue();

        ProfitValue31B value31B = new ProfitValue31B();
        value31B.setDate(queryDate);
        value31B.setVerified(verified);
        value31B.setSession(session);
        value31B.calc();
        v31B = value31B.getValue();


        v32A = v30A.subtract(v31A);
        v32B = v30B.subtract(v31B);
    }

}
