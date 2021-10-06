package com.asc.veiculosmservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// classe de configuração do ambiente do Rabbit para troca de mensagens entre microserviços
@Configuration
public class RabbitConfiguration {

    // Bean de instanciação de uma ConnectionFactory ao servidor Rabbit
    @Bean
    public CachingConnectionFactory connectionFactory()
    {
        return new CachingConnectionFactory("localhost");
    }

    // Bean de instanciação da classe de operações administrativas
    @Bean(name="rAdmin")
    public RabbitAdmin amqpAdmin()
    {
        return new RabbitAdmin(connectionFactory());
    }
    
    // Bean de instanciação da classe de envio e recebimento de mensagens
    @Bean(name="rTemplate")
    public RabbitTemplate rabbitTemplate()
    {
        return new RabbitTemplate(connectionFactory());
    }

    // Bean de instanciação de uma classe de fila
    @Bean(name="buildQueue")
    public Queue buildQueue() 
    {
        return new Queue("VEHICLES-QUEUE", true);
    }

    /// Bean de instanciação de uma classe de corretor de mensagens do tipo FANOUT
    @Bean(name="buildFExchange")
    public Exchange fanoutExchange()
    {
        return ExchangeBuilder
        .fanoutExchange("VEHICLES-FEXCHANGE")
        .durable(true)
        .build();
    }

    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildFBinding")
    public Binding fanoutBinding()
    {
        return BindingBuilder
            .bind(buildQueue())
            .to(fanoutExchange())
            .with("")
            .noargs();
    }    

    @Bean(name="mConverter")
    public ObjectMapper messageConverter()
    {
        return new ObjectMapper();
    }
}
