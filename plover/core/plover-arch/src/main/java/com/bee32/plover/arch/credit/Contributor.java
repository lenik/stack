package com.bee32.plover.arch.credit;

import javax.free.Nullables;

import com.bee32.plover.arch.Component;

public class Contributor
        extends Component
        implements Comparable<Contributor> {

    private double rank;

    private String email;

    private String organization;
    private String role;

    public Contributor() {
    }

    public Contributor(String name) {
        super(name);
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        Contributor other = (Contributor) obj;

        if (rank != other.rank)
            return false;

        if (!Nullables.equals(email, other.email))
            return false;

        if (!Nullables.equals(organization, other.organization))
            return false;

        if (!Nullables.equals(role, other.role))
            return false;

        return true;
    }

    @Override
    protected int hashCodeSpecific() {
        int hash = Double.valueOf(rank).hashCode();

        if (email != null)
            hash += email.hashCode();

        if (organization != null)
            hash += organization.hashCode();

        if (role != null)
            hash += role.hashCode();

        return hash;
    }

    public int compareTo(Contributor o) {
        return (int) Math.signum(rank - o.rank);
    }

}
