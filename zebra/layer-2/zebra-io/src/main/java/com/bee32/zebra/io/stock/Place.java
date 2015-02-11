package com.bee32.zebra.io.stock;

import com.bee32.zebra.io.art.Dim3d;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoNode;
import com.tinylily.model.base.IdType;

/**
 * 库位
 */
@IdType(Integer.class)
public class Place
        extends CoNode<Place, Integer> {

    private static final long serialVersionUID = 1L;

    private PlaceUsage usage = PlaceUsage.INTERNAL;
    private Dim3d position = new Dim3d();
    private Dim3d bbox = new Dim3d();
    private Person party;
    private Organization partyOrg;

    public Place() {
        super();
    }

    public Place(Place parent) {
        super(parent);
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

    public void setPosition(Dim3d position) {
        if (position == null)
            throw new NullPointerException("position");
        this.position = position;
    }

    /**
     * 尺寸
     */
    public Dim3d getBbox() {
        return bbox;
    }

    public void setBbox(Dim3d bbox) {
        if (bbox == null)
            throw new NullPointerException("bbox");
        this.bbox = bbox;
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
