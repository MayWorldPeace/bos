
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-30 14:01
 **/
public class ActiveProducer {

    @Test
    public void testProduct() throws JMSException {
        //创建工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session  true表示使用事务
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建队列或者话题对象
        Queue queue = session.createQueue("HelloWorld");
        // 创建生产者 或者 消费者
        MessageProducer producer = session.createProducer(queue);

        // 发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(session.createTextMessage("你好，贱人窑!:" + i));
        }
        // 提交操作
        session.commit();

    }
}
