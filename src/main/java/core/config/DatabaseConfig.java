package core.config;

import core.common.DatabaseType;
import static core.common.ExtractorConstant.*;

/**
 * Created by stefanie on 09/01/2017.
 */

public class DatabaseConfig {

	private DatabaseType dbType;
	private int id;
	private String classname;
	private String ip;
	private String port;
	private String schema;
	private String username;
	private String password;

	public DatabaseConfig() {
	}

	private void setId(String id) throws Exception {

		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
			throw new Exception("Database ID is not an integer.");
		}
	}
	
	public int getId(){
		return this.id;
	}

	private void setDbType(String dbType) throws Exception {

		switch (dbType) {
			case DATABASE_TSFILE_DEFAULT_NAME: this.dbType = DatabaseType.DELTA; break;
			case DATABASE_MYSQL_DEFAULT_NAME: this.dbType = DatabaseType.MYSQL; break;
			default:
				throw new Exception("Unsurpported database type:" + dbType);
		}
	}

	private void setClassname(String classname) throws Exception {

		if(classname.equals("")) {
			//TODO
			throw new Exception("Null value: classname.");
		} else {
			this.classname = classname;
		}

	}

	private void setIP(String ip) throws Exception {

		if(ip.equals("")) {
			//TODO
			throw new Exception("Null value: ip.");
		} else {
			this.ip = ip;
		}
	}

	private void setPort(String port) throws Exception {

		if(port.equals("")) {
			//TODO
			throw new Exception("Null value: port.");
		} else {
			this.port = port;
		}
	}
	
	
	
	private void setSchema(String schema) throws Exception {

		if (schema.equals("")) {
			if (dbType.equals(DatabaseType.DELTA)) {
				this.schema = DATABASE_TSFILE_DEFAULT_SCHEMA;
			} else {
				//TODO
				throw new Exception("Null value: schema.");
			}
		} else {
			this.schema = schema;
		}
	}

	private void setUsername(String username) throws Exception {

		if (username.equals("")) {
			//TODO
			throw new Exception("Null value: username.");
		} else {
			this.username = username;
		}
	}

	private void setPassword(String password) throws Exception {

		if (password.equals("")) {
			//TODO
			throw new Exception("Null value: password.");
		} else {
			this.password = password;
		}
	}

	public void setAttr(String labelName, String attr) throws Exception {

		switch (labelName) {
			case DATABASE_ID_LABEL: setId(attr); break;
			case DATABASE_TYPE_LABEL: setDbType(attr); break;
			case DATABASE_CLASS_LABEL: setClassname(attr); break;
			case DATABASE_IP_LABEL: setIP(attr); break;
			case DATABASE_PORT_LABEL: setPort(attr); break;
			case DATABASE_SCHEMA_LABEL: setSchema(attr); break;
			case DATABASE_USERNAME_LABEL: setUsername(attr); break;
			case DATABASE_PASSWORD_LABEL: setPassword(attr); break;
			default:
				//TODO
				throw new Exception("Unsupported attr name.");
		}
	}

	public DatabaseType getDbType() {
		return dbType;
	}

	public void setDbType(DatabaseType dbType) {
		this.dbType = dbType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClassname() {
		return classname;
	}

	public String getPort() {
		return port;
	}

	public String getSchema() {
		return schema;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(int id) {
		this.id = id;
	}

}
