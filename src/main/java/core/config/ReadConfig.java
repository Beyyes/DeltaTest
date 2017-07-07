package core.config;

import action.ActionType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by stefanie on 09/01/2017.
 */

public class ReadConfig extends ActionConfig{

    public ReadConfig(int id) {
        super(id, ActionType.READ);
    }

    public ReadConfig(int id, int delay, String desc) {
        super(id, ActionType.READ, delay, desc);
    }

}
