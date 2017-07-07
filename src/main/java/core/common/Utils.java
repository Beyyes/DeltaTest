package core.common;

import org.apache.commons.lang.StringUtils;

/**
 * Created by stefanie on 09/01/2017.
 */

public class Utils {

    public static int stringToInt(String str) throws Exception {

        int result;
        if (str.equals("")) {
            //TODO
            throw new Exception("Cannot convert null string.");
        }
        try {
            result = Integer.parseInt(StringUtils.strip(str));
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
            throw new Exception("Cannot convert string: " + str + " to int.");
        }
        return result;
    }
}
