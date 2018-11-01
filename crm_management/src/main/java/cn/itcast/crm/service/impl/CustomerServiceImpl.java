package cn.itcast.crm.service.impl;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-24 9:20
 **/
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    //注入CustomerRepository
    @Autowired
    private CustomerRepository customerRepository;
    //查询所有未关联的客户
    @Override
    public List<Customer> findAllNoCustomer() {
        return customerRepository.findByFixedAreaIdIsNull();
    }
    //查询所有已关联客户
    @Override
    public List<Customer> findAllHasCustomer(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }
    //将客户关联到定区
    @Override
    public void putCustomer(String customerIds, String fixedAreaId) {
        //设置定区id为fixedAreaId所有客户的fixedAreaId为null
        customerRepository.clearFixedArea(fixedAreaId);
        //判断是否为空
        if (StringUtils.isBlank(customerIds)){
            return;
        }
        String[] ids = customerIds.split(",");
        for (String idstr : ids) {
            Integer id = Integer.parseInt(idstr);
            customerRepository.updateCustomer(fixedAreaId,id);
        }
    }

    //保存注册的客户
    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    //根据电话查询客户
    @Override
    public Customer findCustomer(String telephone) {
       return customerRepository.findByTelephone(telephone);
    }

    //根据电话修改客户状态
    @Override
    public void updataCustomer(String telephone) {
        customerRepository.updataCustomer(telephone);
    }

    //客户登录功能
    @Override
    public Customer login(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }

    //根据地址获取定区id
    @Override
    public String findAreaByAddress(String address) {
        return customerRepository.findAreaByAddress(address);
    }
}
