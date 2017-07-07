package core.common;

/**
 * Created by stefanie on 09/01/2017.
 */

public class ExtractorConstant {

    // Database config
    public final static String DATABASE_CONFIG_PATH = "//config/connection/DB";
    public final static String DATABASE_ID_LABEL = "id";
    public final static String DATABASE_TYPE_LABEL = "type";
    public final static String DATABASE_CLASS_LABEL = "class";
    public final static String DATABASE_IP_LABEL = "ip";
    public final static String DATABASE_PORT_LABEL = "port";
    public final static String DATABASE_SCHEMA_LABEL = "schema";
    public final static String DATABASE_USERNAME_LABEL = "username";
    public final static String DATABASE_PASSWORD_LABEL = "password";

    public final static String DATABASE_TSFILE_DEFAULT_NAME = "tsfile";
    public final static String DATABASE_TSFILE_DEFAULT_SCHEMA = "x";
    public final static String DATABASE_MYSQL_DEFAULT_NAME = "mysql";

    // Thread config
    public final static String THREAD_NUM_PATH = "//config/threads";
    public final static String THREAD_NUM_LABEL = "num";

    public final static String THREAD_OPERATION_LABEL = "operation";
    public final static String THREAD_OPERATION_PREPARE = "prepare";
    public final static String THREAD_OPERATION_INSERT = "insert";
    public final static String THREAD_OPERATION_DELETE = "delete";
    public final static String THREAD_OPERATION_UPDATE = "update";
    public final static String THREAD_OPERATION_READ = "read";

    public final static String THREAD_CONFIG_PATH = "//config/threads/thread";

    public final static String THREAD_ID_LABEL = "id";
    public final static String THREAD_DESC_LABEL = "desc";
    public final static String THREAD_DELAY_LABEL = "delay";
    public final static String THREAD_PRETHREAD_LABEL = "prethread";
    public final static String THREAD_PRETHREAD_SPLIT = ",";
    public final static String THREAD_STATEMENTS_LABEL = "statements";
    public final static String THREAD_STATEMENT_LABEL = "statement";
    public final static String THREAD_DATABASE_LABEL = "db";
    public final static String THREAD_FILEPATH_LABEL = "filePath";

    public final static String FILE_SUFFIX = ".xml";


}
