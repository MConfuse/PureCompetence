package de.cake.util;

public class TimeHelper {

	private static long lastMS = System.currentTimeMillis();
	
	private static void reset()
	{
		lastMS = System.currentTimeMillis();
	}
	
	
	/**
	 * Used to determine if a set amount of Seconds has passed. 1 Second = 1000, usually resetting is necessary.
	 * 
	 * @param time
	 * @param reset
	 * @return boolean
	 */
	public static boolean hasTimeElapsed(long time, boolean reset)
	{
		if(System.currentTimeMillis() - lastMS > time)
		{
			if(reset)
				reset();
				
			return true;
		}
		
		return false;
	}
	
}
