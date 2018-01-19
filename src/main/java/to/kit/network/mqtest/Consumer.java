package to.kit.network.mqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Consumer {
	private static final String TOPIC_NAME = "HelloWorld";

	private void receive() throws JMSException {
		ConnectionFactory factory = new com.sun.messaging.ConnectionFactory();

		try (Connection connection = factory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			Destination destination = session.createTopic(TOPIC_NAME);

			connection.start();
			System.out.println("Begin");
			try (MessageConsumer consumer = session.createConsumer(destination)) {
				for (;;) {
					TextMessage message = (TextMessage) consumer.receive();
					String text = message.getText();
					String lower = text.toLowerCase();

					System.out.println("\t" + text + "@" + message.getJMSTimestamp());
					if (lower.contains("end")) {
						break;
					}
				}
				System.out.println("End.");
			}
		}
	}

	public static void main(String[] args) {
		Consumer app = new Consumer();

		try {
			app.receive();
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
