package cn.ybzy.qihangkt;


import javax.activation.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class QihangTest {
	
	
	@SuppressWarnings("resource")
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		System.out.println(dataSource.getClass());
	}
	
	

}
