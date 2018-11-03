package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import app.config.ServicesConfig;
import app.config.WebMvcConfig;

@Configuration
@Import({
	ServicesConfig.class, /* !Test */
	WebMvcConfig.class,   /* !Test */
	TestTransactionManagerConfig.class
})
public class TestWebConfig
{

}
