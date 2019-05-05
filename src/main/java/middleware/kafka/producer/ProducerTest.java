package middleware.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Properties;

public class ProducerTest {

    public static void main(String[] args) {
        Properties properties = new Properties();

        // kafka 服务端的主机号和端口号
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 等待所有副本节点的应答
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        // 消息发送最大尝试数
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        // 一批消息处理大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 请求延时
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 发送缓存区内存大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // key序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 自定义分区，如果不定义，则自己做负载均衡
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "middleware.kafka.producer.CustomPartitioner");

        // 创建kafka Producer实例
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i=0; i<50; i++) {
            producer.send(new ProducerRecord<String, String>("first", "hello world-" + i),
                    (recordMetadata, e) -> {
                            if (recordMetadata != null) {
                                System.out.println(recordMetadata.partition() + "---" +
                                        recordMetadata.offset());
                            } else {
                                System.out.println("发送失败");
                            }
                    });
        }

        producer.close();

    }
}

