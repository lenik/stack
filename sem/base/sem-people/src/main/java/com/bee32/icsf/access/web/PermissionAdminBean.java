package com.bee32.icsf.access.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@ForEntity(R_ACE.class)
public class PermissionAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private UserDto selectedUser;
    private GroupDto selectedGroup;
    private RoleDto selectedRole;

    List<UserDto> users;
    List<GroupDto> groups;
    List<RoleDto> roles;

    private List<RPEntry> rpEntries;
    private RPEntry selectedRpEntry;

    private int activeTab;

    public PermissionAdminBean() {
        List<Group> _groups = ctx.data.access(Group.class).list();
        groups = DTOs.marshalList(GroupDto.class, _groups);

        List<Role> _roles = ctx.data.access(Role.class).list();
        roles = DTOs.marshalList(RoleDto.class, _roles);

        List<User> _users = ctx.data.access(User.class).list();
        users = DTOs.marshalList(UserDto.class, _users);
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

    public List<RPEntry> getRpEntries() {
        return rpEntries;
    }

    public void setRpEntries(List<RPEntry> rpEntries) {
        this.rpEntries = rpEntries;
    }

    public RPEntry getSelectedRpEntry() {
        return selectedRpEntry;
    }

    public void setSelectedRpEntry(RPEntry selectedRpEntry) {
        this.selectedRpEntry = selectedRpEntry;
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public List<SelectItem> getPermissionStatus() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();
        item.setValue(0);
        item.setLabel("-");
        items.add(item);

        item = new SelectItem();
        item.setValue(1);
        item.setLabel("允许");
        items.add(item);

        item = new SelectItem();
        item.setValue(2);
        item.setLabel("拒绝");
        items.add(item);

        return items;
    }

    private void loadEntries(PrincipalDto principalDto) {
        ScannedResourceRegistry srr = ctx.bean.getBean(ScannedResourceRegistry.class);
        R_ACLService aclService = ctx.bean.getBean(R_ACLService.class);

        Map<String, ResourcePermission> havePermissions = new HashMap<String, ResourcePermission>();
        Principal principal = principalDto.unmarshal();
        List<ResourcePermission> haveResourcePermissions = aclService.getResourcePermissions(principal);
        for (ResourcePermission rp : haveResourcePermissions) {
            String permissionQulifier = srr.qualify(rp.getResource());
            havePermissions.put(permissionQulifier, rp);
        }

        rpEntries = new ArrayList<RPEntry>();
        for (IResourceNamespace rn : srr.getNamespaces()) {
            for (Resource res : rn.getResources()) {
                String resourceType = ClassUtil.getTypeName(res.getClass());
                String permissionQulifier = srr.qualify(res);
                RPEntry rpEntry = new RPEntry();
                rpEntry.setResourceType(resourceType);
                rpEntry.setDisplayName(res.getAppearance().getDisplayName());
                rpEntry.setQualifiedName(permissionQulifier);

                if (havePermissions.containsKey(permissionQulifier)) {
                    rpEntry.setPermission(havePermissions.get(permissionQulifier).getPermission());
                } else {
                    rpEntry.setPermission(new Permission(0));
                }

                rpEntries.add(rpEntry);
            }
        }

        uiLogger.info("权限装载完成!");
    }

    public void onRowSelectUser(SelectEvent event) {
        UserDto clickOn = (UserDto) event.getObject();

        if (clickOn == null) {
            uiLogger.error("请选择需要设置权限的用户!");
            return;
        }

        loadEntries(clickOn);
    }

    public void onRowUnselectUser(UnselectEvent event) {
        rpEntries = new ArrayList<RPEntry>();
    }

    public void onRowSelectRole(SelectEvent event) {
        RoleDto clickOn = (RoleDto) event.getObject();

        if (clickOn == null) {
            uiLogger.error("请选择需要设置权限的角色!");
            return;
        }

        loadEntries(clickOn);
    }

    public void onRowUnselectRole(UnselectEvent event) {
        rpEntries = new ArrayList<RPEntry>();
    }

    public void onRowSelectGroup(SelectEvent event) {
        GroupDto clickOn = (GroupDto) event.getObject();

        if (clickOn == null) {
            uiLogger.error("请选择需要设置权限的组!");
            return;
        }

        loadEntries(clickOn);
    }

    public void onRowUnselectGroup(UnselectEvent event) {
        rpEntries = new ArrayList<RPEntry>();
    }

    /**
     *
     * save: 1, delete from R_ACE where principal = currentUser
     *
     * 2, for entry: RPEntry[] ace = new R_ACE(entry.getResource(), currentUser, entry.permission)
     * dataManager.save(ace)
     */
    public void doSave() {
        PrincipalDto principalDto = null;

        switch (activeTab) {
        case 0:
            principalDto = selectedGroup;
            break;
        case 1:
            principalDto = selectedRole;
            break;
        case 2:
            principalDto = selectedUser;
            break;
        }

        if (principalDto == null) {
            uiLogger.error("请选择需要设置权限的主体!");
            return;
        }

        Principal principal = principalDto.unmarshal();

        ctx.data.access(R_ACE.class).findAndDelete(new Equals("principal", principal));

        ScannedResourceRegistry srr = ctx.bean.getBean(ScannedResourceRegistry.class);

        Iterator<RPEntry> iterator = rpEntries.iterator();
        while (iterator.hasNext()) {
            RPEntry rpEntry = (RPEntry) iterator.next();

            Resource resource = srr.query(rpEntry.getQualifiedName());
            R_ACE ace = new R_ACE(resource, principal, rpEntry.permission);

            ctx.data.access(R_ACE.class).save(ace);
        }

        uiLogger.info("权限保存成功!");
    }

    public void onSelectAllAdmin() {
        for (RPEntry e : rpEntries) {
            e.permission.setAdmin(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneAdmin() {
        for (RPEntry e : rpEntries) {
            e.permission.setAdmin(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllUsable() {
        for (RPEntry e : rpEntries) {
            e.permission.setUsable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneUsable() {
        for (RPEntry e : rpEntries) {
            e.permission.setUsable(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllReadable() {
        for (RPEntry e : rpEntries) {
            e.permission.setReadable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneReadable() {
        for (RPEntry e : rpEntries) {
            e.permission.setReadable(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllWritable() {
        for (RPEntry e : rpEntries) {
            e.permission.setWritable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneWritable() {
        for (RPEntry e : rpEntries) {
            e.permission.setWritable(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllExecutable() {
        for (RPEntry e : rpEntries) {
            e.permission.setExecutable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneExecutable() {
        for (RPEntry e : rpEntries) {
            e.permission.setExecutable(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllCreatable() {
        for (RPEntry e : rpEntries) {
            e.permission.setCreatable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneCreatable() {
        for (RPEntry e : rpEntries) {
            e.permission.setCreatable(false);
        }

        uiLogger.info("取消选择完成!");
    }

    public void onSelectAllDeletable() {
        for (RPEntry e : rpEntries) {
            e.permission.setDeletable(true);
        }

        uiLogger.info("全选完成!");
    }

    public void onSelectNoneDeletable() {
        for (RPEntry e : rpEntries) {
            e.permission.setDeletable(false);
        }

        uiLogger.info("取消选择完成!");
    }

}
