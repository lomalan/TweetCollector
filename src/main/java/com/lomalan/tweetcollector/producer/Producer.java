package com.lomalan.tweetcollector.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Producer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final String resourceConfigTopic;

  public Producer(KafkaTemplate<String, String> kafkaTemplate,
      @Value("${kafka.topic.resource-configuration}") String resourceConfigTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.resourceConfigTopic = resourceConfigTopic;
  }

  public void sendMessage(String message) {
    if (message == null) {
      log.info("Message is empty");
      return;
    }
    log.info("#### -> Producing message -> {}", message);
    kafkaTemplate.send(resourceConfigTopic, "key", message);
  }
}
