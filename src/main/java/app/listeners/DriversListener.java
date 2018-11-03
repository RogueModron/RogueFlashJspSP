package app.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DriversListener implements ServletContextListener
{
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		// http://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered
		deregisterDrivers();
	}
	
	private void deregisterDrivers()
	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements())
		{
			Driver driver = drivers.nextElement();
			if (driver.getClass().getClassLoader() != cl)
			{
				continue;
			}

			try
			{
				DriverManager.deregisterDriver(driver);
			}
			catch (SQLException ex)
			{
				Log log = LogFactory.getLog(DriversListener.class);
				log.debug(ex.getMessage());
			}
		}
	}
}
