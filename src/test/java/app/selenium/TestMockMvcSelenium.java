package app.selenium;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

import app.TestWebConfig;

@SpringJUnitWebConfig(TestWebConfig.class)
@ActiveProfiles("default")
public class TestMockMvcSelenium
{
	@SuppressWarnings("unused")
	@Autowired
	private WebApplicationContext wac;
	
	private WebDriver driver = null;
	
	private final String localHost = "http://localhost";
	
	
	@BeforeEach
	public void setup()
	{
		/* See: https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-server
		
			MockMvc works with templating technologies that do not rely on a Servlet Container (for example, Thymeleaf, FreeMarker, and others), but it does not work with JSPs, since they rely on the Servlet container.
		 */
		
		/*
		driver = MockMvcHtmlUnitDriverBuilder
				.webAppContextSetup(wac)
				.build();
		*/
	}
	
	
	@Disabled
	@Test
	public void testStart()
	{
		driver.get(localHost + "/");
		
		assertNotNull(driver.getTitle());
	}
}
