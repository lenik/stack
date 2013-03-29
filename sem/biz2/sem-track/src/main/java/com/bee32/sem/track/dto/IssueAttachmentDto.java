package com.bee32.sem.track.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.file.dto.FileBlobDto;
import com.bee32.sem.track.entity.IssueAttachment;

public class IssueAttachmentDto
        extends UIEntityDto<IssueAttachment, Long>
        implements IEnclosedObject<IssueDto> {

    private static final long serialVersionUID = 1L;

    public static final int MREF = 1;

    private IssueDto issue;
    private FileBlobDto fileBlob;

    @Override
    public IssueDto getEnclosingObject() {
        return getIssue();
    }

    @Override
    public void setEnclosingObject(IssueDto enclosingObject) {
        setIssue(enclosingObject);
    }

    @Override
    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void _marshal(IssueAttachment source) {
        if (selection.contains(MREF)) {
            issue = mref(IssueDto.class, source.getIssue());
            fileBlob = mref(FileBlobDto.class, source.getFileBlob());
        }
    }

    @Override
    protected void _unmarshalTo(IssueAttachment target) {
        if (selection.contains(MREF)) {
            merge(target, "issue", issue);
            merge(target, "fileBlob", fileBlob);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    public FileBlobDto getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(FileBlobDto fileBlob) {
        if (fileBlob == null)
            throw new NullPointerException("fileBlob");
        this.fileBlob = fileBlob;
    }

}
