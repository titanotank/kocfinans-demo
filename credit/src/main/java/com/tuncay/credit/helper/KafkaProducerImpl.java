package com.tuncay.credit.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuncay.credit.domain.CreditSmsMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

// Kafka topic bilgileri application properties filedan degiştirilebilir  ESNEK YAPIDA olması icin

@Component
public class KafkaProducerImpl implements KafkaProducerHelper {

    @Value("${kafka.sms.topic.name}")
    String topicName="sms";

    @Value("${kafka.producer.url}")
    private String server_config;

    @Autowired
    private ObjectMapper objectMapper;

    private Properties configPro;

    @PostConstruct
    public void init() {
        configPro = new Properties();
        configPro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,server_config);
        configPro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.ByteArraySerializer");
        configPro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

    }

    public void sentSmsMessageToKafkaTopic(CreditSmsMessage smsMessage) throws JsonProcessingException {

        String topicMessage = objectMapper.writeValueAsString(smsMessage);

        Producer producer=new KafkaProducer<String,String>(configPro);
        ProducerRecord<String,String> recItem = new ProducerRecord<String, String>(topicName,topicMessage);
        producer.send(recItem);
        producer.close();
    }


}
