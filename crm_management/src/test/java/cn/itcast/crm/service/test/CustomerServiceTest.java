package cn.itcast.crm.service.test;

import cn.itcast.crm.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Test
	public void testFindNoAssociationCustomers() {
		System.out.println(customerService.findAllNoCustomer());
	}

	@Test
	public void testFindHasAssociationFixedAreaCustomers() {
		System.out.println(customerService
				.findAllHasCustomer("dq001"));
	}

	@Test
	public void testAssociationCustomersToFixedArea() {
		customerService.putCustomer("1,2", "dq001");
	}

    @Test
    public void testAssociationCustomesToFixedArea() {
        System.out.println(customerService.findCustomer("13599999999"));
    }
}
