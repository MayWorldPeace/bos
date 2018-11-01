package cn.itcast.crm.dao;

import cn.itcast.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-24 10:07
 **/
public interface CustomerRepository extends JpaRepository<Customer,Integer>,JpaSpecificationExecutor<Customer> {
    public List<Customer> findByFixedAreaIdIsNull();

    public List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    public void updateCustomer(String fixedAreaId, Integer id);

    @Query("update Customer  set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    public void clearFixedArea(String fixedAreaId);

    //根据电话查询客户
    Customer findByTelephone(String telephone);
    //根据电话更改客户
    @Query("update Customer  set type = 1 where telephone = ?")
    @Modifying
    void updataCustomer(String telephone);

    //客户登录功能
    Customer findByTelephoneAndPassword(String telephone, String password);

    //查询定区id
    @Query("select fixedAreaId from Customer where address = ?")
    String findAreaByAddress(String address);
}
