package com.bee32.plover.inject.spring;

import java.io.File;
import java.io.IOException;

import javax.free.ICharOut;
import javax.free.IFile;
import javax.free.JavaioFile;
import javax.free.TempFile;

import com.bee32.plover.inject.InitializingService;

public class BeanDefinitionDumper
        extends InitializingService {

    final IFile csvFile;

    public BeanDefinitionDumper() {
        File tmpDir = TempFile.getTmpDir();
        File csvFile = new File(tmpDir, "bean.csv");
        this.csvFile = new JavaioFile(csvFile);
    }

    @Override
    public void initialize()
            throws IOException {
        ICharOut cout = csvFile.toTarget().newCharOut();
        BeanDefinitions.dumpCsv(cout, appctx);
    }

}
