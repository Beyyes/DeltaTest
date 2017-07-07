package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.exceptions.ActionRunErrorException;
import core.config.DatabaseConfig;

public class ConnectionFactory {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	public static Connection getConnection(DatabaseConfig config) throws ActionRunErrorException {
		try {
			Connection connection;
			String host = config.getIp();
			String port = config.getPort();
			String schema = config.getSchema();
			String username = config.getUsername();
			String password = config.getPassword();
			switch (config.getDbType()) {
			case DELTA:
				schema = "x";
				Class.forName("cn.edu.thu.tsfiledb.jdbc.TsfileDriver");
				connection = DriverManager.getConnection("jdbc:tsfile://" + host + ":" + port + "/" + schema,
						username, password);
				break;
			case MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema,
						username, password);
				break;
			default:
				throw new ActionRunErrorException("unknow database type: " + config.getDbType());
			}

			
			return connection;
		} catch (ClassNotFoundException e) {
			logger.error("",e);
			throw new ActionRunErrorException(e.getMessage());
		} catch (SQLException e) {
			logger.error("",e);
			throw new ActionRunErrorException(e.getMessage());
		}
	}
}
