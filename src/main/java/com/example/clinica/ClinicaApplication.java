package com.example.clinica;

import com.example.clinica.config.ActiveMQConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class ClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
    }

    // Envia mensagem de teste para o tópico após a aplicação iniciar
    @Bean
    public CommandLineRunner runner(JmsTemplate jmsTemplate) {
        return args -> {
            Thread.sleep(2000); // espera 2s para o listener iniciar
            System.out.println("Enviando mensagem para o tópico...");
            jmsTemplate.convertAndSend(ActiveMQConfig.TOPICO_EVENTOS_PACIENTE, "Mensagem de teste do tópico!");
        };
    }
}
