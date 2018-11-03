package app.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import app.listeners.DriversListener;

public class AppInitializer implements WebApplicationInitializer
{
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		servletContext.addListener(DriversListener.class);

		// https://stackoverflow.com/questions/28877982/spring-java-config-with-multiple-dispatchers

		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(
				ServicesConfig.class,
				TransactionManagerConfig.class);
		appContext.refresh();

		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.setParent(appContext);
		mvcContext.setServletContext(servletContext);
		mvcContext.register(WebMvcConfig.class);
		mvcContext.refresh();

		DispatcherServlet mvcServlet = new DispatcherServlet(mvcContext);
		Dynamic mvcRegistration = servletContext.addServlet("appMvc", mvcServlet);
		mvcRegistration.setLoadOnStartup(1);
		mvcRegistration.addMapping("/");
	}
}
