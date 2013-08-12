package com.bee32.icsf.access.resource;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 自动发现资源注册表
 *
 * @see ResourceRegistry
 */
@Component
@Lazy
public class ScannedResourceRegistry
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(ScannedResourceRegistry.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        Map<String, IResourceScanner> scanners = applicationContext.getBeansOfType(IResourceScanner.class);

        for (IResourceScanner scanner : scanners.values()) {
            logger.info("Scan resources: " + scanner);

            try {
                scanner.scan();
            } catch (Exception e) {
                logger.error("Failed to scan resources with " + scanner, e);
            }
        }
    }

    public Resource query(String qualifiedName) {
        return ResourceRegistry.query(qualifiedName);
    }

    public String qualify(Resource resource) {
        return ResourceRegistry.qualify(resource);
    }

    public IResourceNamespace getNamespace(String ns) {
        return ResourceRegistry.getNamespace(ns);
    }

    public Collection<IResourceNamespace> getNamespaces() {
        return ResourceRegistry.getNamespaces();
    }

}
