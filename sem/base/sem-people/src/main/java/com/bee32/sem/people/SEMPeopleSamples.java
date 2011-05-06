package com.bee32.sem.people;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.plover.orm.ext.userCategory.UserCategory;
import com.bee32.plover.orm.ext.userCategory.UserCategoryItem;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonTitle;

@ImportSamples(SEMOrgSamples.class)
public class SEMPeopleSamples
        extends EntitySamplesContribution {

    public static Person one77 = new Person();
    public static UserCategory carIndustry = new UserCategory();
    public static UserCategoryItem important = new UserCategoryItem();
    public static Contact weiXiaoBao = new Contact();
    public static PersonTitle salesTitle = new PersonTitle();
    public static PersonTitle productSale = new PersonTitle();

    static {

        Calendar cal = Calendar.getInstance();

        one77.setName("布加迪 ONE-77");
        one77.setUser(SEMOrgSamples.admin);
        one77.setFax("87010376");
        one77.setAddress("上海");
        one77.setDescription("布加迪上海代理");
        one77.setSummary("艺术结合澎湃动力");
        one77.setPhone("13758378888");

        salesTitle.setDepartment("销售部");
        salesTitle.setCharge("导购汽车及提供一系列的服务");
        salesTitle.setPost("汽车导购");
        salesTitle.setBusinessEntity(one77);
        salesTitle.setContact(weiXiaoBao);

        productSale.setDepartment(SEMOrgSamples.abcSales.getName());
        productSale.setCharge("产品销售");
        productSale.setPost("销售经理");
        productSale.setBusinessEntity(one77);
        productSale.setContact(weiXiaoBao);

        weiXiaoBao.setAddress("上海市卢湾区景山前街4号");
        weiXiaoBao.setBirthday(cal.getTime());
        weiXiaoBao.setCellphone("1310210XXXX");
        weiXiaoBao.setCertType("身份证");
        weiXiaoBao.setCertNo("11204416541220243X");
        weiXiaoBao.setEmail("email@email.com");
        weiXiaoBao.setName("韦小宝");
        weiXiaoBao.setSex("男");
        weiXiaoBao.setQq("3719347");
        weiXiaoBao.setWebsite("www.website.com.cn");
        weiXiaoBao.setFax("02109356");
        weiXiaoBao.setGtalk("gtalk@gmail.com");
        weiXiaoBao.setHomephone("123456789");
        weiXiaoBao.setInterests("看电影,逛街");
        weiXiaoBao.setLivemsg("msn@live.com");
        weiXiaoBao.setDescription("曾经是男的");
        weiXiaoBao.setOwner(SEMOrgSamples.admin);
        weiXiaoBao.setPostcode("210371");
        weiXiaoBao.setSkype("skype@skype.com");
        weiXiaoBao.setPersonTitles(Arrays.asList(salesTitle, productSale));

        carIndustry.setName("重工业");
        carIndustry.setDescription("重工业-汽车");

        // 分类值?
        important.setCategory(carIndustry);
        important.setValue("重要");
        important.setDescription("十分重要");

        carIndustry.setItems(Arrays.asList(important));
    }

    @Override
    protected void preamble() {
        addNormalSample(one77);
        addNormalSample(salesTitle);
        addNormalSample(productSale);
        addNormalSample(weiXiaoBao);
        addNormalSample(carIndustry);
        addNormalSample(important);
    }

}
