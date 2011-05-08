package com.bee32.sem.file.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.sem.file.entity.LocalFileStore;
import com.bee32.sem.file.util.VariableType;

public class LocalFileStoreDto
        extends FileStoreDto<LocalFileStore> {

    private static final long serialVersionUID = 1L;

    VariableType prefixType;
    String path;

    @Override
    protected void _marshal(LocalFileStore source) {
        prefixType = source.getPrefixType();
        path = source.getPath();
    }

    @Override
    protected void _unmarshalTo(IEntityMarshalContext context, LocalFileStore target) {
        target.setPrefixType(prefixType);
        target.setPath(path);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        prefixType = VariableType.valueOf(map.getString("prefixType"));
        path = map.getString("path");
    }

    public VariableType getPrefixType() {
        return prefixType;
    }

    public void setPrefixType(VariableType prefixType) {
        this.prefixType = prefixType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
