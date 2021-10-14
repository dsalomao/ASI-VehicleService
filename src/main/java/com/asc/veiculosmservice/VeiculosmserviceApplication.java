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
		Queue veiq = (Queue) context.getBean("buildVeiQueue");
		Queue rq = (Queue) context.getBean("buildRQueue");
		Queue cq = (Queue) context.getBean("buildCQueue");
		Queue vq = (Queue) context.getBean("buildVQueue");

		Exchange fe = (Exchange) context.getBean("buildFExchange");
		Exchange de = (Exchange) context.getBean("buildDExchange");

		Binding rb = (Binding) context.getBean("buildFRBinding");
		Binding cb = (Binding) context.getBean("buildFCBinding");
		Binding vb = (Binding) context.getBean("buildFVBinding");
		
		Binding db = (Binding) context.getBean("buildDRBinding");
		Binding drb = (Binding) context.getBean("buildDCBinding");
		Binding dcb = (Binding) context.getBean("buildDVBinding");

		// declaração dos recursos no servidor do Rabbit
		admin.declareQueue(veiq);
		admin.declareQueue(rq);
		admin.declareQueue(cq);
		admin.declareQueue(vq);

		admin.declareExchange(fe);
		admin.declareExchange(de);

		admin.declareBinding(rb);
		admin.declareBinding(cb);
		admin.declareBinding(vb);

		admin.declareBinding(db);
		admin.declareBinding(drb);
		admin.declareBinding(dcb);
		
		SpringApplication.run(VeiculosmserviceApplication.class, args);
	}
}
