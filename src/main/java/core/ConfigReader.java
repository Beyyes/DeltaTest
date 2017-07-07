package core;

import static core.common.ExtractorConstant.*;
import core.config.*;
import core.common.Utils;
import core.xml.XmlReader;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by stefanie on 06/01/2017.
 * Used for load xml config file.
 */

public class ConfigReader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

	private GraphConfig graphConfig;
	private Map<Integer, DatabaseConfig> databaseConfigList = new HashMap<Integer, DatabaseConfig>();
	private Map<Integer, ActionConfig> actionConfigList = new HashMap<Integer, ActionConfig>();
	private XmlReader reader;

	public ConfigReader(String filePath, String fileName) {

		logger.info("[ConfigResolve]: Resolve database config from file: " + filePath + "/" + fileName);
		reader = new XmlReader(filePath, fileName);
	}

	private void getActionGraph(int threadId, String preThreads) throws Exception{

		String[] threads = preThreads.split(THREAD_PRETHREAD_SPLIT);
		for (int i = 0; i < threads.length; i++) {
			if(threads[i].trim().equals("")){
				continue;
			}
			graphConfig.addOneEdge(Utils.stringToInt(threads[i]), threadId);
		}
	}

	/**
	 * resolve statement list like: <statements id=""><statement></statement></statements>
	 * @param statsIterator statements iterator
	 * @return <statsId, statsList> key-value map
	 */
	private HashMap<Integer, List<String>> resolveStatements(Iterator statsIterator) throws Exception {

		HashMap<Integer, List<String>> statementsMap = new HashMap<>();

		while(statsIterator.hasNext()) {

			Element statements = (Element)statsIterator.next();
			Iterator it = statements.elements(THREAD_STATEMENT_LABEL).iterator();
			int databaseId = Utils.stringToInt(statements.attributeValue(THREAD_DATABASE_LABEL));
			List<String> statementsList = new ArrayList<String>();

			while(it.hasNext()) {
				String statement = ((Element)it.next()).getTextTrim();
				statementsList.add(statement);
			}
			statementsMap.put(databaseId, statementsList);
		}
		return statementsMap;
	}

	private ActionConfig resolvePrepareThread(int id, String desc, Element e) throws Exception {

		PrepareConfig prepareConfig = new PrepareConfig(id, desc);
		List statements = e.elements(THREAD_STATEMENTS_LABEL);
		Iterator iterator = statements.iterator();

		prepareConfig.setStatsMap(resolveStatements(iterator));
		return prepareConfig;
	}

	private ActionConfig resolveInsertThread(int id, String desc, Element e) throws Exception {

		int delay = Utils.stringToInt(e.element(THREAD_DELAY_LABEL).getText());
		String filePath = e.element(THREAD_FILEPATH_LABEL).getText();

		InsertConfig insertConfig = new InsertConfig(id, delay, desc);
		insertConfig.setFilePath(filePath);

		String preThreads = e.element(THREAD_PRETHREAD_LABEL).getText();
		getActionGraph(id, preThreads);

		return insertConfig;
	}

	private ActionConfig resolveUpdateThread(int id, String desc, Element e) throws Exception {

		int delay = Utils.stringToInt(e.element(THREAD_DELAY_LABEL).getText());

		String preThreads = e.element(THREAD_PRETHREAD_LABEL).getText();
		getActionGraph(id, preThreads);

		List statements = e.elements(THREAD_STATEMENTS_LABEL);
		Iterator iterator = statements.iterator();

		UpdateConfig updateConfig = new UpdateConfig(id, delay, desc);
		updateConfig.setStatsMap(resolveStatements(iterator));
		return updateConfig;
	}

	private ActionConfig resolveDeleteThread(int id, String desc, Element e) throws Exception {

		int delay = Utils.stringToInt(e.element(THREAD_DELAY_LABEL).getText());

		String preThreads = e.element(THREAD_PRETHREAD_LABEL).getText();
		getActionGraph(id, preThreads);

		List statements = e.elements(THREAD_STATEMENTS_LABEL);
		Iterator iterator = statements.iterator();

		DeleteConfig deleteConfig = new DeleteConfig(id, delay, desc);
		deleteConfig.setStatsMap(resolveStatements(iterator));

		return deleteConfig;
	}

	private ActionConfig resolveReadThread(int id, String desc, Element e) throws Exception {

		int delay = Utils.stringToInt(e.element(THREAD_DELAY_LABEL).getText());

		String preThreads = e.element(THREAD_PRETHREAD_LABEL).getText();
		getActionGraph(id, preThreads);

		List statements = e.elements(THREAD_STATEMENT_LABEL);
		Iterator iterator = statements.iterator();

		HashMap<Integer, List<String>> statementMap = new HashMap<>();
		while(iterator.hasNext()) {

			Element statement = (Element)iterator.next();
			int databaseId = Utils.stringToInt(statement.attributeValue(THREAD_DATABASE_LABEL));
			ArrayList<String> stats = new ArrayList<>();
			stats.add(StringUtils.strip(statement.getText()));
			statementMap.put(databaseId, stats);
		}

		ReadConfig readConfig = new ReadConfig(id, delay, desc);
		readConfig.setStatsMap(statementMap);
		return readConfig;
	}

	public void resolveDatabaseConfig() throws Exception{

		Iterator iterator = reader.getDatabaseConfigs().iterator();

		while (iterator.hasNext()) {

			DatabaseConfig config = new DatabaseConfig();
			Element e = (Element) iterator.next();

			config.setAttr(DATABASE_ID_LABEL, e.attributeValue(DATABASE_ID_LABEL));
			config.setAttr(DATABASE_TYPE_LABEL, e.elementTextTrim(DATABASE_TYPE_LABEL));
			config.setAttr(DATABASE_CLASS_LABEL, e.elementTextTrim(DATABASE_CLASS_LABEL));
			config.setAttr(DATABASE_IP_LABEL, e.elementTextTrim(DATABASE_IP_LABEL));
			config.setAttr(DATABASE_PORT_LABEL, e.elementTextTrim(DATABASE_PORT_LABEL));
			config.setAttr(DATABASE_SCHEMA_LABEL, e.elementTextTrim(DATABASE_SCHEMA_LABEL));
			config.setAttr(DATABASE_USERNAME_LABEL, e.elementTextTrim(DATABASE_USERNAME_LABEL));
			config.setAttr(DATABASE_PASSWORD_LABEL, e.elementTextTrim(DATABASE_PASSWORD_LABEL));
			databaseConfigList.put(Utils.stringToInt(e.attributeValue(DATABASE_ID_LABEL)), config);
		}
		logger.info("[ConfigResolve]: Database configs resolve is finished. Total: " + actionConfigList.size() + " actions.");
	}

	public void resolveActionsConfig() throws Exception {

		List l = reader.getThreadConfigs();
		Iterator iterator = l.iterator();

		graphConfig = new GraphConfig(l.size());

		while (iterator.hasNext()) {
			ActionConfig action;
			Element ele = (Element)iterator.next();
			int actionId = Utils.stringToInt(ele.attributeValue(THREAD_ID_LABEL));
			String desc = ele.attributeValue(THREAD_DESC_LABEL);

			switch (ele.attributeValue(THREAD_OPERATION_LABEL)) {
				case THREAD_OPERATION_PREPARE: action = resolvePrepareThread(actionId, desc, ele); break;
				case THREAD_OPERATION_INSERT: action = resolveInsertThread(actionId, desc, ele); break;
				case THREAD_OPERATION_UPDATE: action = resolveUpdateThread(actionId, desc, ele); break;
				case THREAD_OPERATION_DELETE: action = resolveDeleteThread(actionId, desc, ele); break;
				case THREAD_OPERATION_READ: action = resolveReadThread(actionId, desc, ele); break;
				default:
					throw new Exception("Unsupported operation type.");
			}
			actionConfigList.put(actionId, action);
		}

		logger.info("[ConfigResolve]: Action configs resolve is finished. Total: " + actionConfigList.size() + " actions.");
	}

//	public static void main(String[] args) throws Exception{
//
//		ConfigReader configReader = new ConfigReader("src/main/resources", "config_template.xml");
//		configReader.resolveActionsConfig();
//		Map<Integer, ActionConfig> l = configReader.actionConfigList;
//		GraphConfig g = configReader.graphConfig;
//		System.out.println();
//	}

	public Map<Integer, ActionConfig> getActionConfigList() {
		return actionConfigList;
	}

	public void setActionConfigList(Map<Integer, ActionConfig> actionConfigList) {
		this.actionConfigList = actionConfigList;
	}

	public Map<Integer, DatabaseConfig> getDatabaseConfigList() {
		return databaseConfigList;
	}

	public void setDatabaseConfigList(Map<Integer, DatabaseConfig> databaseConfigList) {
		this.databaseConfigList = databaseConfigList;
	}

	public GraphConfig getGraphConfig() {
		return graphConfig;
	}

	public void setGraphConfig(GraphConfig graphConfig) {
		this.graphConfig = graphConfig;
	}

	public static void main(String args[]) throws Exception {
		ConfigReader reader = new ConfigReader("src/main/resources", "testCaseExample.xml");
		reader.resolveActionsConfig();
		Map<Integer, ActionConfig> a = reader.getActionConfigList();
		System.out.println();
	}
}