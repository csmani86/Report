package com.skava.framework.action;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deepthika.r
 *
 */
public class ActionEngineFactory 
{	
	private static Map<Integer, ActionEngine> actionEngineMap = new HashMap<>();

	public static ActionEngine getActionEngine(int browserType) 
	{	
		ActionEngine actionEngine = actionEngineMap.get(browserType);
		if (null == actionEngine) 
		{
			actionEngine = new SeleniumActionEngine(browserType);
		}
		
		/*if (null == actionEngine) {
			synchronized (ActionEngine.class) {
				if (null == actionEngine) {
					actionEngine = new SeleniumActionEngine(browserType);
				}
			}
		}*/
		
		return actionEngine;
	}

}