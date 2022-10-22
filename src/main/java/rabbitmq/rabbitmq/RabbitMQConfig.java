package rabbitmq.rabbitmq;




import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
	
	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.queue.name}")
	private String queue;

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	// spring bean for rabbitmq queue
	@Bean
	public Queue queue() {
		return new Queue(queue);
	}

	// spring bean for rabbitmq exchange
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	// binding between queue and exchange using routing key
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
	}
	
	@PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue(queue, true));
        amqpAdmin.declareExchange(new TopicExchange(exchange) );
        amqpAdmin.declareBinding(BindingBuilder.bind(new Queue(queue, true)).to(new TopicExchange(exchange)).with(routingKey));
        
    }

// Spring boot autoconfiguration provides following beans
	// ConnectionFactory
	// RabbitTemplate
	// RabbitAdmin
	
	
}
