package com.raiden.sharding;


import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 访问日志 精准分表算法
 */
public class AccessLogPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    private static final AtomicInteger VISITS = new AtomicInteger(0);

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Date> shardingValue) {
        int i = VISITS.addAndGet(1);
//        return (String) tableNames.stream().toArray()[i & 1];
        return "test_tb_grade_2";
    }
}
