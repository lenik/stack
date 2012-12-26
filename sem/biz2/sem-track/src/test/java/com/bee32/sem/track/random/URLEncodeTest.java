package com.bee32.sem.track.random;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncodeTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String s ="12/22/2012";
        String encode = URLEncoder.encode(s, "UTF-8");
        System.out.println(encode);

    }

}
