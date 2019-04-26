package middleware.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 提供者
public class AppProvider {
    // MQ的默认端口
    private static final String url = "tcp://127.0.0.1:61616";
    private static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        // 1. 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // 2. 创建Connection
        Connection connection = connectionFactory.createConnection();

        // 3. 启动连接
        connection.start();

        // 4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5. 创建一个目标，即队列
        Destination destination = session.createQueue(queueName);

        // 6. 创建专门向消息队列发信息的producer
        MessageProducer producer = session.createProducer(destination);

        for (int i=0; i<100; i++) {
            // 7. 创建消息
            TextMessage textMessage = session.createTextMessage("test " + i);
            producer.send(textMessage);

            System.out.println("发送消息" + textMessage.getText());
        }

        // 记得关闭连接
        connection.close();
    }
}
