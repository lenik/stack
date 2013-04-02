package com.bee32.sem.track.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.track.entity.IssueHref;

/**
 * 事件参考链接
 */
public class IssueHrefDto
        extends UIEntityDto<IssueHref, Long>
        implements IEnclosedObject<IssueDto> {

    private static final long serialVersionUID = 1L;

    private IssueDto issue;
    private String url;

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
    protected void _marshal(IssueHref source) {
        issue = mref(IssueDto.class, source.getIssue());
        url = source.getUrl();
    }

    @Override
    protected void _unmarshalTo(IssueHref target) {
        merge(target, "issue", issue);
        target.setUrl(url);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    // @NotNullId
    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        this.issue = issue;
    }

    @NLength(max = IssueHref.URL_LENGTH)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null)
            throw new NullPointerException("url");
        this.url = TextUtil.normalizeSpace(url);
    }

}
