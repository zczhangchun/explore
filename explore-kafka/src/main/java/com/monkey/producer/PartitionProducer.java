package com.monkey.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 使用自己的Partition分配规则生产数据
 */
public class PartitionProducer {
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

        //多设置一个Partition属性
        props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.zhangchun.partition.MyPartition");

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 2.创建Producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // 3.发送消息
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("first",
                    "hello-" + i));
            System.out.println("----");
        }

        //关闭资源
        producer.close();


    }
}
