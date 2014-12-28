package com.bee32.zebra.io.stock;

import com.bee32.zebra.io.art.Dim3d;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoNode;

public class Place
        extends CoNode<Place> {

    private static final long serialVersionUID = 1L;

    private int id;
    private PlaceUsage usage = PlaceUsage.INTERNAL;
    private final Dim3d position = new Dim3d();
    private final Dim3d bbox = new Dim3d();
    private Person party;
    private Organization partyOrg;

    public Place() {
        super();
    }

    public Place(Place parent) {
        super(parent);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 用途
     */
    public PlaceUsage getUsage() {
        return usage;
    }

    public void setUsage(PlaceUsage usage) {
        if (usage == null)
            throw new NullPointerException("usage");
        this.usage = usage;
    }

    /**
     * 位置
     */
    public Dim3d getPosition() {
        return position;
    }

    /**
     * 尺寸
     */
    public Dim3d getBbox() {
        return bbox;
    }

    /**
     * 代理人
     */
    public Person getParty() {
        return party;
    }

    public void setParty(Person party) {
        this.party = party;
    }

    /**
     * 代理企业
     */
    public Organization getPartyOrg() {
        return partyOrg;
    }

    public void setPartyOrg(Organization partyOrg) {
        this.partyOrg = partyOrg;
    }

}
