package com.bee32.sem.process.verify.impl;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.sem.process.verify.VerifyState;

public class AllowState
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private IPrincipal allowedBy;

    public AllowState() {
    }

    public AllowState(IPrincipal allowedBy) {
        this.allowedBy = allowedBy;
    }

    public IPrincipal getAllowedBy() {
        return allowedBy;
    }

    public void setAllowedBy(IPrincipal allowedBy) {
        this.allowedBy = allowedBy;
    }

    @Override
    public void stage(IModelStage stage)
            throws ModelStageException {

        // stage.add("allowedBy", allowedBy);
// if (stage.getView().kindOf(StandardViews.EDIT)) {
// try {
// HttpSession session = stage.require(HttpSession.class);
// List<Person> persons = SessionFacet.getCurrentCorp(session).getPersons();
// // stage.add(persons);
// } catch (ContextException e) {
// throw new ModelStageException(e.getMessage(), e);
// }
// }
    }

    @Override
    public void reload(IModelStage stage)
            throws ModelLoadException {
        // stage.get
    }

}
