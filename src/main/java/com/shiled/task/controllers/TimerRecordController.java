package com.shiled.task.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.shiled.task.beans.TimerRecord;
import com.shiled.task.dao.ITimerRecordDao;
import com.shiled.task.dao.TimerRecordsDao;

/**
 * 
 * @author assaf
 *
 *Timer records controller provides data validation layer for incoming requests.
 */
@Component("timer_record_controller")
public class TimerRecordController implements ITimerRecordController{
	@Autowired
	@Qualifier("timer_record_dao")
	private ITimerRecordDao dao;
	
	
	public TimerRecordController() {
		this.dao = new TimerRecordsDao();
	}
	/**
	 * Retrieves all records from db, no actual validation needed.
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TimerRecord> getTimeRecords()throws Exception{
		return this.dao.getTimeRecords();
	}
	
	/**
	 * Validates new record details -> requires no nulls
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Override
	public TimerRecord createTimerRecord(TimerRecord record) throws Exception{
		try {
			/**
			 * Sorting all values in a list makes sure that once we will check for nun null elements
			 * We will check for both json keys as sent from client and values
			 * In case a missing key than the object.getAtt will result also in null.
			 */
			List<String> attributes = Arrays.asList(record.getMinutes()
					,record.getSeconds(),record.getMillis());
			for(String att : attributes) {
				Objects.requireNonNull(att);
			}
			/**
			 * In case the record is valid creating a new record in DB and returns it.
			 */
			record = this.dao.createTimerRecord(record);
			return record;
		}catch (NullPointerException e) {
			throw new IllegalArgumentException("Object has null keys");
		}
	}

	/**
	 * Validates that the recordID actual exists in the DB before removing it.
	 * 
	 * @param recordID
	 * @throws Exception
	 */
	@Override
	public void removeRecord(int recordID)throws Exception {
		if(recordID == 0) {
			throw new IllegalArgumentException("Record id cannot be 0");
		}
		TimerRecord record = this.dao.getRecord(recordID);
		if(record == null) {
			throw new IllegalArgumentException("Invalid recordID");
		}
		this.dao.removeRecord(recordID);
	}
	
	/**
	 * removes all records from db.
	 * @throws Exception
	 */
	@Override
	public void removeAllRecords() throws Exception{
		this.dao.removeAllRecords();
	}
	@Override
	public TimerRecord getRecord(int recordID) throws Exception{
		return this.dao.getRecord(recordID);
	}
}
