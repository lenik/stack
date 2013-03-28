package user.ztf;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Member {

    public static final NumberFormat fmt = new DecimalFormat("000000");
    String memberId;
    static ArrayList<Member> accounts = new ArrayList<Member>();

    public Member() {
        int size = accounts.size() + 1;
        this.setMemberId("" + 2012 + "-" + (fmt.format(size)));
        accounts.add(this);
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}