import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import producer.ActiveProducerQueues;
import producer.ActiveProducerTopics;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-30 14:53
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-consumer.xml")
public class ConsumerTest {
    @Test
    public void onMessage(){
       while (true){

       }
    }
}
