package learning.apache.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageProducerAsync {

  private final static Logger logger = Logger.getLogger(Application.class.getName());

  public void produceMessagesToKafka() {

    Properties producerProperties = Util.getProducerProperties();

    // TODO:: Create KafkaProducer using the producerProperties
    KafkaProducer <String,String> kafkaProducer = new KafkaProducer<String, String>(producerProperties);

    for (int i = 1; i <= 10; i++) {

      String message = "sync message " + i;
      // TODO:: Write to kafka using the producer created above
      ProducerRecord producerRecord = new ProducerRecord ("MyTopic", message);
      logger.log(Level.INFO, "sending");
      kafkaProducer.send(producerRecord);
    }

    kafkaProducer.flush();

  }

}
