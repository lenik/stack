package com.bee32.sem.chance.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.arch.bean.BeanPropertyAccessor;
import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;
import com.bee32.sem.world.thing.AbstractItemList;

/**
 * 选型
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "lectotype_seq", allocationSize = 1)
public class Lectotype
    extends AbstractItemList<LectotypeItem>
    implements DecimalConfig, IVerifiable<ISingleVerifierWithNumber> {

    private static final long serialVersionUID = 1L;

    Chance chance;

    List<LectotypeItem> items;

    public Lectotype() {
        setVerifyContext(new SingleVerifierWithNumberSupport());
    }

    /**
     * 对应机会
     */
    @OneToOne(optional = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<LectotypeItem> getItems() {
        return items;
    }

    public void setItems(List<LectotypeItem> items) {
        this.items = items;
    }


    public static final IPropertyAccessor<BigDecimal> nativeTotalProperty = BeanPropertyAccessor.access(//
            Lectotype.class, "nativeTotal");

    SingleVerifierWithNumberSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this, nativeTotalProperty, "金额");
    }

}
