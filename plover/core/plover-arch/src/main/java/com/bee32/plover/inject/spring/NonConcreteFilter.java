package com.bee32.plover.inject.spring;

import java.io.IOException;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class NonConcreteFilter
        implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        boolean concrete = classMetadata.isConcrete();

        // System.err.println(" -- " + (concrete ? '+' : '-') + " " + classMetadata.getClassName());

        return !concrete;
    }

}
