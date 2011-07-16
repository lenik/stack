package com.bee32.sem.people;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import javax.free.Dates;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.misc.Sets;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.entity.PersonSidType;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMPeopleSamples
        extends SampleContribution {

    // 原来 SEMOrgSamples 中的样本。
    public static Group abcCorp = new Group("ABC Company");
    public static Group humanCorp = new Group("Human Company");
    public static Group abcRAD = new Group("ABC 研究发展办公室");
    public static Group abcSales = new Group("ABC 国际贸易部");

    public static Person jackPerson = new Person("贾雨村");
    public static Person tangPerson = new Person("唐玄奘");
    public static User jack;
    public static User tang;

    public static Org abcOrg = new Org("abc");
    public static Person bugatti = new Person("Bugatti");
    public static Person bentley = new Person("Bentley");
    public static Person weiXiaoBao = new Person("wxb");

    static {
        try {
            series1();
            series2();
        } catch (ParseException e) {
            throw new RuntimeException("Error when setup SEMPeopleSamples.", e);
        }
    }

    /**
     * 相当于原来的 ebi-org
     *
     * @throws ParseException
     */
    static void series1()
            throws ParseException {
        humanCorp.setDescription(//
                "本公司致力于人类补完计划。人类（无论是肉体还是心灵）都是由脆弱的物质构成的，" + //
                        "所以也就非常容易受到伤害，而在人与人之间，心灵的世界是彼此隔绝的，要使人类向" + //
                        "更高的领域进化，就必须使人的心灵摆脱躯体的束缚，重新回到人类的诞生之地“莉莉" + //
                        "斯之卵”中。唯有如此，才能最终拆除人与人之间的心灵屏障，使不同的心灵世界能够" + //
                        "相互补充，走向进化的终点——成为永生的“神”。人类补完计划就是为了实现这个神" + //
                        "圣目的而创立的。");

        abcRAD.setInheritedGroup(abcCorp);
        abcCorp.getDerivedGroups().add(abcRAD);

        abcRAD.setOwner(User.admin);

        // admin.setPasswordByString("");

        {
            Contact jackHome = new Contact(jackPerson, ContactCategory.HOME);
            jackHome.setAddress("海狞鞋桥");
            jackHome.setMobile("15392969212");
            jackHome.setTel("85963291");

            Contact jackWork = new Contact(jackPerson, ContactCategory.WORK);
            jackHome.setAddress("海狞黑丝园区");
            jackWork.setTel("87219592");
            jackWork.setEmail("jack@abc.com");

            jackPerson.setBirthday(Dates.YYYY_MM_DD.parse("1980-4-5"));
            jackPerson.setMemo("康桥鞋业二十年");
            jackPerson.setSex(Gender.MALE);
            jackPerson.setCensusRegister("海狞市公安局");
            jackPerson.setSid("330401198210250230");
        }
        jack = jackPerson.createUserLikeThis("jack");
        jack.setPrimaryGroup(abcRAD);
        abcRAD.addMemberUser(jack);

        {
            Contact tangHome = new Contact(tangPerson, ContactCategory.HOME);
            Contact tangWork = new Contact(tangPerson, ContactCategory.WORK);
            tangHome.setEmail("cruise@war.org");
            tangHome.setAddress("美国德州海滨公园");
            tangHome.setMobile("13947385860");
            tangHome.setTel("82957395");
            tangWork.setTel("86593184");
            tangPerson.setBirthday(Dates.YYYY_MM_DD.parse("1970-3-12"));
            tangPerson.setMemo("每天喝水8升拥有好身体。");
            tangPerson.setSex(Gender.MALE);
            tangPerson.setCensusRegister("德州府");
            tangPerson.setSid("330481197003124931");
        }
        tang = tangPerson.createUserLikeThis("tang");
        tang.setPrimaryGroup(abcRAD);
        tang.addAssignedGroup(abcSales);
        abcRAD.addMemberUser(tang);
        abcSales.setOwner(tang);
        abcSales.addMemberUser(tang);
    }

    /** 相当于原来的 ebo-templatetypes */
    static void series2() {
        abcOrg.setFullName("ABC 有限责任公司");
        abcOrg.setType(OrgType.LTD_CORP);
        abcOrg.setSize(20);
        abcOrg.setInterests("互联网搜索");
        abcOrg.setOwner(IcsfPrincipalSamples.eva);

        bugatti.setFullName("Ettore Bugatti ONE - 77");
        bugatti.setNickName("ONE - 77");
        bugatti.setOwner(IcsfPrincipalSamples.eva);
        bugatti.setSid("294741103659387246x");
        Calendar one77Birthday = Calendar.getInstance();
        one77Birthday.set(1909, 1, 1);
        bugatti.setBirthday(one77Birthday.getTime());
        bugatti.setTags(Sets.newSet(PartyTagname.CUSTOMER));

        bentley.setFullName("Walter Owen Bentley");
        bentley.setNickName("Arnage");
        bentley.setOwner(IcsfPrincipalSamples.eva);
        bentley.setSid("580672610347561394");
        Calendar bentleyBirthday = Calendar.getInstance();
        bentleyBirthday.set(1919, 7, 1);
        bentley.setBirthday(bentleyBirthday.getTime());
        bentley.setTags(Sets.newSet(PartyTagname.SUPPLIER));

        weiXiaoBao.setBirthday(bentleyBirthday.getTime());
        weiXiaoBao.setCensusRegister("北京市");
        weiXiaoBao.setFullName("韦小宝");
        weiXiaoBao.setInterests("吃饭睡觉玩老婆");
        weiXiaoBao.setNickName("小宝");
        weiXiaoBao.setOwner(IcsfPrincipalSamples.eva);
        weiXiaoBao.setSex(Gender.MALE);
        weiXiaoBao.setSid("11010116541220517");
        weiXiaoBao.setSidType(PersonSidType.IDENTITYCARD);
        weiXiaoBao.setTags(Sets.newSet(PartyTagname.CUSTOMER));

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
        salesTitle.setOrg(abcOrg);
        salesTitle.setAltOrgUnit("销售部");
        salesTitle.setRole("销售经理");
        salesTitle.setDescription("The ripest fruit falls first.");
        bentley.setRoles(Sets.newSet(salesTitle));

        PersonRole productSale = new PersonRole();
        productSale.setPerson(bugatti);
        productSale.setOrg(abcOrg);
        productSale.setAltOrgUnit("销售部");
        productSale.setRole("产品导购");
        productSale.setDescription("People are beginning to notice you.  Try dressing before you leave the house.");
        bugatti.setRoles(Sets.newSet(productSale));
    }

    @Override
    protected void preamble() {
        addSample(PartyTagname.CUSTOMER);
        addSample(PartyTagname.SUPPLIER);
        addSample(PartyTagname.INTERNAL);

        addSample(PersonSidType.IDENTITYCARD);
        addSample(PersonSidType.PASSPORT);
        addSample(PersonSidType.DRIVINGLICENES);

        addSample(ContactCategory.NORMAL);
        addSample(ContactCategory.HOME);
        addSample(ContactCategory.WORK);
        addSample(ContactCategory.OUT);

        addSample(OrgType.EDUCATION);
        addSample(OrgType.FACTORY);
        addSample(OrgType.INDIVIDUAL);
        addSample(OrgType.INF_CORP);
        addSample(OrgType.LTD_CORP);
        addSample(OrgType.MILITARY);
        addSample(OrgType.PARTNER);

        addSample(abcOrg);

        addNormalSample(jackPerson);
        addNormalSample(tangPerson);
        addSample(bugatti);
        addSample(bentley);
        addSample(weiXiaoBao);
    }

}
