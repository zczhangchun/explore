package com.monkey.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author zhangchun
 */
public class ConmmitConsumer {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "kafkaIP:9092");
        props.put("group.id", "12312");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", "30000");
        //相当于begining，开启后也回读取原来的数据，但是前提是这个消费者组没有消费过这些数据
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("hello_topic"));
        while (true) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }

                //异步提交
//                consumer.commitAsync();
                //同步提交
                consumer.commitSync();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
