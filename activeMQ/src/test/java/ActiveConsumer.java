import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-30 14:14
 **/
public class ActiveConsumer {

    @Test
    // 直接消费
    public void testCosumeMQ() throws Exception {
        // 连接工厂
        // 使用默认用户名、密码、路径
        // 路径 tcp://host:61616
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        // 获取一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 建立会话
        // 第一个参数，是否使用事务，如果设置true，操作消息队列后，必须使用 session.commit();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列或者话题对象
        Queue queue = session.createQueue("HelloWorld");
        // 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        while (true) {
            TextMessage message = (TextMessage) messageConsumer.receive(5000);
            if (message != null) {
                System.out.println(message.getText());
            } else {
                break;
            }
        }
    }


    @Test
    // 使用监听器消费
    public void testCosumeMQ2() throws Exception {
        // 连接工厂
        // 使用默认用户名、密码、路径
        // 路径 tcp://host:61616
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        // 获取一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 建立会话
        // 第一个参数，是否使用事务，如果设置true，操作消息队列后，必须使用 session.commit();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        // 创建队列或者话题对象
        Queue queue = session.createQueue("HelloWorld");
        // 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        while (true) {
            // 不能让junit线程死掉
        }
    }
}
