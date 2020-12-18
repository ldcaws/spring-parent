package com.ldc.springboot_shiro.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 21:53
 */
public class ValidateUtil {
    // 正整数
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    // 正整数、0
    public static final String REGEX_DINTEGER_ZERO = "^[1-9]\\d*|0$";
    // 整数：正整数、负整数、0
    public static final String REGEX_INTEGER = "^[-\\\\+]?[1-9]\\d*|0$";
    // 数字
    public static final String REGEX_DIGIT = "^[0-9]*$";
    // 正浮点数
    public static final String REGEX_FLOAT_POSITIVE = "^([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])$";
    // 负浮点数
    public static final String REGEX_FLOAT_NEGTIVE = "^(-[1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])$";
    // 大写字母
    public static final String REGEX_LETTER_UPCASE = "^[A-Z]+$";
    public static final String REGEX_SPECIAL = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
    public static final String REGEX_CHINESE_LETTER_DIGIT_ONLY = "^[a-zA-Z0-9\\u4e00-\\u9fa5]*$";
    public static final String REGEX_CHINESE_LETTER_DIGIT_UNDERLINE = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]*$";
    public static final String REGEX_LETTER_DIGIT = "^[A-Za-z0-9]*$";
    public static final String REFEX_CHINESE = "[\\u4e00-\\u9fa5]+";
    public static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";

    public static boolean isPositiveInteger(String field) {
        return isMatch(REGEX_POSITIVE_INTEGER, field);
    }

    public static boolean isIntegerAndZero(String field) {
        return isMatch(REGEX_DINTEGER_ZERO, field);
    }

    public static boolean isInteger(String field) {
        return isMatch(REGEX_INTEGER, field);
    }

    public static boolean IsUpperCaseLetter(String field) {
        return isMatch(REGEX_LETTER_UPCASE, field);
    }

    public static boolean IsDigit(String field) {
        return isMatch(REGEX_DIGIT, field);
    }

    public static boolean isChineseLetterDigitOnly(String field) {
        return isMatch(REGEX_CHINESE_LETTER_DIGIT_ONLY, field);
    }

    public static boolean isChineseLetterDigitUnderlineOnly(String field) {
        return isMatch(REGEX_CHINESE_LETTER_DIGIT_UNDERLINE, field);
    }

    public static boolean isSpecialChar(String str) {
        Pattern p = Pattern.compile(REGEX_SPECIAL);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static int checkPassword(String newpswd) {
        String pswdComplex = "1";
        String filter = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";//可兼容特殊字符
        if ("0".equals(pswdComplex.trim())) {
            if (isMatch(filter, newpswd)) {
                return 0;
            } else {
                //showTipMsg("密码请输入8-20位数字和大小写字母组合！");
                return 1;
            }
        } else if ("1".equals(pswdComplex.trim())) {
            filter = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";//可兼容特殊字符
            if (isMatch(filter, newpswd)) {
                if (dropSpecial(newpswd)) {
                    return 0;
                } else {
                    //showTipMsg("密码请输入8-20位数字、大小写字母和特殊字符！");
                    return 2;
                }
            } else {
                //showTipMsg("密码请输入8-20位数字、大小写字母和特殊字符！");
                return 2;
            }
        }

        return -1;
    }

    public static boolean checkIP(String fromIP, String toIP) {
        boolean flag = true;
        if (!isMatch(REGX_IP,fromIP)) {
            flag = false;
        }
        if (!isMatch(REGX_IP,toIP)) {
            flag = false;
        }

        if (flag) {
            compareIP(fromIP, toIP);
        }

        return flag;
    }

    public static boolean compareIP(String startIp, String endIp) {
        boolean flag = false;
        String startips[] = startIp.split("\\.");
        String endIps[] = endIp.split("\\.");
        for (int i = 0; i < startips.length; i++) {
            if (Integer.parseInt(endIps[i]) > Integer.parseInt(startips[i])) {
                flag = true;
                break;
            } else {
                if (Integer.parseInt(endIps[i]) == Integer.parseInt(startips[i])) {
                    continue;
                } else {
                    break;
                }
            }
        }

        return flag;
    }

    public static boolean checkIpRegx(String ip){
        return ip.matches(REGX_IP);
    }

    private static boolean dropSpecial(String _value) {
        String IllegalString = "[`~!@#%$^&*_+-()=|{}':;', \\[\\].<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]‘’";
        for (int i = 0; i < _value.length(); i++) {
            char s = _value.charAt(i);
            if (IllegalString.indexOf(s) > 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean ipIsInRange(String ip, String ipRange) {
        if (ipRange == null)
            throw new NullPointerException("IP段不能为空！");
        if (ip == null)
            throw new NullPointerException("IP不能为空！");
        ipRange = ipRange.trim();
        ip = ip.trim();
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
        if (!ipRange.matches(REGX_IPB) || !ip.matches(REGX_IP))
            return false;
        int idx = ipRange.indexOf('-');
        String[] sips = ipRange.substring(0, idx).split("\\.");
        String[] sipe = ipRange.substring(idx + 1).split("\\.");
        String[] sipt = ip.split("\\.");
        long ips = 0L, ipe = 0L, ipt = 0L;
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(sips[i]);
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);
        }
        if (ips > ipe) {
            long t = ips;
            ips = ipe;
            ipe = t;
        }
        return ips <= ipt && ipt <= ipe;
    }

    private static boolean isMatch(String regex, CharSequence input) {
        if (input == null || "".equals(input)) return false;
        return Pattern.matches(regex, input);
    }
}