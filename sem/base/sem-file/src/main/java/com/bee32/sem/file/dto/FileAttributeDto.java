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

    UserFileDto file;

    String name;
    int intVal;
    double floatVal;
    String strVal;

    public FileAttributeDto() {
        super();
    }

    public FileAttributeDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(FileAttribute source) {
        file = mref(UserFileDto.class, source.getFile());
        name = source.getName();
        intVal = source.getIntVal();
        floatVal = source.getFloatVal();
        strVal = source.getStrVal();
    }

    @Override
    protected void _unmarshalTo(FileAttribute target) {
        merge(target, "file", file);
        target.setName(name);
        target.setIntVal(intVal);
        target.setFloatVal(floatVal);
        target.setStrVal(strVal);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        file = new UserFileDto().ref(map.getLong("file"));
        name = map.getString("name");
        intVal = map.getInt("intVal");
        floatVal = map.getDouble("floatVal");
        strVal = map.getString("strVal");
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(file), name);
    }

    @Override
    public UserFileDto getEnclosingObject() {
        return getFile();
    }

    @Override
    public void setEnclosingObject(UserFileDto enclosingObject) {
        setFile(enclosingObject);
    }

    public UserFileDto getFile() {
        return file;
    }

    public void setFile(UserFileDto file) {
        this.file = file;
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
