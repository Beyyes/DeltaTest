package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.common.GraphEdge;
import core.config.GraphConfig;

/**
 * 表达所有Action之间拓扑关系的一个图
 *
 */

public class ActionGraph {

	public int actionCount;

	// -1 means unvisited, 0 means being visited, 1 means visited
	private int[] visited;
	private boolean circleFlag;
	private ArrayList<GraphEdge> edges;
	public HashMap<Integer, ArrayList<Integer>> prefixNodes;
	public HashMap<Integer, ArrayList<Integer>> suffixNodes;

	public ActionGraph(GraphConfig config) {

		//this.mp = config.mp;
		this.actionCount = config.n;
		this.edges = config.edges;
		prefixNodes = new HashMap<>();
		suffixNodes = new HashMap<>();
		init();
	}

	private void init() {
		for(int i = 0 ; i < actionCount ; i++){
			prefixNodes.put(i, new ArrayList<>());
			suffixNodes.put(i, new ArrayList<>());
		}
		for (GraphEdge e : edges) {
			mapValueAdd(prefixNodes, e.getEdgeEnd(), e.getEdgeStart());
			mapValueAdd(suffixNodes, e.getEdgeStart(), e.getEdgeEnd());
		}
		
		visited = new int[actionCount];
		for (int i = 0; i < actionCount; i++) {
			visited[i] = -1;
		}
		circleFlag = false;
	}

	private void mapValueAdd(HashMap<Integer, ArrayList<Integer>> mp, Integer key, Integer value) {

		ArrayList<Integer> list = mp.get(key);
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(value);
		mp.put(key, list);
	}

	public List<Integer> getActionIdsWithNoPrefix(){
		List<Integer> ret = new ArrayList<>();
		for(Integer id : prefixNodes.keySet()){
			if(prefixNodes.get(id).size() == 0){
				ret.add(id);
			}
		}
		return ret;
	}
	
	/**
	 * 通过给定一个Action的ID,获取该Action所有的先驱，并将其所有先驱的ID通过List返回
	 *
	 * @param id 目标Action的ID
	 * @return
	 */
	public List<Integer> getInputEdgesById(Integer id) {
		return prefixNodes.get(id);
	}

	public List<Integer> getOutputEdgesById(Integer id){
		return suffixNodes.get(id);
	}

	/**
	 * 检查当前的Graph是否可以进行拓扑排序，即是否有环
	 *
	 * @return
	 */
	public boolean hasCircle() {

		for (int i = 0; i < actionCount; i++) {
			dfs(i);
			if (circleFlag == true) {
				return true;
			}
		}
		return false;
	}
	
	private void dfs (int s) {

		visited[s] = 0;
		List<Integer> suffix = suffixNodes.get(s);

		for (int i = 0; suffix != null && i < suffix.size(); i++) {
			int currentNode = suffix.get(i);
			if (visited[currentNode] == -1) {
				dfs(suffix.get(i));
			} else if (visited[currentNode] == 0) {
				circleFlag = true;
			}
		}

		visited[s] = 1;
	}
}
