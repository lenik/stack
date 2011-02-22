package com.bee32.plover.orm.util;

import org.hibernate.cfg.Configuration;

public class HibernateMappingCreator {

    {
        Configuration conf = new Configuration();
    }

    /**
     *
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping package="com.bee32.sems.srm.inquiry.entity" default-access="field">
    <class name="Inquiry" table="EBO_SRM_INQUIRY">
        <id name="id" column="ID">
            <generator class="native"/>
        </id>
        <property name="memo"  column="MEMO"/>
        <many-to-one name="maker" column="MAKER" cascade="none" class="com.bee32.sems.org.entity.PersonImpl"/>
        <property name="createDate" column="CREATE_DATE" type="timestamp"/>
        <property name="updateDate" column="UPDATE_DATE" type="timestamp"/>
        <bag name="items" lazy="true" cascade="all,delete-orphan" inverse="true">
            <key column="INQUIRY"/>
            <one-to-many class="InquiryItem"/>
        </bag>
    </class>
</hibernate-mapping>

     */
}
