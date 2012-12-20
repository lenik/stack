package com.bee32.sem.file;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.file.entity.FileAttribute;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.people.SEMPeopleUnit;

@ImportUnit({ IcsfAccessUnit.class, SEMPeopleUnit.class })
public class SEMFileUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(FileBlob.class);
        add(FileAttribute.class);
        add(UserFile.class);
        add(UserFileTagname.class);
        add(UserFolder.class);
    }

}
