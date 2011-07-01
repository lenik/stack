package com.bee32.sem.file.dto;

import java.util.HashSet;
import java.util.Set;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.file.entity.UserFile;

public class UserFileDto
        extends EntityDto<UserFile, Long> {

    private static final long serialVersionUID = 1L;

    public static final int TAGS = 1;

    UserDto owner;
    FileBlobDto fileBlob;

    String origPath;
    String filename;
    String subject;

    Set<UserFileTagnameDto> tags;

    public UserFileDto() {
        super();
    }

    public UserFileDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UserFile source) {
        owner = mref(UserDto.class, 0, source.getOwner());
        fileBlob = mref(FileBlobDto.class, 0, source.getFileBlob());
        origPath = source.getOrigPath();
        filename = source.getFilename();
        subject = source.getSubject();

        if (selection.contains(TAGS))
            tags = marshalSet(UserFileTagnameDto.class, 0, source.getTags(), true);
    }

    @Override
    protected void _unmarshalTo(UserFile target) {
        merge(target, "owner", owner);
        merge(target, "fileBlob", fileBlob);
        target.setOrigPath(origPath);
        target.setFilename(filename);
        target.setSubject(subject);

        if (selection.contains(TAGS))
            mergeSet(target, "tags", tags);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        owner = new UserDto().ref(map.getString("owner"));
        fileBlob = new FileBlobDto().ref(map.getString("fileBlob"));
        origPath = map.getString("origPath");
        filename = map.getString("filename");
        subject = map.getString("subject");

        if (selection.contains(TAGS)) {
            String[] _tags = map.getStringArray("tags");
            tags = new HashSet<UserFileTagnameDto>();
            for (int i = 0; i < _tags.length; i++) {
                String _tag = _tags[i];
                long _tagL = Long.parseLong(_tag);
                UserFileTagnameDto tag = new UserFileTagnameDto().ref(_tagL);
                tags.add(tag);
            }
        }
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        if (owner == null)
            throw new NullPointerException("owner");
        this.owner = owner;
    }

    public FileBlobDto getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(FileBlobDto fileBlob) {
        if (fileBlob == null)
            throw new NullPointerException("fileBlob");
        this.fileBlob = fileBlob;
    }

    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        if (filename == null)
            throw new NullPointerException("filename");
        this.filename = filename;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<UserFileTagnameDto> getTags() {
        return tags;
    }

    public void setTags(Set<UserFileTagnameDto> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

}
