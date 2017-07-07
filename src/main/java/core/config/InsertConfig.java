package core.config;

import action.ActionType;
import org.apache.commons.lang.StringUtils;

/**
 * Created by stefanie on 06/01/2017.
 */

public class InsertConfig extends ActionConfig{
	
	private String filePath;
	
	public InsertConfig(int id) {
		super(id, ActionType.INSERT);
	}
	
	public InsertConfig(int id, int delay, String desc) {
		super(id, ActionType.INSERT, delay, desc);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = StringUtils.strip(filePath);
	}
}
