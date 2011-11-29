package com.bee32.sem.asset.web;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.dict.DtoCodeTreeBuilder;
import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;

public class AccountSubjectAdminBean
        extends EntityViewBean {

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
        if (accountSubject == null)
            _newAccountSubject();
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

        List<AccountSubject> subjects = serviceFor(AccountSubject.class).list(Order.asc("name"));
        List<AccountSubjectDto> subjectDtos = DTOs.mrefList(AccountSubjectDto.class, -1, subjects);

        // 将 subject dto 列表转换为前缀树
        DtoCodeTreeBuilder ctb = new DtoCodeTreeBuilder();
        ctb.learn(subjectDtos);
        ctb.reduce(); // 清除临时构建的中间结点

        PoNode modelRoot = ctb.getRoot();
        loadAccountSubjectRecursive(modelRoot, root);
    }

    private void loadAccountSubjectRecursive(PoNode modelNode, TreeNode guiParent) {
        AccountSubjectDto subjectDto = (AccountSubjectDto) modelNode.getData();
        TreeNode guiNode = new DefaultTreeNode(subjectDto, guiParent);
        for (PoNode child : modelNode.getChildren()) {
            loadAccountSubjectRecursive(child, guiNode);
        }
    }

    public void _newAccountSubject() {
        accountSubject = new AccountSubjectDto().create();
    }

    public void newAccountSubject() {
        editNewStatus = true;
        _newAccountSubject();

    }

    public void chooseAccountSubject() {
        accountSubject = reload((AccountSubjectDto) selectedNode.getData());
    }

    public void modifyAccountSubject() {
        if (accountSubject.getEntityFlags().isLocked()) {
            uiLogger.info("保留科目，不能修改!");
            return;
        }

        chooseAccountSubject();
        editNewStatus = false;
    }

    public void save() {
        AccountSubject s = accountSubject.unmarshal();

        try {
            serviceFor(AccountSubject.class).saveOrUpdate(s);
            loadAccountSubjectTree();
            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void delete() {
        if (accountSubject.getEntityFlags().isLocked()) {
            uiLogger.info("保留科目，不能删除!");
            return;
        }

        try {
            serviceFor(AccountSubject.class).delete(accountSubject.unmarshal());
            loadAccountSubjectTree();
            uiLogger.info("删除成功!");
        } catch (DataIntegrityViolationException e) {
            uiLogger.error("删除失败.", e);
        }
    }

    public void selectParentSubject() {
        AccountSubjectDto parentSubject = (AccountSubjectDto) selectedParentSubjectNode.getData();
        accountSubject.setDebitSign(parentSubject.isDebitSign());
        accountSubject.setCreditSign(parentSubject.isCreditSign());
    }

}
