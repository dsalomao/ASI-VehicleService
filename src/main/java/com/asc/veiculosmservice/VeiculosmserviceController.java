package com.asc.veiculosmservice;

import com.asc.veiculosmservice.config.RabbitConfiguration;
import com.asc.veiculosmservice.data.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VeiculosmserviceController {
	// variável context sendo declarada no excopo global da classe para evitar vazamento de recursos
	private static ApplicationContext context;
    private static String exchange = "VEHICLES-FEXCHANGE";
    
    @GetMapping("/veiculos")
    public String index()
    {
        return "Olá!! Bem vindo(a) ao micro serviço de veículos.";
    }

    @PostMapping(path="veiculos/salvar")
    public String save(@RequestBody Vehicle payload)
    {
        // absorvendo contexto da aplicação - injeção das configurações
		context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);

		// utilizando a bean de instanciação de um rabbitAdmin para declarar nossos recursos do Rabbit (Fila, Corretor & Ligação)
		RabbitTemplate t = (RabbitTemplate) context.getBean("rTemplate");
        ObjectMapper m = (ObjectMapper) context.getBean("mConverter");

        try {
            String json = m.writeValueAsString(payload);

            t.convertAndSend(exchange, "", json);
    
            return "Muito bem. Veículo salvo com sucesso!";
        } catch (Exception e) {
            return "Opa! Houve algo de errado na transcrição de seu veículo.";
        }
    }

    @RabbitListener(queues="VEHICLES-QUEUE")
    public Void get(Message m)
    {
        org.slf4j.Logger log = LoggerFactory.getLogger("name");
        String json = m.toString();
        log.info(json);

        return null;
    }
}
