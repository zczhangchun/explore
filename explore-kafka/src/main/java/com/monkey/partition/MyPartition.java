package com.monkey.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class MyPartition implements Partitioner {
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //这个方法就是将数据存储到那个哪个分区。
        return 1;
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
