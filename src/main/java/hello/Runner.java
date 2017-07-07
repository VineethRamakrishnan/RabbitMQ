package hello;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;
	private final ConfigurableApplicationContext context;
	
	public Runner (Receiver receiver,RabbitTemplate rabbitTemplate,ConfigurableApplicationContext context){
		
		this.rabbitTemplate=rabbitTemplate;
		this.receiver=receiver;
		this.context=context;
	}
	
	@Override
	public void run(String... args)throws Exception {
		
		System.out.println("Sending Message...");
		rabbitTemplate.convertAndSend(Application.queueName,"This is the Messge from the RabbitMQ!!!");
		//breceiver.getLatch().await(10000,TimeUnit.MILLISECONDS);
		context.close();
	}
}
