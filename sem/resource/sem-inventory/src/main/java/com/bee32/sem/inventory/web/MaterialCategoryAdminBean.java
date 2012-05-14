package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(MaterialCategory.class)
public class MaterialCategoryAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected String stringFragment;

    protected List<MaterialType> types; // = new ArrayList<MaterialType>();

    public MaterialCategoryAdminBean() {
        super(MaterialCategory.class, MaterialCategoryDto.class, 0);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap) throws Exception {
        super.postUpdate(uMap);
        ctx.bean.getBean(ChooseMaterialCategoryDialogBean.class).refreshTree();
    }

    public void addCfinishedRestriction(){
        setAttributeFilter("限定为成品", Arrays.asList(MaterialType.PRODUCT));
        searchFragmentsChanged();
    }

    public void addSemiRestriction(){
        setAttributeFilter("限定为半成品", Arrays.asList(MaterialType.SEMI));
        searchFragmentsChanged();
    }

    public void addRawRestriction(){
        setAttributeFilter("限定为原材料", Arrays.asList(MaterialType.RAW));
        searchFragmentsChanged();
    }

    public void addOtherRestriction(){
        setAttributeFilter("限定为其他", Arrays.asList(MaterialType.OTHER));
        searchFragmentsChanged();
    }


    TypeSearchFragment setAttributeFilter(String pattern, List<MaterialType> types){

        TypeSearchFragment tsf = null;
        for(SearchFragment searchFragment : getSearchFragments()){
            if(searchFragment instanceof TypeSearchFragment)
                tsf = (TypeSearchFragment) searchFragment;
        }
        if(tsf == null ){
            tsf = new TypeSearchFragment(pattern, types);
            addSearchFragment(tsf);
        }
        else{
            tsf.setPattern(pattern);
            tsf.setTypes(types);
        }
        return tsf;
    }

    public List<SelectItem> getMaterialTypes() {
        List<SelectItem> materialTypes = new ArrayList<SelectItem>();
        for (MaterialType c : MaterialType.values())
            materialTypes.add(new SelectItem(c.getValue(), c.getDisplayName()));
        return materialTypes;
    }

    public String getStringFragment() {
        return stringFragment;
    }

    public void setStringFragment(String stringFragement) {
        this.stringFragment = stringFragement;
    }

}
