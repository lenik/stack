package com.bee32.sem.make.entity;

import java.util.ArrayList;
import java.util.List;

import javax.free.NoSuchKeyException;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.MomentInterval;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 1)
@DiscriminatorValue(value = "-")
@SequenceGenerator(name = "idgen", sequenceName = "qc_result_seq", allocationSize = 1)
public class QCResult
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int MEMO_LENGTH = 1000;

    List<QCResultParameter> parameters = new ArrayList<QCResultParameter>();
    String memo;

    /**
     * 质检得到的实际参数
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<QCResultParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<QCResultParameter> parameters) {
        if (parameters == null)
            throw new NullPointerException("parameters");
        this.parameters = parameters;
    }

    public QCResultParameter findParameter(QCSpecParameter key) {
        if (key == null)
            throw new NullPointerException("key");
        for (QCResultParameter p : parameters) {
            QCSpecParameter pSpec = p.getKey();
            if (key.equals(pSpec))
                return p;
        }
        return null;
    }

    public String getParameter(QCSpecParameter key, String defaultValue) {
        QCResultParameter found = findParameter(key);
        if (found == null)
            return defaultValue;
        else
            return found.getValue();
    }

    public String getParameter(QCSpecParameter key) {
        return getParameter(key, null);
    }

    public void setParameter(QCSpecParameter key, String value) {
        QCResultParameter found = findParameter(key);
        if (found == null)
            throw new NoSuchKeyException(key.getEntryLabel());
        found.setValue(value);
    }

    /**
     * 质检备注
     */
    @Column(length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
