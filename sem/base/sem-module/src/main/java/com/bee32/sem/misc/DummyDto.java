package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class DummyDto {

    /**
     * 创建傀儡 DTO 对象，用于避免空指针错误、或者转移指针到无效的对象以避免将数据写入到重要的实例中。
     */
    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D create(Class<D> dtoType) {
        D dto;
        try {
            dto = dtoType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        dto.create();

        return dto;
    }

}
