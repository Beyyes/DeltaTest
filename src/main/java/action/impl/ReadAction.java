package action.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import action.DBAction;
import action.StatementExecutor;
import common.exceptions.ActionRunErrorException;
import core.TestCase;
import core.config.ActionConfig;

public class ReadAction extends DBAction {

    //private static final int TSFILE_KEY_ID = 1;
    //private static final int MYSQL_KEY_ID = 2;

    public ReadAction(ActionConfig config, TestCase testCase) {
        super(config, testCase);
    }

    @Override
    public void execute() throws ActionRunErrorException {

        try {
            if (!dbConnMap.containsKey(TSFILE_KEY_ID))
                throw new ActionRunErrorException("dbConnMap do not contatins key " + TSFILE_KEY_ID);
//            if (!dbConnMap.containsKey(MYSQL_KEY_ID))
//                throw new ActionRunErrorException("dbConnMap do not contatins key " + MYSQL_KEY_ID);
            if (!config.getStatsMap().containsKey(TSFILE_KEY_ID))
                throw new ActionRunErrorException("StatsMap do not contatins key " + TSFILE_KEY_ID);
//            if (!config.getStatsMap().containsKey(MYSQL_KEY_ID))
//                throw new ActionRunErrorException("StatsMap do not contatins key " + MYSQL_KEY_ID);
            if (config.getStatsMap().get(TSFILE_KEY_ID).size() != 1)
                throw new ActionRunErrorException("tsfile read statement's number is not one");
//            if (config.getStatsMap().get(MYSQL_KEY_ID).size() != 1)
//                throw new ActionRunErrorException("mysql read statement's number is not one");

            String ts_stat = config.getStatsMap().get(TSFILE_KEY_ID).get(0);
//            String mysql_stat = config.getStatsMap().get(MYSQL_KEY_ID).get(0);

            ResultSet ts_result = StatementExecutor.executeQuery(dbConnMap.get(TSFILE_KEY_ID), ts_stat);
//            ResultSet mysql_result = StatementExecutor.executeQuery(dbConnMap.get(MYSQL_KEY_ID), mysql_stat);

            int colCount = ts_result.getMetaData().getColumnCount();
            int count = 0;
            boolean ret1hasValue = ts_result.next();
//            boolean ret2hasValue = mysql_result.next();
//            while (ret1hasValue && ret2hasValue) {
            while (ret1hasValue) {
                count++;
//                for (int i = 0; i < colCount; i++) {
//                    if (!Double.valueOf(ts_result.getString(i)).equals(Double.valueOf(mysql_result.getString(i + 1)))) {
//                        throw new ActionRunErrorException("results on row " + count + " column " + i + " are not match");
//                    }
//                }
                ret1hasValue = ts_result.next();
//                ret2hasValue = mysql_result.next();
            }

//            if (ret1hasValue || ret2hasValue) {
//                throw new ActionRunErrorException("results row number is different");
//            }
            ts_result.close();
//            mysql_result.close();
            //match

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ActionRunErrorException(e.getMessage());
        }
    }
}