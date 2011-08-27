package com.bee32.plover.ox1.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class PrincipalImplication {

    private final List<? extends IPrincipal> principals;

    public PrincipalImplication() {
        principals = new ArrayList<IPrincipal>();
    }

    public PrincipalImplication(IPrincipal... principals) {
        if (principals == null)
            throw new NullPointerException("principals");
        this.principals = Arrays.asList(principals);
    }

    public PrincipalImplication(Collection<? extends IPrincipal> principals) {
        if (principals == null)
            throw new NullPointerException("principals");
        this.principals = new ArrayList<IPrincipal>(principals);
    }

    public int getPrincipalCount() {
        return principals.size();
    }

    public IPrincipal getPrincipal(int index) {
        return principals.get(index);
    }

    /**
     * 合并安全主体及其继承的（被授予的）角色、所属组集合等，形成最小闭包集，并将这个集合按照一定的顺序排列后，得到的主体的蕴含列表称为原主体的<em>规范蕴含展开式</em> (
     * {@link PrincipalImplication})。
     */
    public static PrincipalImplication expandImplication(IPrincipal principal, boolean includeRoles,
            boolean includeGroups) {
        List<IPrincipal> lub = new ArrayList<IPrincipal>();
        return new PrincipalImplication(lub);
    }

}
