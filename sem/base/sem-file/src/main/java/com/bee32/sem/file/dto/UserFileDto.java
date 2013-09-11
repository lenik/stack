package com.bee32.sem.file.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.persistence.Transient;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.util.Mime;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.web.UserFileController;
import com.bee32.sem.people.dto.PartyDto;

public class UserFileDto
        extends UIEntityDto<UserFile, Long>
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    public static final int TAGS = 1;

    FileBlobDto fileBlob;
    UserFolderDto folder;
    FileName fileName;
    Date fileDate;
    Date expiredDate;
    List<UserFileTagnameDto> tags;
    UserDto operator;
    PartyDto party;

    public UserFileDto() {
        super();
    }

    public UserFileDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(UserFile source) {
        fileBlob = mref(FileBlobDto.class, source.getFileBlob());
        folder = mref(UserFolderDto.class, source.getFolder());
        setName(source.getName());
        fileDate = source.getFileDate();
        expiredDate = source.getExpiredDate();
        if (selection.contains(TAGS))
            tags = mrefList(UserFileTagnameDto.class, 0, source.getTags());
        operator = mref(UserDto.class, source.getOperator());
        party = mref(PartyDto.class, source.getParty());
    }

    @Override
    protected void _unmarshalTo(UserFile target) {
        merge(target, "fileBlob", fileBlob);
        merge(target, "folder", folder);
        target.setName(fileName.toString());
        target.setFileDate(fileDate);
        target.setExpiredDate(expiredDate);
        if (selection.contains(TAGS))
            mergeSet(target, "tags", tags);
        merge(target, "operator", operator);
        merge(target, "party", party);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        fileBlob = new FileBlobDto().ref(map.getString("fileBlob"));
        setName(map.getString("name"));
        fileDate = map.getDate("fileDate");
        expiredDate = map.getDate("expiredDate");

        if (selection.contains(TAGS)) {
            String[] _tags = map.getStringArray("tags");
            tags = new ArrayList<UserFileTagnameDto>();
            for (int i = 0; i < _tags.length; i++) {
                String _tag = _tags[i];
                int _tagL = Integer.parseInt(_tag);
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

    public UserFolderDto getFolder() {
        return folder;
    }

    public void setFolder(UserFolderDto folder) {
        this.folder = folder;
    }

    public String getLabelOrName() {
        if (label != null && !label.isEmpty())
            return label;
        else
            return getName();
    }

    public FileName getFileName() {
        return fileName;
    }

    @NLength(max = UserFile.NAME_LENGTH)
    public String getName() {
        return fileName.toString();
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.fileName = FileName.parse(name);
    }

    @Transient
    public String getPath() {
        UserFolderDto folder = getFolder();
        if (folder == null)
            return "";
        else
            return folder.getPath() + "/" + fileName;
    }

    public String getViewHref() {
        return getHref("view");
    }

    public String getDownloadHref() {
        return getHref("download");
    }

    public String getHref(String mode) {
        Location location = WEB_APP.join("/servlet/user-file/" + mode + "/" + id + "/" + fileName);
        String href = location.resolveAbsolute(ThreadHttpContext.getRequest());
        return href;
    }

    public String getImageHref() {
        Location iconLoc;

        String contentType = "file";

        String extension = fileName.getExtensionNoDot();
        Mime mime = Mime.getInstanceByExtension(extension);
        if (mime != null)
            contentType = mime.getContentType();

        if (contentType.startsWith("image/")) {
            iconLoc = WEB_APP.join(UserFileController.PREFIX + "/view.do?id=" + id);
        } else {
            iconLoc = ICON.join("obj16/elements_obj.gif");
        }

        String href = iconLoc.resolveAbsolute(ThreadHttpContext.getRequest());
        return href;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public synchronized List<UserFileTagnameDto> getTags() {
        return tags;
    }

    public synchronized void setTags(List<UserFileTagnameDto> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

    public String getTagsString() {
        if (!selection.contains(TAGS))
            return "(N/A)";

        if (tags.isEmpty())
            return "(没有标签)";

        StringBuilder sb = null;
        for (UserFileTagnameDto tag : tags) {
            if (sb == null)
                sb = new StringBuilder(tags.size() * 20);
            else
                sb.append(", ");
            sb.append(tag.getName());
        }
        return sb.toString();
    }

    public UserDto getOperator() {
        return operator;
    }

    public void setOperator(UserDto operator) {
        this.operator = operator;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

}
