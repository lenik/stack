package com.bee32.plover.inject.spring;

import java.io.File;
import java.io.IOException;

import javax.free.ICharOut;
import javax.free.IFile;
import javax.free.JavaioFile;
import javax.free.TempFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.inject.InitializingService;

public class BeanDefinitionDumper
        extends InitializingService {

    static Logger logger = LoggerFactory.getLogger(BeanDefinitionDumper.class);

    final IFile csvFile;

    public BeanDefinitionDumper() {
        File tmpDir = TempFile.getTmpDir();
        File csvFile = new File(tmpDir, "bean.csv");
        this.csvFile = new JavaioFile(csvFile);
    }

    @Override
    protected void _initialize()
            throws IOException {
        logger.info("Dump bean definitions to " + csvFile);
        ICharOut cout = csvFile.toTarget().newCharOut();
        BeanDefinitions.dumpCsv(cout, appctx);
    }

}
