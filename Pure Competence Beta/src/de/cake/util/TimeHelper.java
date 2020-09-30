package de.cake.util;

public class TimeHelper {

	private static long lastMS = System.currentTimeMillis();
	
	/**
	 * Resets the Timer used by {@link de.cake.util.TimeHelper#hasTimeElapsed(long, boolean)}.
	 */
	public static void resetTimer()
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
				resetTimer();
				
			return true;
		}
		
		return false;
	}
	
}
