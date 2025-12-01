package com.example.clinica.messaging;

import com.example.clinica.config.ActiveMQConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PetFilaListener {

    @JmsListener(destination = ActiveMQConfig.QUEUE_NOVO_PACIENTE, containerFactory = "queueListenerFactory")
    public void receberMensagemFila(String mensagem) {
        System.out.println("[FILA] Consumidor 1 recebeu: " + mensagem);
    }
}
