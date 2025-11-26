package com.example.clinica.messaging;

import com.example.clinica.config.ActiveMQConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MensagemConsumer {

    @JmsListener(destination = ActiveMQConfig.TOPICO_EVENTOS_PACIENTE, containerFactory = "topicListenerFactory")
    public void receberTopico(String mensagem) {
        System.out.println("[Tópico] Recebido: " + mensagem);
    }
}
