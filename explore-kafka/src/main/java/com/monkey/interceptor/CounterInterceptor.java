package com.monkey.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author zhangchun
 */
public class CounterInterceptor implements ProducerInterceptor {
    private int errorCounter = 0;
    private int successCounter = 0;

    public ProducerRecord onSend(ProducerRecord record) {
        return null;
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

        if (exception == null) {
            successCounter++;
        } else {
            errorCounter++;
        }
    }

    public void close() {
        // 保存结果
        System.out.println("Successful sent: " + successCounter);
        System.out.println("Failed sent: " + errorCounter);
    }

    public void configure(Map<String, ?> configs) {

    }
}
