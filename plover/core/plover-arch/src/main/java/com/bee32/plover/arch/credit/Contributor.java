package com.bee32.plover.arch.credit;

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

    public int compareTo(Contributor o) {
        return (int) Math.signum(rank - o.rank);
    }

}
