package com.bee32.sem.file.service;

import java.util.Collection;
import java.util.Map;

import com.bee32.sem.file.entity.UserFileTagname;

public interface IUserFileTagStatService {

    Map<UserFileTagname, Long> getStats();

    void addUsage(Collection<UserFileTagname> tags);

    void removeUsage(Collection<UserFileTagname> tags);

}
