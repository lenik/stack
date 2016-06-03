package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.meta.decl.ObjectType;

import com.bee32.zebra.io.stock.Place;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 仓库、楼层、区间、货架、单元格等按层次结构组织的空间区域。
 * <p>
 * 区域可以用来堆放存货。
 * 
 * @label 区域/位置
 * 
 * @rel art/: 管理物料
 */
@ObjectType(Place.class)
public class PlaceIndex
        extends QuickIndex<Place> {

}
