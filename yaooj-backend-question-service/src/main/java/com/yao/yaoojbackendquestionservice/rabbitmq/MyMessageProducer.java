package com.yao.yaoojbackendquestionservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: MyMessageProducer
 * Package: com.yao.yaoojbackendquestionservice.rabbitmq
 * Description:
 * 生产者代码
 *
 * @Author Joy_瑶
 * @Create 2024/8/13 15:10
 * @Version 1.0
 */
@Component
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void sendMessage(String exchange,String routingKey,String message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
