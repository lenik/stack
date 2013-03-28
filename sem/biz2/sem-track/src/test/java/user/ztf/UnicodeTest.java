package user.ztf;

public class UnicodeTest {

    public static void main(String[] args) {

        String s = "我";
        String hex = Integer.toHexString(s.charAt(0));
        System.out.println(hex);

        System.out.println(toUNICODE(s));

        String ff = "2c6";
        int num = Integer.parseInt(ff, 16);
        char ch = (char) num;
        System.out.println(ch);
    }

    public static String toUNICODE(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) <= '\u0080') {
                sb.append("\\u00");
            } else {
                sb.append("\\u");
            }
            sb.append(Integer.toHexString(s.charAt(i)));
        }
        return sb.toString();
    }

}
