package com.example.clinica.messaging;

import com.example.clinica.config.ActiveMQConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PetTopicoListener {

    @JmsListener(destination = ActiveMQConfig.TOPICO_EVENTOS_PACIENTE, containerFactory = "topicListenerFactory")
    public void receberMensagem(String mensagem) {
        System.out.println("[TÓPICO] Listener A recebeu: " + mensagem);
    }
}
