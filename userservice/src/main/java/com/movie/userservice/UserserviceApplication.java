package com.movie.userservice;

import com.movie.userservice.filter.AdminFilter;
import com.movie.userservice.filter.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean getfilter()
	{
		FilterRegistrationBean fUserBean=new FilterRegistrationBean();

		fUserBean.setFilter(new UserFilter());

		/*String url1 = "/api/v1/user/view/{viewById}";
		String url2 = "/api/v1/user/update";*/

		String [] userUrlList = {"/user/view/*",
								"/user/update"};

		fUserBean.addUrlPatterns(userUrlList);

		return fUserBean;
	}

	@Bean
	public FilterRegistrationBean filter(){

		FilterRegistrationBean fAdminBean = new FilterRegistrationBean<>();

		fAdminBean.setFilter(new AdminFilter());

		String [] adminUrlList = {"/user/viewAll"
								,"/user/delete/*"
								,"/user/deleteAll"};

		fAdminBean.addUrlPatterns(adminUrlList);

		return fAdminBean;
	}
}
