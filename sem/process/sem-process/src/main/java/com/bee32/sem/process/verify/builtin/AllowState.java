package com.bee32.sem.process.verify.builtin;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.Component;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.sem.process.verify.VerifyState;

public class AllowState
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private IPrincipal principal;
    private String message;

    public AllowState() {
    }

    public AllowState(IPrincipal allowedBy) {
        this.principal = allowedBy;
    }

    public IPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(IPrincipal principal) {
        this.principal = principal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((principal == null) ? 0 : principal.hashCode());
        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        AllowState other = (AllowState) obj;
        if (principal == null) {
            if (other.principal != null)
                return false;
        } else if (!principal.equals(other.principal))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Allowd by " + principal;
    }

}
