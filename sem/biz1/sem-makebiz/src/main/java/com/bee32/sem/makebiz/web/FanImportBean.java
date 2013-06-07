package com.bee32.sem.makebiz.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialAttribute;

public class FanImportBean extends EntityViewBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    boolean upload;
    boolean analysis;
    boolean imported;

    private File tmpFile;

    public void doAnalysis() {
        InputStream inputstream = null;
        InputStreamReader streamReader = null;

        try {
            inputstream = new FileInputStream(tmpFile);
            streamReader = new InputStreamReader(inputstream, "UTF-8");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }

        if (null == streamReader) {
            uiLogger.info("inputstream cant be null");
            return;
        }

        String line = null;
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                Material material = new Material();
                List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();

                String[] split = line.split(",");
                String type = split[0];

                switch (type) {
                case "风口":
                    String label = split[1];
                    material.setLabel(label);
                    MaterialAttribute a1 = new MaterialAttribute();
                    break;
                case "风阀":
                    break;
                case "消声器":
                    break;
                case "静压箱":
                    break;
                case "风机":
                    break;
                case "软接":
                    break;
                case "法兰":
                    break;
                case "减震吊钩":
                    break;
                case "人防":
                    break;
                default:
                    break;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isUpload() {
        return upload;
    }

    public boolean isAnalysis() {
        return analysis;
    }

    public boolean isImported() {
        return imported;
    }

}