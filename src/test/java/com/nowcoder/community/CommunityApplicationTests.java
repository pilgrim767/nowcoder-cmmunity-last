package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
//测试代码中，通过以下注解也启用CommunityApplication这个类作为配置类
@ContextConfiguration(classes = CommunityApplication.class)
//Spring最重要的就是容器，通过实现接口ApplicationContextAware，该类得到spring容器
public class CommunityApplicationTests implements ApplicationContextAware {

	//加一个成员变量，用来记录spring容器
	private ApplicationContext applicationContext;


	//set方法传入一个参数，这个参数实际上就是spring容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//测试spring容器
	@Test
	public void testApplicationContext() {
		System.out.println(applicationContext);
		//生成结果：org.springframework.web.context.support.GenericWebApplicationContext@3336e6b6，，，，
		//其中的GenericWebApplicationContext@3336e6b6是对象的名字

		//从容器中获取自动装配的bean【从容器中获取AlphaDao的bean】
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());//不知道使用那个实现类，则按照优先级

		//指定bean的名字
		alphaDao = applicationContext.getBean("alphaHibernate",AlphaDao.class);
		System.out.println(alphaDao.select());
	}


	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);//打印对象

		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
		//com.nowcoder.community.service.AlphaService@55f4887d
		//com.nowcoder.community.service.AlphaService@55f4887d【hashcode一样，证明是同一个对象】
		//证明被spring容器管理的bean是单个实例
		//如果想要新建实例，需要在bean上加一个注解
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}
//------------------------------------------------------------------------------------
	//通常选择下面的方法，注解给属性，更简洁更方便
	@Autowired //希望spring容器把AlphaDa注入这个属性【把当前bean注入AlphaDao】
	//不想选默认的优先实现类，就用下面的方法
	@Qualifier("alphaHibernate")
	private AlphaDao alphaDao;

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI() {
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);

	}
}
