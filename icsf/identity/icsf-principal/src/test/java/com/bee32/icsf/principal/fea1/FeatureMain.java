package com.bee32.icsf.principal.fea1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.bee32.plover.inject.spring.ApplicationContextBuilder;

public class FeatureMain {

    public static void main(String[] args)
            throws Exception {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                FeaturePlayer player = ApplicationContextBuilder.create(FeaturePlayer.class);

                player.listSamples();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

}
