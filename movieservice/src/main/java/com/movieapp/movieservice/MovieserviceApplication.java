package com.movieapp.movieservice;

import com.movieapp.movieservice.filter.AdminFilter;
import com.movieapp.movieservice.filter.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
public class MovieserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean getfilter()
	{
		FilterRegistrationBean fUserBean=new FilterRegistrationBean();

		fUserBean.setFilter(new UserFilter());

//		String url1 = "/api/v1/user/view/{viewById}";
//		String url2 = "/api/v1/user/update";

		String [] userUrlList = {
				"/api/movie/get/*"
				,"/api/movie/getAll"
				};

		fUserBean.addUrlPatterns(userUrlList);

		return fUserBean;
	}

	@Bean
	public FilterRegistrationBean filter(){

		FilterRegistrationBean fAdminBean = new FilterRegistrationBean<>();

		fAdminBean.setFilter(new AdminFilter());

		String [] adminUrlList = {"/api/movie/add",
				"/api/movie/update/*",
				"/api/movie/delete/*",
				"/api/movie/deleteAll"};

		fAdminBean.addUrlPatterns(adminUrlList);

		return fAdminBean;
	}

}
