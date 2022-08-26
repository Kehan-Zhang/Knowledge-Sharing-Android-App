package com.example.graduation.Util;

public class StringSimilarityUtil {
    public static int levenshteinDistance(String str, String target) {
        int strLength = str.length(), targetLength = target.length();
        int stri, targetj;
        int characteristicFunction;
        char strChar, targetChar;
        if (strLength == 0)
            return targetLength;
        if (targetLength == 0)
            return strLength;
        int distance[][];
        distance = new int[strLength + 1][targetLength + 1];
        for (stri = 0; stri <= strLength; stri++)
            distance[stri][0] = stri;
        for (targetj = 0; targetj <= targetLength; targetj++)
            distance[0][targetj] = targetj;
        for (stri = 1; stri <= strLength; stri++) {
            strChar = str.charAt(stri - 1);
            for (targetj = 1; targetj <= targetLength; targetj++) {
                targetChar = target.charAt(targetj - 1);
                if (strChar == targetChar || strChar == targetChar + 32 || strChar + 32 == targetChar)
                    characteristicFunction = 0;
                 else
                    characteristicFunction = 1;
                distance[stri][targetj] = min(distance[stri - 1][targetj] + 1, distance[stri][targetj - 1] + 1, distance[stri - 1][targetj - 1] + characteristicFunction);
            }
        }
        return distance[strLength][targetLength];
    }

    private static int min(int a, int b, int c) {
        return (a = a < b ? a : b) < c ? a : c;
    }

    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) levenshteinDistance(str, target) / Math.max(str.length(), target.length());
    }
}
