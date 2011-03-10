package com.bee32.plover.arch.credit;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.credit.builtin.Lenik;

public class Credit
        extends Component
        implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Subject -> Contributors
     */
    private final TreeMap<CreditSubject, Collection<Contributor>> subjects;

    public Credit() {
        super("CREDIT");
        this.subjects = new TreeMap<CreditSubject, Collection<Contributor>>();
    }

    public Collection<CreditSubject> getSubjects() {
        return subjects.keySet();
    }

    public Collection<Contributor> getContributors(CreditSubject subject) {
        Collection<Contributor> contributors = subjects.get(subject);
        if (contributors == null)
            contributors = Collections.emptyList();
        return contributors;
    }

    public synchronized void addContributor(CreditSubject subject, Contributor contributor) {
        Collection<Contributor> contributors = subjects.get(subject);
        if (contributors == null) {
            contributors = new TreeSet<Contributor>();
            subjects.put(subject, contributors);
        }
        contributors.add(contributor);
    }

    public static final Credit dummy;
    static {
        dummy = new Credit();
        dummy.addContributor(CreditSubject.SystemArchitect, Lenik.getLenik());
    }

}
