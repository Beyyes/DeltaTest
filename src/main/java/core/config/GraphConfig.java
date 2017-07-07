package core.config;

import core.common.GraphEdge;
import java.util.ArrayList;

/**
 * Created by stefanie on 09/01/2017.
 */

public class GraphConfig {

	public int n;
	//public int mp[][];
	public ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
	
	public GraphConfig() {
		
	}
	
	public GraphConfig(int n){
		this.n = n;
		//mp = new int[n+1][n+1];
		//initMp();
	}
	
//	public void initMp(){
//		for(int i = 0 ; i < mp.length ; i ++){
//			for(int j = 0 ; j < mp[0].length ; j ++){
//				mp[i][j] = 0;
//			}
//		}
//	}
	
	public void addOneEdge(int from, int to){
		//mp[from][to] = 1;
		GraphEdge e = new GraphEdge(from, to);
		edges.add(e);
	}
}
