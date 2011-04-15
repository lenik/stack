package com.bee32.sem.process.verify.builtin;

import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.orm.util.EntityDto;

public class AllowListDto
        extends EntityDto<AllowList, Integer> {

    private static final long serialVersionUID = 1L;

    public static int RESPONSIBLES = 1;

    String name;
    List<PrincipalDto<?>> responsibles;

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

    public List<PrincipalDto<?>> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<PrincipalDto<?>> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    protected void _marshal(AllowList source) {
        name = source.getName();

        if (selection.contains(RESPONSIBLES))
            responsibles = PrincipalDto.marshalList(0, source.getResponsibles());
    }

    @Override
    protected void _unmarshalTo(AllowList target) {
        target.setName(name);

        if (selection.contains(RESPONSIBLES))
            throw new NotImplementedException("unmarshal responsible");
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        id = map.getInt("id");
        version = map.getInt("version");
        name = map.getString("name");
    }

    public static List<AllowListDto> marshalList(int selection, Iterable<? extends AllowList> entities) {
        return marshalList(AllowListDto.class, selection, entities);
    }

}
