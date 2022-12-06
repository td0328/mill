package com.myth.system.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        Map<String, Object> map = getYaml();
        //这里通过Map的方式获取到yaml文件里面的dataType是哪一个
        Map<String, Object> dataBaseConfig = (Map<String, Object>) map.get("datatype");
        return dataBaseConfig;
    }
    public Map<String, Object> getDefaultDataBase() {
        Map<String, Object> map = getYaml();
        //这里通过Map的方式获取到yaml文件里面的dataType是哪一个
        Map<String, Object> dataBaseConfig = (Map<String, Object>)((Map<String, Object>) map.get("spring")).get("datasource");
        return dataBaseConfig;
    }
    //获取Yaml
    public Map<String, Object> getYaml() {
        //获取Yaml
        Yaml yaml = new Yaml();
        Map<String, Object> map;
        try {
            map = yaml.load(new FileInputStream(System.getProperty("user.dir") + "\\mill-service\\src\\main\\resources\\application.yml"));
        } catch (IOException e) {
            throw new RuntimeException("SYS_PATH_ERROR");
        }
        return map;
    }
}