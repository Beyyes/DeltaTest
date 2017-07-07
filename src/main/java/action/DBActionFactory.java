package action;

import java.util.Map;

import action.impl.DeleteAction;
import action.impl.InsertAction;
import action.impl.PrepareAction;
import action.impl.ReadAction;
import action.impl.UpdateAction;
import core.TestCase;
import core.config.ActionConfig;
import core.config.DatabaseConfig;

public class DBActionFactory {
	public static DBAction getDBAction(ActionConfig config, TestCase testCase) {
		switch(config.getType()){
		case DELETE:
			return new DeleteAction(config, testCase);
		case INSERT:
			return new InsertAction(config, testCase);
		case PREPARE:
			return new PrepareAction(config, testCase);
		case READ:
			return new ReadAction(config, testCase);
		case UPDATE:
			return new UpdateAction(config, testCase);
		default:
			return null;
		}
	}
}
