package action.impl;

import java.sql.SQLException;

import action.DBAction;
import action.StatementExecutor;
import common.exceptions.ActionRunErrorException;
import core.TestCase;
import core.config.ActionConfig;

public class PrepareAction extends DBAction{

	public PrepareAction(ActionConfig config, TestCase testCase) {
		super(config, testCase);
	}

	@Override
	public void execute() throws ActionRunErrorException {
		try {
			for(Integer id : dbConfigMap.keySet()) {
				StatementExecutor.execute(dbConnMap.get(id), config.getStatsMap().get(id));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new ActionRunErrorException(e.getMessage());
		}
	}
}
