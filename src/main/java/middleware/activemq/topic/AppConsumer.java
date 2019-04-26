package middleware.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 提供者
public class AppConsumer {
    // MQ的默认端口
    private static final String url = "tcp://127.0.0.1:61616";
    // 队列名称
    private static final String topicName = "topic-test";

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1. 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // 2. 创建Connection
        Connection connection = connectionFactory.createConnection();

        // 3. 启动连接
        connection.start();

        // 4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5. 创建一个目标
        Destination destination = session.createTopic(topicName);

        // 6. 创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        // 7. 设置一个监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;    // 向下转型
                try {
                    System.out.println("接收消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 记得接收是异步的
        // 8. 记得关闭连接
//        Thread.sleep(20);
//        connection.close();
    }
}
