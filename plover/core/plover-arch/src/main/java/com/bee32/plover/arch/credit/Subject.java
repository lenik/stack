package com.bee32.plover.arch.credit;

import java.io.Serializable;

import com.bee32.plover.arch.Component;

public class Subject
        extends Component
        implements Comparable<Subject>, Serializable {

    private static final long serialVersionUID = 1L;

    private int rank;

    public Subject(String name, int rank) {
        super(name);
        this.rank = rank;
    }

    @Override
    public int compareTo(Subject o) {
        return rank - o.rank;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rank;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subject other = (Subject) obj;
        if (rank != other.rank)
            return false;
        return true;
    }

}
