package com.bee32.xem.zjhf.util;


public class ModelCombiner {

    public static String combine(String x, String result) {

        String tmp = null;
        switch (x) {
        case "圆":
            tmp = "Y";
            break;
        case "方":
            tmp = "F";
            break;
        case "阀":
            tmp = "V";
            break;
        case "防虫网":
            tmp = "I";
            break;
        case "过漏网":
            tmp = "G";
            break;
        case "铁皮":
            tmp = "I";
            break;
        case "镁璃钢":
            tmp = "II";
            break;
        case "70℃":
            tmp = "L";
            break;
        case "150℃":
            tmp = "M";
            break;
        case "280℃":
            tmp = "H";
            break;
        case "调风量":
            tmp = "W";
            break;
        case "常开":
            tmp = "O";
            break;
        case "常闭":
            tmp = "C";
            break;
        case "钢线远控":
            tmp = "T";
            break;
        case "电控":
            tmp = "E";
            break;
        case "二次动作":
            tmp = "S";
            break;
        case "自动复位":
            tmp = "A";
            break;
        case "电动调节阀":
            tmp = "D";
            break;
        case "防火布":
            tmp = "P";
            break;
        case "帆布":
            tmp = "K";
            break;
        default:
            break;
        }

        if (null == result || result.length() == 0)
            if (null == tmp || tmp.length() == 0)
                return "Unspecified Model";
            else
                return tmp;
        else if (null == tmp || tmp.length() == 0)
            return result;
        else
            return result + tmp;
    }

}
