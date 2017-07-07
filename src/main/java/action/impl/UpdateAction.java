package action.impl;

import java.sql.Connection;
import java.sql.SQLException;

import action.DBAction;
import action.StatementExecutor;
import common.exceptions.ActionRunErrorException;
import core.TestCase;
import core.config.ActionConfig;

public class UpdateAction extends DBAction{

    public UpdateAction(ActionConfig config, TestCase testCase) {
		super(config, testCase);
    }

    @Override
    public void execute() throws ActionRunErrorException {
        try {
            for(Integer id : dbConnMap.keySet()){
                Connection conn = dbConnMap.get(id);
                StatementExecutor.execute(conn, config.getStatsMap().get(id));
            }
        }
    	catch (SQLException e) {
            e.printStackTrace();
            throw new ActionRunErrorException(e.getMessage());
        }
    }
}
