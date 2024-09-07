package com.yao.yaoojbackendquestionservice;

import com.yao.yaoojbackendquestionservice.rabbitmq.MyMessageProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.yao.yaoojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.yao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yao.yaoojbackendserviceclient.service"})
public class YaoojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YaoojBackendQuestionServiceApplication.class, args);
    }

}
