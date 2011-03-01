package com.bee32.plover.util.i18n;

public interface ITitleFormat {

    String formatName(String familyName, String middleName, String nickName);

    String formatTitle(Boolean gender, String familyName, String middleName, String nickName);

    String formatLongName(String realName, String displayName, String email);

}
