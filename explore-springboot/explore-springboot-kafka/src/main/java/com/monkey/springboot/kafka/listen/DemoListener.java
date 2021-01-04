package com.monkey.springboot.kafka.listen;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Slf4j
@Component
public class DemoListener {


    //    @KafkaListener(id = "12312", topics = "history")
    public void listen(ConsumerRecord<Integer, String> record) {
        System.out.println("---------------------");
        log.info("history receive : " + record.toString());
    }

    @KafkaListener(id = "12312", topics = "history")
    public void annoListener(@Payload String data,
//                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partitionId,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    ) {

        log.info("history receive : \n" +
                "data : " + data + "\n" +
                "partitionId : " + partitionId + "\n" +
                "topic : " + topic + "\n" +
                "timestamp : " + ts + "\n");

    }


}
