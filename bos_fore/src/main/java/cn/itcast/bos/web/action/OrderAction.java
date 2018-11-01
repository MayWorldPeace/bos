package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.crm.domain.Customer;
import lombok.Data;
import lombok.Setter;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.ws.rs.core.MediaType;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-03 18:29
 **/
@SuppressWarnings("all")
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
@Data
public class OrderAction extends BaseAction<Order>{

    private String sendAreaInfo;    //寄件人的省市区 XX省/XX市/XX区
    private String recAreaInfo;     //收件人的省市区
    @Action(value = "order_save",results = {@Result(name = "success",location = "index.html",type = "redirect")})
    public String orderSave(){

        //封装寄件人省市区信息
        Area sendArea = new Area();
        String[] sendAreas = sendAreaInfo.split("/");
        sendArea.setProvince(sendAreas[0]);
        sendArea.setCity(sendAreas[1]);
        sendArea.setDistrict(sendAreas[2]);

        //封装收件人省市区信息
        Area recArea = new Area();
        String[] recAreas = sendAreaInfo.split("/");
        sendArea.setProvince(recAreas[0]);
        sendArea.setCity(recAreas[1]);
        sendArea.setDistrict(recAreas[2]);
        //关联当前客户
        Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");

        model.setSendArea(sendArea);
        model.setRecArea(recArea);
        model.setCustomer_id(customer.getId());
        //调用webService传递数据到bos_management
        WebClient.create("http://localhost:8080/bos_management/services/orderService/saveOrder")
                .type(MediaType.APPLICATION_JSON).post(model);
        return SUCCESS;
    }
}
