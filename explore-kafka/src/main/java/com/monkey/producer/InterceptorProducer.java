package com.monkey.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author zhangchun
 */
public class InterceptorProducer {
    public static void main(String[] args) {


        // 1.设置参数
        Properties props = new Properties();
        //kafka 集群，broker-list
        props.put("bootstrap.servers", "192.168.203.132:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        //每批发送的数据最大限制 默认就是16384
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小 默认就是33554432
        props.put("buffer.memory", 33554432);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        List<String> interceptorList = new ArrayList<String>();
        interceptorList.add("com.zhangchun.interceptor.CounterInterceptor");
        interceptorList.add("com.zhangchun.interceptor.TimeInterceptor");

        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorList);

        // 2.创建Producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);


        // 3.发送消息
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("javademo1", "man-" + i));
        }

        //关闭资源，才会调用interceptor的close方法
        producer.close();

    }
}
