package com.bee32.sem.people.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(Party.class)
public abstract class AbstractPartyAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<String> selectedTagsToAdd;
    String selectedTagId;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    AbstractPartyAdminBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    public List<String> getSelectedTagsToAdd() {
        return selectedTagsToAdd;
    }

    public void setSelectedTagsToAdd(List<String> selectedTagsToAdd) {
        this.selectedTagsToAdd = selectedTagsToAdd;
    }

    public String getSelectedTagId() {
        return selectedTagId;
    }

    public void setSelectedTagId(String selectedTagId) {
        this.selectedTagId = selectedTagId;
    }

    public void addTags() {
        PartyDto party = getOpenedObject();

        if (party == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        if (party.getTags() == null) {
            List<PartyTagnameDto> tags = new ArrayList<PartyTagnameDto>();
            party.setTags(tags);
        }
        for (String tagId : selectedTagsToAdd) {
            PartyTagname tag = ctx.data.getOrFail(PartyTagname.class, tagId);
            PartyTagnameDto t = DTOs.mref(PartyTagnameDto.class, tag);

            if (!party.getTags().contains(t))
                party.getTags().add(t);
        }
    }

    public void deleteTag() {
        PartyDto party = getOpenedObject();

        if (party == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        for (PartyTagnameDto t : party.getTags()) {
            if (t.getId().equals(selectedTagId)) {
                party.getTags().remove(t);
                return;
            }
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                PeopleCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

    public void addInterestRestriction() {
        addSearchFragment("兴趣含有 " + searchPattern, //
                PeopleCriteria.interestLike(searchPattern));
        searchPattern = null;
    }

    public void addPhoneRestriction() {
        addSearchFragment("电话含有 " + searchPattern, //
                PeopleCriteria.customers());
        searchPattern = null;
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<ContactDto> contactsMBean = ListMBean.fromEL(this, //
            "openedObject.contacts", ContactDto.class);

    public ListMBean<ContactDto> getContactsMBean() {
        return contactsMBean;
    }

}
