package com.bee32.sem.chance.web;

import org.primefaces.component.fieldset.Fieldset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.sem.sandbox.MultiTabEntityVdx;

@Component
@Scope("view")
public class ChanceBeanVdx
        extends MultiTabEntityVdx {

    private static final long serialVersionUID = 1L;

    public static final int TAB_INDEX = 0;
    public static final int TAB_FORM = 1;

    static final String DATATABLE_CHANCES = "chanceForm:chanceTable";
    static final String DATATABLE_QUOTATIONS = "quotationForm:quotationItem";
    static final String FIELD_ACTIONHISTORY = "chanceForm:actionHistoryField";
    static final String FIELD_QUOTATION = "chanceForm:quotationField";

    static final String BUTTON_CHANCE_NEW = "chanceForm:chanceNewButton";
    static final String BUTTON_CHANCE_EDIT = "chanceForm:chanceEditButton";
    static final String BUTTON_CHANCE_DELETE = "chanceForm:chanceDeleteButton";
    static final String BUTTON_CHANCE_ADD = "chanceForm:chanceAddButton";
    static final String BUTTON_CHANCE_RESET = "chanceForm:chanceResetButton";
    static final String BUTTON_CHANCE_DETAIL = "chanceForm:chanceDetailButton";
    static final String BUTTON_CHANCE_RELATEACTION = "chanceForm:relateActionButton";
    static final String BUTTON_CHANCE_UNRELATEACTION = "chanceForm:unrelateActionButton";
    static final String BUTTON_CHANCE_VIEWACTION = "chanceForm:viewActionDetailButton";

    static final String BUTTON_QUOTATION_NEW = "chanceForm:chanceNewQuotationButton";
    static final String BUTTON_QUOTATION_EDIT = "chanceForm:chanceEditQuotationButton";
    static final String BUTTON_QUOTATION_DELETE = "chanceForm:chanceDeleteQuotationButton";
    static final String BUTTON_QUOTATION_VIEW = "chanceForm:chanceViewQuotationButton";

    static final String OUTPUT_DISCOUNT = "quotationForm:outputDiscount";
    static final String INPUT_DISCOUNT = "quotationForm:inputDiscount";
    static final String COMMANDLINK_EDITDISCOUNT = "quotationForm:editDiscount";
    static final String COMMANDLINK_SUREDISCOUNT = "quotationForm:sureDiscount";

    static final String OUTPUT_PRICE = "quotationForm:outputPrice";
    static final String INPUT_PRICE = "quotationForm:inputPrice";
    static final String COMMANDLINK_EDITPRICE = "quotationForm:editPrice";
    static final String COMMANDLINK_SUREPIRCE = "quotationForm:surePrice";

    static final String OUTPUT_QUANTITY = "quotationForm:outputQuantity";
    static final String INPUT_QUANTITY = "quotationForm:inputQuantity";
    static final String COMMANDLINK_EDITQUANTITY = "quotationForm:editQuantity";
    static final String COMMANDLINK_SUREQUANTITY = "quotationForm:sureQuantity";

    boolean isSearching;

    boolean editable;
    boolean roleRendered;
    boolean isChancePartyEditing;

    boolean quotationEdit;
    boolean isDiscountEdting;
    boolean isPriceEditing;
    boolean isQuantityEditing;

    public Fieldset getActionHistoryFieldset() {
        return (Fieldset) findComponent(FIELD_ACTIONHISTORY);
    }

    public Fieldset getQuotaFieldset() {
        return (Fieldset) findComponent(FIELD_QUOTATION);
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isRoleRendered() {
        return roleRendered;
    }

    public void setRoleRendered(boolean roleRendered) {
        this.roleRendered = roleRendered;
    }

    public boolean isChancePartyEditing() {
        return isChancePartyEditing;
    }

    public void setChancePartyEditing(boolean isChancePartyEditing) {
        this.isChancePartyEditing = isChancePartyEditing;
    }

    public boolean isQuotationEdit() {
        return quotationEdit;
    }

    public void setQuotationEdit(boolean quotationEdit) {
        this.quotationEdit = quotationEdit;
    }

    public boolean isDiscountEdting() {
        return isDiscountEdting;
    }

    public void setDiscountEdting(boolean isDiscountEdting) {
        this.isDiscountEdting = isDiscountEdting;
    }

    public boolean isPriceEditing() {
        return isPriceEditing;
    }

    public void setPriceEditing(boolean isPriceEditing) {
        this.isPriceEditing = isPriceEditing;
    }

    public boolean isQuantityEditing() {
        return isQuantityEditing;
    }

    public void setQuantityEditing(boolean isQuantityEditing) {
        this.isQuantityEditing = isQuantityEditing;
    }

}
