package com.bee32.zebra.oa.file.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.bas.t.range.IntRange;
import net.bodz.bas.t.range.LongRange;

import com.tinylily.model.mx.base.CoMessageCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.oa.file.FileInfo
 */
public class FileInfoCriteria
        extends CoMessageCriteria {

    public LongRange sizeRange;
    public Integer orgId;
    public Integer personId;
    public Integer tagId;
    public Set<String> tags;

    public IntRange downloadCountRange;
    public DoubleRange valueRange;

    public boolean noOrg;
    public boolean noPerson;
    public boolean noTag;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        sizeRange = map.getLongRange("sizes", sizeRange);
        orgId = map.getInt("org", orgId);
        personId = map.getInt("person", personId);
        tagId=map.getInt("tag", tagId);
        downloadCountRange = map.getIntRange("downloads", downloadCountRange);
        valueRange = map.getDoubleRange("values", valueRange);
        
        String tagsStr = map.getString("tags");
        if (tagsStr != null)
            tags = new TreeSet<String>(Arrays.asList(tagsStr.split(",")));
        
        noOrg=map.getBoolean("no-org");
        noPerson=map.getBoolean("no-person");
        noTag=map.getBoolean("no-tag");
    }

}
