package com.bee32.sem.asset.web;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;

public class AccountSubjectAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean editNewStatus; // true:新增状态;false:修改状态;

    private TreeNode root;

    private AccountSubjectDto accountSubject;
    private TreeNode selectedNode;
    private TreeNode selectedParentSubjectNode;


    public AccountSubjectAdminBean() {
        loadAccountSubjectTree();
    }

    public boolean isEditNewStatus() {
        return editNewStatus;
    }

    public void setEditNewStatus(boolean editNewStatus) {
        this.editNewStatus = editNewStatus;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public AccountSubjectDto getAccountSubject() {
        return accountSubject;
    }

    public void setAccountSubject(AccountSubjectDto accountSubject) {
        this.accountSubject = accountSubject;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getSelectedParentSubjectNode() {
        return selectedParentSubjectNode;
    }

    public void setSelectedParentSubjectNode(TreeNode selectedParentSubjectNode) {
        this.selectedParentSubjectNode = selectedParentSubjectNode;
    }

    protected void loadAccountSubjectTree() {
        root = new DefaultTreeNode("root", null);

        List<AccountSubject> subjects = serviceFor(AccountSubject.class).list(TreeCriteria.root());
        List<AccountSubjectDto> subjectDtos = DTOs.mrefList(AccountSubjectDto.class, -1, subjects);

        for (AccountSubjectDto subjectDto : subjectDtos) {
            loadAccountSubjectRecursive(subjectDto, root);
        }
    }

    private void loadAccountSubjectRecursive(AccountSubjectDto subjectDto, TreeNode parentTreeNode) {
        TreeNode subjectNode = new DefaultTreeNode(subjectDto, parentTreeNode);

        List<AccountSubjectDto> subSubjects = subjectDto.getChildren();
        for (AccountSubjectDto subSubject : subSubjects) {
            loadAccountSubjectRecursive(subSubject, subjectNode);
        }
    }


    public void chooseAccountSubject() {
        accountSubject = reload((AccountSubjectDto) selectedNode.getData());
    }

    public void modifyAccountSubject() {
        chooseAccountSubject();
        editNewStatus = false;
    }


}
