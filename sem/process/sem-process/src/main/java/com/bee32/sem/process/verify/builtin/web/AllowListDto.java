package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.AllowList;

public class AllowListDto
        extends EntityDto<AllowList, Integer> {

    private static final long serialVersionUID = 1L;

    public static int RESPONSIBLES = 1;

    String name;
    String description;

    List<Long> responsibleIds;
    List<String> responsibleNames;

    public AllowListDto() {
        super();
    }

    public AllowListDto(AllowList source) {
        super(source);
    }

    public AllowListDto(int selection) {
        super(selection);
    }

    public AllowListDto(AllowList source, int selection) {
        super(source, selection);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getResponsibleIds() {
        return responsibleIds;
    }

    public void setResponsibleIds(List<Long> responsibleIds) {
        this.responsibleIds = responsibleIds;
    }

    public List<String> getResponsibleNames() {
        return responsibleNames;
    }

    public void setResponsibleNames(List<String> responsibleNames) {
        this.responsibleNames = responsibleNames;
    }

    @Override
    protected void _marshal(AllowList source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(RESPONSIBLES)) {
            responsibleIds = new ArrayList<Long>();
            responsibleNames = new ArrayList<String>();
            for (Principal responsible : source.getResponsibles()) {
                responsibleIds.add(responsible.getId());
                responsibleNames.add(responsible.getName());
            }
        }
    }

    @Override
    protected void _unmarshalTo(AllowList target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(RESPONSIBLES))
            throw new NotImplementedException("unmarshal responsible");
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        if (map.containsKey("id") && !map.getString("id").isEmpty())
            id = map.getInt("id");
        if (map.containsKey("version") && !map.getString("version").isEmpty())
            version = map.getInt("version");

        name = map.getString("name");
        description = map.getString("description");

        if (selection.contains(RESPONSIBLES)) {
            responsibleIds = new ArrayList<Long>();
            String[] rUsers = map.getStringArray("r_users");
            if (rUsers != null) {
                for (String rUser : rUsers) {
                    long rUserId = Long.parseLong(rUser);
                    responsibleIds.add(rUserId);
                }
            }
        }
    }

    public static List<AllowListDto> marshalList(int selection, Iterable<? extends AllowList> entities) {
        return marshalList(AllowListDto.class, selection, entities);
    }

}
