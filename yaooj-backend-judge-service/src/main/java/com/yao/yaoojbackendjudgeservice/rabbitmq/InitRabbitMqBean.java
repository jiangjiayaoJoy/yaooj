package com.yao.yaoojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ClassName: MqInitMain
 * Package: com.yao.yaoojbackendcommon.utils
 * Description:
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 *
 * @Author Joy_瑶
 * @Create 2024/8/13 15:04
 * @Version 1.0
 */
@Component
@Slf4j
public class InitRabbitMqBean {
    @Value("${spring.rabbitmq.host:localhost}")
    private String host;

    @PostConstruct
    public void init(){
        try {
            //新建一个操作rabbitMQ的客户端
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //新建名为EXCHANGE_NAME的交换机
            String EXCHANGE_NAME = "code_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = "code_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");
            log.info("消息队列启动成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("消息队列启动失败！");
        }
    }
}
