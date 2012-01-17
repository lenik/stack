package com.bee32.icsf.access.web;

import java.util.List;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.plover.arch.util.dto.MarshalType;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityPeripheralBean;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityAccessor;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.util.FormatStyle;

public class SecurityOptionsDialogBean
        extends EntityPeripheralBean {

    private static final long serialVersionUID = 1L;

    String caption = "安全选项";
    ACLDto selectedACL; // selected ACL to be bound.
    ACLDto activeACL;
    String selectionDump;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void refreshSelection() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Object item : getSelection()) {
            String prefix = String.format("[%2d]", ++index);
            sb.append(prefix);

            String itemText;

            if (item instanceof CEntityDto<?, ?>) {
                CEntityDto<?, ?> dto = (CEntityDto<?, ?>) item;
                Integer aclId = dto.getAclId();
                sb.append(" [ACL = " + aclId + "]");
            }

            if (item instanceof EntityDto<?, ?>) {
                EntityDto<?, ?> dto = (EntityDto<?, ?>) item;
                dto = reload(dto);
                itemText = dto.toString(FormatStyle.VERBOSE);
            } else {
                itemText = String.valueOf(item);
            }

            itemText = itemText.replace("&", "&amp;");
            itemText = itemText.replace("<", "&lt;");
            itemText = itemText.replace(">", "&gt;");
            itemText = itemText.replace("\n", "\n    ");
            sb.append(' ');
            sb.append(itemText.trim());
            sb.append('\n');
        }
        selectionDump = sb.toString();

        loadACL();
    }

    public String getSelectionDump() {
        return selectionDump;
    }

    public ACLDto getSelectedACL() {
        return selectedACL;
    }

    public void setSelectedACL(ACLDto selectedACL) {
        this.selectedACL = selectedACL;
    }

    public void confirmACL() {
        this.activeACL = selectedACL;
    }

    public void clearACL() {
        this.activeACL = null;
    }

    public ACLDto getActiveACL() {
        return activeACL;
    }

    public void setActiveACL(ACLDto activeACL) {
        this.activeACL = activeACL;
    }

    public void loadACL() {
        activeACL = getACLFromFirstSelection();
    }

    ACLDto getACLFromFirstSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        Object first = selection.get(0);
        if (!(first instanceof CEntityDto<?, ?>))
            return null;

        CEntityDto<?, ?> c1 = (CEntityDto<?, ?>) first;
        c1.marshalAs(MarshalType.ID_REF); // force-reload.
        c1 = reload(c1);

        Integer aclId = c1.getAclId();
        if (aclId == null)
            return null;

        ACL _acl = asFor(ACL.class).get(aclId);
        if (_acl == null)
            return null;

        ACLDto acl = DTOs.mref(ACLDto.class, 0, _acl);
        return acl;
    }

    public void showEditACLForm() {
        if (activeACL == null) {
            uiLogger.error("没有 ACL 可供编辑！");
            return;
        }
        activeACL = reload(activeACL, -1);
    }

    public void saveACL() {
        if (activeACL == null) {
            uiLogger.error("ACL尚未装载。");
            return;
        }

        ACL _acl;
        try {
            _acl = activeACL.unmarshal(this);
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        try {
            asFor(ACL.class).update(_acl);
        } catch (Exception e) {
            uiLogger.error("保存 ACL 失败", e);
            return;
        }

        uiLogger.info("保存 ACL 成功。");
    }

    @SuppressWarnings("unchecked")
    public void applyACL() {
        Integer aclId = null;
        if (activeACL != null)
            aclId = activeACL.getId();

        for (Object selection : getSelection()) {
            if (!(selection instanceof CEntityDto<?, ?>))
                continue;

            CEntityDto<?, ?> dto = (CEntityDto<?, ?>) selection;
            CEntity<?> _entity;
            try {
                _entity = dto.unmarshal(this);
            } catch (Exception e) {
                uiLogger.error("反编列失败", e);
                return;
            }
            CEntityAccessor.setAclId(_entity, aclId);

            try {
                asFor(Entity.class).update(_entity);
            } catch (Exception e) {
                uiLogger.error("更新对象属性失败", e);
                return;
            }
        }
        uiLogger.info("更新安全选项成功。");
    }

}
