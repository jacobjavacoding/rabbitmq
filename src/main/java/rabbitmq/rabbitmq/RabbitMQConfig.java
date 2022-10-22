package rabbitmq.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.queue.name}")
	private String queueName;

	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;

	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	@PostConstruct
	public void createQueues() {
		TopicExchange exchange = new TopicExchange(exchangeName);
		Queue queue1 = new Queue(queueName);
		amqpAdmin.declareExchange(exchange);
		amqpAdmin.declareQueue(queue1);
		Binding binding = BindingBuilder.bind(queue1).to(exchange).with(routingKey);
		amqpAdmin.declareBinding(binding);

	}

}
