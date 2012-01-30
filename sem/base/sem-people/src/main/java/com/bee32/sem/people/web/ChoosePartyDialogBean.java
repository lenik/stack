package com.bee32.sem.people.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

public class ChoosePartyDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePartyDialogBean.class);

    String caption = "Please choose a party..."; // NLS: 选择用户或组
    String stereo;
    Integer tagId;

    public ChoosePartyDialogBean() {
        super(Party.class, PartyDto.class, 0);
        // addSearchFragment("类型为", User.class);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        if (tagId != null) {
            elements.add(new Alias("tags", "tag"));
            elements.add(new Equals("tag.id", tagId));
        }
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setStereo(String stereo) {
        if (stereo == null)
            stereo = "";
        switch (stereo) {
        case "":
        case "-":
            entityClass = Party.class;
            dtoClass = PartyDto.class;
            break;
        case "PER":
            entityClass = Person.class;
            dtoClass = PersonDto.class;
            break;
        case "ORG":
            entityClass = Org.class;
            dtoClass = OrgDto.class;
            break;
        default:
            throw new IllegalArgumentException("Bad stereo: " + stereo);
        }
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
        refreshRowCount();
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, PeopleCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

}
