package com.bee32.zebra.erp.stock;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoNode;

public class Cell
        extends CoNode<Cell> {

    private static final long serialVersionUID = 1L;

    private int id;
    private CellUsage usage = CellUsage.INTERNAL;
    private final Dim3d bbox = new Dim3d();
    private Person party;
    private Organization partyOrg;

    public Cell() {
        super();
    }

    public Cell(Cell parent) {
        super(parent);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CellUsage getUsage() {
        return usage;
    }

    public void setUsage(CellUsage usage) {
        if (usage == null)
            throw new NullPointerException("usage");
        this.usage = usage;
    }

    public Dim3d getBbox() {
        return bbox;
    }

    public Person getParty() {
        return party;
    }

    public void setParty(Person party) {
        this.party = party;
    }

    public void setPartyId(int partyId) {
        (this.party = new Person()).setId(partyId);
    }

    public Organization getPartyOrg() {
        return partyOrg;
    }

    public void setPartyOrg(Organization partyOrg) {
        this.partyOrg = partyOrg;
    }

    public void setPartyOrg(int partyOrgId) {
        (this.partyOrg = new Organization()).setId(partyOrgId);
    }

}
