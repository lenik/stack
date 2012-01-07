package com.bee32.sem.inventory.web.settings;

import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(StockWarehouse.class)
public class StockWarehouseAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private List<StockWarehouseDto> stockWarehouses;
    private StockWarehouseDto selectedStockWarehouse;
    private boolean editMode;

    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;


    public StockWarehouseDto getSelectedStockWarehouse() {
        return selectedStockWarehouse;
    }

    public void setSelectedStockWarehouse(StockWarehouseDto selectedStockWarehouse) {
        this.selectedStockWarehouse = selectedStockWarehouse;
    }


    public List<StockWarehouseDto> getStockWarehouses() {
        List<StockWarehouse> ws = serviceFor(StockWarehouse.class).list();
        stockWarehouses = DTOs.marshalList(StockWarehouseDto.class, ws);
        return stockWarehouses;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getPersonPattern() {
        return personPattern;
    }

    public void setPersonPattern(String personPattern) {
        this.personPattern = personPattern;
    }

    public List<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDto> persons) {
        this.persons = persons;
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public StockWarehouseDto getNullStockWarehouse() {
        return new StockWarehouseDto().create();
    }

    public void delete() {
        try {
            serviceFor(StockWarehouse.class).delete(
                    selectedStockWarehouse.unmarshal());
            uiLogger.info("删除成功");
        } catch (Exception e) {
            uiLogger.warn("删除失败，错误信息:" + e.getMessage());
        }
    }

    public void save() {
        try {
            serviceFor(StockWarehouse.class).saveOrUpdate(
                    selectedStockWarehouse.unmarshal());
            uiLogger.info("保存成功");
        } catch (Exception e) {
            uiLogger.warn("保存失败，错误信息:" + e.getMessage());
        }
    }

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(
                    PeopleCriteria.internal(),
                    PeopleCriteria.namedLike(personPattern));

            persons = DTOs.mrefList(PersonDto.class, _persons);
        }
    }

    public void choosePerson() {
        selectedStockWarehouse.setManager(selectedPerson);
    }

}
