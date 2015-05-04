package user.batis;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.lily.model.base.schema.TagDef;

public interface TagMapper
        extends IMapper {

    @Select("select * from tag")
    List<TagDef> getTags();

    TagDef selectTag(int id);

}
