package core.config;

import java.util.HashMap;
import java.util.List;

import action.ActionType;

public class ActionConfig {

	private int id;
	private ActionType type;
	private int delay;
	private String description;
	protected HashMap<Integer, List<String>> statsMap;
	
	public ActionConfig(int id, ActionType type){
		this.setId(id);
		this.setType(type);
	}
	
	public ActionConfig(int id, ActionType type, int delay, String description){
		this(id, type);
		this.setDelay(delay);
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
    public HashMap<Integer, List<String>> getStatsMap() {
        return statsMap;
    }

    public void setStatsMap(HashMap<Integer, List<String>> statsMap) {
        this.statsMap = statsMap;
    }
}
