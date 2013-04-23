package com.bee32.sem.file.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseUserFileDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseUserFileDialogBean.class);

    boolean pending = true;

    public ChooseUserFileDialogBean() {
        super(UserFile.class, UserFileDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (pending)
            elements.add(new IsNull("chance.id"));
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

}
