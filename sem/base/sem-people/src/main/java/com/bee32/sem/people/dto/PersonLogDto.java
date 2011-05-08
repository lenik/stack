package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PersonLog;

public class PersonLogDto
        extends EntityDto<PersonLog, Long> {

    private static final long serialVersionUID = 1L;

    PersonDto person;
    PersonLogCategoryDto category;
    String description;

    public PersonLogDto() {
        super();
    }

    public PersonLogDto(PersonLog source) {
        super(source);
    }

    @Override
    protected void _marshal(PersonLog source) {
        person = new PersonDto(source.getPerson());
        category = new PersonLogCategoryDto(source.getCategory());
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PersonLog target) {
        target.setDescription(description);

        merge(target, "person", person);
        merge(target, "category", category);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        int personId = map.getInt("person.id");
        person = new PersonDto().ref(personId);

        String categoryId = map.getString("category.id");
        category = new PersonLogCategoryDto().ref(categoryId);

        description = map.getString("description");
    }

}
