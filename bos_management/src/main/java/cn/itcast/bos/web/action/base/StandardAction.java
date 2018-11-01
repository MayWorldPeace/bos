package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
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
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-17 16:49
 **/
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    private Standard standard = new Standard();
    @Override
    public Standard getModel() {
        return standard;
    }
    @Autowired
    @Qualifier("standardService")
    private StandardService standardService;

    //添加数据
    @Action(value = "standard_save",
            results = {@Result(name = "success",
                    type = "redirect",
                    location = "./pages/base/standard.html")})
    public String add(){
        standardService.save(standard);
        System.out.println("添加收派标准成功");
        return "success";
    }


    //删除数据
    @Action(value = "standard_delete")
    public void delete(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        int id = (int) request.getAttribute("id");
        standardService.delete(id);
        System.out.println("删除收派标准成功");
        try {
            response.getWriter().print("删除成功!! !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查询分页
    //封装数据
    private Integer page;
    private Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Action(value = "standard_pageQuery")
    public void pageQuery(){
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        //创建pageable
        Pageable pageable = new PageRequest(page-1,rows);
        //调用service获取封装好的total和rows
        Page<Standard> pageDate = standardService.findPage(pageable);
        //获取total 总记录数
        long total = pageDate.getTotalElements();
        //获取rows 表单数据
        List<Standard> standards = pageDate.getContent();
        //存入map集合
        Map<String,Object> result = new HashMap();
        result.put("total",total);
        result.put("rows",standards);
        //转换为json
        String jsonString = JSON.toJSONString(result);
        //返回到前台
        try {
            ServletActionContext.getResponse().getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //管理取派员返回所有取派标准
    @Action(value = "standard_findAll",
                results = {@Result(name = "success",type = "redirect",location = "./pages/base/courier.html")})
    public void findAll(){
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        List<Standard>standards = standardService.findAll();
        String jsonString = JSON.toJSONString(standards);
        try {
            System.out.println(jsonString);
            ServletActionContext.getResponse().getWriter().print(jsonString);
        } catch (IOException e) {
        }
    }
}

