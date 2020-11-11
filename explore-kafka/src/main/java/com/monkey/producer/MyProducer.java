package com.monkey.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 基本生产者生产数据
 */
public class MyProducer {
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

        // 2.创建Producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);


        // 3.发送消息
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228546\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109200\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228546\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109201\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228546\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109202\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228547\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109203\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228547\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109204\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228547\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109205\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228548\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109206\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228548\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109207\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));
        producer.send(new ProducerRecord<String, String>("general_history_log_origin", "{\"1\":{\"str\":\"2650228548\"},\"2\":{\"i32\":1},\"3\":{\"str\":\"109208\"},\"4\":{\"i32\":3},\"5\":{\"i64\":1575253320664},\"6\":{\"i32\":1},\"7\":{\"str\":\"111.19.41.222\"}}"));

        //关闭资源
        producer.close();

    }
}
