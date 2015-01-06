package com.bee32.zebra.io.stock;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.io.art.Dim3d;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoNode;
import com.tinylily.model.base.IdType;
import com.tinylily.model.sea.QVariantMap;

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

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        usage = map.getPredef(PlaceUsage.class, "usage", usage);
        position.dx = map.getDouble("position_dx");
        position.dy = map.getDouble("position_dy");
        position.dz = map.getDouble("position_dz");
        bbox.dx = map.getDouble("bbox_dx");
        bbox.dy = map.getDouble("bbox_dy");
        bbox.dz = map.getDouble("bbox_dz");
        party = map.getIntIdRef("party", new Person());
        partyOrg = map.getIntIdRef("partyOrg", new Organization());
    }

}
