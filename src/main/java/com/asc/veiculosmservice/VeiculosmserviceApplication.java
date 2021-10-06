package com.asc.veiculosmservice;

import com.asc.veiculosmservice.config.RabbitConfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class VeiculosmserviceApplication {
	// variável context sendo declarada no excopo global da classe para evitar vazamento de recursos
	private static ApplicationContext context;

	// classe principal, utiliza a classe de configuração do Rabbit para criar a estrutura necessária para o funcionamento do micro serviço
	public static void main(String[] args) 
	{
		// absorvendo contexto da aplicação - injeção das configurações
		context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
		
		// utilizando a bean de instanciação de um rabbitAdmin para declarar nossos recursos do Rabbit (Fila, Corretor & Ligação)
		RabbitAdmin admin = (RabbitAdmin) context.getBean("rAdmin");
		
		// criação dos recursos
		Queue q = (Queue) context.getBean("buildQueue");
		Exchange e = (Exchange) context.getBean("buildFExchange");
		Binding b = (Binding) context.getBean("buildFBinding");

		// declaração dos recursos no servidor do Rabbit
		admin.declareQueue(q);
		admin.declareExchange(e);
		admin.declareBinding(b);
		
		SpringApplication.run(VeiculosmserviceApplication.class, args);
	}
}
