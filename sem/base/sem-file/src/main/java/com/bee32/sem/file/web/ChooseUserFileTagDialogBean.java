package com.bee32.sem.file.web;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseUserFileTagDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseUserFileTagDialogBean.class);

    String caption = "Please choose a user file tag..."; // NLS: 选择用户或组
    String pattern;

    public ChooseUserFileTagDialogBean() {
        super(UserFileTagname.class, UserFileTagnameDto.class, 0);
    }

    @Override
    protected List<? extends ICriteriaElement> getCriteriaElements() {
        return Arrays.asList(//
                CommonCriteria.namedLike(pattern));
    }

    @Override
    public void submit() {
        logger.debug("Selected: " + getSelection());
    }

    @Override
    public void cancel() {
        logger.debug("Cancelled.");
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
