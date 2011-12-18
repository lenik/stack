package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.ox1.principal.PrincipalDto;
import com.bee32.sem.process.verify.builtin.AllowListPolicy;

public class AllowListPolicyDto
        extends AbstractVerifyPolicyDto<AllowListPolicy> {

    private static final long serialVersionUID = 1L;

    public static int RESPONSIBLES = 1;

    List<PrincipalDto> responsibles;

    public AllowListPolicyDto() {
        super();
    }

    public AllowListPolicyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(AllowListPolicy source) {
        if (selection.contains(RESPONSIBLES))
            responsibles = marshalList(PrincipalDto.class, 0, source.getResponsibles());
    }

    @Override
    protected void _unmarshalTo(AllowListPolicy target) {
        if (selection.contains(RESPONSIBLES))
            mergeSet(target, "responsibles", responsibles);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        if (selection.contains(RESPONSIBLES)) {
            responsibles = new ArrayList<PrincipalDto>();

            String[] _userIds = map.getStringArray("r_users");
            if (_userIds != null) {
                for (String _userId : _userIds) {
                    PrincipalDto responsible = new PrincipalDto().parseRef(_userId);
                    responsibles.add(responsible);
                }
            }
        }
    }

    public List<PrincipalDto> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<PrincipalDto> responsibles) {
        this.responsibles = responsibles;
    }

    static {
        EntityHelper.getInstance(AllowListPolicy.class).setSelection(RESPONSIBLES);
    }

}
