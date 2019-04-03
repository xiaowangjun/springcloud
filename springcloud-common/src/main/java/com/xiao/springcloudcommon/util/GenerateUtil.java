package com.xiao.springcloudcommon.util;

import java.util.UUID;

public class GenerateUtil {
    private static char[] CapitalAndNumberLetters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public GenerateUtil() {
    }

    public static String genUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toLowerCase();
    }

    public static String genCapitalAndNumber(int length) {
        return genByLetters(CapitalAndNumberLetters, length);
    }

    public static String genByLetters(char[] letters, int length) {
        boolean[] flags = new boolean[letters.length];
        char[] chs = new char[length];

        for(int i = 0; i < chs.length; ++i) {
            int index;
            do {
                index = (int)(Math.random() * (double)letters.length);
            } while(flags[index]);

            chs[i] = letters[index];
            flags[index] = true;
        }

        return String.valueOf(chs);
    }
}
