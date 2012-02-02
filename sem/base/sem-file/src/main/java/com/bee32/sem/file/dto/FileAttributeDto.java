package com.bee32.sem.file.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.file.entity.FileAttribute;
import com.bee32.sem.frame.ui.IEnclosedObject;

public class FileAttributeDto
        extends EntityDto<FileAttribute, Long>
        implements IEnclosedObject<UserFileDto> {

    private static final long serialVersionUID = 1L;

    FileBlobDto blob;

    String name;
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
        name = source.getName();
        intVal = source.getIntVal();
        floatVal = source.getFloatVal();
        strVal = source.getStrVal();
    }

    @Override
    protected void _unmarshalTo(FileAttribute target) {
        merge(target, "blob", blob);
        target.setName(name);
        target.setIntVal(intVal);
        target.setFloatVal(floatVal);
        target.setStrVal(strVal);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        blob = new FileBlobDto().ref(map.getString("blob"));
        name = map.getString("name");
        intVal = map.getInt("intVal");
        floatVal = map.getDouble("floatVal");
        strVal = map.getString("strVal");
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(blob), name);
    }

    public FileBlobDto getBlob() {
        return blob;
    }

    public void setBlob(FileBlobDto blob) {
        if (blob == null)
            throw new NullPointerException("blob");
        this.blob = blob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
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
