package com.bee32.sem.make.entity;

import java.util.ArrayList;
import java.util.List;

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

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 质量规范
 *
 * <ul>
 * <li>label = 规范名称
 * <li>description = 规范描述
 * </ul>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 1)
@DiscriminatorValue(value = "-")
@SequenceGenerator(name = "idgen", sequenceName = "qc_spec_seq", allocationSize = 1)
public class QCSpec
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 2000;

    String text;
    List<QCSpecParameter> parameters = new ArrayList<QCSpecParameter>();

    /**
     * 质量规范正文/备注
     */
    @Column(length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<QCSpecParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<QCSpecParameter> parameters) {
        if (parameters == null)
            throw new NullPointerException("parameters");
        this.parameters = parameters;
    }

}
