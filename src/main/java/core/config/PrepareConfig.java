package core.config;

import action.ActionType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by stefanie on 09/01/2017.
 */

public class PrepareConfig extends ActionConfig {

    //private HashMap<Integer,List<String>> statsMap;

    public PrepareConfig(int id, String desc) {
        super(id, ActionType.PREPARE, 0, desc);
    }

//    public HashMap<Integer,List<String>> getStatsMap() {
//        return statsMap;
//    }

//    public void setStatsMap(HashMap<Integer,List<String>> statsMap) {
//        this.statsMap = statsMap;
//    }
}
