package com.radien;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raiden.config.Config;
import com.raiden.model.URLInfo;
import com.raiden.utils.JSONUtil;
import com.raiden.utils.RoutingRulesUtils;
import com.raiden.utils.UrlUtils;
import com.raiden.utils.WaitingUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppTest {

    private static final Map<String, Function<Integer, Integer>> map = new HashMap<>(2);

    static {
        map.put("1", AppTest::print1);
        map.put("2", AppTest::print2);
    }

    @Test
    public void test1(){
        for (int i = 1; i < 3; i++) {
            System.out.print(map.get(String.valueOf(i)).apply(0));
        }
    }

    public static int print1(int i){
        return i + 1;
    }

    public static int print2(int i){
        return i + 2;
    }


    @Test
    public void test2() throws UnsupportedEncodingException, ParseException {
        String path = "com.yao.item.business.interfaces.series.SeriesManager";
        String[] hostArr = {"172.17.4.21","172.17.4.20"};
        String ruleUrl = RoutingRulesUtils.createRule(path, hostArr);
        URLInfo urlInfo = UrlUtils.parseUrl(URLDecoder.decode(ruleUrl, "utf-8"));
        String rule = URLDecoder.decode(urlInfo.getParameters().get("rule"));
        System.out.println(rule);
        int i = rule.indexOf("=>");
        String whenRule = i < 0 ? null : rule.substring(0, i).trim();
        String thenRule = i < 0 ? rule.trim() : rule.substring(i + 2).trim();
        System.err.println(RoutingRulesUtils.parseRule(whenRule));
        System.err.println(RoutingRulesUtils.parseRule(thenRule));
    }

    @Test
    public void test3() throws UnsupportedEncodingException, ParseException {
        String path = "com.yao.item.business.interfaces.series.SeriesManager";
        String path1 = "com.yao.item.business.interfaces.series.SeriesManager2";
        String path2 = "com.yao.item.business.interfaces.series.SeriesManager3";
        System.err.println(createExceptionInfo(Stream.of(path, path1, path2).collect(Collectors.toSet())));
    }

    private String createExceptionInfo(Set<String> interfaceNameOfError){
        StringBuilder builder = new StringBuilder("接口：");
        for (String error : interfaceNameOfError){
            builder.append(error);
            builder.append(";");
        }
        int length = builder.length();
        return builder.replace(length - 1, length, "存在异常").toString();
    }

    @Test
    public void test4() throws UnsupportedEncodingException, ParseException {
        Pattern pattern = Pattern.compile("/dubbo/(.*)/routers");
        Matcher matcher = pattern.matcher("/dubbo/com.yao.item.business.interfaces.series.Series$Manager/routers");
        if (matcher.find()){
            System.err.println(matcher.group(1));
        }
    }

    @Test
    public void test5(){
        int[][] points = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
        System.err.println(findMinArrowShots(points));
    }

    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) {
            return 0;
        }
        //这里贪心算法，体现在优先找最大最多的相交数组集合
        //这里对数组根据末尾元素的大小进行排序 目的是为了后面取末尾元素和后面数组元素的第一个元素比较时，
        //能从小到大的取
        Arrays.sort(points, (p1, p2) -> p1[1] < p2[1] ? -1 : 1);
        //记录相交集合的数量
        int arrowConunt = 1;
        //取第一个数组的末尾（即是最大的）元素和后面的数组第一个（即是最小的）元素进行比较
        int pre = points[0][1];
        //遍历所有的数组
        for (int i = 1; i < points.length; i++) {
            //如果这个数组的第一个元素，比记录的元素大，说明它和上一个集合中的数组不相交
            if (points[i][0] > pre) {
                //数量加1
                arrowConunt++;
                //取这个数组的末尾元素，接着和后面的数组比较
                pre = points[i][1];
            }
        }
        return arrowConunt;
    }

    @Test
    public void test6(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(10);
        TreeNode node3 = new TreeNode(4);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(3);
        node2.left = node4;

        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(9);
        node3.left = node6;
        node3.right = node7;

        TreeNode node8 = new TreeNode(12);
        TreeNode node9 = new TreeNode(8);
        node4.left = node8;
        node4.right = node9;

        TreeNode node12 = new TreeNode(6);
        node6.left = node12;

        TreeNode node15 = new TreeNode(2);
        node7.right = node15;
        System.err.println(isEvenOddTree(node1));
    }

    @Test
    public void test7(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(2);
        node1.left = node2;
        node1.right = node3;
        TreeNode node4 = new TreeNode(3);
        TreeNode node6 = new TreeNode(3);
        TreeNode node7 = new TreeNode(7);
        node2.left = node4;
        node2.right = node6;
        node3.left = node7;
        System.err.println(isEvenOddTree(node1));
    }

    @Test
    public void test8(){
        TreeNode node1 = new TreeNode(2);
        System.err.println(isEvenOddTree(node1));
    }


    @Test
    public void test9(){
        TreeNode node1 = new TreeNode(11);
        TreeNode node2 = new TreeNode(12);
        TreeNode node3 = new TreeNode(8);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(3);
        TreeNode node6 = new TreeNode(7);
        node2.left = node4;
        node2.right = node6;

        TreeNode node7 = new TreeNode(11);
        node3.left = node7;


        TreeNode node12 = new TreeNode(20);
        node6.right = node12;

        System.err.println(isEvenOddTree(node1));
    }


    public boolean isEvenOddTree(TreeNode root) {
        List<TreeNode> roots = new ArrayList<>();
        roots.add(root);
        boolean result = true;
        //记录层级
        int level = 0;
        for (;;){
            //创建用来装这下一层的元素列表
            List<TreeNode> treeNodes = new ArrayList<>();
            //如果是偶数层 必须是奇整数，而且从左到右按顺序 严格递增
            if ((level & 1) == 0){
                int temp = 0;
                for (int i = 0,n = roots.size(); i < n; i++){
                    TreeNode treeNode = roots.get(i);
                    //看看是否是一次递曾且都为奇数
                    if ((treeNode.val & 1) != 1 || (i != 0 && treeNode.val <= temp)){
                        return false;
                    }
                    temp = treeNode.val;
                    if (treeNode.left != null){
                        treeNodes.add(treeNode.left);
                    }
                    if (treeNode.right != null){
                        treeNodes.add(treeNode.right);
                    }
                }
            }else {//如果是奇数层 必须是偶整数，而且从左到右按顺序 严格递减
                int temp = 0;
                for (int i = 0,n = roots.size(); i < n; i++){
                    TreeNode treeNode = roots.get(i);
                    //看看是否是一次递减且都为偶数
                    if ((treeNode.val & 1) != 0 || (i != 0 && treeNode.val >= temp)){
                        return false;
                    }
                    temp = treeNode.val;
                    if (treeNode.left != null){
                        treeNodes.add(treeNode.left);
                    }
                    if (treeNode.right != null){
                        treeNodes.add(treeNode.right);
                    }
                }
            }
            //如果为空直接结束
            if (treeNodes.isEmpty()){
                return result;
            }
            roots = treeNodes;
            level++;
        }
    }



    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    @Test
    public void test10(){
        TreeNode node1 = new TreeNode(11);
        TreeNode node2 = new TreeNode(12);
        TreeNode node3 = new TreeNode(8);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(3);
        TreeNode node6 = new TreeNode(7);
        node2.left = node4;
        node2.right = node6;

        TreeNode node7 = new TreeNode(11);
        node3.left = node7;


        TreeNode node12 = new TreeNode(20);
        node6.right = node12;

        System.err.println(countNodes(node1));
    }

    public int countNodes(TreeNode root) {
        if (root == null){
            return 0;
        }
        return 1+countNodes(root.left)+countNodes(root.right);
    }

    @Test
    public void test11(){
        String s = "aaaabbbbcccc";
        System.err.println(sortString(s));
    }

    public String sortString(String s) {
        //先创建 26个桶 代表26个英文字母
        int[] ints = new int[26];
        int size = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            ints[s.charAt(i) - 97]++;
        }
        StringBuilder builder = new StringBuilder(s.length());
        for (; builder.length() < length; ){
            for (int i = 0; i < 26; i++) {
                int count = ints[i];
                if (count > 0){
                    builder.append((char) (i + 97));
                }
                ints[i] = count - 1;
            }
            for (int i = 25; i > -1; i--) {
                int count = ints[i];
                if (count > 0){
                    builder.append((char) (i + 97));
                }
                ints[i] = count - 1;
            }
        }
        return builder.toString();
    }

    @Test
    public void test12() throws IOException {
        System.err.println(countPrimes(10));
    }

    public int countPrimes(int n) {
        if (n < 2){
            return 0;
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrimes2(i)){
                count++;
            }
        }
        return count;
    }
    private boolean isPrimes(int number){
        int last = number;
        int count = 0;
        for (int i = 2; i < last; i++){
            count++;
            if (number % i == 0){
                return false;
            }else {
                last = number / i;
            }
        }
        System.err.println(count);
        return true;
    }

    private boolean isPrimes2(int number){
        int max = (int) Math.sqrt(number);
        for (int i = 2; i <= max; i++){
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }


    @Test
    public void test13() throws IOException {
//        int[] ints1 = {1,2,3,3,4,5};
//        int[] ints2 = {1,2,3,3,4,4,5,5};
        int[] ints3 = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,23,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,31,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,34,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,39,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,40,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,43,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,44,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,46,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,47,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,51,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,54,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,56,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,57,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,58,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,59,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,61,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,62,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,67,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,68,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,71,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,73,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,74,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,75,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,76,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,77,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,78,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,79,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,81,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,82,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,83,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,84,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,85,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,86,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,87,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,88,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,89,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,91,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,92,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,93,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,94,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,96,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,97,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100};
//        System.err.println("int1: " + isPossible(ints1));
//        System.err.println("int2: " + isPossible(ints2));
        System.err.println("int3: " + isPossible(ints3));
    }

    public boolean isPossible(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>(nums.length << 1);
        for (int i : nums) {
            Integer conut = counter.get(i);
            if (conut != null){
                conut +=1;
            }else {
                conut = 1;
            }
            counter.put(i, conut);
        }
        Map<Integer, Integer> dicy = new HashMap<>(nums.length << 1);
        for(int n : nums) {
            Integer value = counter.get(n);
            if (value == 0){
                continue;
            }
            int temp = n - 1;
            Integer integer = dicy.get(temp);
            if (integer == null || integer == 0){
                int temp1 = n + 1;
                Integer int1 = counter.get(temp1);
                if (int1 == null || int1 == 0){
                    return false;
                }
                int temp2 = n + 2;
                Integer int2 = counter.get(temp2);
                if (int2 == null || int2 == 0){
                    return false;
                }
                counter.put(n , value - 1);
                counter.put(temp1 , int1 - 1);
                counter.put(temp2 , int2 - 1);
                Integer i = dicy.get(temp2);
                dicy.put(temp2, i == null ? 1 : i + 1);
            }else {
                dicy.put(temp, integer - 1);
                Integer i = dicy.get(n);
                dicy.put(n, i == null ? 1 : i + 1);
                counter.put(n , value - 1);
            }
        };
        return true;
    }

    @Test
    public void test14() throws IOException {
        String[] arry = {"1", "2", "3"};
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for (String a : arry){
            builder.append(a);
            builder.append(", ");
        }
        System.err.println(builder.substring(0, builder.length() - 2));
    }

    @Test
    public void test15() throws IOException {
        File path = findPath();
        System.err.println();
    }

    private static File findPath()  {
        String classResourcePath = AppTest.class.getName().replaceAll("\\.", "/") + ".class";

        URL resource = ClassLoader.getSystemClassLoader().getResource(classResourcePath);
        if (resource != null) {
            String urlString = resource.toString();

            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;

            if (isInJar) {
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                File agentJarFile = null;
                try {
                    agentJarFile = new File(new URL(urlString).toURI());
                } catch (MalformedURLException | URISyntaxException e) {
                }
                if (agentJarFile.exists()) {
                    return agentJarFile.getParentFile();
                }
            } else {
                int prefixLength = "file:".length();
                String classLocation = urlString.substring(
                        prefixLength, urlString.length() - classResourcePath.length());
                return new File(classLocation);
            }
        }
        return null;
    }

    @Test
    public void test16() throws IOException {
        int[] cost = {10, 15, 20};
        System.err.println(minCostClimbingStairs(cost));
    }

    public int minCostClimbingStairs(int[] cost) {
        for (int i = 2; i < cost.length; i++) {
            cost[i] = Math.min(cost[i - 1], cost[i - 2]) + cost[i];
        }
        return Math.min(cost[cost.length - 1], cost[cost.length - 2]);
    }

    @Test
    public void test17() throws IOException {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.right = node2;
        node2.left = node3;
        System.err.println(inorderTraversal(node1));
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> integers = new ArrayList<>();
        traversal(integers, root);
        return integers;
    }

    private void traversal(List<Integer> integers, TreeNode treeNode){
        if (treeNode != null){
            traversal(integers, treeNode.left);
            integers.add(treeNode.val);
            traversal(integers, treeNode.right);
        }
    }

    public int firstUniqChar2(String s) {
        int[] arr = new int[26];
        for (int i = 0, n = s.length(); i < n; i++) {
            arr[s.charAt(i) - 97]++;
        }
        for (int i = 0, n = s.length(); i < n; i++) {
            if (arr[s.charAt(i) - 97] == 1){
                return i;
            }
        }
        return -1;
    }

    @Test
    public void test18() throws IOException {
        Class<Object> objectClass = Object.class;
        System.err.println(objectClass.isInstance(null));
    }

    public int candy(int[] ratings) {
        int last = 0;
        int count = 0;
        for (int i = 0,j = i + 1;j < ratings.length; i++){

        }
        return 1;
    }

    public static int search(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;
        //核心在这 双指针到底是要 <= 因为 如果长度为 1 呢 是不是等于
        while (start <= end) {
            int middle = (start + end) / 2;
            if (key < arr[middle]) {
                //为什么 减1 因为 下标为 middle 的数不是要找的
                //只要找他前面的就行了 下面 +1 相同
                end = middle - 1;
            } else if (key > arr[middle]) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public void setUser(User<Permissions> user,Student<Permissions> student){

    }

    @Test
    public void test19() throws IOException, NoSuchMethodException {
        String str = "{\n" +
                "\t\"name\": \"11\",\n" +
                "\t\"ids\": [\"1\", \"2\"]\n" +
                "}";
        Permissions object = JSON.parseObject(str, Permissions.class);
        System.err.println(object);
    }

    @Test
    public void test20() throws IOException, NoSuchMethodException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresDate = now.plusSeconds(60 * 60 * 24);
        Date from = Date.from(expiresDate.toInstant(ZoneOffset.of("+8")));
        System.err.println(from);
    }

    @Test
    public void test21() throws IOException, NoSuchMethodException {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        System.err.println(queue.peek());
        for (;!queue.isEmpty();){
            System.err.println(queue.poll());
        }
    }

    @Test
    public void test22() throws IOException, NoSuchMethodException {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Thread thread = new Thread(() -> {
            for (int i = 0;i < 100000000;i++){
                synchronized (list){
                    list.add(String.valueOf(i));
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> list2 = new ArrayList<>();
        synchronized (list){
            list2 = new ArrayList<>(list);
        }
        list2.stream().forEach(a -> System.err.println(a));
    }

    @Test
    public void test24() throws Exception {
        Integer i = 1;
        Integer i2 = 1;
        System.err.println(i.equals(i2));
        int a = 10;
        int b = 20;
        b = a ^ b;
        a = a ^ b;
        b = b ^ a;
        System.out.println("a-------------------->" + a);
        System.out.println("b-------------------->" + b);
    }

    @Test
    public void test25() throws IOException, NoSuchMethodException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 100L, TimeUnit.MINUTES, new LinkedBlockingDeque<>(0));
        executorService.execute(() -> System.out.println(1111));
        WaitingUtil.waiting(1000);
        executorService.execute(() -> System.out.println(2222));
    }
}
