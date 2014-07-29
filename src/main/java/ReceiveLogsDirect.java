import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsDirect {

	private String[] severities;

	public ReceiveLogsDirect(String... severities) {
		this.severities = severities;
	}
	
	private final static String EXCHANGE_NAME = "logs-direct";

	public void run() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String queueName = channel.queueDeclare().getQueue();
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		for (String severity : severities) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
		}

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, /*autoack*/true, consumer);
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String severity = delivery.getEnvelope().getRoutingKey();
			String message = new String(delivery.getBody());
			System.out.println("["+severity+"] " + message);        
		}
	}

}
