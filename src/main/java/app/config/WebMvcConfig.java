package app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("app.controllers")
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry
				.addResourceHandler("/static/**")
				.addResourceLocations("/static/")
				.setCachePeriod(60 * 60 * 24 * 365);
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
		registry.jsp().prefix("/WEB-INF/views/");
	}
}
