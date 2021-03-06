package com.bee32.sem.make.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.util.IEnclosingContext;
import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.make.dto.MakeStepInputDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.dto.PartItemDto;
import com.bee32.sem.make.dto.QCSpecParameterDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.make.service.PartService;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.material.dto.MaterialCategoryDto;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.entity.MaterialType;
import com.bee32.sem.material.util.MaterialCriteria;
import com.bee32.sem.material.web.ChooseMaterialDialogBean;
import com.bee32.sem.material.web.MaterialCategorySupportBean;
import com.bee32.sem.material.web.MaterialCategoryTreeModel;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(Part.class)
public class PartAdminBean
        extends MaterialCategorySupportBean {

    private static final long serialVersionUID = 1L;

    boolean productLike;
//    MaterialDto selectedMaterial;
    BigDecimal calcPriceResult;

    BomTreeModel bomTree;

    BomTreeNode makeStepTarget;

    public PartAdminBean() {
        super(Part.class, PartDto.class, 0);
    }

    @Override
    protected MaterialCategoryTreeModel createCategoryTree() {
        return new MaterialCategoryTreeModel(//
                MaterialCriteria.categoryType(//
                        MaterialType.PRODUCT, //
                        MaterialType.SEMI));
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(BomCriteria.targetCategory(categoryId));
    }

    public void addSpecRestriction() {
        //已经选择了bom节点，直接搜索， 反之，创建一个alias
        setSearchFragment("spec", "型号含有 " + searchPattern, //
                BomCriteria.specLike(searchPattern));
        searchPattern = null;
    }

    public void addCategoryRestriction() {
        setSearchFragment("category", "限定分类 " + searchPattern,//
                MaterialCriteria.clike(searchPattern));
        searchPattern = null;
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern,//
                BomCriteria.nameOrLabelLike(searchPattern));
    }

    @Override
    public void addDescriptionRestriction() {
        setSearchFragment("name", "描述含有 " + searchPattern, //
                BomCriteria.targetDescriptionLike(searchPattern));
    }

    ChanceDto searchChance;

    public ChanceDto getSearchChance() {
        return searchChance;
    }

    public void setSearchChance(ChanceDto searchChance) {
        this.searchChance = searchChance;
    }

    public void addChanceRestriction() {
        if (searchChance != null) {
            setSearchFragment("chance", "机会为 " + searchChance.getSubject(), //
                    new Equals("chance.id", searchChance.getId()));
            searchChance = null;
        }
    }

    public void setPartMaterial() {
        PartDto part = getOpenedObject();
        // 检查此物料(成品)是否已经有bom存在
        List<Part> partList = DATA(Part.class).list(new Limit(0, 3),//
                new Equals("target.id", part.getTarget().getId()), new Equals("valid", true));
        if (!partList.isEmpty()) {
            uiLogger.error("此物料已经存在BOM信息 (" + partList.get(0).getId() + "...)");
            return;
        }
        // part.setTarget(selectedMaterial);
        // selectedMaterial = null;
    }

    public void setPartItemMaterial(MaterialDto material) {
        PartItemDto item = childrenMBean.getOpenedObject();

        Part itemPart = DATA(Part.class).getUnique(new Equals("target.id", material.getId()));
        if (itemPart != null) {
            // 选中的物料是半成品
            item.setPart(DTOs.marshal(PartDto.class, itemPart));
        } else {
            // 选中的物料是原材料
            item.setMaterial(material);
        }
    }

    public void calcPrice()
            throws FxrQueryException {
        PartDto part = getOpenedObject();
        if (part == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }
        Part _part = part.unmarshal();
        BigDecimal price = _part.calcPrice();
        if (price == null) {
            uiLogger.error("没有找到此产品的原材料原价格!");
            return;
        }
        calcPriceResult = price;
    }

    public BigDecimal getCalcPriceResult() {
        return calcPriceResult;
    }

    public void loadBomTree() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection();

        PartDto part = getOpenedObject();
        bomTree = new BomTreeModel(part);
    }

    public BomTreeModel getBomTree() {
        return bomTree;
    }

    public void setBomTree(BomTreeModel bomTree) {
        this.bomTree = bomTree;
    }

    public BomTreeNode getMakeStepTarget() {
        return makeStepTarget;
    }

    public List<PartDto> getXrefs() {
        List<PartDto> xrefs = new ArrayList<PartDto>();

        PartDto part = getOpenedObject();
        if (part != null && !(DTOs.isNull(part))) {
            part = reload(part, PartDto.XREFS);

            for (PartItemDto item : part.getXrefs()) {
                xrefs.add(item.getParent());    //取得交叉引用的PartItem的parent
            }
        }
        return xrefs;
    }

    public void setMakeStepTarget(BomTreeNode makeStepTarget) {
        this.makeStepTarget = makeStepTarget;
        PartDto part = makeStepTarget.getPart();
        setSingleSelection(part);
        openSelection(Fmask.F_MORE);
        showView(StandardViews.EDIT_FORM);
    }

    public void findXref() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection(PartDto.XREFS);
    }

    public void copyBom() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection();

        PartDto part = getOpenedObject();

        setOpenedObject(BEAN(PartService.class).copyBom(part));
        showView(StandardViews.CREATE_FORM);
    }

    /**
     * 编辑Part时，
     */
    public void setCategory() {
        ChooseMaterialDialogBean bean = BEAN(ChooseMaterialDialogBean.class);
        if (this.categoryTree.getSelectedId() != null && this.categoryTree.getSelectedId() != -1) {
            bean.setCategoryRestriction(this.categoryTree.getSelectedId());
        }
    }

    /**
     * 编辑明细选择原材料时，限定原材料的分类
     */
    public void setRawCategory() {
        PartItemDto partItem = childrenMBean.getOpenedObject();
        ChooseMaterialDialogBean bean = BEAN(ChooseMaterialDialogBean.class);
        bean.clearSearchFragments();

        if (!DTOs.isNull(partItem.getMaterial())) {
            bean.setCategoryRestriction(partItem.getMaterial().getCategory().getId());
        } else {
            if (!DTOs.isNull(partItem.getPart())) {
                bean.setCategoryRestriction(partItem.getPart().getTarget().getCategory().getId());
            }
        }
    }

    @Transactional
    public void clearXrefAndDelete() {
        try {
            PartDto part = getOpenedObject();
            if (part != null && !(DTOs.isNull(part))) {
                part = reload(part, PartDto.XREFS);

                for (PartItemDto item : part.getXrefs()) {
                    // 引用本Part的PartItem,清空时，把partItem的part清空，并设置相应material
                    item.setMaterial(part.getTarget());
                    DATA(PartItem.class).saveOrUpdate(item.unmarshal());
                }

                DATA(Part.class).deleteById(part.getId());
                onDeletePart(part);
            }
            uiLogger.info("清空并删除成功.");
        } catch (Exception e) {
            uiLogger.error("清空并删除失败!", e);
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<PartItemDto> childrenMBean = ListMBean.fromEL(this, //
            "openedObject.children", PartItemDto.class);

    final ListMBean<MakeStepModelDto> stepsMBean = ListMBean.fromEL(this, //
            "openedObject.steps", MakeStepModelDto.class);

    final ListMBean<MakeStepInputDto> stepInputsMBean = ListMBean.fromEL(stepsMBean, //
            "openedObject.inputs", MakeStepInputDto.class);

    class QCSpecContext
            implements IEnclosingContext, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Object getEnclosingObject() {
            return stepsMBean.getOpenedObject().getQcSpec();
        }

    }

    final ListMBean<QCSpecParameterDto> qcSpecParasMBean = ListMBean.fromEL(new QCSpecContext(), //
            "enclosingObject.parameters", QCSpecParameterDto.class);

    public ListMBean<PartItemDto> getChildrenMBean() {
        return childrenMBean;
    }

    public ListMBean<MakeStepModelDto> getStepsMBean() {
        return stepsMBean;
    }

    public ListMBean<MakeStepInputDto> getStepInputsMBean() {
        return stepInputsMBean;
    }

    public ListMBean<QCSpecParameterDto> getQcSpecParasMBean() {
        return qcSpecParasMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            PartDto part = (PartDto) dto;
            if (DTOs.isNull(part.getTarget())) {
                uiLogger.error("组件没有设置对应的物料");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (Part _part : uMap.<Part> entitySet()) {
            BEAN(PartService.class).changePartItemFromMaterialToPart(_part);
            PartDto partDto = uMap.getSourceDto(_part);
            if (partDto.isNewCreated())
                onCreatePart(partDto);

            for (MakeStepModelDto step : partDto.getSteps()) {
                step.setOutput(partDto);
            }
        }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (PartDto part : uMap.<PartDto> dtos()) {
            if (part.getXrefCount() != 0) {
                uiLogger.error("此组件已经被其他组件引用，必须先删除相应组件，才能删除!");
                return false;
            }
        }

        return true;
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        for (Part _part : uMap.<Part> entitySet()) {
            PartDto partDto = uMap.getSourceDto(_part);
            onDeletePart(partDto);

// for (MakeStepModel step : _part.getSteps()) {
// QCSpec _qcSpec = step.getQcSpec();
// if (_qcSpec != null) {
// DATA(QCSpec.class).delete(_qcSpec);
// }
// }
        }
    }

    protected void onCreatePart(PartDto part) {
        Integer categoryId = part.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setPartCount(category.getPartCount() + 1);
    }

    protected void onDeletePart(PartDto part) {
        Integer categoryId = part.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setPartCount(category.getPartCount() - 1);
    }

}
