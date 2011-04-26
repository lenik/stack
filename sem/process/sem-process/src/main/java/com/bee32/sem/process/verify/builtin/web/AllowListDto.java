package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
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

    public AllowListDto(AllowList source, int selection) {
        super(source, selection);
    }

    @Override
    protected void _marshal(AllowList source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(RESPONSIBLES))
            responsibles = marshalList(PrincipalDto.class, 0, source.getResponsibles());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, AllowList target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(RESPONSIBLES))
            with(context, target).unmarshalSet(responsiblesProperty, responsibles);
    }

    @Override
    public void _parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {

        if (map.containsKey("id") && !map.getString("id").isEmpty())
            id = map.getInt("id");

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

    static final PropertyAccessor<AllowList, Set<Principal>> responsiblesProperty = new PropertyAccessor<AllowList, Set<Principal>>(
            List.class) {

        @Override
        public Set<Principal> get(AllowList entity) {
            return entity.getResponsibles();
        }

        @Override
        public void set(AllowList entity, Set<Principal> responsibles) {
            entity.setResponsibles(responsibles);
        }

    };

}
