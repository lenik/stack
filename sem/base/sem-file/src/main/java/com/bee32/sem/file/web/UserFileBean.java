package com.bee32.sem.file.web;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.file.service.IUserFileTagStatService;
import com.bee32.sem.file.util.UserFileCriteria;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.misc.IDateCriteriaComposer;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.PartyDto;

@ForEntity(UserFile.class)
public class UserFileBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    UserFileTagnameDto selectedTag;
    Map<Integer, String> searchTags = new LinkedHashMap<Integer, String>();

    PartyDto searchParty;

    public UserFileBean() {
        super(UserFile.class, UserFileDto.class, UserFileDto.TAGS);
    }

    public void createUserFile(FileBlob fileBlob, IncomingFile incomingFile) {
        try {
            DATA(FileBlob.class).saveOrUpdate(fileBlob);

            UserFile userFile = new UserFile();
            userFile.setName(incomingFile.getFileName());
            userFile.setFileBlob(fileBlob);
            DATA(UserFile.class).saveOrUpdate(userFile);
        } catch (Exception e) {
            uiLogger.error("无法保存文件基本信息", e);
            return;
        }
        uiLogger.info("保存文件成功。");
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        IUserFileTagStatService tagStats = BEAN(IUserFileTagStatService.class);
        for (UserFile userFile : uMap.<UserFile> entitySet()) {
            // boolean newAdded = uMap.get(userFile).getId() == null;
            tagStats.addUsage(userFile.getTags());
        }
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        IUserFileTagStatService tagStats = BEAN(IUserFileTagStatService.class);
        for (UserFile userFile : uMap.<UserFile> entitySet()) {
            // boolean newAdded = uMap.get(userFile).getId() == null;
            tagStats.removeUsage(userFile.getTags());
        }
    }

    public TagCloudModel getTagCloudModel() {
        TagCloudModel model = new DefaultTagCloudModel();
        IUserFileTagStatService tagStats = BEAN(IUserFileTagStatService.class);
        for (Entry<UserFileTagname, Long> entry : tagStats.getStats().entrySet()) {
            UserFileTagname tag = entry.getKey();
            String tagName = tag.getName();
            Long usage = entry.getValue();
            model.addTag(new DefaultTagCloudItem(tagName, "#tagId=" + tag.getId(), usage.intValue()));
        }
        return model;
    }

    public void addTag() {
        UserFileDto userFile = getOpenedObject();
        userFile.getTags().add(selectedTag);
    }

    public void removeTag() {
        UserFileDto userFile = getOpenedObject();
        List<UserFileTagnameDto> tags = userFile.getTags();
        tags.remove(selectedTag);
    }

    public UserFileTagnameDto getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(UserFileTagnameDto selectedTag) {
        this.selectedTag = selectedTag;
    }

    public Object getCreateUserFileListener() {
        return new IncomingFileBlobAdapter() {
            @Override
            protected void process(FileBlob fileBlob, IncomingFile incomingFile)
                    throws IOException {
                createUserFile(fileBlob, incomingFile);
            }

            @Override
            protected void reportError(String message, Exception exception) {
                uiLogger.error(message, exception);
            }
        };
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, UserFileCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

    public void addTagRestriction() {
        String tagIdStr = ctx.view.getRequest().getParameter("id");
        String tagName = ctx.view.getRequest().getParameter("name");
        if (tagIdStr == null)
            return;
        tagIdStr = tagIdStr.trim();
        if (tagIdStr.isEmpty())
            return;

        int tagId = Integer.parseInt(tagIdStr);
        // UserFileTagname tag = DATA(UserFileTagname.class).get(tagId);

        TagsSearchFragment tsf = null;
        for (SearchFragment sf : getSearchFragments("tag")) {
            if (sf instanceof TagsSearchFragment)
                tsf = (TagsSearchFragment) sf;
        }
        if (tsf == null) {
            tsf = new TagsSearchFragment();
            addSearchFragment("tag", tsf);
        }

        tsf.map.put(tagId, tagName);
        searchFragmentsChanged();
    }

    public PartyDto getSearchParty() {
        return searchParty;
    }

    public void setSearchParty(PartyDto searchParty) {
        this.searchParty = searchParty;
    }

    public void addPartyRestriction() {
        if (DTOs.isNull(searchParty))
            return;
        setSearchFragment("party", "客户是 " + searchParty.getDisplayName(), //
                new Equals("party.id", searchParty.getId()));
        searchParty = null;
    }

    public void addOperatorRestriction() {
        if (searchPrincipal == null)
            return;
        setSearchFragment("operator", "经办人是 " + searchPrincipal.getDisplayName(), //
                new Equals("operator.id", searchPrincipal.getId()));
        searchPrincipal = null;
    }

    public void addFileDateRestriction() {
        setDateSearchFragment("file-date", "档案日期为", new IDateCriteriaComposer() {
            @Override
            public ICriteriaElement composeDateCriteria(Date begin, Date end) {
                return CommonCriteria.between("fileDate", begin, end);
            }
        });
    }

}
