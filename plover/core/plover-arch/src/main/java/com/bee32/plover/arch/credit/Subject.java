package com.bee32.plover.arch.credit;

import java.io.Serializable;

import com.bee32.plover.arch.Component;

public class Subject
        extends Component
        implements Comparable<Subject>, Serializable {

    private static final long serialVersionUID = 1L;

    private int rank;

    public Subject(int rank) {
        super();
    }

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

    public static final Subject Executor = new Subject("Executor", 1);
    public static final Subject Director = new Subject("Director", 1);

    public static final Subject SystemArchitect = new Subject("System Architect", 10);

    public static final Subject SoftwareDesigner = new Subject("Software Designer", 30);
    public static final Subject Programmer = new Subject("Programmer", 31);

    public static final Subject DatabaseDesigner = new Subject("Database Designer", 60);
    public static final Subject DatabaseMaintainer = new Subject("Database Maintainer", 61);

    public static final Subject ArtificialIntelligence = new Subject("Artificial Intelligence", 120);

    public static final Subject BackgroundMusic = new Subject("BackgroundMusic", 310);
    public static final Subject SoundEffects = new Subject("SoundEffects", 301);

    public static final Subject Contributor = new Subject("Contributor", 10000);

}
