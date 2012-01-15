package com.bee32.sem.file.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.file.entity.UserFileTagname;

public class UserFileTagStatService
        extends DataService
        implements IUserFileTagStatService {

    Map<UserFileTagname, Long> stats;

    @Override
    public Map<UserFileTagname, Long> getStats() {
        if (stats == null) {
            synchronized (this) {
                if (stats == null) {
                    stats = loadStats();
                }
            }
        }
        return stats;
    }

    protected Map<UserFileTagname, Long> loadStats() {
        Map<UserFileTagname, Long> stats = new HashMap<UserFileTagname, Long>();
        List<UserFileTagname> tags = asFor(UserFileTagname.class).list();
        for (UserFileTagname tag : tags) {
            long initial = tag.getRefCount();
            stats.put(tag, initial);
        }
        return stats;
    }

    @Override
    public void addUsage(Collection<UserFileTagname> tags) {
        Map<UserFileTagname, Long> stats = getStats();
        for (UserFileTagname tag : tags) {
            Long n = stats.get(tag);
            if (n == null)
                n = 1L;
            else
                n++;
            stats.put(tag, n);
            tag.setRefCount(n.intValue());
        }
    }

    @Override
    public void removeUsage(Collection<UserFileTagname> tags) {
        Map<UserFileTagname, Long> stats = getStats();
        for (UserFileTagname tag : tags) {
            Long n = stats.get(tag);
            if (n != null)
                stats.put(tag, --n);
            tag.setRefCount(n.intValue());
        }
    }

}
