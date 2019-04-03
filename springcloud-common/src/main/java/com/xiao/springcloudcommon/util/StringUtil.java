package com.xiao.springcloudcommon.util;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");

    public StringUtil() {
    }

    public static String filterReturn(String text) {
        Matcher m = CRLF.matcher(text);
        if (m.find()) {
            text = m.replaceAll("");
        }

        return text;
    }

    public static String purify(String text) {
        return Strings.isNullOrEmpty(text) ? text : filterReturn(text.trim());
    }

    public static String substring(String str, int beginIndex, int endIndex) {
        if (str != null && beginIndex >= 0 && endIndex >= 0) {
            int maxIndex = str.length();
            return str.substring(Math.min(beginIndex, maxIndex), Math.min(endIndex, maxIndex));
        } else {
            return str;
        }
    }

    public static String maskStar(String str, int index) {
        if (str != null && index >= 0) {
            int length = str.length() - index;
            return _maskStar(str, index, length);
        } else {
            return str;
        }
    }

    public static String maskStar(String str, int index, int length) {
        return str != null && index >= 0 && length > 0 && str.length() >= index + length ? _maskStar(str, index, length) : str;
    }

    private static String _maskStar(String str, int index, int length) {
        String stars = Strings.repeat("*", length);
        str = String.format("%s%s%s", str.substring(0, index), stars, str.substring(index + length));
        return str;
    }

    public static String genProgressString(int current, int total) {
        float progress = (float)current / (float)total * 100.0F;
        return String.format("%s/%s=%.2f%%", current, total, progress);
    }

    public static String valueOf(Integer val) {
        return val == null ? null : String.valueOf(val);
    }

    public static String valueOf(Double val) {
        return val == null ? null : String.valueOf(val);
    }

    public static String toString(Integer val) {
        return val == null ? null : String.valueOf(val);
    }

    public static boolean equal(String text1, String text2) {
        return text1 == null ? text2 == null : text1.equals(text2);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().equals("");
    }
}
