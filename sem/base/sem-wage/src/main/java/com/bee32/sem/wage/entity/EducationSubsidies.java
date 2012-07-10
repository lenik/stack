package com.bee32.sem.wage.entity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.sample.SampleList;
import com.bee32.plover.orm.sample.StandardSamples;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.hr.entity.PersonEducationTypes;

public class EducationSubsidies
        extends StandardSamples {

    @Override
    protected void getSamples(SampleList samples, boolean grouped) {
        super.getSamples(samples, grouped);
        Map<String, PersonEducationType> map = new HashMap<String, PersonEducationType>();
        PersonEducationTypes tempInstance;
        try {
            tempInstance = PersonEducationTypes.class.newInstance();
            List<Entity<?>> educationList = tempInstance.getSamples(true);
            for (Entity<?> entity : educationList) {
                map.put(entity.getName(), (PersonEducationType) entity);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Field[] fields = PersonEducationTypes.class.getDeclaredFields();
        List<EducationSubsidy> les = new ArrayList<EducationSubsidy>();
        for (Field f : fields) {
            PersonEducationType education = map.get(f.getName());
            EducationSubsidy es = new EducationSubsidy(education.getName() + "Subsidy",//
                    education.getLabel() + "补贴", "", education, new BigDecimal(0));
            les.add(es);
        }
        samples.addAll(samples);
    }
}
