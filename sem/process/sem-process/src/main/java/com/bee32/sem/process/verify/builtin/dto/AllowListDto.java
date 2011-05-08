package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.AllowList;

public class AllowListDto
        extends EntityDto<AllowList, Integer> {

    private static final long serialVersionUID = 1L;

    public static int RESPONSIBLES = 1;

    String name;
    String description;

    List<PrincipalDto> responsibles;

    public AllowListDto() {
        super();
    }

    public AllowListDto(AllowList source) {
        super(source);
    }

    public AllowListDto(int selection) {
        super(selection);
    }

    public AllowListDto(int selection, AllowList source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(AllowList source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(RESPONSIBLES))
            responsibles = marshalList(PrincipalDto.class, 0, source.getResponsibles());
    }

    @Override
    protected void _unmarshalTo(AllowList target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(RESPONSIBLES))
            mergeSet(target, "responsibles", responsibles);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        name = map.getString("name");
        description = map.getString("description");

        if (selection.contains(RESPONSIBLES)) {
            responsibles = new ArrayList<PrincipalDto>();

            String[] _userIds = map.getStringArray("r_users");
            if (_userIds != null) {
                for (String _userId : _userIds) {
                    long userId = Long.parseLong(_userId);
                    PrincipalDto responsible = new PrincipalDto().ref(userId);
                    responsibles.add(responsible);
                }
            }
        }
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

    public List<PrincipalDto> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<PrincipalDto> responsibles) {
        this.responsibles = responsibles;
    }

    public List<Long> getResponsibleIds() {
        return id(responsibles);
    }

}
