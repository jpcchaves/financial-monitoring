package com.financialmonitoring.fraudservice.config.kafka;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    private static final Integer PARTITION_COUNT = 1;
    private static final Integer REPLICATION_FACTOR = 1;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    @Value("${spring.kafka.topic.balance-check-success}")
    private String balanceCheckSuccessTopic;

    @Value("${spring.kafka.topic.balance-check-fail}")
    private String balanceCheckFailTopic;

    private final ApplicationContext applicationContext;

    public KafkaConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public Set<NewTopic> buildTopics() {
        BeanDefinitionRegistry registry =
                (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        List<String> topics = List.of(
                orchestratorTopic,
                balanceCheckSuccessTopic,
                balanceCheckFailTopic);

        return topics.stream()
                .map(topic -> createAndRegisterTopic(topic, registry))
                .collect(Collectors.toSet());
    }

    private Map<String, Object> consumerProps() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
        );
    }

    private Map<String, Object> producerProps() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );
    }

    private NewTopic createAndRegisterTopic(String topicName, BeanDefinitionRegistry registry) {
        logger.info("Creating topic: {}", topicName);

        BeanDefinitionBuilder builder =
                BeanDefinitionBuilder.genericBeanDefinition(NewTopic.class)
                        .addConstructorArgValue(topicName)
                        .addConstructorArgValue(PARTITION_COUNT)
                        .addConstructorArgValue(REPLICATION_FACTOR);

        registry.registerBeanDefinition(topicName, builder.getBeanDefinition());

        return TopicBuilder.name(topicName).partitions(PARTITION_COUNT).replicas(REPLICATION_FACTOR).build();
    }
}
