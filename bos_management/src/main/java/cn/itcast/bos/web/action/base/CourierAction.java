package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-19 10:11
 **/
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier courier = new Courier();

    @Override
    public Courier getModel() {
        return courier;
    }
    @Autowired
    @Qualifier("courierService")
    private CourierService courierService;

    //添加数据
    @Action(value = "courier_save",
            results = {@Result(name = "success",type = "redirect",
                        location = "./pages/base/courier.html")})
    public String save(){
        courierService.save(courier);
        System.out.println("添加快递员成功!");
        return SUCCESS;
    }

    //返回所有快递员
    private Integer page;
    private Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Action(value = "courier_pageQuery")
    public void findAll(){
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                //添加条件一:快递员工号:courierNum
                if(StringUtils.isNotBlank(courier.getCourierNum())){
                    Predicate p1 = cb.equal(
                            root.get("courierNum").as(String.class),
                            courier.getCourierNum());
                    list.add(p1);
                }
                //添加条件二:快递员单位:company
                if (StringUtils.isNotBlank(courier.getCompany())){
                    Predicate p2 = cb.like(
                            root.get("company").as(String.class),
                            "%"+courier.getCompany()+"%");
                    list.add(p2);
                }
                //添加条件三:快递员类型:type
                if(StringUtils.isNotBlank(courier.getType())){
                    Predicate p3 = cb.equal(
                            root.get("type").as(String.class),
                            courier.getType());
                    list.add(p3);
                }
                //添加条件四:快递员取派类型:standard.name
                Join<Courier, Standard> standard = root.join("standard", JoinType.INNER);
                if (courier.getStandard() != null && StringUtils.isNotBlank(courier.getStandard().getName())){
                    Predicate p4 = cb.like(
                            standard.get("name").as(String.class),
                            "%"+courier.getStandard().getName()+"%");
                    list.add(p4);
                }

                Predicate[] array = list.toArray(new Predicate[0]);
                return cb.and(array);
            }
        };

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Courier> pageData = courierService.findPage(specification,pageable);

        int total = (int) pageData.getTotalElements();
        List<Courier> rows = pageData.getContent();

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",rows);

        String jsonString = JSON.toJSONString(map);
        try {
            ServletActionContext.getResponse().getWriter().print(jsonString);
        } catch (IOException e) {
        }
    }


    //作废快递员功能
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value = "courier_deltag",results = {@Result(name = "success",type ="redirect",location = "./pages/base/courier.html")})
    public String deltag(){
        String[] arr = ids.split(",");
        courierService.del(arr);
        return SUCCESS;
    }


    //将为关联定区的快递员返回页面
    @Action(value = "courier_findNoAssociation")
    public void findNoAssociation() throws IOException {
        List<Courier>couriers = courierService.findNoAssociation();
        String jsonString = JSON.toJSONString(couriers);
        System.out.println(jsonString);
        ServletActionContext.getResponse().getWriter().print(jsonString);
    }
}
