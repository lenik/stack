package com.bee32.sem.chance.dto;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.entity.ChanceStages;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.process.base.ProcessEntityDto;

public class ChanceDto
        extends ProcessEntityDto<Chance> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int ACTIONS = 4;
    public static final int COMPETITORIES = 8;
    public static final int PRODUCTS = 16;
    public static final int PRODUCTS_MORE = 32 | PRODUCTS;

    ChanceCategoryDto category;
    String subject;
    String content;
    String date;

    ChanceSourceTypeDto source;
    Date anticipationBegin;
    Date anticipationEnd;

    List<ChancePartyDto> parties;
    List<ChanceCompetitorDto> competitories;
    List<ChanceActionDto> actions;
    List<WantedProductDto> products;

    ChanceStageDto stage;
    ProcurementMethodDto procurementMethod;
    PurchaseRegulationDto purchaseRegulation;

    String address;

    public ChanceDto() {
        super();
    }

    public ChanceDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _copy() {
        parties = CopyUtils.copyList(parties, this);
        products = CopyUtils.copyList(products, this);
    }

    @Override
    protected void _marshal(Chance source) {
        date = DateToRange.fullFormat.format(source.getCreatedDate()).substring(0, 16);
        category = mref(ChanceCategoryDto.class, source.getCategory());
        this.source = mref(ChanceSourceTypeDto.class, source.getSource());
        subject = source.getSubject();
        content = source.getContent();

        anticipationBegin = source.getAnticipationBegin();
        anticipationEnd = source.getAnticipationEnd();
        if (selection.contains(PARTIES))
            parties = marshalList(ChancePartyDto.class, source.getParties());
        else
            parties = Collections.emptyList();

        if (selection.contains(COMPETITORIES))
            competitories = marshalList(ChanceCompetitorDto.class, source.getCompetitories());
        else
            competitories = Collections.emptyList();

        if (selection.contains(ACTIONS))
            actions = mrefList(ChanceActionDto.class, source.getActions());
        else
            actions = Collections.emptyList();

        if (selection.contains(PRODUCTS))
            products = marshalList(WantedProductDto.class, selection.translate(PRODUCTS_MORE, F_MORE),
                    source.getProducts());
        else
            products = Collections.emptyList();

        stage = mref(ChanceStageDto.class, source.getStage());
        procurementMethod = mref(ProcurementMethodDto.class, source.getProcurementMethod());
        purchaseRegulation = mref(PurchaseRegulationDto.class, source.getPurchaseRegulation());
        address = source.getAddress();
    }

    @Override
    protected void _unmarshalTo(Chance target) {
        merge(target, "category", category);
        merge(target, "source", source);
        target.setSubject(subject);
        target.setContent(content);
        target.setAnticipationBegin(anticipationBegin);
        target.setAnticipationEnd(anticipationEnd);

        if (selection.contains(PARTIES))
            mergeList(target, "parties", parties);
        if (selection.contains(COMPETITORIES))
            mergeList(target, "competitories", competitories);
        if (selection.contains(ACTIONS))
            mergeList(target, "actions", actions);
        if (selection.contains(PRODUCTS))
            mergeList(target, "products", products);

        merge(target, "procurementMethod", procurementMethod);
        merge(target, "purchaseRegulation", purchaseRegulation);
        target.setAddress(address);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortContent() {
        return Strings.ellipse(content, 16);
    }

    public ChanceCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ChanceCategoryDto category) {
        this.category = category;
    }

    public ChanceSourceTypeDto getSource() {
        return source;
    }

    public void setSource(ChanceSourceTypeDto source) {
        this.source = source;
    }

    @NLength(min = 1, max = Chance.SUBJECT_LENGTH)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = TextUtil.normalizeSpace(subject);
    }

    @NLength(min = 1, max = Chance.CONTENT_LENGTH)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = TextUtil.normalizeSpace(content);
    }

    public Date getAnticipationBegin() {
        return anticipationBegin;
    }

    public void setAnticipationBegin(Date anticipationBegin) {
        this.anticipationBegin = anticipationBegin;
    }

    public Date getAnticipationEnd() {
        return anticipationEnd;
    }

    public void setAnticipationEnd(Date anticipationEnd) {
        this.anticipationEnd = anticipationEnd;
    }

    public List<ChancePartyDto> getParties() {
        return parties;
    }

    public void setParties(List<ChancePartyDto> parties) {
        if (parties == null)
            throw new NullPointerException("parties");
        this.parties = parties;
    }

    public void addParty(ChancePartyDto chanceParty) {
        if (chanceParty == null)
            throw new NullPointerException("chanceParty");
        if (!parties.contains(chanceParty))
            parties.add(chanceParty);
    }

    public void removeParty(ChancePartyDto chanceParty) {
        if (parties.contains(chanceParty))
            parties.remove(chanceParty);
    }

    public String getPartiesHint() {
        if (parties == null)
            return "(n/a)";
        StringBuilder sb = null;
        for (ChancePartyDto chparty : parties) {
            if (sb == null)
                sb = new StringBuilder();
            else
                sb.append(", ");
            sb.append(chparty.getParty().getDisplayName());
        }
        return sb.toString();
    }

    public List<ChanceCompetitorDto> getCompetitories() {
        return competitories;
    }

    public void setCompetitories(List<ChanceCompetitorDto> competitories) {
        this.competitories = competitories;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        if (actions == null)
            throw new NullPointerException("actions");
        this.actions = actions;
    }

    public void addAction(ChanceActionDto action) {
        if (action == null)
            throw new NullPointerException("action");
        actions.add(action);
        refreshStage();
    }

    public boolean removeAction(ChanceActionDto action) {
        if (!actions.remove(action))
            return false;
        refreshStage();
        return true;
    }

    public List<WantedProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<WantedProductDto> products) {
        if (products == null)
            throw new NullPointerException("products");
        this.products = products;
    }

    public void addProduct(WantedProductDto product) {
        if (product == null)
            throw new NullPointerException("product");
        products.add(product);
    }

    public boolean removeProduct(WantedProductDto product) {
        return products.remove(product);
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

    public ProcurementMethodDto getProcurementMethod() {
        return procurementMethod;
    }

    public void setProcurementMethod(ProcurementMethodDto procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    public PurchaseRegulationDto getPurchaseRegulation() {
        return purchaseRegulation;
    }

    public void setPurchaseRegulation(PurchaseRegulationDto purchaseRegulation) {
        this.purchaseRegulation = purchaseRegulation;
    }

    @NLength(max = Chance.ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = TextUtil.normalizeSpace(address);
    }

    void refreshStage() {
        int cachedOrder = getStage().getOrder();
        int maxOrder = 0;
        ChanceStage initStage = predefined(ChanceStages.class).INIT;
        ChanceStageDto maxStage = new ChanceStageDto().ref(initStage);
        for (ChanceActionDto action : getActions()) {
            int order = action.getStage().getOrder();
            if (order > maxOrder) {
                maxOrder = order;
                maxStage = action.getStage();
            }
        }

        if (maxOrder > cachedOrder)
            this.stage = maxStage;
    }

}
