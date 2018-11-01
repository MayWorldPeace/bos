package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-25 21:14
 **/

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends ActionSupport implements ModelDriven<TakeTime> {

    private TakeTime takeTime = new TakeTime();
    @Override
    public TakeTime getModel() {
        return takeTime;
    }

    @Autowired
    @Setter
    private TakeTimeService takeTimeService;

    @Action(value = "time_findAll")
    public void findAll() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        List<TakeTime> times = takeTimeService.findAll();
        String jsonString = JSON.toJSONString(times);
        System.out.println(jsonString);
        ServletActionContext.getResponse().getWriter().print(jsonString);
    }
}
