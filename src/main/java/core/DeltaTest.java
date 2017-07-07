package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.config.PropertiesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import static core.common.ExtractorConstant.*;

/**
 * Created by stefanie on 12/01/2017.
 */
public class DeltaTest {

	private static final Logger logger = LoggerFactory.getLogger(DeltaTest.class);
	private int caseNum;

	public DeltaTest() {
		caseNum = 0;
	}

	private void traverseFolder(String folderPath) throws Exception {

		File folder = new File(folderPath);

		if (folder.exists()) {

			File[] fileList = folder.listFiles();

			if (fileList.length != 0) {
				for (File file : fileList) {
					if (file.isFile()) {
						if (file.getName().endsWith(FILE_SUFFIX)) {
							logger.info("[TestCase" + caseNum + "]: Start test case from file: " + file.getName());
							System.out.println("file path: " + file.getParent() + ";file name: " + file.getName());
							TestCase testCase = new TestCase(file.getParent(), file.getName());
							testCase.start();
							caseNum++;
						} else {
							// TODO
						}
					} else {
						traverseFolder(file.getAbsolutePath());
					}
				}
			} else {
				throw new FileNotFoundException("Folder has no file!");
			}
		} else {
			throw new FileNotFoundException("Folder not found!");
		}
	}

	public void run(String folderPath) throws Exception {
		traverseFolder(folderPath);
	}

	public static void main(String args[]) throws Exception {
		DeltaTest test = new DeltaTest();
		test.run("src/main/resources");
	}
}









