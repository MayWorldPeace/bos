import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import producer.ActiveProducerQueues;
import producer.ActiveProducerTopics;
import javax.jms.Message;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-30 14:53
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mq.xml")
public class ProducerTest {

    @Autowired
    private ActiveProducerQueues activeProducerQueues;
    @Autowired
    private ActiveProducerTopics activeProducerTopics;

    @Test
    public void send(){
        activeProducerQueues.send("queue","hello,贱人");
        activeProducerTopics.send("topics","hello鸡儿");
    }
}
