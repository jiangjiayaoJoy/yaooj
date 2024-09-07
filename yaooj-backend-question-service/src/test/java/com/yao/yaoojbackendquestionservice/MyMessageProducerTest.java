package com.yao.yaoojbackendquestionservice;

import com.yao.yaoojbackendquestionservice.rabbitmq.MyMessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName: MyMessageProducerTest
 * Package: com.yao.yaoojbackendquestionservice
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/13 15:38
 * @Version 1.0
 */
@SpringBootTest
public class MyMessageProducerTest {
    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    void sendMessage() {
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", "你好呀");
    }
}
