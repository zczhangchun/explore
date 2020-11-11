package com.monkey.springboot.kafka.listen;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Kafka的Ack机制比较简单，只需简单的三步即可：
 *
 * 设置ENABLE_AUTO_COMMIT_CONFIG=false，禁止自动提交
 * 设置AckMode=MANUAL_IMMEDIATE
 * 监听方法加入Acknowledgment ack 参数
 *
 * 怎么拒绝消息呢，只要在监听方法中不调用ack.acknowledge()即可
 *
 * @author zhangchun
 */
//@Component
@Slf4j
public class AckListener {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafkaIP:9092");

        //设置ENABLE_AUTO_COMMIT_CONFIG=false，禁止自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean("ackContainerFactory")
    public ConcurrentKafkaListenerContainerFactory ackContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        //设置AckMode=MANUAL_IMMEDIATE
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        return factory;
    }


    /*
    //监听方法加入Acknowledgment ack 参数
    @KafkaListener(id = "12312", topics = "history",containerFactory = "ackContainerFactory")
    public void ackListener(ConsumerRecord record, Acknowledgment ack) {
        log.info("history receive : " + record.value());

        //紧接着注释ack.acknowledge()方法，重新测试，
        // 同样你会发现监听容器能接收到消息，这个时候如果你重启项目还是可以看到未被确认的那几条消息。
//        ack.acknowledge();
    }
     */

    /*
    //1.当使用ack时，想让没有被ack确认的消息能够继续被消费，可以将此消息在发送一次到kafka。
    @KafkaListener(id = "12312", topics = "history", containerFactory = "ackContainerFactory")
    public void ackListener(ConsumerRecord record, Acknowledgment ack, Consumer consumer) {
        log.info("history receive : " + record.value());

        //如果偏移量为偶数则确认消费，否则拒绝消费
        if (record.offset() % 2 == 0) {
            log.info(record.offset()+"--ack");
            ack.acknowledge();
        } else {
            log.info(record.offset()+"--nack");
            kafkaTemplate.send("history", record.value());
        }
    }
     */


    // 2.使用Consumer.seek方法，重新回到该未ack消息偏移量的位置重新消费，
    // 这种可能会导致死循环，原因出现于业务一直没办法处理这条数据，
    // 但还是不停的重新定位到该数据的偏移量上。
    @KafkaListener(id = "12312", topics = "history", containerFactory = "ackContainerFactory")
    public void ackListener(ConsumerRecord record, Acknowledgment ack, Consumer consumer) {
        log.info("history receive : " + record.value());

        //如果偏移量为偶数则确认消费，否则拒绝消费
        if (record.offset() % 2 == 0) {
            log.info(record.offset()+"--ack");
            ack.acknowledge();
        } else {
            log.info(record.offset()+"--nack");
            consumer.seek(new TopicPartition("history",record.partition()),record.offset() );
        }
    }

}
