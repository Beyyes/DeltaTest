package core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import action.DBAction;
import action.DBActionFactory;
import common.exceptions.HasCircleInActionGraphException;
import core.config.ActionConfig;
import core.config.DatabaseConfig;

/**
 * 这个类来初始化Action之间的拓扑关系，并控制一个TestCase中所有Action的执行
 *
 */
public class TestCase{
	private static final Logger logger = LoggerFactory.getLogger(TestCase.class);
	private ActionGraph actionGraph;
	private HashMap<Integer, DBAction> actionMap;
	private ConfigReader configReader;
	
	private HashMap<Integer, Integer> actionDepCount;
	private HashMap<Integer, Integer> actionFinishedDepCount;
	private int actionCount;
	private int actionFinishedCount;
	private boolean status;
	
	public TestCase(String filePath, String fileName) throws Exception{
		status = true;
		configReader = new ConfigReader(filePath, fileName);
		configReader.resolveDatabaseConfig();
		configReader.resolveActionsConfig();
		
		actionFinishedCount = 0;
		actionCount = configReader.getActionConfigList().size(); 
		initTopology();
		initActions();
	}
	
	public void initTopology() throws Exception{
		actionGraph = new ActionGraph(configReader.getGraphConfig());
		if(actionGraph.hasCircle()){
			throw new HasCircleInActionGraphException("There is circle dependency in actions");
		}
		actionDepCount = new HashMap<>();
		actionFinishedDepCount = new HashMap<>();
		for(Integer id : actionGraph.prefixNodes.keySet()){
			actionDepCount.put(id, actionGraph.prefixNodes.get(id).size());
			actionFinishedDepCount.put(id, 0);
		}
	}
	
	public void initActions(){
		actionMap = new HashMap<Integer, DBAction>();
		for(Integer id : configReader.getActionConfigList().keySet()){
			ActionConfig config = configReader.getActionConfigList().get(id);
			DBAction action = DBActionFactory.getDBAction(config, this);
			actionMap.put(id, action);
		}
	}
	
	public void start(){
		List<Integer> actionsWithNoPrefix = actionGraph.getActionIdsWithNoPrefix();
		for(Integer id: actionsWithNoPrefix){
			runAnActionById(id);
		}
	}
	
	public Map<Integer, DatabaseConfig> getDbConfigMap(){
		return configReader.getDatabaseConfigList();
	}
	
	public List<Integer> getSuffixNodesById(Integer id){
		return actionGraph.getOutputEdgesById(id);
	}
	
	public synchronized void oneActionFinished(int id,boolean executeStatus, String msg){
		logger.info("[ActionFinished]: Action ID: " + id + "|status: " + executeStatus + "|Message: " + msg);
		actionFinishedCount ++;
		if(!executeStatus){
			status = false;	
		}
		if(actionFinishedCount == actionCount){
			if(status){
				logger.info("[AllActionFinished]: All actions are finished. Final status: " + status);
			}else{
				logger.error("[AllActionFinished]: All actions are finished. Final status: " + status);
			}
		}
	}
	
	public void notifyNextAction(int id){
		synchronized (actionFinishedDepCount) {
			int currentV = actionFinishedDepCount.get(id);
			actionFinishedDepCount.put(id, currentV + 1);
			if(currentV + 1 == actionDepCount.get(id)){
				runAnActionById(id);
			}
		}
	}
	
	public void runAnActionById(int id){
		Thread t = new Thread(actionMap.get(id));
		logger.info("[ActionStarts]:  Action ID: {}", id);
		t.start();
	}
	
	public static void main(String args[]){
		try {
			TestCase testCase = new TestCase("src/main/resources", "config_template.xml");
			testCase.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}









