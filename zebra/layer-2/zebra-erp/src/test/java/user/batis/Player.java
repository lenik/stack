package user.batis;

import org.junit.Assert;

import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.site.vhost.MutableVirtualHost;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.schema.TagDef;

public class Player
        extends Assert {

    public static void main(String[] args)
            throws Exception {
        DataSourceArguments dsargs = new DataSourceArguments();
        dsargs.setPort(1063);
        dsargs.setDatabase("zjhf_db");
        dsargs.setUserName("postgres");
        dsargs.setPassword("cW3EADp8");
        MutableVirtualHost vhost = new MutableVirtualHost();
        vhost.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, dsargs);

        VhostDataService service = new VhostDataService(vhost);

        TagMapper tagMapper = service.getMapper(TagMapper.class);
        for (TagDef tag : tagMapper.getTags())
            System.out.println(tag.getCode() + ": " + tag.getLabel());

        TagDef tag = tagMapper.selectTag(107);
        System.out.println(tag.getCode() + ": " + tag.getLabel());
    }

}
