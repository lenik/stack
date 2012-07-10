package com.bee32.sem.service;

import com.bee32.sem.people.entity.Org;

/**
 * 在所有的org中取得本公司，如果有多个，则只取一个，从逻辑上讲，用户应该只输入一个employee为true的org
 * @author jack
 *
 */
public interface IPeopleService {
    public Org getSelfOrg();
}
