package com.bee32.sem.chance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseChanceDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseChanceDialogBean.class);

    String caption = "Please choose a party..."; // NLS: 选择用户或组

    public ChooseChanceDialogBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                ChanceCriteria.subjectLike(searchPattern, true));
        searchPattern = null;
    }

}
