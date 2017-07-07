package core.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import static core.common.ExtractorConstant.*;

import core.common.Utils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Created by stefanie on 06/01/2017.
 */

public class XmlReader {

    File xmlFile = null;
    FileInputStream fis = null;
    SAXReader saxReader = null;
    Document doc = null;

    public XmlReader(String filepath, String filename) {

        xmlFile = new File(filepath, filename);

        try {
            fis = new FileInputStream(xmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        saxReader = new SAXReader();
        try {
            doc = saxReader.read(fis);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public List getDatabaseConfigs() throws Exception {

        List l = doc.selectNodes(DATABASE_CONFIG_PATH);
        return l;
    }

    public List getThreadConfigs() throws Exception {

        List threads = doc.selectNodes(THREAD_NUM_PATH);
        int num = Utils.stringToInt(((Element)threads.get(0)).attributeValue(THREAD_NUM_LABEL));

        List threadList = doc.selectNodes(THREAD_CONFIG_PATH);

        if (num != threadList.size()) {
            throw new Exception("Acquire " + num + " threads, but found " + threadList.size() + " threads.");
        }
        return threadList;
    }

//public static void main(String[] args) throws Exception {
//        XmlReader reader = new XmlReader("src/main/resources", "config_template.xml");
//        reader.getThreadConfigs();
//        System.out.println();
//    }

}


