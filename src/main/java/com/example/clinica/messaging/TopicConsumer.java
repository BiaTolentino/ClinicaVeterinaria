package com.example.clinica.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = "TesteTopico", containerFactory = "topicListenerFactory")
    public void ouvirTopico(String mensagem) {
        System.out.println("[TOPICO] Consumidor recebeu: " + mensagem);
    }
}