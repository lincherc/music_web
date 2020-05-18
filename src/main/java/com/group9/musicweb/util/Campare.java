package com.group9.musicweb.util;

public class Campare {
    public static double campare(int[] L1, int[] L2, int length) {
        double sum1 = 0, sum2 = 0, sum3 = 0;
        for (int i = 0; i < length; i++) {
            sum1 += L1[i] * L2[i];
            sum2 += L1[i] * L1[i];
            sum3 += L2[i] * L2[i];
        }
        return sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));

    }
}
