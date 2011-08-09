package com.bee32.sem.file.dto;

import java.util.HashSet;
import java.util.Set;

import javax.free.FilePath;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.util.Mime;
import com.bee32.sem.file.entity.UserFile;

public class UserFileDto
        extends EntityDto<UserFile, Long>
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    public static final int TAGS = 1;

    FileBlobDto fileBlob;

    String origPath;
    String fileName;
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
        fileBlob = mref(FileBlobDto.class, source.getFileBlob());
        origPath = source.getOrigPath();
        fileName = source.getFileName();
        subject = source.getSubject();

        if (selection.contains(TAGS))
            tags = marshalSet(UserFileTagnameDto.class, 0, source.getTags(), true);
    }

    @Override
    protected void _unmarshalTo(UserFile target) {
        merge(target, "fileBlob", fileBlob);
        target.setOrigPath(origPath);
        target.setFileName(fileName);
        target.setSubject(subject);

        if (selection.contains(TAGS))
            mergeSet(target, "tags", tags);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        fileBlob = new FileBlobDto().ref(map.getString("fileBlob"));
        origPath = map.getString("origPath");
        fileName = map.getString("filename");
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        this.fileName = fileName;
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

    public String getImageHref() {
        Location iconLoc;

        String mimeType = "file";

        String extension = FilePath.getExtension(origPath, false);
        Mime mime = Mime.getInstanceByExtension(extension);
        if (mime != null)
            mimeType = mime.getName();

        if (mimeType.startsWith("image/")) {
            iconLoc = WEB_APP.join("/3/15/1/6/file/view.do?id=" + id);
        } else {
            iconLoc = ICON.join("obj16/elements_obj.gif");
        }

        String href = iconLoc.resolve(ThreadHttpContext.getRequest());
        return href;
    }

}
