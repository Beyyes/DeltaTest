package action.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import action.DBAction;
import action.StatementExecutor;
import common.exceptions.ActionRunErrorException;
import core.TestCase;
import core.config.ActionConfig;
import core.config.InsertConfig;

public class InsertAction extends DBAction{
	private static final Logger logger = LoggerFactory.getLogger(InsertAction.class);
	private static final String SEGMENT_SYMBOL = ",";
	private static final int DEVICE_COLUMN_NUM = 0;
	private static final int TIME_COLUMN_NUM = 1;
	private static final int DEVICE_TYPE_COLUMN_NUM = 2;
	private static final int SENSOR_NAME_COLUMN_START_NUM = 3;
	private static final int SENSOR_VALUE_COLUMN_START_NUM = 4;
	//private static final int TSFILE_KEY_ID = 1;
	//private static final int MYSQL_KEY_ID = 2;
	
	private List<String> db1StatList;
//	private List<String> db2StatList;
	private int batchCount = 10000;
	
	public InsertAction(ActionConfig config, TestCase testCase) {
		super(config, testCase);
		db1StatList = new ArrayList<String>();
//		db2StatList = new ArrayList<String>();
	}

	private void addbatchSql(int dbId, ArrayList<String> statas){
		if(dbId == TSFILE_KEY_ID){
			db1StatList.addAll(statas);
		}
//		if(dbId == MYSQL_KEY_ID){
//			db2StatList.addAll(statas);
//		}
	}
	
	private void executeBatch() throws SQLException{
		long execTime = StatementExecutor.executeBatch(dbConnMap.get(TSFILE_KEY_ID), db1StatList);
		logger.info("Execute time: {} ms", execTime);
//    	StatementExecutor.executeBatch(dbConnMap.get(MYSQL_KEY_ID), db2StatList);
    	db1StatList.clear();
//    	db2StatList.clear();
	}
	
	@Override
	public void execute() throws ActionRunErrorException {

	    if(!dbConnMap.containsKey(TSFILE_KEY_ID))
	        throw new ActionRunErrorException("dbConnMap do not contatins key " + TSFILE_KEY_ID);
//        if(!dbConnMap.containsKey(MYSQL_KEY_ID))
//            throw new ActionRunErrorException("dbConnMap do not contatins key " + MYSQL_KEY_ID);

	    try {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(new File(((InsertConfig) config).getFilePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new ActionRunErrorException(e.getMessage());
            }
            int insertCount = 0;
            Scanner sc = new Scanner(inputStream);
            while (sc.hasNext()) {
            	String[] datas = sc.nextLine().split(SEGMENT_SYMBOL);
                int length = datas.length;

                ArrayList<String> stats = new ArrayList<String>();
                
                StringBuilder sb_tsfile = new StringBuilder();
                StringBuilder sb_tsValues = new StringBuilder();
                sb_tsfile.append("insert into ");
                sb_tsfile.append(datas[DEVICE_TYPE_COLUMN_NUM]);
                sb_tsfile.append(".");
                sb_tsfile.append(datas[DEVICE_COLUMN_NUM]);
                sb_tsfile.append("(timestamp");
                
                sb_tsValues.append("values(");
                sb_tsValues.append(datas[TIME_COLUMN_NUM]);
                
                for (int i = SENSOR_NAME_COLUMN_START_NUM; i < length; i = i + 2) {
                	sb_tsfile.append(",");
                	sb_tsfile.append(datas[i]);
                                        
                	sb_tsValues.append(",");
                	sb_tsValues.append(datas[i + 1]);
                }
                sb_tsfile.append(") ");
                sb_tsValues.append(")");
                sb_tsfile.append(sb_tsValues.toString());
                stats.add(sb_tsfile.toString());

//                StatementExecutor.execute(dbConnMap.get(TSFILE_KEY_ID), stats);
                addbatchSql(TSFILE_KEY_ID, stats);

//                StringBuilder sb_mysql = new StringBuilder();
//                sb_mysql.append("insert into ");
//                sb_mysql.append(datas[DEVICE_TYPE_COLUMN_NUM].replaceAll("\\.", "_"));
//                sb_mysql.append("_");
//                sb_mysql.append(datas[DEVICE_COLUMN_NUM]);
//                sb_mysql.append("(time");
//                for (int i = SENSOR_NAME_COLUMN_START_NUM; i < length; i = i + 2) {
//                    sb_mysql.append(",");
//                    sb_mysql.append(datas[i]);
//                }
//                sb_mysql.append(") values (");
//                sb_mysql.append(datas[TIME_COLUMN_NUM]);
//                for (int i = SENSOR_VALUE_COLUMN_START_NUM; i < length; i = i + 2) {
//                    sb_mysql.append(",");
//                    sb_mysql.append(datas[i]);
//                }
//                sb_mysql.append(");");
//                stats.clear();
//                stats.add(sb_mysql.toString());
//
////                StatementExecutor.execute(dbConnMap.get(MYSQL_KEY_ID), stats);
//                addbatchSql(MYSQL_KEY_ID, stats);
                insertCount ++;
                if(insertCount % batchCount == 0){
                	logger.info("start to batchExecute...");
                	executeBatch();
                	logger.info("done");
                	logger.info("insert count :" + insertCount);
                }
            }
            executeBatch();
            logger.info("finally insert count :" + insertCount);
        }
        catch(SQLException e) {
	        e.printStackTrace();
	        throw new ActionRunErrorException(e.getMessage());
        }
	}
	
	
	
	

}
