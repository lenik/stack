package com.bee32.zebra.oa.file.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.bas.t.range.IntRange;
import net.bodz.bas.t.range.LongRange;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.QVariantMap;
import net.bodz.lily.model.mx.CoMessageMask;

/**
 * @see com.bee32.zebra.oa.file.FileInfo
 */
public class FileInfoMask
        extends CoMessageMask {

    public LongRange sizeRange;
    public Integer orgId;
    public Integer personId;

    public IntRange downloadCountRange;
    public DoubleRange valueRange;

    public boolean noOrg;
    public boolean noPerson;
    public boolean noTag;

    @Override
    public void readObject(IVariantMap<String> _map)
            throws ParseException {
        super.readObject(_map);
        QVariantMap<String> map = QVariantMap.from(_map);
        sizeRange = map.getLongRange("sizes", sizeRange);
        orgId = map.getInt("org", orgId);
        personId = map.getInt("person", personId);
        downloadCountRange = map.getIntRange("downloads", downloadCountRange);
        valueRange = map.getDoubleRange("values", valueRange);

        noOrg = map.getBoolean("no-org");
        noPerson = map.getBoolean("no-person");
    }

}
