package com.yao.yaoojbackendcommon.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MyBatisPlusConfig
 * Package: com.yao.yaoojbackendcommon.config
 * Description:
 * MyBatisPlus配置
 *
 * @Author Joy_瑶
 * @Create 2024/7/24 16:14
 * @Version 1.0
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 数据库语句拦截器配置
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
