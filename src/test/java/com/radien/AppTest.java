package com.radien;

import com.alibaba.fastjson.JSON;
import com.raiden.annotation.NRpcScan;
import com.raiden.controller.OrderController;
import com.raiden.model.Order;
import com.raiden.model.URLInfo;
import com.raiden.utils.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Slf4j
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
            int middle = (start + end) >> 1;
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

    @Test
    public void test28() throws UnsupportedEncodingException {
        int[] nums = {1,2,2,4};
        System.out.println(findErrorNums(nums));
    }

    public int[] findErrorNums(int[] nums) {
        int[] newNums = new int[nums.length];
        for (int i : nums) {
            newNums[i - 1]++;
        }
        int[] result = new int[2];
        for (int i = 0; i < newNums.length; i++){
            result[0] = newNums[i] > 1 ? i + 1 : result[0];
            result[1] = newNums[i]  == 0 ? i + 1 : result[1];
        }
        return result;
    }

    @Test
    public void test29() throws UnsupportedEncodingException {
        for (int i = 1; i < 200;i++){
            int[] ints = AlgorithmUtils.creatsIntArr(i);
            int i1 = maxSubArray(ints);
            int i2 = maxSubArray2(ints);
            if (i1 != i2){
                throw new IllegalAccessError("这里错了i1：" + i1 + "  i2：" + i2);
            }
        }
    }


    public int maxSubArray(int[] nums) {
        int sum = nums[0];
        int result = sum;
        for (int i= 1; i < nums.length ; i++) {
            if (sum < 0){
                sum = 0;
            }
            sum = sum + nums[i];
            if (sum > result){
                result = sum;
            }
        }
        return result;
    }

    public int maxSubArray2(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    public String maximumTime(String time) {
        char[] chars = time.toCharArray();
        if (chars[0] == '?'){
            chars[0] = chars[1] >= '4' && chars[1] <= 9 ? '1' : '2';
        }
        if (chars[1] == '?'){
            chars[1] = chars[0] == '2'? '3' : '9';
        }
        if (chars[3] == '?'){
            chars[3] = '5';
        }
        if (chars[4] == '?'){
            chars[4] = '9';
        }
        return new String(chars);
    }


    @Test
    public void test30() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long result = new Random().nextInt(100);
                System.out.println("result1="+result);
                return result;
            }
        }).thenApply(new Function<Long, Long>() {
            @Override
            public Long apply(Long t) {
                long result = t*5;
                System.out.println("result2="+result);
                return result;
            }
        });

        long result = future.get();
        System.out.println(result);
    }

    @Test
    public void test31() throws Exception {
        System.err.println(fib(3));
        System.err.println(fib(4));
    }

    public int fib(int n) {
        if (n < 2){
            return n;
        }
        int a = 0, b = 1, c = 1;
        for (int i = 2; i < n; i++){
            a = b;
            b = c;
            c = a + b;
        }
        return c;
    }

    @Test
    public void test32() throws Exception {
        System.err.println(tribonacci(3));
        System.err.println(tribonacci(25));
    }

    public long tribonacci(int n) {
        if (n < 2){
            return n;
        }
        if (n == 2){
            return 1;
        }
        int a = 0, b = 1, c = 1, d = 2;
        for (int i = 3; i < n; i++){
            a = b;
            b = c;
            c = d;
            d = a + b + c;
        }
        return d;
    }

    @Test
    public void test33() throws Exception {
        System.err.println(firstBadVersion(2));
    }


    public int firstBadVersion(int n) {
        int left = 1,right = n, version = n;
        while (left < right){
            version = left + ((right - left) >> 1);
            if (isBadVersion(version)){
                right = version;
            }else {
                left = version + 1;
            }
        }
        return left;
    }

    private boolean isBadVersion(int version){
        return version >= 2;
    }


    @Test
    public void test34() throws Exception {
        int target = 0;
        for (int i = 4; i < 100; i++){
            int[] nums = AlgorithmUtils.creatsIntArr(i);
            target += (int) (Math.random() * 10);
            if (searchInsert(nums, target) != searchInsert2(nums, target)){
                System.err.println("出错了！target:" + target);
                System.err.println(Arrays.toString(nums));
            }
        }
    }

    public int searchInsert(int[] nums, int target) {
        int left = 0,right = nums.length - 1,min;
        while (left < right){
            min = left + ((right - left) >> 1);
            if (nums[min] == target){
                return min;
            }else if (nums[min] > target){
                right = min - 1;
            }else {
                left = min + 1;
            }
        }
        if (nums[left] < target){
            left += 1;
        }
        return left;
    }
    public int searchInsert2(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, ans = n;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    @Test
    public void test35() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                // 改值在FeignClient体系中会被动态覆盖
                .connectTimeout(6, TimeUnit.SECONDS)
                // 添加拦截器，支持动态设置超时时间
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        System.err.println();
                        return null;
                    }
                })
                .build();
        String url = "http://127.0.0.1:8100/getMemberList?name=xinlei";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

    @Test
    public void test36() throws Exception {
        Method searchInsert = this.getClass().getMethod("searchInsert2", new Class[]{int[].class, int.class});
        Parameter[] parameters = searchInsert.getParameters();
        for (Parameter parameter : parameters) {
            System.err.println(parameter.getName());
        }
    }

    private static final Pattern PATTERN = Pattern.compile("\\$\\{[a-z|A-Z]+[.a-z|A-Z]*}");

    @Test
    public void test37() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            FileChannel rw = null;
            try {
                rw = new RandomAccessFile(new File("text.txt"), "rw").getChannel();
                MappedByteBuffer buffer = rw.map(FileChannel.MapMode.READ_WRITE, 0, 10000);
                countDownLatch.await();
                for (int i = 0;i< 1000;i++){
                    buffer.putInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            FileChannel rw = null;
            try {
                rw = new RandomAccessFile(new File("text.txt"), "rw").getChannel();
                MappedByteBuffer buffer = rw.map(FileChannel.MapMode.READ_WRITE, 0, 10000);
                countDownLatch.await();
                for (int i = 0;i< 1000;i++){
                    buffer.putInt(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        countDownLatch.countDown();
        WaitingUtil.waiting(10000);
        System.err.println("----------------------------------------------------------");
        try {
            FileChannel rw = new RandomAccessFile(new File("text.txt"), "rw").getChannel();
            MappedByteBuffer buffer = rw.map(FileChannel.MapMode.READ_WRITE, 0, 10000);
            int temp = 0;
            for (int i = 0;i< 1000;i++){
                int anInt = buffer.getInt();
                if (i == 0){
                    temp = anInt;
                }
                if (anInt == temp){
                    System.out.println("--" + anInt + "   count：" + i);
                }else {
                    throw new IllegalArgumentException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test38(){
        int[] height = {1,8,6,2,5,4,8,3,7};
        System.err.println(maxArea(height));
    }

    public int maxArea(int[] height) {
        int max = 0;
        int left = 0,right = height.length - 1,lenght = right;
        while (left < right){
            boolean sign = height[left] < height[right];
            int volume = (sign ? height[left++] : height[right--]) * lenght--;
            if (volume > max){
                max = volume;
            }
        }
        return max;
    }


    @Test
    public void test39(){
        int[] nums = {0,0,0,0};
        System.err.println(threeSum(nums));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3){
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        if (nums[0] > 0){
            return new ArrayList<>();
        }
        int length = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < length; i++){
            //拍完序后 如果 第i个大于0 那么他后面的必定不会小于 0 那么他们的和也会大于0
            if (nums[i] > 0){
                break;
            }
            //这里是跳过重复的
            if (i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            //获取第一个数
            int one = nums[i];
            //如果要让 三个数等于 0 就是必须使得后面两个数相加的和等于负的第一个数
            int left = i + 1,right = length - 1;
            while (left < right){
                // 需要和上一次枚举的数不相同
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                    continue;
                }else if (right < length - 1 && nums[right] == nums[right + 1]){
                    right--;
                    continue;
                }
                if (nums[left] + nums[right] == -one){
                    List<Integer> list = new ArrayList<>();
                    list.add(one);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    result.add(list);
                }
                if (nums[left] + nums[right] > -one){
                    right--;
                }else {
                    left++;
                }
            }
        }
        return result;
    }


    @Test
    public void test40(){
        String digits = "23";
        System.err.println(letterCombinations(digits));
    }

    private static final Map<Character, List<String>> phoneMap = new HashMap<Character, List<String>>() {{
        put('2', Arrays.asList(new String[]{"a", "b","c"}));
        put('3', Arrays.asList(new String[]{"d", "e","f"}));
        put('4', Arrays.asList(new String[]{"g","h","i"}));
        put('5', Arrays.asList(new String[]{"j","k","l"}));
        put('6', Arrays.asList(new String[]{"m","n","o"}));
        put('7', Arrays.asList(new String[]{"p","q","r","s"}));
        put('8', Arrays.asList(new String[]{"t","u","v"}));
        put('9', Arrays.asList(new String[]{"w","x","y","z"}));
    }};

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()){
            return new ArrayList<>();
        }
       if (digits.length() == 1){
           return phoneMap.get(digits.charAt(0));
       }else {
           List<String> temp = null;
           for (int i = digits.length() - 1; i > -1; i--){
               if (temp == null){
                   temp = phoneMap.get(digits.charAt(i));
               }else {
                   List<String> strings = phoneMap.get(digits.charAt(i));
                   List<String> finalTemp = temp;
                   temp = strings.stream().flatMap(s -> finalTemp.stream().map(l -> s + l)).collect(Collectors.toList());
               }
           }
           return temp;
       }
    }


    @Test
    public void test41(){
        ListNode node = new ListNode(1, new ListNode(2));
        ListNode node1 = removeNthFromEnd(node, 2);
        System.err.println();
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null){
            return null;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode next = head.next;
        if (next == null){
            return null;
        }
        stack.push(head);
        while (next != null){
            stack.push(next);
            next = next.next;
        }
        ListNode node = null;
        for (int i = 0; i < n; i++) {
            node = stack.pop();
        }
        if (stack.isEmpty()){
            return node.next;
        }else {
            ListNode pop = stack.pop();
            if (node != null){
                pop.next = node.next;
            }
            node.next = null;
            return head;
        }
    }

    @Test
    public void test42(){
        String s = "){";
        System.err.println(isValid(s));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        if (chars.length < 2){
            return false;
        }
        boolean sign = true;
        Character peek;
        for (char c : chars){
            switch (c){
                case '{':
                    stack.push(c);
                    break;
                case '[':
                    stack.push(c);
                    break;
                case '(':
                    stack.push(c);
                    break;
                case '}':
                    if (stack.isEmpty()){
                        return false;
                    }
                    peek = stack.pop();
                    sign &= peek.equals('{');
                    break;
                case ']':
                    if (stack.isEmpty()){
                        return false;
                    }
                    peek = stack.pop();
                    sign &= peek.equals('[');
                    break;
                case ')':
                    if (stack.isEmpty()){
                        return false;
                    }
                    peek = stack.pop();
                    sign &= peek.equals('(');
                    break;
            }
        }
        return sign & stack.isEmpty();
    }

    @Test
    public void test43(){
        mergeTwoLists(null, null);
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode left = list1;
        ListNode right = list2;
        List<ListNode> listNodes = new ArrayList<>();
        while (true){
            if (left == null){
                for (;right != null;){
                    listNodes.add(right);
                    right = right.next;
                }
                break;
            }
            if (right == null){
                for (;left != null;){
                    listNodes.add(left);
                    left = left.next;
                }
                break;
            }
            if (right != null && left != null){
                int val1 = left.val;
                int val2 = right.val;
                if (val1 > val2){
                    listNodes.add(right);
                    right = right.next;
                }else {
                    listNodes.add(left);
                    left = left.next;
                }
            }
        }
        if (listNodes.isEmpty()){
            return null;
        }
        ListNode first = listNodes.get(0);
        ListNode next = first;
        for (int i = 1; i < listNodes.size(); i++) {
            if (next != null){
                next.next = listNodes.get(i);
                next = next.next;
            }
        }
        return first;
    }


    @Test
    public void test44(){
        String[] strings = {"(((())))","((()()))","((())())","((()))()","(()(()))","(()()())","(()())()",
                "(())(())","(())()()","()((()))","()(()())","()(())()","()()(())","()()()()"};
        List<String> list = Arrays.asList(strings);
        System.err.println(list.equals(generateParenthesis(4)));
        System.err.println(generateParenthesis(4));
    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        // 特判
        if (n == 0) {
            return res;
        }

        // 执行深度优先遍历，搜索可能的结果
        dfs("", n, n, res);
        return res;
    }

    /**
     * @param curStr 当前递归得到的结果
     * @param left   左括号还有几个可以使用
     * @param right  右括号还有几个可以使用
     * @param res    结果集
     */
    private void dfs(String curStr, int left, int right, List<String> res) {
        // 因为每一次尝试，都使用新的字符串变量，所以无需回溯
        // 在递归终止的时候，直接把它添加到结果集即可，注意与「力扣」第 46 题、第 39 题区分
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }

        // 剪枝（如图，左括号可以使用的个数严格大于右括号可以使用的个数，才剪枝，注意这个细节）
        if (left > right) {
            return;
        }

        if (left > 0) {
            dfs(curStr + "(", left - 1, right, res);
        }

        if (right > 0) {
            dfs(curStr + ")", left, right - 1, res);
        }
    }

    @Test
    public void test45(){
        ListNode[] lists = {
                new ListNode(0),
                new ListNode(1),
                new ListNode(2),
                new ListNode(3),
        };
        ListNode node = mergeKLists(lists);
        System.err.println(lists);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0){
            return null;
        }
        for (int i = 1; i < lists.length; i = i*2){
            for (int j = 0; j + i < lists.length; j = j + i*2){
                System.err.println("i:" + i + " j:" + j);
                lists[j] = merge2Lists(lists[j], lists[j+i]);
            }
        }
        return lists[0];
    }

    private ListNode merge2Lists(ListNode left,ListNode right){
        if (left == null){
            return right;
        }
        if (right == null){
            return left;
        }
        ListNode first,next;
        if (left.val < right.val){
            first = left;
            next = first;
            left = first.next;
        }else {
            first = right;
            next = first;
            right = first.next;
        }
        while (left != null && right != null){
            if (left.val < right.val){
                next.next = left;
                left = left.next;
            }else {
                next.next = right;
                right = right.next;
            }
            next = next.next;
        }
        if (left == null){
            next.next = right;
        }else {
            next.next = left;
        }
        return first;
    }


    @Test
    public void test46(){
        PrefixHeader header = new PrefixHeader();
        header.addStr("abc");
        header.addStr("ab");
        header.addStr("abcd");
        header.addStr("abcd");
        header.addStr("abcd");
//        header.addStr("abcd");
        System.err.println(header.countStr("abcd"));
    }

    @Test
    public void test47(){
        TreeNode root = new TreeNode(5);
        TreeNode node3 = new TreeNode(3);
        root.left = node3;
        TreeNode node6 = new TreeNode(6);
        root.right = node6;
        TreeNode node4 = new TreeNode(4);
        node3.right = node4;
        TreeNode node2 = new TreeNode(2);
        node3.left = node2;
        TreeNode node1 = new TreeNode(1);
        node2.left = node1;
        System.err.println(kthLargest(root, 3));
    }

    private int index = 0;
    private int res = 0;

    public int kthLargest(TreeNode root, int k) {
        traverseTree(root, k);
        return res;
    }

    private void traverseTree(TreeNode node, int k){
        if (node == null){
            return;
        }
        traverseTree(node.right, k);
        index++;
        if(index == k){
            res = node.val;
            return;
        }
        traverseTree(node.left, k);
    }



    @Test
    public void test48(){
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(8);
        ListNode l2 = new ListNode(0);
        System.err.println(addTwoNumbers(l1, l2));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode left = l1,right = l2;
        ListNode result = null,node = null;
        int temp = 0;
        while (left != null || right != null){
            int i = (left == null ? 0 : left.val) + (right == null ? 0 : right.val) + temp;
            if (i >= 10){
                i = i - 10;
                temp = 1;
            }else {
                temp = 0;
            }
            if (node == null){
                node = new ListNode(i);
                result = node;
            }else {
                node.next = new ListNode(i);
                node = node.next;
            }
            if (left != null){
                left = left.next;
            }
            if (right != null){
                right = right.next;
            }
        }
        if (temp > 0){
            node.next = new ListNode(temp);
        }
        return result;
    }

    @Test
    public void test49(){
        int[] nums = {1,1,2,3,3,4,4,8,8};
        System.err.println(singleNonDuplicate(nums));
    }

    /**
     * LeetCode 540题
     * @param nums
     * @return
     */
    public int singleNonDuplicate(int[] nums) {
        int i = 0;
        for (; i < nums.length - 1; i += 2) {
            if ((nums[i] ^ nums[i + 1]) != 0){
                return nums[i];
            }
        }
        return nums[i];
    }

    @Test
    public void test50(){
        int[] prices = {3,2,6,5,0,3};
        System.err.println(maxProfit121(prices));
    }

    /**
     * leetcode 第121 题
     * @param prices
     * @return
     */
    public int maxProfit121(int[] prices) {
        if (prices.length < 2){
            return 0;
        }
        int result = 0;
        int min = 10001,max = 0;
        int length = prices.length;
        for (int i = 0; i < length; i++){
            int temp;
            int p = prices[i];
            //这里是获取最小的点 如果最小的点是最后一个也就没有意义了
            if (p < min && i < length - 1){
                min = p;
                //因为 只有买了之后才能卖,后面的买的不能卖在前面的最高点,
                // 所以每次买的时候要将最高价还原成0
                max = 0;
            }else if (p > max){
                //获取最大的点
                max = p;
            }
            //求从最低到最高能获利多少钱
            temp = max - min;
            //如果比前面的最高获利还小 因为只能买卖一次,那这对最低和最高就没有意义
            if (temp > result){
                result = temp;
            }
        }
        return result;
    }

    @Test
    public void test51(){
        int[] prices = {3,2,6,5,0,3};
        System.err.println(maxProfit122_2(prices));
    }

    /**
     * leetcode 第122题 使用动态规划
     * @param prices
     * @return
     */
    public int maxProfit122_1(int[] prices) {
        //创建二维记录表
        //一天只有两种状态 买 和 不买 所以第一维是第几天 第二维是 买或者不买的状态 假设下标 0代表不买 1代表买 值为当天的收益
        int length = prices.length;
        int[][] status = new int[length][2];
        //第一天不买 收益为0
        status[0][0] = 0;
        //第一天买,切不当天卖的收益为 负的当天价格
        status[0][1] = -prices[0];
        //动态规划 状态转移方程
        for (int i = 1; i < length; i++) {
            // status[i][0] 如果当天不买,则当天最大的收益为 之前不买的收益(之前没有买股票,所以当天产生收益) 和 （之前买了的收益 + 今天本日价格的收益[之前买了现在才有收益]） 取一个最大值即为当前不买的最大收益
            int temp1 = status[i - 1][0],temp2 = status[i - 1][1] + prices[i];
            status[i][0] = temp1 > temp2 ? temp1 : temp2;
            // status[i][1] 如果当天买,则当天最大的收益为 之前不买的收益 + 因为今天买了当前价格股票产生的负收益 和 之前买了的收益(因为只能持有一只股票,所以之前买了今天不能再买,所以没有负收益) 取一个最大值 即为当前买的最大收益
            temp1 = status[i - 1][0] - prices[i];
            temp2 = status[i - 1][1];
            status[i][1] = temp1 > temp2 ? temp1 : temp2;
        }
        //最后一天肯定是不能买的 因为无法卖出 所以 去不买状态的 收益即可
        return status[length - 1][0];
    }

    /**
     * leetcode 第122题 使用贪心算法
     * @param prices
     * @return
     */
    public int maxProfit122_2(int[] prices) {
        //贪心算法的基本思路就是 把一段时间的收益 转换为 昨天买今天卖的单天的收益累加和
        //贪心算法 取局部最优解 即取所有的单天收益为正数的所有收益累加之和为最大收益
        int price = 0;
        for (int i = 1; i < prices.length; i++) {
            //昨天买今天卖 单天收益 为 今天的价格 - 昨天的价格
            int temp = prices[i] - prices[i - 1];
            //如果收益为正 累加
            if (temp > 0){
                price += temp;
            }
        }
        return price;
    }

    @Test
    public void test52() throws IOException {
        File file = new File("text.txt");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append(i);
            builder.append("\r\n");
        }
        byte[] bytes = builder.toString().getBytes("utf-8");
        MappedByteBuffer map = FileChannel.open(file.toPath(), StandardOpenOption.READ,StandardOpenOption.WRITE).map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
        map.put(bytes);
        map.force();
        byte[] read = new byte[bytes.length];
        map.flip();
        map.mark();
        map.get(read, 0, bytes.length);
        System.err.println(new String(read));
        System.err.println("开始切换");
        map.reset();
        map.get(read, 0, bytes.length);
        System.err.println(new String(read));
    }


    @Test
    public void test53() throws IOException {
        String json = "{\n" +
                "\t\"Test1\": \"1\",\n" +
                "\t\"Test2\": \"2\"\n" +
                "}";
        UserTest userTest = JSON.parseObject(json, UserTest.class);
        System.err.println(userTest);
    }

    @Test
    public void test54() throws IOException {
        int[] keys = new int[5];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            int hash = hash1(i, keys);
            hash = hash + 1;
        }
        long end = System.currentTimeMillis() - start;
        System.err.println("第一版本耗时：" + end + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            int hash = hash2(i, keys);
            hash = hash + 1;
        }
        end = System.currentTimeMillis() - start;
        System.err.println("第二版本耗时：" + end + "ms");
    }

    private int hash1(int i,int[] keys){
        int hash = i % keys.length;
        if (hash > 3) {
            hash += keys.length;
        }
        return hash;
    }

    private int hash2(int i,int[] keys){
        return (i % keys.length + keys.length) % keys.length;
    }

    private static final Pattern P = Pattern.compile("([0-9]*)\\.([0-9]*)\\.([0-9]*)\\.([0-9]*)");

    @Test
    public void test55() throws IOException {
        byte[] bytes = Files.readAllBytes(new File("C:\\Work\\work\\spring-boot-test\\src\\test\\resources\\test.json").toPath());
        String json = new String(bytes, "utf-8");
    }

    @Test
    public void test56() throws IOException {
        Semaphore semp = new Semaphore(0);
        new Thread(() ->{
            for (;;){
                try {
                    semp.acquire();
                    System.err.println("申请到了信号量！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() ->{
            for (;;){
                semp.release();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test57() throws UnsupportedEncodingException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Set<String> sentinels = new HashSet<>(Arrays.asList(
                "哨兵IP1:port1",
                "哨兵IP2:port2",
                "哨兵IP3:port3"
        ));
        JedisSentinelPool pool = new JedisSentinelPool("masterName", sentinels, jedisPoolConfig, "password");
        Jedis jedis = pool.getResource();
        System.out.println(jedis.getClient().getHost());
        System.out.println(jedis.getClient().getPort());
    }


    private static final Charset ASCII = Charset.forName("ASCII");
    /**
     * # 0 =  "String Encoding"
     * # 1 =  "List Encoding"
     * # 2 =  "Set Encoding"
     * # 3 =  "Sorted Set Encoding"
     * # 4 =  "Hash Encoding"
     * # 9 =  "Zipmap Encoding"
     * # 10 = "Ziplist Encoding"
     * # 11 = "Intset Encoding"
     * # 12 = "Sorted Set in Ziplist Encoding"
     * # 13 = "Hashmap in Ziplist Encoding"
     * @throws IOException
     */
    @Test
    public void test58() throws IOException {
        File rdb = new File("D:\\redis-cluster\\home\\redis-cluster\\8013\\data\\dump.rdb");
        FileChannel open = FileChannel.open(rdb.toPath(), StandardOpenOption.READ);
        long size = open.size();
        System.err.println(size);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = open.read(byteBuffer);
        System.err.println(read);
        if(read > 0){
            byteBuffer.flip();
            byte[] data = new byte[1024];
            byteBuffer.get(data);
            int index = 0;
            for (;byte2Int(data[index]) != 251;index++);
            index += 3;
            byte b = data[index];
            int i = byte2Int(b);
            if (i > -1 && i < 14){
                //这里是类型
                switch (i){
                    case 0: {
                        System.err.println("这是一个String!");
                        int keySize = byte2Int(data[index += 1]);
                        System.err.println("Key长度是：" + keySize);
                        byte[] bytes = new byte[keySize];
                        byteBuffer.get(bytes, 0, keySize);
                        System.err.println("Key:" + new String(bytes, ASCII));
                    }
                        break;
                    case 1:
                        System.err.println("这是一个List!");
                        break;
                    case 2:
                        System.err.println("这是一个Set!");
                        break;
                    case 3:
                        System.err.println("这是一个Sorted Set!");
                        break;
                    case 4:
                        System.err.println("这是一个Hash!");
                        break;
                    case 9:
                        System.err.println("这是一个Zipmap!");
                        break;
                    case 10:
                        System.err.println("这是一个Ziplist!");
                        break;
                    case 11:
                        System.err.println("这是一个Intset!");
                        break;
                    case 12: {
                        System.err.println("这是一个Sorted Set in Ziplist!");
                        int keySize = byte2Int(data[index += 1]);
                        System.err.println("Key长度是：" + keySize);
                        byte[] bytes = new byte[keySize];
                        System.arraycopy(data, index+=1, bytes, 0, keySize);
                        index+=keySize;
                        System.err.println("Key:" + new String(bytes, ASCII));
                        int valueSize = byte2Int(data[index += 1]);
                        bytes = new byte[valueSize];
                        System.arraycopy(data, index+=1, bytes, 0, valueSize);
                        index+=keySize;
                        System.err.println("Value:" + new String(bytes, ASCII));
                    }
                        break;
                    case 13:
                        System.err.println("这是一个Hashmap in Ziplist!");
                        break;
                }
            }else if (i == 252){
                //这里是超时时间
            }
        }
    }

    public static int byte2Int(byte b){
        return (int)(b & 0xff);
    }


    @Test
    public void test59() throws IOException {
        byte[] bytes = new byte[2704];
        File rdb = new File("D:\\redis-cluster\\home\\redis-cluster\\8013\\data\\dump.rdb");
        FileChannel open = FileChannel.open(rdb.toPath(), StandardOpenOption.READ);
        long size = open.size();
        long sum = size / 1024 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //判断是否是最后一个
        for (long i = 0; i < sum; i ++){
            if (i == sum - 1){
                int length = (int) (size & (1024 - 1));
                if (length > 0){
                    open.read(byteBuffer);
                    byteBuffer.flip();
                    byteBuffer.get(bytes, (int)(i << 10), length);
                    byteBuffer.flip();
                }else {
                    open.read(byteBuffer);
                    byteBuffer.flip();
                    byteBuffer.get(bytes, (int)(i << 10), 1024);
                    byteBuffer.flip();
                }
            }else {
                open.read(byteBuffer);
                byteBuffer.flip();
                byteBuffer.get(bytes, (int)(i << 10), 1024);
                byteBuffer.flip();
            }
        }
        System.err.println(new String(bytes, "utf-8"));
    }


    @Test
    public void test60() throws IOException {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(20);
        for (int i = 99; i > -1; i--) {
            int size = minHeap.size();
            if (size == 10){
                Integer peek = minHeap.peek();
                if (peek.intValue() < i){
                    minHeap.poll();
                    minHeap.add(i);
                }
            }else {
                minHeap.add(i);
            }
        }
        Integer poll;
        do {
            poll = minHeap.poll();
            if (poll != null){
                System.err.println(poll);
            }
        }while (poll != null);
    }


    @Test
    public void test61() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User<String> user = new User<>();
        long start = System.currentTimeMillis();
        String name = "!1111";
        for (int i = 0; i < 1000_0000; i++) {
            user.setName(name);
            name = user.getName();
        }
        System.err.println("方法调用消耗时间:" + (System.currentTimeMillis() - start));
        Method setName = user.getClass().getDeclaredMethod("setName", String.class);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            setName.invoke(user, name);
            name = user.getName();
        }
        System.err.println("反射调用消耗时间:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test63()  {
        int[] nums = {2,0,2,1,1,0};
        System.out.println(nums);
        sort(nums, 0, nums.length - 1);
        System.out.println(nums);
    }

    public void sort(int[] nums,int start,int end){
        if(start >= end){
            return;
        }
        if(start + 1 == end){
            if(nums[start] > nums[end]){
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
            return;
        }
        int left = start,right = end;
        while(left < right){
            for(;right > left;right--){
                if(nums[right] < nums[left]){
                    int temp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = temp;
                    break;
                }
            }
            for(;left < right;left++){
                if(nums[right] < nums[left]){
                    int temp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = temp;
                    break;
                }
            }
        }
        sort(nums, start, left - 1);
        sort(nums, left + 1, end);
    }

    private static final Pattern PATTERN2 = Pattern.compile(".*拆分成功([0-9]+)个订单$");

    @Test
    public void test64()  {
        String str = "拆分成功2个订单拆分成功1个订单";
        Matcher matcher = PATTERN2.matcher(str);
        int successQty = -1;
        if (matcher.find()){
            successQty = Integer.parseInt(matcher.group(1));
        }
        System.out.println(successQty);
    }


    @Test
    public void test65() throws Exception{
        String t = "9nQGzqdxeNAusrnBe5jX3stdxszuYOrj7hUHUVOdiGeZI8qllIxUapmxAtaZpztQmXA5qLBDw3cPwtvGISHlKXfi8GGk640F58KlJWixLLr7U23pJSPtiflvM4SKl62UnabzIl6XcN2u2KiJ9bgHDnMg80qEMoaQJdDGtclP4CUSHpblUaixbFbiE0p4xMgsALFcQJhs3RUC7tThDF8PlgxSTLe15ahSzY6d7X9PIY4vgoVldzrfjNb1kQKZYOc2";
        String n = t.substring(1, 3)
              ,a = t.substring(10, 12)
              , c = t.substring(25, 27)
              , i = t.substring(38, 40)
              , o = t.substring(50, 52)
              , r = t.substring(69, 71)
              , s = t.substring(90, 92)
              , u = t.substring(120, 122)
              , d = n + a + c + i + o + r.toLowerCase() + s + u;
        // accountId: b7f3d2a015db9ceafeba1d639e31b420e24c28714031773257a877d4fdc5bac3
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        System.err.println(MD5Utils.encode(("b7f3d2a015db9ceafeba1d639e31b420e24c28714031773257a877d4fdc5bac3" + timestamp + d)));
        String sign = "6614b8dd0d182e701397b1664fa21950";
    }

    public String customSortString(String order, String s){
        List<Character> characterList = new ArrayList<>(s.length());
        for (char c : s.toCharArray()) {
            characterList.add(c);
        }
        Comparator<Character> comparator = (a, b) -> order.indexOf(a) - order.indexOf(b);
        characterList.sort(comparator);
        StringBuilder builder = new StringBuilder(s.length());
        for (Character c : characterList) {
            builder.append(c);
        }
        return builder.toString();
    }

    @Test
    public void test66() {
        // 定义PDF文件路径
        String filePath = "C:\\Users\\Raiden\\Desktop\\skiplists.pdf";

        try {

            // 创建PDF解析器对象
            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(filePath));
            parser.parse();

            // 获取PDF文档对象
            COSDocument cosDoc = parser.getDocument();
            PDDocument pdDoc = new PDDocument(cosDoc);

            // 创建PDF文本抽取器对象
            PDFTextStripper stripper = new PDFTextStripper();

            // 获取PDF文档的页数
            int numOfPages = pdDoc.getNumberOfPages();

            // 遍历PDF文件的每一页，抽取文本内容并输出到控制台
            for (int i = 1; i <= numOfPages; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String text = stripper.getText(pdDoc);
                System.out.println("Page " + i + ":");
                System.out.println(text);
            }

            // 关闭PDF文档对象
            pdDoc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test67() {
        Integer test = getUnitPrice(1, null);
    }

    private Integer getUnitPrice(int a,Integer b) {
        return a > 0 ? b : a;
    }

    @Test
    public void test68() throws FileNotFoundException {
        // 指定文件路径
        String filePath = "path/to/your/file.txt";
        // 使用 FileReader 和 BufferedReader 读取文件
        try (
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            // 读取文件内容
            String line;
            while ((line = bufferedReader.readLine()) != null) {
               log.info(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test69() throws FileNotFoundException {
        Order order1 = new Order("1", "a", 1);
        Order order2 = new Order("2", "b", 2);
        Order order3 = new Order("3", "c", 3);
        Order order4 = new Order("1", "d", 4);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4);
        // 如果存在重复可以直接忽略直接覆盖
        orders.stream().collect(Collectors.toMap(Order::getMemberId, Function.identity(), (a, b) -> a));
        // 如果存在重复 必须要中断程序 可以抛出自己的业务异常 并记录key
        orders.stream().collect(Collectors.toMap(Order::getMemberId, Function.identity(),
                (a, b) -> {
            throw new RuntimeException("订单中 memberId 存在重复! memberId:" + a.getMemberId());
        }));
    }

    @Test
    public void test70() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        Long count1 = 0L;
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            count1 += i;
        }
        long end = System.currentTimeMillis();
        log.error("第一次耗时:{} s", (end - start) / 1000);
        start = System.currentTimeMillis();
        long count2 = 0L;
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            count2 += i;
        }
        end = System.currentTimeMillis();
        log.error("第二次耗时:{} s", (end - start) / 1000);
    }

    @Test
    public void test71() throws FileNotFoundException {

    }
}

