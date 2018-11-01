package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;
import com.opensymphony.xwork2.ActionContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-29 21:17
 **/
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion>{

    //分页功能,调用bos-management获取PageBean  /js/self/page.js
    @Action(value = "promotion_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        PageBean<Promotion> pageBean = WebClient.create("http://localhost:8080/bos_management/services/promotionService/pageQuery?page=" + page + "&rows=" + rows)
                .accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        ActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }

    //静态显示活动详情页面
    @Action(value = "promotion_showDetail")
    public String showDetail() throws Exception {
        //判断id 对应的html是否存在,如果存在直接返回
        String realPath = ServletActionContext.getServletContext().getRealPath("/freemarker");
        File htmlFile = new File(realPath +"/" + model.getId() +".html");
        //不存在,查询数据库,使用freemarker生成模板后返回
        if( !htmlFile.exists()){
            //配置对象
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_20);
            //配置模板位置
            configuration.setDirectoryForTemplateLoading(new File(ServletActionContext.getServletContext()
                    .getRealPath("/WEB-INF/freemarker_templates")));
            //获取模板对象
            Template template = configuration.getTemplate("promotion_detail.ftl");
            //获取数据
            Promotion promotion = WebClient.create("http://localhost:8080/bos_management/services/promotionService/showDetail/"+model.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .get(Promotion.class);
            //将数据存入map集合
            Map<String,Object>map = new HashMap<String,Object>();
            map.put("promotion",promotion);
            //输出到freemarker_template
            template.process(map,new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
        }
         //存在直接返回数据
        //设置返回字体为utf-8
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        //返回数据
        FileUtils.copyFile(htmlFile,ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }
}
