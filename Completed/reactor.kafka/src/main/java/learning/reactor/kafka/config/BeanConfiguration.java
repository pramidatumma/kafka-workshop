package learning.reactor.kafka.config;

import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class BeanConfiguration {

  public static Logger log = LoggerFactory.getLogger(BeanConfiguration.class);

  @Bean
  public KafkaSender createKafkaSender(AppConfig appConfig) {
    Properties producerProperties = getProducerProperties(appConfig.getKafkaConfig());
    SenderOptions senderOptions = SenderOptions.create(producerProperties);
    KafkaSender kafkaSender = KafkaSender.create(senderOptions);
    return kafkaSender;
  }

  private Properties getProducerProperties(KafkaConfig config) {
    Properties producerProperties = new Properties();

    producerProperties
        .setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
    producerProperties
        .setProperty(ProducerConfig.ACKS_CONFIG, config.getProducerConfig().getAcksConfig());
    producerProperties
        .setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            config.getProducerConfig().getKeySerializer());
    producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        config.getProducerConfig().getValueSerializer());
    return producerProperties;
  }

  @Bean
  public KafkaReceiver createKafkaReceiver(AppConfig appConfig) {
    Properties consumerProperties = getConsumerProperties(appConfig.getKafkaConfig());
    ReceiverOptions receiverOptions = ReceiverOptions.create(consumerProperties);

    receiverOptions
        .subscription(Collections.singleton(AppConfig.KafkaTopicName))
        .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
        .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));

    KafkaReceiver kafkaReceiver = KafkaReceiver.create(receiverOptions);
    return kafkaReceiver;
  }


  private Properties getConsumerProperties(KafkaConfig config) {
    Properties consumerProperties = new Properties();

    consumerProperties
        .setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
    consumerProperties
        .setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            config.getConsumerConfig().getKeyDeserializer());
    consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        config.getConsumerConfig().getValueDeserializer());
    consumerProperties
        .setProperty(ConsumerConfig.GROUP_ID_CONFIG, config.getConsumerConfig().getGroupId());
    consumerProperties
        .setProperty(ConsumerConfig.CLIENT_ID_CONFIG, config.getConsumerConfig().getClientId());
    consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        config.getConsumerConfig().getAutoOffsetReset());

    return consumerProperties;
  }
}
