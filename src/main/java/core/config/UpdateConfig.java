package core.config;

import java.util.HashMap;
import java.util.List;

import action.ActionType;

/**
 * Created by stefanie on 06/01/2017.
 */

public class UpdateConfig extends ActionConfig{

	//private HashMap<Integer, List<String>> statsMap;
	
	public UpdateConfig(int id) {
		super(id, ActionType.UPDATE);
	}
	
	public UpdateConfig(int id, int delay, String desc) {
		super(id, ActionType.UPDATE, delay, desc);
	}
	
//	public HashMap<Integer, List<String>> getStatsMap() {
//		return statsMap;
//	}
//
//	public void setStatsMap(HashMap<Integer, List<String>> statsMap) {
//		this.statsMap = statsMap;
//	}
}
