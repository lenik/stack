package com.bee32.plover.ox1.principal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 合并安全主体及其继承的（被授予的）角色、所属组集合等，形成最小闭包集，并将这个集合按照一定的顺序排列后，得到的主体的蕴含列表称为原主体的<em>规范蕴含展开式</em>。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "principal_responsible_seq", allocationSize = 1)
public class PrincipalResponsible
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    private Principal principal;
    private Principal responsible;

    public PrincipalResponsible() {
    }

    public PrincipalResponsible(Principal principal, Principal responsible) {
        this.principal = principal;
        this.responsible = responsible;
    }

    @ManyToOne(optional = false)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @ManyToOne(optional = false)
    public Principal getResponsible() {
        return responsible;
    }

    public void setResponsible(Principal responsible) {
        this.responsible = responsible;
    }

}
