package com.myth.system.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr.Lee
 * @Date: 2022/8/13 11:05
 * @param
 * @Description:
 *  动态数据源配置类
 */
@Configuration
//事务管理，数据库连接这里涉及到事务的提交
@EnableTransactionManagement
public class DataSourceConfig {
    @Value("${spring.datasource.name}")
    private String name;
    // 动态注入数据库信息
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.dataType}")
    private String dataType;

    @Autowired
    private Environment environment;

    // 创建DynamicDataSource的bean交给SpringIOC容器管理
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource() {
        // 配置默认数据源
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(false);
        datasource.setName("default");

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("defaultDataSource", datasource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 将该数据源设置成默认数据源
        dynamicDataSource.setDefaultTargetDataSource(datasource);
        return dynamicDataSource;
    }

    // 获取数据源的驱动信息
    public Map<String, Object> getDataBaseConfig() {
        //这里通过Map的方式获取到yaml文件里面的dataType是哪一个
        Map<String, Object> dataBaseConfig = new HashMap<>();
        Map<String,String> mysql = new HashMap<>();
        mysql.put("driverClassName",environment.getProperty("datatype.mysql.driverClassName"));
        mysql.put("url",environment.getProperty("datatype.mysql.url"));
        dataBaseConfig.put("mysql",mysql);

        Map<String,String> clickhouse = new HashMap<>();
        clickhouse.put("driverClassName",environment.getProperty("datatype.clickhouse.driverClassName"));
        clickhouse.put("url",environment.getProperty("datatype.clickhouse.url"));
        dataBaseConfig.put("clickhouse",clickhouse);

        Map<String,String> sqlserver = new HashMap<>();
        sqlserver.put("driverClassName",environment.getProperty("datatype.sqlserver.driverClassName"));
        sqlserver.put("url",environment.getProperty("datatype.sqlserver.url"));
        dataBaseConfig.put("sqlserver",sqlserver);

        Map<String,String> postgresql = new HashMap<>();
        postgresql.put("driverClassName",environment.getProperty("datatype.postgresql.driverClassName"));
        postgresql.put("url",environment.getProperty("datatype.postgresql.url"));
        dataBaseConfig.put("postgresql",postgresql);

        return dataBaseConfig;
    }
    public Map<String, String> getDefaultDataBase() {
        //System.out.println("数据库配置："+ JSON.toJSONString(defaultDatabase));
        //这里通过Map的方式获取到yaml文件里面的dataType是哪一个
        Map<String, String> dataBaseConfig = new HashMap<>();
        String driverClassName =  environment.getProperty("spring.datasource.driverClassName");
        dataBaseConfig.put("driverClassName",driverClassName);
        String url =  environment.getProperty("spring.datasource.url");
        dataBaseConfig.put("url",url);
        String name =  environment.getProperty("spring.datasource.name");
        dataBaseConfig.put("name",name);
        String username =  environment.getProperty("spring.datasource.username");
        dataBaseConfig.put("username",username);
        String password =  environment.getProperty("spring.datasource.password");
        dataBaseConfig.put("password",password);

        return dataBaseConfig;
    }
}