package user.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class HelloApp {

    private static String load(String fileName)
            throws IOException {
        FileReader r = new FileReader(fileName);
        StringWriter w = new StringWriter(4096);
        char[] buf = new char[4096];
        int count = r.read(buf);
        while (count > 0) {
            w.write(buf, 0, count);
            count = r.read(buf);
        }
        r.close();
        return w.toString();
    }

    public static void main(String[] args)
            throws IOException {
        System.out.println(load("/etc/motd"));
    }

}
