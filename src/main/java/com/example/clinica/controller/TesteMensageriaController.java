package com.example.clinica.controller;

import com.example.clinica.messaging.MensagemProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteMensageriaController {

    private final MensagemProducer producer;

    public TesteMensageriaController(MensagemProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/enviar-fila")
    public String enviarFila(@RequestParam String msg) {
        producer.enviarFilaNovoPaciente(msg);
        return "Mensagem enviada para fila: " + msg;
    }

    @GetMapping("/enviar-topico")
    public String enviarTopico(@RequestParam String msg) {
        producer.enviarTopicoEventoPaciente(msg);
        return "Mensagem enviada para tópico: " + msg;
    }
}
