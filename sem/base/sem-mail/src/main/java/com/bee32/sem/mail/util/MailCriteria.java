package com.bee32.sem.mail.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class MailCriteria
        extends CriteriaSpec {

    public static CriteriaElement listDeliveryByFolder(int folderId) {
        return equals("folder.id", folderId);
    }
}
