package to.kit.network.mqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Sender {
	private static final String TOPIC_NAME = "HelloWorld";

	private void send() throws JMSException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (Connection connection = cf.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			Destination destination = session.createTopic(TOPIC_NAME);
			TextMessage message = session.createTextMessage();

			connection.start();
			try (MessageProducer producer = session.createProducer(destination)){

				for (int ix = 0; ix < 5; ix++) {
					String text = "Hello にゃんこ (" + System.currentTimeMillis() + ")#" + ix;

					message.setText(text);
					producer.send(message);
				}
				message.setText("END");
				producer.send(message);
			}
		}
	}

	public static void main(String[] args) {
		Sender app = new Sender();

		try {
			app.send();
		} catch (JMSException ex) {
			System.out.println("Error = " + ex.getMessage());
		}
	}
}
