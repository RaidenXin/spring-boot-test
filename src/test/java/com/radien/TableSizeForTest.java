package com.radien;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 2 轮，每次 1s
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次1s
@Fork(1)
@State(Scope.Thread) 
public class TableSizeForTest {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int ONE = 1 << 26;
    static final int TWO = 1 << 27;

    static final int roundSize = 100_000;

    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(TableSizeForTest.class.getSimpleName())
//                .build();
//        new Runner(opt).run();
        long start = System.nanoTime();
        jdk7();
        System.err.println("JDK7耗时： " + (System.nanoTime() - start) + " 纳秒");
        start = System.nanoTime();
        jdk8();
        System.err.println("JDK8耗时： " + (System.nanoTime() - start) + " 纳秒");
        start = System.nanoTime();
        jdk11();
        System.err.println("JDK11耗时：" + (System.nanoTime() - start) + " 纳秒");
    }

//    @Benchmark
    public static int jdk7() {
        int size = 0;
        for (int i = 1; i <= roundSize; i++) {
            size *= roundUpToPowerOf2(i);
        }
        return size;
    }

//    @Benchmark
    public static int jdk8() {
        int size = 0;
        for (int i = 1; i <= roundSize; i++) {
            size *= tableSizeFor(i);
        }
        return size;
    }

//    @Benchmark
    public static int jdk11() {
        int size = 0;
        for (int i = 1; i <= roundSize; i++) {
            size *= tableSizeFor_JDK11(i);
        }
        return size;
    }


//    @Benchmark
    public void test1() {
        for (int i = 1; i <= roundSize; i++) {
            int n = i - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            boolean b = n >= ONE;
        }
    }
//    @Benchmark
    public void test2() {
        for (int i = 1; i <= roundSize; i++) {
            int n = i - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            boolean b = n >= TWO;
        }
    }


    static int roundUpToPowerOf2(int cap) {
        return cap >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (cap > 1) ? Integer.highestOneBit((cap - 1) << 1) : 1;
    }

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static int tableSizeFor_JDK11(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
