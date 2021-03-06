package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) throws InterruptedException {
       SpringApplication.run(Application.class, args);
    }
    
    final static String queueName = "spring-boot-rabbit";
    
    @Bean
    Queue queue() {
    	return new Queue(queueName,false);
    }
    
    @Bean 
    TopicExchange exchange(){
    	return new TopicExchange("spring-boot-rabbit-exchange");
    }
    
    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
    	return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory , MessageListenerAdapter listnerAdapter) {

    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    	container.setConnectionFactory(connectionFactory);
    	container.setQueueNames(queueName);
    	container.setMessageListener(listnerAdapter);
    	return container;
	}
    
    @Bean
    MessageListenerAdapter listnerAdapter (Receiver receiver) {
    	return new MessageListenerAdapter(receiver , "receiveMessage");
    }
}
