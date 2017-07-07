package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.exceptions.ActionRunErrorException;
import core.ConnectionFactory;
import core.TestCase;
import core.common.DatabaseType;
import core.config.ActionConfig;
import core.config.DatabaseConfig;

/**
 * “操作”的基类，insert、update等均继承这个类，并通过实现execute()方法来实现自己的执行逻辑
 *
 */
public abstract class DBAction implements Runnable{
	
	public ActionConfig config;
	public Map<Integer, DatabaseConfig> dbConfigMap;
	public Map<Integer, Connection> dbConnMap;
	public List<Integer> outEdges;
	public TestCase testCase;
	public boolean executeStatus;
	public int TSFILE_KEY_ID;
//	public int MYSQL_KEY_ID;
	
	public DBAction(ActionConfig config, TestCase testCase){
		this.config = config;
		this.dbConfigMap = testCase.getDbConfigMap();
		this.dbConnMap = new HashMap<>(); 
		this.testCase = testCase;
		this.outEdges = testCase.getSuffixNodesById(config.getId());
	}
	
	public abstract void execute() throws ActionRunErrorException;
	
	public void initConnections() throws ActionRunErrorException{
		for(Integer id : dbConfigMap.keySet()){

			DatabaseConfig config = dbConfigMap.get(id);

			if (config.getDbType().equals(DatabaseType.DELTA)) {
				TSFILE_KEY_ID = id;
//			} else if(config.getDbType().equals(DatabaseType.MYSQL)) {
//				MYSQL_KEY_ID = id;
			} else {
				throw new ActionRunErrorException("Can not resolve datatype: " + config.getDbType());
			}

			Connection conn = ConnectionFactory.getConnection(config);
			dbConnMap.put(id, conn);
		}
	}
	
	public void run(){
		try {
			Thread.sleep(config.getDelay() * 1000);
			initConnections();
			execute();
			closeDBConnection();
			finish(true, config.getId() + " finished");
			notifyNextAction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ActionRunErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeDBConnection() throws SQLException{
		for(Connection conn : dbConnMap.values()){
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		}
	}
	
	public void notifyNextAction(){
		for(Integer id : outEdges){
			testCase.notifyNextAction(id);
		}
	}
	
	public void finish(boolean status, String msg){
		testCase.oneActionFinished(config.getId(), status, msg);
	}
	
}
