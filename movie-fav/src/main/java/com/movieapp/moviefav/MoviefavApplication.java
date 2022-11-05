package com.movieapp.moviefav;

import com.movieapp.moviefav.filter.AdminFilter;
import com.movieapp.moviefav.filter.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class MoviefavApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviefavApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate newRestTemplate(){
		return new RestTemplate();
	}


	@Bean
	public FilterRegistrationBean getfilter()
	{
		FilterRegistrationBean fUserBean=new FilterRegistrationBean();

		fUserBean.setFilter(new UserFilter());

//		String url1 = "/api/v1/user/view/{viewById}";
//		String url2 = "/api/v1/user/update";

		String [] userUrlList = {"/api/favorite/movie/*"
				,"/api/favorite/movie/get/*"
				,"/api/favorite/movie/getAll/*",
				"/api/favorite/movie/delete/*",
				"/api/favorite/movie/deleteAll/*"};

		fUserBean.addUrlPatterns(userUrlList);

		return fUserBean;
	}

//	@Bean
//	public FilterRegistrationBean filter(){
//
//		FilterRegistrationBean fAdminBean = new FilterRegistrationBean<>();
//
//		fAdminBean.setFilter(new AdminFilter());
//
//		String [] adminUrlList = {"/api/favorite/movie/*"
//				,"/api/favorite/movie/get/*"
//				,"/api/favorite/movie/getAll/*",
//				"/api/favorite/movie/delete/*",
//				"/api/favorite/movie/deleteAll/*"};
//
//		fAdminBean.addUrlPatterns(adminUrlList);
//
//		return fAdminBean;
//	}

}
