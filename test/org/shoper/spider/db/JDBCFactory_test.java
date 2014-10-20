package org.shoper.spider.db;

import java.net.UnknownHostException;
import java.util.Date;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
public class JDBCFactory_test {
	private Mongo mg;
	private DB db;
	private DBCollection dbCollection;
	private Logger logger  = Logger.getLogger(JDBCFactory_test.class);
	@Before
	public void init() throws UnknownHostException {
		mg = new Mongo("127.0.0.1",27017);
		db = mg.getDB("shoper");
		if(db.authenticate("shoper", "shoper".toCharArray())){
			logger.error("username or password incorrect");
		}
		dbCollection = db.getCollection("test");
	}

	@Test
	public void test_connection() {
		TestPojo testPojo = new TestPojo();
		testPojo.setAge(10);
		testPojo.setBirthday(new Date());
		testPojo.setName("shoper");
//		File file = new File("D:\\databsae\\mongodb\\mongoStart.bat");
//		testPojo.setFile(file);
		DBObject dbobjct=(DBObject)JSON.parse(JSONArray.fromObject(testPojo).toString());
		dbCollection.insert(dbobjct);
	}

	@After
	public void test_close() {
		if (mg != null)
			mg.close();
		mg = null;
		db = null;
		dbCollection = null;
		System.gc();
	}
}
