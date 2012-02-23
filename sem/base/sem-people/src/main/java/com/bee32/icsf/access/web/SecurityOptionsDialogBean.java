package com.bee32.icsf.access.web;

import java.util.List;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.plover.arch.util.dto.MarshalType;
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

    String header = "安全选项...";
    String selectionDump;
    ACLDto chosenACL;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void refreshContextSelection() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Object item : getContextSelection()) {
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

    public ACLDto getChosenACL() {
        return chosenACL;
    }

    public void setChosenACL(ACLDto chosenACL) {
        this.chosenACL = chosenACL;
    }

    public void clearACL() {
        this.chosenACL = null;
    }

    public void loadACL() {
        chosenACL = getACLFromFirstSelection();
    }

    ACLDto getACLFromFirstSelection() {
        List<?> selection = getContextSelection();
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

        ACL _acl = ctx.data.access(ACL.class).get(aclId);
        if (_acl == null)
            return null;

        ACLDto acl = DTOs.mref(ACLDto.class, 0, _acl);
        return acl;
    }

    public void showEditACLForm() {
        if (chosenACL == null) {
            uiLogger.error("没有 ACL 可供编辑！");
            return;
        }
        chosenACL = reload(chosenACL, -1);
    }

    public void saveACL() {
        if (chosenACL == null) {
            uiLogger.error("ACL尚未装载。");
            return;
        }

        ACL _acl;
        try {
            _acl = chosenACL.unmarshal();
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        try {
            ctx.data.access(ACL.class).update(_acl);
        } catch (Exception e) {
            uiLogger.error("保存 ACL 失败", e);
            return;
        }

        uiLogger.info("保存 ACL 成功。");
    }

    @SuppressWarnings("unchecked")
    public void applyACL() {
        Integer aclId = null;
        if (chosenACL != null)
            aclId = chosenACL.getId();

        for (Object selection : getContextSelection()) {
            if (!(selection instanceof CEntityDto<?, ?>))
                continue;

            CEntityDto<?, ?> dto = (CEntityDto<?, ?>) selection;
            CEntity<?> _entity;
            try {
                _entity = dto.unmarshal();
            } catch (Exception e) {
                uiLogger.error("反编列失败", e);
                return;
            }
            CEntityAccessor.setAclId(_entity, aclId);

            try {
                ctx.data.access(_entity.getClass()).update(_entity);
            } catch (Exception e) {
                uiLogger.error("更新对象属性失败", e);
                return;
            }
        }
        uiLogger.info("更新安全选项成功。");
    }

}
