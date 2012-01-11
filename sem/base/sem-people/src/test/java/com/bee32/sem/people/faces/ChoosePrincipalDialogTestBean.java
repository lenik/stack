package com.bee32.sem.people.faces;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.people.web.ChoosePrincipalDialogListener;

public class ChoosePrincipalDialogTestBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<PrincipalDto> principals = new ArrayList<PrincipalDto>();
    PrincipalDto selection;

    public List<PrincipalDto> getPrincipals() {
        return principals;
    }

    public PrincipalDto getSelection() {
        return selection;
    }

    public void setSelection(PrincipalDto selection) {
        this.selection = selection;
    }

    public ChoosePrincipalDialogListener getChoosePrincipalDialogListener() {
        return new ChoosePrincipalDialogListener() {
            @Override
            public void process(List<?> selection) {
                for (Object item : selection) {
                    PrincipalDto principal = (PrincipalDto) item;
                    principals.add(principal);
                }
            }
        };
    }

}
