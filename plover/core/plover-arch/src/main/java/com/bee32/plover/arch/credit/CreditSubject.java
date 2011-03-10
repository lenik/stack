package com.bee32.plover.arch.credit;

import java.io.Serializable;

import com.bee32.plover.arch.Component;

/**
 *
 */
public class CreditSubject
        extends Component
        implements Comparable<CreditSubject>, Serializable {

    private static final long serialVersionUID = 1L;

    private int rank;

    public CreditSubject(int rank) {
        super();
    }

    public CreditSubject(String name, int rank) {
        super(name);
        this.rank = rank;
    }

    @Override
    public int compareTo(CreditSubject o) {
        return rank - o.rank;
    }

    @Override
    public boolean equalsSpecific(Component obj) {
        CreditSubject other = (CreditSubject) obj;
        if (rank != other.rank)
            return false;
        return true;
    }

    @Override
    protected int hashCodeSpecific() {
        return rank;
    }

    public static final CreditSubject Executor = new CreditSubject("Executor", 1);
    public static final CreditSubject Director = new CreditSubject("Director", 1);

    public static final CreditSubject SystemArchitect = new CreditSubject("System Architect", 10);

    public static final CreditSubject SoftwareDesigner = new CreditSubject("Software Designer", 30);
    public static final CreditSubject Programmer = new CreditSubject("Programmer", 31);

    public static final CreditSubject DatabaseDesigner = new CreditSubject("Database Designer", 60);
    public static final CreditSubject DatabaseMaintainer = new CreditSubject("Database Maintainer", 61);

    public static final CreditSubject ArtificialIntelligence = new CreditSubject("Artificial Intelligence", 120);

    public static final CreditSubject BackgroundMusic = new CreditSubject("BackgroundMusic", 310);
    public static final CreditSubject SoundEffects = new CreditSubject("SoundEffects", 301);

    public static final CreditSubject Contributor = new CreditSubject("Contributor", 10000);

}
