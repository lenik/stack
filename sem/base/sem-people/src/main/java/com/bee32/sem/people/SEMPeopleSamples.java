package com.bee32.sem.people;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.misc.Sets;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.entity.PersonSidType;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMPeopleSamples
        extends EntitySamplesContribution {

    public static Org internetCorp = new Org();
    public static Person one77 = new Person();
    public static Person bentley = new Person();
    public static Person weiXiaoBao = new Person();

    static {
        internetCorp.setType(OrgType.COMPANY);
        internetCorp.setSize(20);
        internetCorp.setName("XX有限责任公司");
        internetCorp.setInterests("互联网搜索");
        internetCorp.setOwner(IcsfPrincipalSamples.eva);

        one77.setName("Bugatti");
        one77.setFullName("Ettore Bugatti ONE - 77");
        one77.setNickName("ONE - 77");
        one77.setOwner(IcsfPrincipalSamples.eva);
        one77.setSid("294741103659387246x");
        Calendar one77Birthday = Calendar.getInstance();
        one77Birthday.set(1909, 1, 1);
        one77.setBirthday(one77Birthday.getTime());
        one77.setTags(Sets.newSet(PartyTag.CUSTOMER));

        bentley.setName("Bentley");
        bentley.setFullName("Walter Owen Bentley");
        bentley.setNickName("Arnage");
        bentley.setOwner(IcsfPrincipalSamples.eva);
        bentley.setSid("580672610347561394");
        Calendar bentleyBirthday = Calendar.getInstance();
        bentleyBirthday.set(1919, 7, 1);
        bentley.setBirthday(bentleyBirthday.getTime());

        bentley.setTags(Sets.newSet(PartyTag.SUPPLIER));

        weiXiaoBao.setBirthday(bentleyBirthday.getTime());
        weiXiaoBao.setCensusRegister("北京市");
        weiXiaoBao.setFullName("韦小宝");
        weiXiaoBao.setInterests("吃饭睡觉玩老婆");
        weiXiaoBao.setName("小宝");
        weiXiaoBao.setNickName("韦公公");
        weiXiaoBao.setOwner(IcsfPrincipalSamples.eva);
        weiXiaoBao.setSex(Gender.MALE);
        weiXiaoBao.setSid("11010116541220517");
        weiXiaoBao.setSidType(PersonSidType.IDENTITYCARD);
        weiXiaoBao.setTags(Sets.newSet(PartyTag.CUSTOMER));

        Contact weiXiaoBaoHome = new Contact();
        weiXiaoBaoHome.setParty(weiXiaoBao);
        weiXiaoBaoHome.setAddress("东城区景山前街4号紫禁城敬事房 胡同1号");
        weiXiaoBaoHome.setCategory(ContactCategory.HOME);
        weiXiaoBaoHome.setTel("010 43728693");
        weiXiaoBaoHome.setMobile("13543823439");
        weiXiaoBaoHome.setEmail("weixiaobao@ldj.novel");
        weiXiaoBaoHome.setPostCode("021286");
        weiXiaoBaoHome.setQq("123456");

        Contact weiXiaoBaoWork = new Contact();
        weiXiaoBaoWork.setParty(weiXiaoBao);
        weiXiaoBaoWork.setAddress("东城区景山前街4号紫禁城敬事房 大清帝国内务敬事房");
        weiXiaoBaoWork.setCategory(ContactCategory.WORK);
        weiXiaoBaoWork.setTel("010 2397825");
        weiXiaoBaoWork.setMobile("13832947841");
        weiXiaoBaoWork.setEmail("weixiaobao@ibm.com.cn");
        weiXiaoBaoWork.setFax("43 58938283");
        weiXiaoBaoWork.setPostCode("021286");
        weiXiaoBaoWork.setWebsite("http://www.weixiaobao.com");

        weiXiaoBao.setContacts(Arrays.asList(weiXiaoBaoHome, weiXiaoBaoWork));

        PersonRole salesTitle = new PersonRole();
        salesTitle.setPerson(bentley);
        salesTitle.setOrg(internetCorp);
        salesTitle.setOrgUnit("销售部");
        salesTitle.setRole("销售经理");
        salesTitle.setDescription("The ripest fruit falls first.");
        bentley.setRoles(Sets.newSet(salesTitle));

        PersonRole productSale = new PersonRole();
        productSale.setPerson(one77);
        productSale.setOrg(internetCorp);
        productSale.setOrgUnit("销售部");
        productSale.setRole("产品导购");
        productSale.setDescription("People are beginning to notice you.  Try dressing before you leave the house.");
        one77.setRoles(Sets.newSet(productSale));
    }

    @Override
    protected void preamble() {
        addNormalSample(PartyTag.CUSTOMER);
        addNormalSample(PartyTag.SUPPLIER);
        addNormalSample(PartyTag.EMPLOYEE);

        addNormalSample(PersonSidType.IDENTITYCARD);
        addNormalSample(PersonSidType.PASSPORT);
        addNormalSample(PersonSidType.DRIVINGLICENES);

        addNormalSample(ContactCategory.NORMAL);
        addNormalSample(ContactCategory.HOME);
        addNormalSample(ContactCategory.WORK);
        addNormalSample(ContactCategory.OUT);

        addNormalSample(OrgType.ARMY);
        addNormalSample(OrgType.COMPANY);
        addNormalSample(OrgType.EDU);
        addNormalSample(OrgType.PARTNER);

        addNormalSample(internetCorp);

        addNormalSample(one77);
        addNormalSample(bentley);
        addNormalSample(weiXiaoBao);
    }

}
