package jl.project.__commontests;

import java.awt.AWTException;
import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;


public class RobotFactory 
{
	static Logger logger = LoggerFactory.getLogger(RobotFactory.class);
	private static Robot robot = null;	
	

	
	/**
	 * 
	 * @return An instance of the Robot class
	 */
	public static Robot getRobotInstance()
	{
		if (robot==null)
		{
			try 
			{
				robot = new Robot();
			} 
			catch (AWTException e) 
			{
					if (logger.isErrorEnabled()) logger.error(StringExternalization.EXCEPTION_AWT);
					e.printStackTrace();
			}
		}

		return robot;
	}

}
