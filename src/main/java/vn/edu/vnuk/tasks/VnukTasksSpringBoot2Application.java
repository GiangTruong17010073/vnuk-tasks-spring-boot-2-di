package vn.edu.vnuk.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import vn.edu.vnuk.tasks.filter.ConnectionFilter;

@SpringBootApplication
public class VnukTasksSpringBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(VnukTasksSpringBoot2Application.class, args);
	}
	
	
	@Bean
	public FilterRegistrationBean<ConnectionFilter> connectionFilter() {
	    FilterRegistrationBean<ConnectionFilter> filterRegistrationBean = new FilterRegistrationBean<>();
	    filterRegistrationBean.setFilter(new ConnectionFilter());
	    filterRegistrationBean.setOrder(2);
	    filterRegistrationBean.addUrlPatterns("/tasks");
	    return filterRegistrationBean;
	}

}
