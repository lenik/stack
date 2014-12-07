package com.bee32.zebra.erp.stock.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.erp.stock.Artifact;

public interface ArtifactMapper
        extends IMapper {

    List<Artifact> all();

    Artifact select(int id);

    int insert(Artifact artifact);

    void update(Artifact artifact);

    @Delete("delete from art where id=#{id}")
    boolean delete(int id);

}
