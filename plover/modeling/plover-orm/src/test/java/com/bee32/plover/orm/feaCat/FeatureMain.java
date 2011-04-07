package com.bee32.plover.orm.feaCat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.bee32.plover.inject.spring.ApplicationContextBuilder;

public class FeatureMain {

    public static void main(String[] args)
            throws Exception {

        // applicationContext.getBean(FeatureMain.class).runAop();

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                FeaturePlayer player = new FeaturePlayer();
                ApplicationContextBuilder.selfWire(player);

                System.err.println("Got player: " + player);

                player.tcPrepare();
                player.tcList();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

}
