package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository; 
	

	@Test
	public void testQuery(){
        List<Standard> list = standardRepository.findByName("20-30");
        System.out.println(list);
    }

    @Test
    public void testQuery2(){
        List<Standard> list = standardRepository.queryName("20-30");
        System.out.println(list);
    }

	@Test
	@Transactional
	public void findById(){
        Standard one = standardRepository.getOne(1);
        System.out.println(one);
    }
}
