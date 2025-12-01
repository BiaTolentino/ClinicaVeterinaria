package com.example.clinica.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class ActiveMQConfig {

    public static final String QUEUE_NOVO_PACIENTE = "fila.novo.paciente";
    public static final String TOPICO_EVENTOS_PACIENTE = "topico.eventos.paciente";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        factory.setUserName("admin");
        factory.setPassword("admin");
        return factory;
    }

    // Listener de FILA
    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(false); // QUEUE
        return factory;
    }

    // Listener de TÓPICO
    @Bean
    public DefaultJmsListenerContainerFactory topicListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true); // TOPIC
        return factory;
    }
}
