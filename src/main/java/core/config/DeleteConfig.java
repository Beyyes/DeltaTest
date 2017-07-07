package core.config;

import action.ActionType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by stefanie on 09/01/2017.
 */

public class DeleteConfig extends ActionConfig{

    public DeleteConfig(int id) {
        super(id, ActionType.DELETE);
    }

    public DeleteConfig(int id, int delay, String desc) {
        super(id, ActionType.DELETE, delay, desc);
    }
}