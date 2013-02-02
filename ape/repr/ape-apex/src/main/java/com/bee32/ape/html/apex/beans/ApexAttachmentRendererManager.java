package com.bee32.ape.html.apex.beans;

import org.activiti.explorer.ui.content.AttachmentRendererManager;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

/**
 * Declared in activiti-ui-context.xml
 *
 * NOTE: The parent class has already annotated with @Component, however, it won't be scanned
 * because it's not in the bee32 packages.
 */
@Component
@PerSite(reason = "appctx in use in the modified ProcessEngines.DEFAULT.")
public class ApexAttachmentRendererManager
        extends AttachmentRendererManager {

    private static final long serialVersionUID = 1L;

}
