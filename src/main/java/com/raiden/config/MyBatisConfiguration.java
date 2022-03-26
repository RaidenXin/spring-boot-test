package com.raiden.config;

import com.raiden.sharding.AccessLogPreciseShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @date: Created by gangling on 9:42 2018/9/26.
 */
@Configuration
public class MyBatisConfiguration {

    /**
     * 也可以写在 application.yml 里面或者配置中心写
     mybatis:
     *   mapper-locations:
     *     - mappers/ProductMapper.xml
     *   config-location:
     *     classpath:mybatis-config.xml
     *   configuration:
     *     interceptors:
     *       - com.*.MybatisTraceSelectInterceptor
     *  放到application.yml中可能要写多份
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws Exception{

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        dataSourceMap.put("test", dataSource);

        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("test_tb_grade","test.test_tb_grade_${1..2}");
        //自定义的分片算法实现
        StandardShardingStrategyConfiguration standardStrategy = new StandardShardingStrategyConfiguration("USER_NAME", new AccessLogPreciseShardingAlgorithm());
        // 配置分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(standardStrategy);

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        // 获取数据源对象
        DataSource shardingDataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());

        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(shardingDataSource);

        return sfb.getObject();
    }
}
