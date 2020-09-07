package com.tuncay.smsservice.listener;

import com.tuncay.smsservice.service.impl.SmsServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

// kafkada sms topigi dinlenir me mesajlar alınıp sms servisine yollanır

@Service
public class KafkaConsumerListener {

    @Autowired
    private SmsServiceImpl smsService;

    @Value("${kafka.sms.topic.name}")
    String topicName="sms";

    @Value("${kafka.producer.url}")
    private String server_config;

    private KafkaConsumer<String,String> consumer;

    @PostConstruct
    public void subscribeKafkaConsumerSms(){
        Properties configPro = new Properties();
        configPro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,server_config);
        configPro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        configPro.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        configPro.put(ConsumerConfig.GROUP_ID_CONFIG,"smsservis");
        configPro.put(ConsumerConfig.CLIENT_ID_CONFIG,"smsclientservis");
        consumer=new KafkaConsumer<String,String>(configPro);
        consumer.subscribe(Arrays.asList(topicName));
    }

    @Scheduled(fixedDelay = 10000)
    public void listenSmsTopic(){
        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
        for(ConsumerRecord<String,String> record: consumerRecords){
            smsService.sentSmsMessage(record.value());
        }
    }

}
