package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedService;
import cn.itcast.crm.domain.Customer;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 20:55
 **/
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAction extends ActionSupport implements ModelDriven<FixedArea> {

    private FixedArea fixedArea= new FixedArea();
    @Autowired
    private FixedService fixedService;
    @Override
    public FixedArea getModel() {
        return fixedArea;
    }

    //添加定区业务
    @Action(value = "fixed_save",results = {@Result(name = "success",type = "redirect",location = "./pages/base/fixed_area.html")})
    public String fixedSave(){

        fixedService.fixedAdd(fixedArea);
        return SUCCESS;
    }


    //定区条件分页查询
    private Integer page;

    private Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Action(value = "fixed_pageQuery")
    public void pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1,rows);
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(fixedArea.getId())){
                    Predicate id = cb.equal(root.get("id").as(String.class), fixedArea.getId());
                    list.add(id);
                }
                if (StringUtils.isNotBlank(fixedArea.getCompany())){
                    Predicate company = cb.equal(root.get("company").as(String.class), fixedArea.getCompany());
                    list.add(company);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<FixedArea> page = fixedService.pageQuery(specification,pageable);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total",page.getTotalElements());
        map.put("rows",page.getContent());

        String jsonString = JSON.toJSONString(map);
        ServletActionContext.getResponse().getWriter().print(jsonString);
    }

    //定区前台展示为关联客户
    @Action(value = "noCustomer")
    public void noCustomer() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        //设置地址
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8002/crm_management/services/customerService/noCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        String jsonString = JSON.toJSONString(collection);
        ServletActionContext.getResponse().getWriter().print(jsonString);
    }
    //定区前台展示以关联客户

    @Action(value = "hasCustomer")
    public void hasCustomer() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        //设置地址
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8002/crm_management/services/customerService/hasCustomer/"+fixedArea.getId())
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        System.out.println(fixedArea.getId());

        String jsonString = JSON.toJSONString(collection);
        ServletActionContext.getResponse().getWriter().print(jsonString);
    }

    // 关联客户 (把已经关联客户 定区id 设置为null)
    // 接收客户id数组(StringUtins.spilt转为字符串)和定区id  调用方法  设置客户表的定区id

    @Setter
    private String[] customerIds;

    @Action(value = "save_customer",results = {@Result(name = "success",location = "./pages/base/fixed_area.html",type = "redirect")})
    public String saveCustomer(){
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        String ids = StringUtils.join(customerIds,",");
        System.out.println(ids);
        //设置地址
        WebClient.create("http://localhost:8002/crm_management/services/customerService/putCustomer?customerIds="
                + ids +"&fixedAreaId=" + fixedArea.getId()).type(MediaType.APPLICATION_JSON).put(null);

        return SUCCESS;
    }
    //关联快递员
    //封装时间id 和 快递员id
    @Setter
    private Integer takeTimeId;
    @Setter
    private Integer courierId;
    @Action(value = "fixedArea_associationCourierToFixedArea",
            results = {@Result(name = "success",location = "./pages/base/fixed_area.html",type = "redirect")})
    public String saveCourier(){
        fixedService.fixedArea_associationCourierToFixedArea(takeTimeId,courierId,fixedArea);

        return SUCCESS;
    }
}
