package com.shiled.task.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.shiled.task.beans.TimerRecord;
import com.shiled.task.dao.hibernate.HibernateUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimerRecordControllerTest {
	
	private static TimerRecordController controller;
	private int testRecordID;
	private static final String TEST_MILLIS = "12";
	private static final String TEST_SECONDS = "25";
	private static final String TEST_MINUTES = "10";
	
	@BeforeClass
	public static void setUp() {
		try {
			HibernateUtils.init();
			controller = new TimerRecordController();
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed initializing app");
		}
	}
	
	
	@AfterClass
	public static void cleanUp() {
		try {
			HibernateUtils.shutDown();
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed closing test app");
		}
	}
	
	@Test
	public void createRecord() {
		try {
			TimerRecord record = new TimerRecord();
			record.setMillis(TEST_MILLIS);
			record.setSeconds(TEST_SECONDS);
			record.setMinutes(TEST_MINUTES);
			record = controller.createTimerRecord(record);
			this.testRecordID = record.getId();
			assertNotEquals(0, this.testRecordID);
			assertEquals(TEST_MILLIS, record.getMillis());
			assertEquals(TEST_SECONDS, record.getSeconds());
			assertEquals(TEST_MINUTES, record.getMinutes());
			getAllRecords();
			getRecord();
			deleteRecord();
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed creating test record: "+e.getMessage());
		}
	}
	
	public void getAllRecords() {
		try {
			List<TimerRecord> records = controller.getTimeRecords();
			assertTrue(records.size() > 0);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed fetching all records: "+e.getMessage());
		}
	}
	
	public void getRecord() {
		try {
			TimerRecord record = controller.getRecord(testRecordID);
			assertNotNull(record);
			assertEquals(TEST_MILLIS, record.getMillis());
			assertEquals(TEST_MINUTES, record.getMinutes());
			assertEquals(TEST_SECONDS, record.getSeconds());
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed getting test record: "+e.getMessage());
		}
	}
	
	public void deleteRecord() {
		try {
			controller.removeRecord(testRecordID);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed deleting test record: "+e.getMessage());
		}
	}
	@Test
	public void deleteAllRecords() {
		try {
			TimerRecord record = new TimerRecord();
			record.setMillis(TEST_MILLIS);
			record.setSeconds(TEST_SECONDS);
			record.setMinutes(TEST_MINUTES);
			controller.createTimerRecord(record);
			controller.removeAllRecords();
			List<TimerRecord> records = controller.getTimeRecords();
			assertEquals(records.size(), 0);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Failed deleting all records: "+e.getMessage());
		}
	}

}
