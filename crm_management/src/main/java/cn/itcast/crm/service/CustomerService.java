package cn.itcast.crm.service;

import cn.itcast.crm.domain.Customer;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-24 9:19
 **/
public interface CustomerService {

    //查询所有为关联客户
    @Path("/noCustomer")
    @GET
    @Produces({"application/json"})
    public List<Customer> findAllNoCustomer();
    //查询以关联客户
    @Path("/hasCustomer/{fixedAreaId}")
    @GET
    @Produces({"application/json"})
    public List<Customer> findAllHasCustomer(@PathParam("fixedAreaId") String fixedAreaId);
    //关联客户到指定 定区
    @Path("/putCustomer")
    @PUT
    @Produces({"application/json"})
    public void putCustomer(
            @QueryParam("customerIds") String customerIds,
            @QueryParam("fixedAreaId") String fixedAreaId);

    //注册功能保存客户
    @Path("/saveCustomer")
    @POST
    @Consumes({"application/json"})
    public void saveCustomer(Customer customer);

    //查询客户信息,判断判断是否绑定
    @Path("/findCustomer/{telephone}")
    @GET
    @Produces({"application/json"})
    public Customer findCustomer(@PathParam("telephone") String telephone);

    //进行绑定操作
    @Path("/updataCustomer/{telephone}")
    @GET
    public void updataCustomer(@PathParam("telephone") String telephone);

    //客户登录功能,根据电话 密码查询客户是否存在
    @Path("/login")
    @GET
    @Produces({"application/json"})
    public Customer login(@QueryParam("telephone") String telephone,
                          @QueryParam("password") String password);


    //根据地址获取定区id
    @Path("/findAreaByAddress")
    @GET
    @Consumes({"application/json"})
    public String findAreaByAddress(@QueryParam("address") String address);

}
