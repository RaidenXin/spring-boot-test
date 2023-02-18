package com.radien;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 17:37 2022/2/26
 * @Modified By:
 */
public class LeetCode {

    @Test
    public void test1() throws IOException {
        int[] nums = {7,1,5,4};
        System.err.println(maximumDifference(nums));
    }
    public int maximumDifference(int[] nums) {
        if (nums.length == 0){
            return -1;
        }
        int result = -1;
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            if (temp > min){
                result = Math.max(result, temp - min);
            }else {
                min = temp;
            }
        }
        return result;
    }

    @Test
    public void test2() throws IOException {
        System.err.println("III  -->  " + romanToInt("III"));
        System.err.println("IV  -->  " + romanToInt("IV"));
        System.err.println("IX  -->  " + romanToInt("IX"));
        System.err.println("LVIII  -->  " + romanToInt("LVIII"));
        System.err.println("MCMXCIV  -->  " + romanToInt("MCMXCIV"));
    }

    private Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};
    /**
     * LeetCode 13 题
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        int length = s.length();
        int sum = 0,pre = -1;
        for (int i = length - 1; i > -1; i--) {
            int n = symbolValues.get(s.charAt(i));
            if (n < pre){
                sum -= n;
            }else {
                sum += n;
            }
            pre = n;
        }
        return sum;
    }
    @Test
    public void test3() throws IOException {
        System.err.println("3  -->  " + intToRoman(3));
        System.err.println("4  -->  " + intToRoman(4));
        System.err.println("9  -->  " + intToRoman(9));
        System.err.println("58  -->  " + intToRoman(58));
        System.err.println("1994  -->  " + intToRoman(1994));
    }

    String[] thousands = {"", "M", "MM", "MMM"};
    String[] hundreds  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] tens      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] ones      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};


    public String intToRoman(int num) {
        StringBuilder builder = new StringBuilder();
        //获取 千位
        builder.append(thousands[num / 1000]);
        //获取 百位
        builder.append(hundreds[(num % 1000) / 100]);
        //获取 十位
        builder.append(tens[(num % 100) / 10]);
        //获取 个位
        builder.append(ones[num % 10]);
        return builder.toString();
    }

    @Test
    public void test4() throws IOException {
        System.err.println(isUnique("leetcode"));
        System.err.println(isUnique("abc"));
    }

    public boolean isUnique2(String astr) {
        int l = astr.length();
        for (int i = 0; i < l; i++) {
            char c1 = astr.charAt(i);
            for (int j = i + 1; j < l; j++) {
                if (c1 == astr.charAt(j)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isUnique(String astr) {
        for(int i = 0,length = astr.length(); i < length; i++){
            if (astr.indexOf(astr.charAt(i), i + 1) != -1){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test5() throws IOException {
        System.err.println(CheckPermutation("abc", "bca"));
        System.err.println(CheckPermutation("abc", "bad"));
        System.err.println(CheckPermutation("aa", "bb"));
        System.err.println(CheckPermutation("ac", "bb"));
        System.err.println(CheckPermutation("asvnpzurz","urzsapzvn"));
    }

    public boolean CheckPermutation(String s1, String s2) {
        int[] arr = new int[26];
        int l = s1.length();
        if (l != s2.length()){
            return false;
        }
        for (int i = 0; i < l; i++) {
            arr[s1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < l; i++) {
            int index = s2.charAt(i) - 'a';
            arr[index]--;
            if (arr[index] < 0){
                return false;
            }
        }
        return true;
    }


    @Test
    public void test6() throws IOException {
//        System.err.println(replaceSpaces("Mr John Smith    ", 13));
        System.err.println(replaceSpaces("               ", 5));
    }

    public String replaceSpaces(String S, int length) {
        //先把字符串转化为字符数组
        char[] chars = S.toCharArray();
        int index = chars.length - 1;
        for (int i = length - 1; i >= 0; i--) {
            //如果遇到空格就把他转化为"%20"
            if (chars[i] == ' ') {
                chars[index--] = '0';
                chars[index--] = '2';
                chars[index--] = '%';
            } else {
                chars[index--] = chars[i];
            }
        }
        return new String(chars, index + 1, chars.length - index - 1);
    }

    @Test
    public void test7() throws IOException {
        int[] nums = {2,2,7,5,4,3,2,2,1};
        nextPermutation(nums);
        System.err.println(Arrays.toString(nums));
    }

    /**
     * LeetCode 31 题
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int len = nums.length - 1;
        int start = -1;
        for (int i = len - 1; i > -1 ; i--) {
            if (nums[i] < nums[i + 1]){
                start = i;
                break;
            }
        }
        for (int i = len; i > -1 ; i--) {
            if (nums[i] > nums[start]){
                nums[i] ^= nums[start];
                nums[start] ^= nums[i];
                nums[i] ^= nums[start];
                break;
            }
        }
        //i + 1 是i 交换了之后 将 i 后面的 按照降序排列
        quickSort(nums, start + 1, nums.length - 1);
    }

    /**
     * 快速排序
     * @param nums
     */
    private void quickSort(int[] nums){
        quickSort(nums, 0, nums.length - 1);
    }

    /**
     * 快速排序
     * @param nums
     * @param start
     * @param end
     */
    private void quickSort(int[] nums,int start,int end){
        if (start < 0 || end > nums.length || start >= end){
            return;
        }
        if (start == end - 1){
            if (nums[start] > nums[end]){
                nums[start] ^= nums[end];
                nums[end] ^= nums[start];
                nums[start] ^= nums[end];
            }
        }else {
            int refer = nums[start];
            int left = start,right = end;
            while (left < right){
                //从左边开始查找大于或者等于 参照数的数字
                while (nums[left] < refer && left < right){
                    left++;
                }
                //从右边开始查找 小于或者等于 参照数字的数字
                while (nums[right] >= refer && left < right){
                    right--;
                }
                //找到后交换
                if (left < right){
                    //交换
                    int temp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = temp;
                }
            }
            quickSort(nums, start, left - 1);
            quickSort(nums, left + 1, end);
        }
    }


    @Test
    public void test8() throws IOException {
        System.err.println(longestValidParentheses("(()"));
//        System.err.println(longestValidParentheses(")()())"));
//        System.err.println(longestValidParentheses(""));
//        System.err.println(longestValidParentheses("()(())"));
    }

    public int longestValidParentheses(String s) {
        int len = s.length();
        if (len == 0){
            return len;
        }
        int count = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '('){
                if (i  + 1< len && s.charAt(i + 1) == ')'){
                    count += 2;
                    i++;
                }else {
                    count = 0;
                }
            }else {
                count = i == len - 1? count : 0;
            }
        }
        return count;
    }

    @Test
    public void test9() throws IOException {
        System.err.println(removeDuplicates(new int[]{0,0,1,1,1,2,2,3,3,4}));
        System.err.println(removeDuplicates(new int[]{1,1,2}));
    }

    public int removeDuplicates(int[] nums) {
        int temp = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length && count <= i; i++) {
            if (temp != nums[i]){
                count += 1;
                temp = nums[i];
                nums[count - 1] = temp;
            }
        }
        return count;
    }

    @Test
    public void test10() throws IOException {
        System.err.println(maxProfit(new int[]{7,1,5,3,6,4}));
        System.err.println(maxProfit(new int[]{1,2,3,4,5}));
        System.err.println(maxProfit(new int[]{7,6,4,3,1}));
    }

    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
        }
        return dp[prices.length - 1][1];
    }

    /**
     * 409. 最长回文串
     * @throws IOException
     */
    @Test
    public void test11() throws IOException {
        System.err.println(longestPalindrome("abccccdd"));
        System.err.println(longestPalindrome("aaaAaaaa"));
    }

    public int longestPalindrome(String s) {
        int[] arr = new int[52];
        int count = 0;
        for (char c : s.toCharArray()) {
            int index = c < 97 ? c - 64 : c - 71;
            switch (arr[index]){
                case 0: arr[index]++; break;
                case 1: {
                    arr[index] = 0;
                    count += 2;
                }
            }
        }
        for (int i : arr){
            if (i == 1){
                return count + 1;
            }
        }
        return count;
    }


    /**
     * 14. 最长公共前缀
     * @throws IOException
     */
    @Test
    public void test12() throws IOException {
        System.err.println(longestCommonPrefix(new String[]{"flower","flow","flight"}));
        System.err.println(longestCommonPrefix(new String[]{"dog","racecar","car"}));
    }

    public String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        if (length == 0){
            return "";
        }
        if (length == 1){
            return strs[0];
        }
        int min = Integer.MAX_VALUE;
        for (String str : strs){
            int size = str.length();
            min = min > size? size : min;
        }
        StringBuilder builder = new StringBuilder(min);
        for (int i = 0; i < min; i++) {
            char c = strs[0].charAt(i);
            for (String str : strs){
                if(c != str.charAt(i)){
                    return builder.toString();
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }


    /**
     * 14. 最长公共前缀
     * @throws IOException
     */
    @Test
    public void test13() throws IOException {
        System.err.println(new BigDecimal("1.00").stripTrailingZeros().scale());
    }


    public String replaceSpace(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()){
            if (c == 32){
                builder.append("%20");
            }else {
                builder.append(c);
            }
        }
        return builder.toString();
    }


}
