package app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class ErrorController
{
	@GetMapping("/error")
	public String error()
	{
		return "error";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleNotFound(
			Exception e,
			WebRequest request)
	{
		Log log = LogFactory.getLog(ErrorController.class);
		log.debug(e.getMessage());

		// See: https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
		
		ModelAndView mav = new ModelAndView();
		
		String requestedWithHeader = request.getHeader("X-Requested-With");
		if (requestedWithHeader != null &&
				requestedWithHeader.equals("XMLHttpRequest"))
		{
			mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mav.setViewName("empty");
		}
		else
		{
			mav.setViewName("error");
		}
		
		return mav;
	}
}
