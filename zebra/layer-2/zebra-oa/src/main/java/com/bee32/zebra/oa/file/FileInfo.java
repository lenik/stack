package com.bee32.zebra.oa.file;

import java.util.Date;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.meta.decl.Priority;
import net.bodz.bas.meta.decl.Volatile;
import net.bodz.bas.repr.form.NullConvertion;
import net.bodz.bas.repr.form.meta.FormInput;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.bas.repr.form.meta.StdGroup.Status;
import net.bodz.bas.repr.form.meta.TextInput;
import net.bodz.bas.repr.path.PathToken;
import net.bodz.lily.model.base.IMomentInterval;
import net.bodz.lily.model.base.IdType;
import net.bodz.lily.model.base.schema.PhaseDef;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.mx.base.CoMessage;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;

/**
 * 文件
 */
@Volatile
@PathToken("file")
// @SchemaId(Schemas.FILE)
@IdType(Integer.class)
public class FileInfo
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int N_DIR_NAME = 200;
    public static final int N_BASE_NAME = 80;
    public static final int N_SHA1 = 32;
    public static final int N_TYPE = 100;
    public static final int N_ENCODING = 30;

    private String dirName;
    private String baseName;
    private long size;
    private String sha1;
    private String type;
    private String encoding;

    private Organization org;
    private Person person;
    private Date activeDate;
    private Date expireDate;

    private int downloads;
    private Double value;

    @DetailLevel(DetailLevel.HIDDEN)
    @FormInput(nullconv = NullConvertion.NONE)
    @TextInput(maxLength = 200)
    @Override
    public String getSubject() {
        return super.getSubject();
    }

    /**
     * 文件备注信息。
     * 
     * @label Text
     * @label.zh 备注
     * @placeholder 输入备注信息…
     */
    @DetailLevel(DetailLevel.HIDDEN)
    @Override
    public String getText() {
        return super.getText();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public String getTextPreview() {
        return super.getTextPreview();
    }

    /**
     * 文件所在目录的路径。
     * 
     * @label 文件位置
     */
    @OfGroup(StdGroup.Identity.class)
    @TextInput(maxLength = N_DIR_NAME)
    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
     * 磁盘上的文件名称。
     * 
     * @label 文件名
     */
    @OfGroup(StdGroup.Identity.class)
    @TextInput(maxLength = N_BASE_NAME)
    public final String getBaseName() {
        return baseName;
    }

    public final void setBaseName(String baseName) {
        if (baseName == null)
            throw new NullPointerException("baseName");
        this.baseName = baseName;
    }

    @DetailLevel(DetailLevel.HIDDEN)
    public String getPath() {
        if (dirName == null || baseName == null)
            return null;
        return dirName + "/" + baseName;
    }

    /**
     * 文件长度
     * 
     * 文件的大小，单位为<code>字节 (bytes)</code>。
     */
    @OfGroup(StdGroup.Content.class)
    @FormInput(readOnly = true)
    @Derived
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * SHA-1摘要
     * 
     * 摘要信息用于验证文件的完整性。
     */
    @OfGroup(StdGroup.Identity.class)
    @FormInput(readOnly = true)
    @TextInput(maxLength = N_SHA1)
    @Derived
    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * 类型
     * 
     * 文件的内容类型（MIME 类型，该信息通过自动发现得到）。
     */
    @OfGroup(StdGroup.Content.class)
    @FormInput(readOnly = true)
    @TextInput(maxLength = N_TYPE)
    @Derived
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 字符编码
     * 
     * （仅用于文本文件）文本存储时采用的字符集编码。（通过自动发现得到）
     */
    @OfGroup(StdGroup.Content.class)
    @TextInput(maxLength = N_ENCODING)
    @Derived
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 公司
     * 
     * 文件（合同、票据、扫描件等）相关的公司。
     */
    @Priority(100)
    @OfGroup(StdGroup.Classification.class)
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     * 
     * 文件（合同、票据、扫描件等）相关的联系人。
     */
    @Priority(101)
    @OfGroup(StdGroup.Classification.class)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 下载次数
     * 
     * 反应文件被下载了多少次。
     */
    @OfGroup(StdGroup.Ranking.class)
    @Derived(cached = true)
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    /**
     * 价值
     * 
     * 文件内容所反映的事件的估价，比如合同的签订价款、票据上的总金额等，或一般意义上的估价。
     * <p>
     * 这个价值用于简单的反应文件的重要程度，为上策决策提供依据，而不参与任何统计运算。
     * 
     * @placeholder 输入（估算的）价值…
     */
    @Priority(50)
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 生效时间
     */
    @Priority(1)
    @OfGroup(StdGroup.Schedule.class)
    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * 过期时间
     */
    @Priority(2)
    @OfGroup(StdGroup.Schedule.class)
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /** ⇱ Implementation Of {@link IMomentInterval }. */
    /* _____________________________ */static section.iface __MOMENT_INTERVAL__;

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public final Date getBeginDate() {
        return getActiveDate();
    }

    @Override
    public final void setBeginDate(Date beginDate) {
        setActiveDate(beginDate);
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public final Date getEndDate() {
        return getExpireDate();
    }

    @Override
    public final void setEndDate(Date endDate) {
        setExpireDate(endDate);
    }

    /** ⇱ Implementation Of {@link ICoMessage}. */
    /* _____________________________ */static section.iface __MESSAGE__;

    /**
     * 经办人
     * 
     * 文件（通常是合同）的经办人。
     */
    @Priority(100)
    // @OfGroup(StdGroup.Classification.class)
    @Override
    public User getOp() {
        return super.getOp();
    }

    /**
     * @label Phase
     * @label.zh 阶段
     */
    @DetailLevel(DetailLevel.HIDDEN)
    @OfGroup(Status.class)
    @Override
    public PhaseDef getPhase() {
        return super.getPhase();
    }

}
