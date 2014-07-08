package com.bee32.zebra.oa.model.contact;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.bodz.bas.db.batis.IMapper;

import com.tinylily.model.base.security.User;

public interface PersonMapper
        extends IMapper {

    @Insert("insert into person(code, label, description" + //
            ", birthday, gender, employee, homeland, passport, ssn, dln" + //
            ", customer, supplier, subject, comment, bank, bankacc" + //
            ", uid, gid, acl)" + //
            "values(#{codeName}, #{label}, #{description}" + //
            ", #{birthday}, #{gender}, #{employee}, #{homeland}, #{passport}, #{ssn}, #{dln}" + //
            ", #{customer}, #{supplier}, #{subject}, #{comment}, #{bank}, #{bankacc}" + //
            ", #{uid}, #{gid}, #{acl})")
    int insertPerson(Person person);

    @Select("select * from person where id=#{id}")
    User getPersonById(String id);

    @Select("select * from person")
    List<User> getAllPersons();

    @Update("update person set name=#{name}, creation=#{creation} where id=#{id}")
    void updatePerson(Person person);

    @Delete("delete from person where id=#{id}")
    void deletePerson(int id);

}
