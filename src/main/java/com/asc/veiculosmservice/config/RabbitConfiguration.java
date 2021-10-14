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

    /**
     * Secção de construção das filas de mensagens
     * @return
     */

    // Bean de instanciação de uma classe de fila
    @Bean(name="buildVeiQueue")
    public Queue buildVeiQueue() 
    {
        return new Queue("VEHICLES-QUEUE", true);
    }

    // Bean de instanciação de uma classe de fila
    @Bean(name="buildRQueue")
    public Queue buildRQueue() 
    {
        return new Queue("REPAROS-QUEUE", true);
    }

    // Bean de instanciação de uma classe de fila
    @Bean(name="buildCQueue")
    public Queue buildCQueue() 
    {
        return new Queue("COMPRAS-QUEUE", true);
    }

    // Bean de instanciação de uma classe de fila
    @Bean(name="buildVQueue")
    public Queue buildVQueue() 
    {
        return new Queue("VENDAS-QUEUE", true);
    }

    /**
     * Criando os corretores de mensagens
     * @return
     */

    /// Bean de instanciação de uma classe de corretor de mensagens do tipo FANOUT
    @Bean(name="buildFExchange")
    public Exchange fanoutExchange()
    {
        return ExchangeBuilder
        .fanoutExchange("VEHICLES-FEXCHANGE")
        .durable(true)
        .build();
    }
    
    // Bean de instanciação de uma classe de corretor de mensagens do tipo FANOUT
    @Bean(name="buildDExchange")
    public Exchange directExchange()
    {
        return ExchangeBuilder
        .directExchange("CHANGE-VEHICLE-DEXCHANGE")
        .durable(true)
        .build();
    }

    /**
     * Construindo as ligações entre as filas e corretores
     * @return
     */ 
    
    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildFRBinding")
    public Binding fanoutRBinding()
    {
        return BindingBuilder
            .bind(buildRQueue())
            .to(fanoutExchange())
            .with("")
            .noargs();
    } 

    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildFCBinding")
    public Binding fanoutCBinding()
    {
        return BindingBuilder
            .bind(buildCQueue())
            .to(fanoutExchange())
            .with("")
            .noargs();
    } 

    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildFVBinding")
    public Binding fanoutVBinding()
    {
        return BindingBuilder
            .bind(buildVQueue())
            .to(fanoutExchange())
            .with("")
            .noargs();
    } 

    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildDRBinding")
    public Binding directRBinding()
    {
        return BindingBuilder
            .bind(buildVeiQueue())
            .to(directExchange())
            .with("CHANGE-VEHICLE-BINDING")
            .noargs();
    }   
    
    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildDCBinding")
    public Binding directCBinding()
    {
        return BindingBuilder
            .bind(buildVeiQueue())
            .to(directExchange())
            .with("CHANGE-VEHICLE-BINDING")
            .noargs();
    } 

    // Bean de instanciação de uma classe de ligação entre o corretor e a fila
    @Bean(name="buildDVBinding")
    public Binding directVBinding()
    {
        return BindingBuilder
            .bind(buildVeiQueue())
            .to(directExchange())
            .with("CHANGE-VEHICLE-BINDING")
            .noargs();
    } 

    // Bean de exposição de ceonversão de classes POJO para JSON string
    @Bean(name="mConverter")
    public ObjectMapper messageConverter()
    {
        return new ObjectMapper();
    }
}
