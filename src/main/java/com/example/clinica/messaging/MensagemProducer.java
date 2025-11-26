package com.example.clinica.messaging;

import com.example.clinica.config.ActiveMQConfig;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MensagemProducer {

    private final JmsTemplate jmsTemplate;

    public MensagemProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // Envia mensagem para fila
    public void enviarFilaNovoPaciente(String mensagem) {
        jmsTemplate.convertAndSend(ActiveMQConfig.QUEUE_NOVO_PACIENTE, mensagem);
    }

    // Envia mensagem para tópico
    public void enviarTopicoEventoPaciente(String mensagem) {
        jmsTemplate.convertAndSend(ActiveMQConfig.TOPICO_EVENTOS_PACIENTE, mensagem);
    }
}
