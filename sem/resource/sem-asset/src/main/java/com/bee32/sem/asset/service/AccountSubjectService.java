package com.bee32.sem.asset.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.dict.DtoCodeTreeBuilder;
import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;

public class AccountSubjectService
        extends DataService {

    @Transactional(readOnly = true)
    public PoNode<?> loadSubjectTree() {
        List<AccountSubject> subjects = asFor(AccountSubject.class).list(Order.asc("id"));
        List<AccountSubjectDto> subjectDtos = DTOs.mrefList(AccountSubjectDto.class, -1, subjects);
        DtoCodeTreeBuilder ctb = new DtoCodeTreeBuilder();
        ctb.learn(subjectDtos);
        ctb.reduce();
        PoNode<?> root = ctb.getRoot();
        return root;
    }

}
