package com.bee32.sem.file.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.CEntityDto;
import com.bee32.sem.file.entity.FileAttribute;

public class FileAttributeDto
        extends CEntityDto<FileAttribute, Long> {

    private static final long serialVersionUID = 1L;

    FileBlobDto blob;

    String key;
    int intVal;
    double floatVal;
    String strVal;

    public FileAttributeDto() {
        super();
    }

    public FileAttributeDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(FileAttribute source) {
        blob = mref(FileBlobDto.class, source.getBlob());
        intVal = source.getIntVal();
        floatVal = source.getFloatVal();
        strVal = source.getStrVal();
    }

    @Override
    protected void _unmarshalTo(FileAttribute target) {
        merge(target, "blob", blob);
        target.setIntVal(intVal);
        target.setFloatVal(floatVal);
        target.setStrVal(strVal);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        blob = new FileBlobDto().ref(map.getString("blob"));
        intVal = map.getInt("intVal");
        floatVal = map.getDouble("floatVal");
        strVal = map.getString("strVal");
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(blob), key);
    }

    public FileBlobDto getBlob() {
        return blob;
    }

    public void setBlob(FileBlobDto blob) {
        if (blob == null)
            throw new NullPointerException("blob");
        this.blob = blob;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public double getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(double floatVal) {
        this.floatVal = floatVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

}
