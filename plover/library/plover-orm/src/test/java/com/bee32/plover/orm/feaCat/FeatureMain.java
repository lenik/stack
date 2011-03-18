package com.bee32.plover.orm.feaCat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.ContextRefs;
import com.bee32.plover.orm.context.TxContext;

public class FeatureMain {

    public static void main(String[] args)
            throws Exception {

        ApplicationContext applicationContext = new FeatureContext(//
                new TxContext(ContextRefs.SCAN_TESTX)).getApplicationContext();

        // applicationContext.getBean(FeatureMain.class).runAop();

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                FeaturePlayer player = applicationContext.getBean(FeaturePlayer.class);
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
