package learning.apache.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Util {

  static String serverConfig = "localhost:9092";
  static String KafkaTopicName = "MyTopic";

  public static Properties getProducerProperties() {
    Properties producerProperties = new Properties();
    producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
    producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    //TODO:: set producer properties
    return producerProperties;
  }

  public static Properties getConsumerProperties() {
    Properties producerProperties = new Properties();

    //TODO:: set consumer properties
    return producerProperties;
  }
}
