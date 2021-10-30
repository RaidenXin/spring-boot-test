package com.raiden.utils;

import java.util.HashSet;
import java.util.Random;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:06 2021/1/16
 * @Modified By:
 */
public final class AlgorithmUtils {

    /***
     * 随机不重复的增长型数组
     * @param length
     * @return
     */
    public static final int[] creatsIntArr(int length){
        int m = 1;
        int n = 5;
        int[] result = new int[length];
        int pre = 0;
        for (int i = 0; i < length; i++){
            int c = (int) (m + Math.random() * (n - m + 1));
            pre += c;
            result[i] = pre;
        }
        return result;
    }

    /***
     * 随机数组
     * @param length
     * @return
     */
    public static final int[] creatsArr(int length){
        int m = 1;
        int n = 5;
        int[] result = new int[length];
        int pre = 0;
        for (int i = 0; i < length; i++){
            int c = (int) (m + Math.random() * (n - m + 1));
            pre += c;
            result[i] = pre;
        }
        return result;
    }

    /**
     * 随机生成字符串
     * @param length
     * @return
     */
    public static final String creatString(int length){
        int m = 97;
        int n = 122;
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            char c = (char) (m + Math.random()* (n-m+1));
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 随机生成不重复的二维数组
     * @param length
     * @return
     */
    public static final int[][] createArrays(int length){
        int scope = length * length + 1;
        HashSet hashSet = new HashSet();
        Random random = new Random();
        int[][] arr = new int[length][length];
        for (int i = 0; i < length;i++) {
            for (int j = 0; j < length; j++) {
                int value;
                while (true) {
                    value = random.nextInt(scope);
                    if (value != 0 && !hashSet.contains(value)) {
                        arr[i][j] = value;
                        hashSet.add(value);
                        break;
                    }
                }
            }
        }
        return arr;
    }
}
