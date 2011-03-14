package com.bee32.sem.process.verify.builtin;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.Component;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.sem.process.verify.VerifyState;

public class AllowState
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private User allowedBy;
    private String message;

    public AllowState() {
    }

    public AllowState(User allowedBy) {
        this.allowedBy = allowedBy;
    }

    @ManyToOne(optional = false, //
    /*            */cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public User getAllowedBy() {
        return allowedBy;
    }

    public void setAllowedBy(User allowedBy) {
        this.allowedBy = allowedBy;
    }

    @Column(length = 200)
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
        result = prime * result + ((allowedBy == null) ? 0 : allowedBy.hashCode());
        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        AllowState other = (AllowState) obj;
        if (allowedBy == null) {
            if (other.allowedBy != null)
                return false;
        } else if (!allowedBy.equals(other.allowedBy))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Allowd by " + allowedBy;
    }

}
