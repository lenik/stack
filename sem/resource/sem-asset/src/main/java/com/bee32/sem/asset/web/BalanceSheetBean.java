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
import com.bee32.sem.asset.service.balance_sheet.Value10A;
import com.bee32.sem.asset.service.balance_sheet.Value10B;
import com.bee32.sem.asset.service.balance_sheet.Value11A;
import com.bee32.sem.asset.service.balance_sheet.Value11B;
import com.bee32.sem.asset.service.balance_sheet.Value12A;
import com.bee32.sem.asset.service.balance_sheet.Value12B;
import com.bee32.sem.asset.service.balance_sheet.Value13A;
import com.bee32.sem.asset.service.balance_sheet.Value13B;
import com.bee32.sem.asset.service.balance_sheet.Value16A;
import com.bee32.sem.asset.service.balance_sheet.Value16B;
import com.bee32.sem.asset.service.balance_sheet.Value17A;
import com.bee32.sem.asset.service.balance_sheet.Value17B;
import com.bee32.sem.asset.service.balance_sheet.Value18A;
import com.bee32.sem.asset.service.balance_sheet.Value18B;
import com.bee32.sem.asset.service.balance_sheet.Value19A;
import com.bee32.sem.asset.service.balance_sheet.Value19B;
import com.bee32.sem.asset.service.balance_sheet.Value1A;
import com.bee32.sem.asset.service.balance_sheet.Value1B;
import com.bee32.sem.asset.service.balance_sheet.Value21A;
import com.bee32.sem.asset.service.balance_sheet.Value21B;
import com.bee32.sem.asset.service.balance_sheet.Value22A;
import com.bee32.sem.asset.service.balance_sheet.Value22B;
import com.bee32.sem.asset.service.balance_sheet.Value23A;
import com.bee32.sem.asset.service.balance_sheet.Value23B;
import com.bee32.sem.asset.service.balance_sheet.Value24A;
import com.bee32.sem.asset.service.balance_sheet.Value24B;
import com.bee32.sem.asset.service.balance_sheet.Value25A;
import com.bee32.sem.asset.service.balance_sheet.Value25B;
import com.bee32.sem.asset.service.balance_sheet.Value26A;
import com.bee32.sem.asset.service.balance_sheet.Value26B;
import com.bee32.sem.asset.service.balance_sheet.Value27A;
import com.bee32.sem.asset.service.balance_sheet.Value27B;
import com.bee32.sem.asset.service.balance_sheet.Value2A;
import com.bee32.sem.asset.service.balance_sheet.Value2B;
import com.bee32.sem.asset.service.balance_sheet.Value31A;
import com.bee32.sem.asset.service.balance_sheet.Value31B;
import com.bee32.sem.asset.service.balance_sheet.Value32A;
import com.bee32.sem.asset.service.balance_sheet.Value32B;
import com.bee32.sem.asset.service.balance_sheet.Value33A;
import com.bee32.sem.asset.service.balance_sheet.Value33B;
import com.bee32.sem.asset.service.balance_sheet.Value34A;
import com.bee32.sem.asset.service.balance_sheet.Value34B;
import com.bee32.sem.asset.service.balance_sheet.Value35A;
import com.bee32.sem.asset.service.balance_sheet.Value35B;
import com.bee32.sem.asset.service.balance_sheet.Value36A;
import com.bee32.sem.asset.service.balance_sheet.Value36B;
import com.bee32.sem.asset.service.balance_sheet.Value37A;
import com.bee32.sem.asset.service.balance_sheet.Value37B;
import com.bee32.sem.asset.service.balance_sheet.Value38A;
import com.bee32.sem.asset.service.balance_sheet.Value38B;
import com.bee32.sem.asset.service.balance_sheet.Value39A;
import com.bee32.sem.asset.service.balance_sheet.Value39B;
import com.bee32.sem.asset.service.balance_sheet.Value3A;
import com.bee32.sem.asset.service.balance_sheet.Value3B;
import com.bee32.sem.asset.service.balance_sheet.Value42A;
import com.bee32.sem.asset.service.balance_sheet.Value42B;
import com.bee32.sem.asset.service.balance_sheet.Value43A;
import com.bee32.sem.asset.service.balance_sheet.Value43B;
import com.bee32.sem.asset.service.balance_sheet.Value44A;
import com.bee32.sem.asset.service.balance_sheet.Value44B;
import com.bee32.sem.asset.service.balance_sheet.Value48A;
import com.bee32.sem.asset.service.balance_sheet.Value48B;
import com.bee32.sem.asset.service.balance_sheet.Value49A;
import com.bee32.sem.asset.service.balance_sheet.Value49B;
import com.bee32.sem.asset.service.balance_sheet.Value4A;
import com.bee32.sem.asset.service.balance_sheet.Value4B;
import com.bee32.sem.asset.service.balance_sheet.Value50A;
import com.bee32.sem.asset.service.balance_sheet.Value50B;
import com.bee32.sem.asset.service.balance_sheet.Value51A;
import com.bee32.sem.asset.service.balance_sheet.Value51B;
import com.bee32.sem.asset.service.balance_sheet.Value5A;
import com.bee32.sem.asset.service.balance_sheet.Value5B;
import com.bee32.sem.asset.service.balance_sheet.Value6A;
import com.bee32.sem.asset.service.balance_sheet.Value6B;
import com.bee32.sem.asset.service.balance_sheet.Value7A;
import com.bee32.sem.asset.service.balance_sheet.Value7B;
import com.bee32.sem.asset.service.balance_sheet.Value8A;
import com.bee32.sem.asset.service.balance_sheet.Value8B;
import com.bee32.sem.asset.service.balance_sheet.Value9A;
import com.bee32.sem.asset.service.balance_sheet.Value9B;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.service.PeopleService;

public class BalanceSheetBean
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

    BigDecimal v34A;
    BigDecimal v34B;

    BigDecimal v5A;
    BigDecimal v5B;

    BigDecimal v33A;
    BigDecimal v33B;

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

    BigDecimal v29A;
    BigDecimal v29B;

    BigDecimal v30A;
    BigDecimal v30B;

    BigDecimal v31A;
    BigDecimal v31B;

    BigDecimal v32A;
    BigDecimal v32B;

    BigDecimal v35A;
    BigDecimal v35B;

    BigDecimal v36A;
    BigDecimal v36B;

    BigDecimal v37A;
    BigDecimal v37B;

    BigDecimal v38A;
    BigDecimal v38B;

    BigDecimal v39A;
    BigDecimal v39B;

    BigDecimal v41A;
    BigDecimal v41B;

    BigDecimal v42A;
    BigDecimal v42B;

    BigDecimal v43A;
    BigDecimal v43B;

    BigDecimal v44A;
    BigDecimal v44B;

    BigDecimal v46A;
    BigDecimal v46B;

    BigDecimal v47A;
    BigDecimal v47B;

    BigDecimal v48A;
    BigDecimal v48B;

    BigDecimal v49A;
    BigDecimal v49B;

    BigDecimal v50A;
    BigDecimal v50B;

    BigDecimal v51A;
    BigDecimal v51B;

    BigDecimal v52A;
    BigDecimal v52B;

    BigDecimal v53A;
    BigDecimal v53B;

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

    public BigDecimal getV34A() {
        return v34A;
    }

    public BigDecimal getV34B() {
        return v34B;
    }

    public BigDecimal getV5A() {
        return v5A;
    }

    public BigDecimal getV5B() {
        return v5B;
    }

    public BigDecimal getV33A() {
        return v33A;
    }

    public BigDecimal getV33B() {
        return v33B;
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

    public BigDecimal getV35A() {
        return v35A;
    }

    public BigDecimal getV35B() {
        return v35B;
    }

    public BigDecimal getV36A() {
        return v36A;
    }

    public BigDecimal getV36B() {
        return v36B;
    }

    public BigDecimal getV37A() {
        return v37A;
    }

    public BigDecimal getV37B() {
        return v37B;
    }

    public BigDecimal getV38A() {
        return v38A;
    }

    public BigDecimal getV38B() {
        return v38B;
    }

    public BigDecimal getV39A() {
        return v39A;
    }

    public BigDecimal getV39B() {
        return v39B;
    }

    public BigDecimal getV41A() {
        return v41A;
    }

    public BigDecimal getV41B() {
        return v41B;
    }

    public BigDecimal getV42A() {
        return v42A;
    }

    public BigDecimal getV42B() {
        return v42B;
    }

    public BigDecimal getV43A() {
        return v43A;
    }

    public BigDecimal getV43B() {
        return v43B;
    }

    public BigDecimal getV44A() {
        return v44A;
    }

    public BigDecimal getV44B() {
        return v44B;
    }

    public BigDecimal getV46A() {
        return v46A;
    }

    public BigDecimal getV46B() {
        return v46B;
    }

    public BigDecimal getV47A() {
        return v47A;
    }

    public BigDecimal getV47B() {
        return v47B;
    }

    public BigDecimal getV48A() {
        return v48A;
    }

    public BigDecimal getV48B() {
        return v48B;
    }

    public BigDecimal getV49A() {
        return v49A;
    }

    public BigDecimal getV49B() {
        return v49B;
    }

    public BigDecimal getV50A() {
        return v50A;
    }

    public BigDecimal getV50B() {
        return v50B;
    }

    public BigDecimal getV51A() {
        return v51A;
    }

    public BigDecimal getV51B() {
        return v51B;
    }

    public BigDecimal getV52A() {
        return v52A;
    }

    public BigDecimal getV52B() {
        return v52B;
    }

    public BigDecimal getV53A() {
        return v53A;
    }

    public BigDecimal getV53B() {
        return v53B;
    }

    @Transactional
    public void query() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);


        Value1A value1A = new Value1A();
        value1A.setDate(queryDate);
        value1A.setVerified(verified);
        value1A.setSession(session);
        value1A.calc();
        v1A = value1A.getValue();

        Value1B value1B = new Value1B();
        value1B.setDate(queryDate);
        value1B.setVerified(verified);
        value1B.setSession(session);
        value1B.calc();
        v1B = value1B.getValue();


        Value2A value2A = new Value2A();
        value2A.setDate(queryDate);
        value2A.setVerified(verified);
        value2A.setSession(session);
        value2A.calc();
        v2A = value2A.getValue();

        Value2B value2B = new Value2B();
        value2B.setDate(queryDate);
        value2B.setVerified(verified);
        value2B.setSession(session);
        value2B.calc();
        v2B = value2B.getValue();


        Value3A value3A = new Value3A();
        value3A.setDate(queryDate);
        value3A.setVerified(verified);
        value3A.setSession(session);
        value3A.calc();
        v3A = value3A.getValue();

        Value3B value3B = new Value3B();
        value3B.setDate(queryDate);
        value3B.setVerified(verified);
        value3B.setSession(session);
        value3B.calc();
        v3B = value3B.getValue();


        Value4A value4A = new Value4A();
        value4A.setDate(queryDate);
        value4A.setVerified(verified);
        value4A.setSession(session);
        value4A.calc();
        v4A = value4A.getValue();

        Value4B value4B = new Value4B();
        value4B.setDate(queryDate);
        value4B.setVerified(verified);
        value4B.setSession(session);
        value4B.calc();
        v4B = value4B.getValue();


        Value34A value34A = new Value34A();
        value34A.setDate(queryDate);
        value34A.setVerified(verified);
        value34A.setSession(session);
        value34A.calc();
        v34A = value34A.getValue();

        Value34B value34B = new Value34B();
        value34B.setDate(queryDate);
        value34B.setVerified(verified);
        value34B.setSession(session);
        value34B.calc();
        v34B = value34B.getValue();


        Value5A value5A = new Value5A();
        value5A.setDate(queryDate);
        value5A.setVerified(verified);
        value5A.setSession(session);
        value5A.calc();
        v5A = value5A.getValue();

        Value5B value5B = new Value5B();
        value5B.setDate(queryDate);
        value5B.setVerified(verified);
        value5B.setSession(session);
        value5B.calc();
        v5B = value5B.getValue();


        Value33A value33A = new Value33A();
        value33A.setDate(queryDate);
        value33A.setVerified(verified);
        value33A.setSession(session);
        value33A.calc();
        v33A = value33A.getValue();

        Value33B value33B = new Value33B();
        value33B.setDate(queryDate);
        value33B.setVerified(verified);
        value33B.setSession(session);
        value33B.calc();
        v33B = value33B.getValue();


        Value6A value6A = new Value6A();
        value6A.setDate(queryDate);
        value6A.setVerified(verified);
        value6A.setSession(session);
        value6A.calc();
        v6A = value6A.getValue();

        Value6B value6B = new Value6B();
        value6B.setDate(queryDate);
        value6B.setVerified(verified);
        value6B.setSession(session);
        value6B.calc();
        v6B = value6B.getValue();


        Value7A value7A = new Value7A();
        value7A.setDate(queryDate);
        value7A.setVerified(verified);
        value7A.setSession(session);
        value7A.calc();
        v7A = value7A.getValue();

        Value7B value7B = new Value7B();
        value7B.setDate(queryDate);
        value7B.setVerified(verified);
        value7B.setSession(session);
        value7B.calc();
        v7B = value7B.getValue();


        Value8A value8A = new Value8A();
        value8A.setDate(queryDate);
        value8A.setVerified(verified);
        value8A.setSession(session);
        value8A.calc();
        v8A = value8A.getValue();

        Value8B value8B = new Value8B();
        value8B.setDate(queryDate);
        value8B.setVerified(verified);
        value8B.setSession(session);
        value8B.calc();
        v8B = value8B.getValue();


        Value9A value9A = new Value9A();
        value9A.setDate(queryDate);
        value9A.setVerified(verified);
        value9A.setSession(session);
        value9A.calc();
        v9A = value9A.getValue();

        Value9B value9B = new Value9B();
        value9B.setDate(queryDate);
        value9B.setVerified(verified);
        value9B.setSession(session);
        value9B.calc();
        v9B = value9B.getValue();


        Value10A value10A = new Value10A();
        value10A.setDate(queryDate);
        value10A.setVerified(verified);
        value10A.setSession(session);
        value10A.calc();
        v10A = value10A.getValue();

        Value10B value10B = new Value10B();
        value10B.setDate(queryDate);
        value10B.setVerified(verified);
        value10B.setSession(session);
        value10B.calc();
        v10B = value10B.getValue();


        Value11A value11A = new Value11A();
        value11A.setDate(queryDate);
        value11A.setVerified(verified);
        value11A.setSession(session);
        value11A.calc();
        v11A = value11A.getValue();

        Value11B value11B = new Value11B();
        value11B.setDate(queryDate);
        value11B.setVerified(verified);
        value11B.setSession(session);
        value11B.calc();
        v11B = value11B.getValue();


        Value12A value12A = new Value12A();
        value12A.setDate(queryDate);
        value12A.setVerified(verified);
        value12A.setSession(session);
        value12A.calc();
        v12A = value12A.getValue();

        Value12B value12B = new Value12B();
        value12B.setDate(queryDate);
        value12B.setVerified(verified);
        value12B.setSession(session);
        value12B.calc();
        v12B = value12B.getValue();


        Value13A value13A = new Value13A();
        value13A.setDate(queryDate);
        value13A.setVerified(verified);
        value13A.setSession(session);
        value13A.calc();
        v13A = value13A.getValue();

        Value13B value13B = new Value13B();
        value13B.setDate(queryDate);
        value13B.setVerified(verified);
        value13B.setSession(session);
        value13B.calc();
        v13B = value13B.getValue();


        v15A = v1A.add(v2A.add(v3A.add(v4A.add(v5A.add(v6A.add(v7A.add(v8A.add(v9A))))))));
        v15B = v1B.add(v2B.add(v3B.add(v4B.add(v5B.add(v6B.add(v7B.add(v8B.add(v9B))))))));


        Value16A value16A = new Value16A();
        value16A.setDate(queryDate);
        value16A.setVerified(verified);
        value16A.setSession(session);
        value16A.calc();
        v16A = value16A.getValue();

        Value16B value16B = new Value16B();
        value16B.setDate(queryDate);
        value16B.setVerified(verified);
        value16B.setSession(session);
        value16B.calc();
        v16B = value16B.getValue();


        Value17A value17A = new Value17A();
        value17A.setDate(queryDate);
        value17A.setVerified(verified);
        value17A.setSession(session);
        value17A.calc();
        v17A = value17A.getValue();

        Value17B value17B = new Value17B();
        value17B.setDate(queryDate);
        value17B.setVerified(verified);
        value17B.setSession(session);
        value17B.calc();
        v17B = value17B.getValue();


        Value18A value18A = new Value18A();
        value18A.setDate(queryDate);
        value18A.setVerified(verified);
        value18A.setSession(session);
        value18A.calc();
        v18A = value18A.getValue();

        Value18B value18B = new Value18B();
        value18B.setDate(queryDate);
        value18B.setVerified(verified);
        value18B.setSession(session);
        value18B.calc();
        v18B = value18B.getValue();


        Value19A value19A = new Value19A();
        value19A.setDate(queryDate);
        value19A.setVerified(verified);
        value19A.setSession(session);
        value19A.calc();
        v19A = value19A.getValue();

        Value19B value19B = new Value19B();
        value19B.setDate(queryDate);
        value19B.setVerified(verified);
        value19B.setSession(session);
        value19B.calc();
        v19B = value19B.getValue();

        v20A = v18A.subtract(v19A);
        v20B = v18B.subtract(v19B);

        Value21A value21A = new Value21A();
        value21A.setDate(queryDate);
        value21A.setVerified(verified);
        value21A.setSession(session);
        value21A.calc();
        v21A = value21A.getValue();

        Value21B value21B = new Value21B();
        value21B.setDate(queryDate);
        value21B.setVerified(verified);
        value21B.setSession(session);
        value21B.calc();
        v21B = value21B.getValue();


        Value22A value22A = new Value22A();
        value22A.setDate(queryDate);
        value22A.setVerified(verified);
        value22A.setSession(session);
        value22A.calc();
        v22A = value22A.getValue();

        Value22B value22B = new Value22B();
        value22B.setDate(queryDate);
        value22B.setVerified(verified);
        value22B.setSession(session);
        value22B.calc();
        v22B = value22B.getValue();


        Value23A value23A = new Value23A();
        value23A.setDate(queryDate);
        value23A.setVerified(verified);
        value23A.setSession(session);
        value23A.calc();
        v23A = value23A.getValue();

        Value23B value23B = new Value23B();
        value23B.setDate(queryDate);
        value23B.setVerified(verified);
        value23B.setSession(session);
        value23B.calc();
        v23B = value23B.getValue();


        Value24A value24A = new Value24A();
        value24A.setDate(queryDate);
        value24A.setVerified(verified);
        value24A.setSession(session);
        value24A.calc();
        v24A = value24A.getValue();

        Value24B value24B = new Value24B();
        value24B.setDate(queryDate);
        value24B.setVerified(verified);
        value24B.setSession(session);
        value24B.calc();
        v24B = value24B.getValue();


        Value25A value25A = new Value25A();
        value25A.setDate(queryDate);
        value25A.setVerified(verified);
        value25A.setSession(session);
        value25A.calc();
        v25A = value25A.getValue();

        Value25B value25B = new Value25B();
        value25B.setDate(queryDate);
        value25B.setVerified(verified);
        value25B.setSession(session);
        value25B.calc();
        v25B = value25B.getValue();

        Value26A value26A = new Value26A();
        value26A.setDate(queryDate);
        value26A.setVerified(verified);
        value26A.setSession(session);
        value26A.calc();
        v26A = value26A.getValue();

        Value26B value26B = new Value26B();
        value26B.setDate(queryDate);
        value26B.setVerified(verified);
        value26B.setSession(session);
        value26B.calc();
        v26B = value26B.getValue();

        Value27A value27A = new Value27A();
        value27A.setDate(queryDate);
        value27A.setVerified(verified);
        value27A.setSession(session);
        value27A.calc();
        v27A = value27A.getValue();

        Value27B value27B = new Value27B();
        value27B.setDate(queryDate);
        value27B.setVerified(verified);
        value27B.setSession(session);
        value27B.calc();
        v27B = value27B.getValue();

        v29A = v16A.add(v17A.add(v20A.add(v21A.add(v22A.add(v23A.add(v24A.add(v25A.add(v26A.add(v27A)))))))));
        v29B = v16B.add(v17B.add(v20B.add(v21B.add(v22B.add(v23B.add(v24B.add(v25B.add(v26B.add(v27B)))))))));


        v30A = v15A.add(v29A);
        v30B = v15B.add(v29B);

        Value31A value31A = new Value31A();
        value31A.setDate(queryDate);
        value31A.setVerified(verified);
        value31A.setSession(session);
        value31A.calc();
        v31A = value31A.getValue();

        Value31B value31B = new Value31B();
        value31B.setDate(queryDate);
        value31B.setVerified(verified);
        value31B.setSession(session);
        value31B.calc();
        v31B = value31B.getValue();

        Value32A value32A = new Value32A();
        value32A.setDate(queryDate);
        value32A.setVerified(verified);
        value32A.setSession(session);
        value32A.calc();
        v32A = value32A.getValue();

        Value32B value32B = new Value32B();
        value32B.setDate(queryDate);
        value32B.setVerified(verified);
        value32B.setSession(session);
        value32B.calc();
        v32B = value32B.getValue();

        Value35A value35A = new Value35A();
        value35A.setDate(queryDate);
        value35A.setVerified(verified);
        value35A.setSession(session);
        value35A.calc();
        v35A = value35A.getValue();

        Value35B value35B = new Value35B();
        value35B.setDate(queryDate);
        value35B.setVerified(verified);
        value35B.setSession(session);
        value35B.calc();
        v35B = value35B.getValue();

        Value36A value36A = new Value36A();
        value36A.setDate(queryDate);
        value36A.setVerified(verified);
        value36A.setSession(session);
        value36A.calc();
        v36A = value36A.getValue();

        Value36B value36B = new Value36B();
        value36B.setDate(queryDate);
        value36B.setVerified(verified);
        value36B.setSession(session);
        value36B.calc();
        v36B = value36B.getValue();

        Value37A value37A = new Value37A();
        value37A.setDate(queryDate);
        value37A.setVerified(verified);
        value37A.setSession(session);
        value37A.calc();
        v37A = value37A.getValue();

        Value37B value37B = new Value37B();
        value37B.setDate(queryDate);
        value37B.setVerified(verified);
        value37B.setSession(session);
        value37B.calc();
        v37B = value37B.getValue();

        Value38A value38A = new Value38A();
        value38A.setDate(queryDate);
        value38A.setVerified(verified);
        value38A.setSession(session);
        value38A.calc();
        v38A = value38A.getValue();

        Value38B value38B = new Value38B();
        value38B.setDate(queryDate);
        value38B.setVerified(verified);
        value38B.setSession(session);
        value38B.calc();
        v38B = value38B.getValue();

        Value39A value39A = new Value39A();
        value39A.setDate(queryDate);
        value39A.setVerified(verified);
        value39A.setSession(session);
        value39A.calc();
        v39A = value39A.getValue();

        Value39B value39B = new Value39B();
        value39B.setDate(queryDate);
        value39B.setVerified(verified);
        value39B.setSession(session);
        value39B.calc();
        v39B = value39B.getValue();

        v41A = v31A.add(v32A.add(v33A.add(v34A.add(v35A.add(v36A.add(v37A.add(v38A.add(v39A))))))));
        v41B = v31B.add(v32B.add(v33B.add(v34B.add(v35B.add(v36B.add(v37B.add(v38B.add(v39B))))))));

        Value42A value42A = new Value42A();
        value42A.setDate(queryDate);
        value42A.setVerified(verified);
        value42A.setSession(session);
        value42A.calc();
        v42A = value42A.getValue();

        Value42B value42B = new Value42B();
        value42B.setDate(queryDate);
        value42B.setVerified(verified);
        value42B.setSession(session);
        value42B.calc();
        v42B = value42B.getValue();

        Value43A value43A = new Value43A();
        value43A.setDate(queryDate);
        value43A.setVerified(verified);
        value43A.setSession(session);
        value43A.calc();
        v43A = value43A.getValue();

        Value43B value43B = new Value43B();
        value43B.setDate(queryDate);
        value43B.setVerified(verified);
        value43B.setSession(session);
        value43B.calc();
        v43B = value43B.getValue();

        Value44A value44A = new Value44A();
        value44A.setDate(queryDate);
        value44A.setVerified(verified);
        value44A.setSession(session);
        value44A.calc();
        v44A = value44A.getValue();

        Value44B value44B = new Value44B();
        value44B.setDate(queryDate);
        value44B.setVerified(verified);
        value44B.setSession(session);
        value44B.calc();
        v44B = value44B.getValue();

        v46A = v42A.add(v43A.add(v44A));
        v46B = v42B.add(v43B.add(v44B));

        v47A = v41A.add(v46A);
        v47B = v41B.add(v46B);


        Value48A value48A = new Value48A();
        value48A.setDate(queryDate);
        value48A.setVerified(verified);
        value48A.setSession(session);
        value48A.calc();
        v48A = value48A.getValue();

        Value48B value48B = new Value48B();
        value48B.setDate(queryDate);
        value48B.setVerified(verified);
        value48B.setSession(session);
        value48B.calc();
        v48B = value48B.getValue();

        Value49A value49A = new Value49A();
        value49A.setDate(queryDate);
        value49A.setVerified(verified);
        value49A.setSession(session);
        value49A.calc();
        v49A = value49A.getValue();

        Value49B value49B = new Value49B();
        value49B.setDate(queryDate);
        value49B.setVerified(verified);
        value49B.setSession(session);
        value49B.calc();
        v49B = value49B.getValue();


        Value50A value50A = new Value50A();
        value50A.setDate(queryDate);
        value50A.setVerified(verified);
        value50A.setSession(session);
        value50A.calc();
        v50A = value50A.getValue();

        Value50B value50B = new Value50B();
        value50B.setDate(queryDate);
        value50B.setVerified(verified);
        value50B.setSession(session);
        value50B.calc();
        v50B = value50B.getValue();


        Value51A value51A = new Value51A();
        value51A.setDate(queryDate);
        value51A.setVerified(verified);
        value51A.setSession(session);
        value51A.calc();
        v51A = value51A.getValue();

        Value51B value51B = new Value51B();
        value51B.setDate(queryDate);
        value51B.setVerified(verified);
        value51B.setSession(session);
        value51B.calc();
        v51B = value51B.getValue();

        v52A = v48A.add(v49A.add(v50A.add(v51A)));
        v52B = v48B.add(v49B.add(v50B.add(v51B)));

        v53A = v47A.add(v52A);
        v53B = v47B.add(v52B);
    }

}
