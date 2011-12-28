package com.bee32.sem.process.verify.web;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;

public interface IVerifyHandlerHook<E extends Entity<?>> {

    /**
     * 准备实体中的和审核有关的上下文参数。
     *
     * @return 成功返回 <code>null</code>，否则返回错误消息。
     */
    String doPreVerify(E entity, User currentUser, TextMap textMap);

    /**
     * 审核完成后的设置。
     */
    void doPostVerify(E entity, User currentUser, TextMap textMap);

}
