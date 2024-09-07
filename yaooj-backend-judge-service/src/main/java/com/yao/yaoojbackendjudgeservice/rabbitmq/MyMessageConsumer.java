package com.yao.yaoojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.yao.yaoojbackendjudgeservice.judge.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: MyMessageConsumer
 * Package: com.yao.yaoojbackendjudgeservice.rabbitmq
 * Description:
 * 消费者代码
 *
 * @Author Joy_瑶
 * @Create 2024/8/13 15:12
 * @Version 1.0
 */
@Component
@Slf4j
public class MyMessageConsumer {
    @Resource
    private JudgeService judgeService;
    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag,false);
        }catch(Exception e){
            e.printStackTrace();
            channel.basicNack(deliveryTag,false,false);
        }
    }
}
