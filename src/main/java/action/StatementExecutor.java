package action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StatementExecutor {

	public static long executeBatch(Connection conn, List<String> stats) throws SQLException{
		Statement statement = conn.createStatement();
		for(String stat : stats){
			statement.addBatch(stat);
		}
		long st = System.currentTimeMillis();
		statement.executeBatch();
		long et = System.currentTimeMillis();
		statement.close();
		return et - st;
	}
	
	public static void execute(Connection conn, List<String> stats) throws SQLException {
		for (String stat : stats) {
			Statement statement = conn.createStatement();
			statement.execute(stat);
			statement.close();
		}
	}

	public static ResultSet executeQuery(Connection conn, String stat) throws SQLException {
		Statement statement = conn.createStatement();
		ResultSet ret = statement.executeQuery(stat);
		//TODO
//		statement.close();
		return ret;
	}
}
