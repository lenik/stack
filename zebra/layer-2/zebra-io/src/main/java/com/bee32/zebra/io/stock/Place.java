package com.bee32.zebra.io.stock;

import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.CoNode;

import com.bee32.zebra.io.art.Dim3d;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;

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
     * 
     * 说明该空间区域的用途，如企业内部存放半成品的（<code>企业</code>）， 存放来自于供应商的原材料（<code>供应商</code>）， 存放待送往客户的产成品（
     * <code>客户</code>）， 存放废料的（<code>废品</code>）等。
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
     * 
     * 用三维笛卡尔坐标系表示的空间位置，对齐到库位的基点。
     * <p>
     * 库位的基点通常选择库位底部的左下角，或垂直于地球表面向下的底部的正西南角。
     * <p>
     * 坐标轴的原点通常选择或总仓库一层地面的正西南角，或地球的中心点（用经纬度表示的数值）。
     * <p>
     * 坐标的单位可以自行设定，通常使用<code>米</code>，或<code>经度</code>(X)、<code>纬度</code>(Y)。
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
     * 
     * 可以用来容纳物件的空间尺寸。
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
     * 
     * 说明这个库位是为指定代理人（供应商或客户）服务的，专门用来存放该代理人的货品。
     */
    public Person getParty() {
        return party;
    }

    public void setParty(Person party) {
        this.party = party;
    }

    /**
     * 代理企业
     * 
     * 说明这个库位是为指定企业（供货商或客户）服务的，专门用来存放该企业的货品。
     */
    public Organization getPartyOrg() {
        return partyOrg;
    }

    public void setPartyOrg(Organization partyOrg) {
        this.partyOrg = partyOrg;
    }

}
