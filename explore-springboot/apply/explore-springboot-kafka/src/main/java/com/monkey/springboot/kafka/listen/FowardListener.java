package com.monkey.springboot.kafka.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
//@Component
@Slf4j
public class FowardListener {

    @KafkaListener(id = "12312", topics = "history")
    @SendTo("first")
    public String forward(String data){
        log.info("history  forward "+data+" to  first");
        return "history send msg : " + data;
    }
}
