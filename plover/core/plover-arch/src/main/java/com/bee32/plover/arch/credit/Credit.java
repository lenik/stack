package com.bee32.plover.arch.credit;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

import com.bee32.plover.arch.Component;

public class Credit
        extends Component
        implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Role -> Contributor
     */
    private final TreeMap<Subject, Collection<Contributor>> subjects;

    public Credit() {
        super("CREDIT");
        this.subjects = new TreeMap<Subject, Collection<Contributor>>();
    }

    public Collection<Subject> getSubjects() {
        return subjects.keySet();
    }

    public Collection<Contributor> getContributors(Subject subject) {
        Collection<Contributor> contributors = subjects.get(subject);
        if (contributors == null)
            contributors = Collections.emptyList();
        return contributors;
    }

}
