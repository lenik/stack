package com.bee32.sem.chance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseChanceDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseChanceDialogBean.class);

    public ChooseChanceDialogBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                ChanceCriteria.subjectLike(searchPattern, true));
        searchPattern = null;
    }

}
