package com.bee32.sem.file.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.file.entity.UserFolder;

public class UserFolderDto extends
        TreeEntityDto<UserFolder, Integer, UserFolderDto> {
    private static final long serialVersionUID = 1L;

    public static final int FILES = 0x01000000;

    String name;
    String path;
    List<UserFileDto> files;

    int fileCount;

    @Override
    protected void _marshal(UserFolder source) {
        this.name = source.getName();
        this.path = source.getPath();
        if (selection.contains(FILES))
            this.files = mrefList(UserFileDto.class, -1, source.getFiles());
        else
            this.files = Collections.emptyList();

        this.fileCount = source.getFileCount();

    }

    @Override
    protected void _unmarshalTo(UserFolder target) {
        target.setName(name);
        target.setPath(path);

        if (selection.contains(FILES))
            mergeList(target, "files", files);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NLength(max = UserFolder.PATH_LENGTH)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<UserFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<UserFileDto> files) {
        this.files = files;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getTotalFileCount() {
        int total = fileCount;
        for (UserFolderDto child : getChildren())
            total += child.getFileCount();
        return total;
    }

}
