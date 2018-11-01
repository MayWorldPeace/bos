package cn.itcast.bos.web.action;

import cn.itcast.bos.utils.MailUtils;
import cn.itcast.crm.domain.Customer;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.Servlet;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

	//注入jmstemplate
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	@Action(value = "customer_sendSms")
	public String sendSms() throws IOException {
		// 手机号保存在Customer对象
		// 生成短信验证码
		String randomCode = RandomStringUtils.randomNumeric(6);
		// 将短信验证码 保存到session
		ServletActionContext.getRequest().getSession()
				.setAttribute(model.getTelephone(), randomCode);

		System.out.println("生成手机验证码为：" + randomCode);
		// 编辑短信内容
		final String msg = "尊敬的用户您好，本次获取的验证码为：" + randomCode
				+ ",服务电话：帅的被人砍";
		//调用activeMQ发送消息
		jmsTemplate.send("bos-sms", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建map集合存入 电话 和 消息内容
				MapMessage mapMessage = session.createMapMessage();

				mapMessage.setString("telephone",model.getTelephone());
				mapMessage.setString("msg",msg);
				//返回mapMessage
				return mapMessage;
			}
		});
		return NONE;
		//调用sms发送短信
		/*String result = SmsUtils.sendSmsByHTTP(model.getTelephone(),msg);
		String result = "000/***";
		if (result.startsWith("000")){
			return NONE;
		}else {
			throw new RuntimeException("发送失败"+ result);
		}*/
	}
	//校验验证码

	//属性驱动获得验证码
	@Setter
	private String checkcode;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Action(value = "customer_regist",results = {@Result(name = "success",location = "/signup-success.html"),
												 @Result(name = "input",location = "/signup.html")})
	public String registCustomer(){
		String checkcodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if (checkcode.equals(checkcodeSession)){
			System.out.println("验证码输入成功!");
			WebClient.create("http://localhost:8002/crm_management/services/customerService/saveCustomer").
					type(MediaType.APPLICATION_JSON).post(model);

			// 发送一封激活邮件
			// 生成激活码
			String activecode = RandomStringUtils.randomNumeric(32);

			//保存到redis
			redisTemplate.opsForValue().set(model.getTelephone(), activecode, 24,
					TimeUnit.HOURS);

			// 调用MailUtils发送激活邮件
			String content = "尊敬的客户您好，请于24小时内，进行邮箱账户的绑定，点击下面地址完成绑定:<br/><a href='"
					+ MailUtils.activeUrl + "?telephone=" + model.getTelephone()
					+ "&activecode=" + activecode + "'>速运快递邮箱绑定地址</a>";
			MailUtils.sendMail("激活邮件", content, model.getEmail());
			return SUCCESS;
		}else{
			System.out.println("验证码输入错误");
			return INPUT;
		}
	}

	//绑定邮箱功能

	@Setter
	private String activecode;
	@Action("customer_activeMail")
	public String actionMail() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		// 判断激活码是否有效
		String activecodeRedis = redisTemplate.opsForValue().get(model.getTelephone());
		if (activecodeRedis == null || !activecodeRedis.equals(activecodeRedis)) {
			// 激活码无效
			ServletActionContext.getResponse().getWriter().println("激活码无效，请登录系统，重新绑定邮箱！");
		}else {
			// 激活码有效
			// 防止重复绑定
			// 调用CRM webService 查询客户信息，判断是否已经绑定
			Customer customer = WebClient
					.create("http://localhost:8002/crm_management/services/customerService/findCustomer/"
							+ model.getTelephone())
					.accept(MediaType.APPLICATION_JSON).get(Customer.class);
			if (customer.getType() == null || customer.getType() != 1) {
				// 没有绑定,进行绑定
				WebClient.create(
						"http://localhost:8002/crm_management/services/customerService/updataCustomer/"
								+ model.getTelephone()).get();
				ServletActionContext.getResponse().getWriter().println("邮箱绑定成功！");
			} else {
				// 已经绑定过
				ServletActionContext.getResponse().getWriter().println("邮箱已经绑定过，无需重复绑定！");
			}

			// 删除redis的激活码
			redisTemplate.delete(model.getTelephone());
		}
		return NONE;
	}

	//用户登录功能
	@Action(value = "customer_login",results = {
			@Result(name = "success",location = "index.html#/myhome",type = "redirect")
			,@Result(name = "login",location = "login.html",type = "redirect")})
	public String login() throws IOException {
        Customer customer = WebClient
                .create("http://localhost:8002/crm_management/services/customerService/login?telephone=" +
                         model.getTelephone()+"&password="+model.getPassword())
                .accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer == null){
            ServletActionContext.getResponse().getWriter().print("helloworld");
            return "login";
        }else {
            ServletActionContext.getRequest().getSession().setAttribute("customer",customer);
            return SUCCESS;
        }
	}
}
