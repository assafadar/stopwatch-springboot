package com.shiled.task.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shiled.task.beans.TimerRecord;
import com.shiled.task.controllers.ITimerRecordController;
@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping(value="/api/times")
/**
 * 
 * @author assaf
 * 
 * Timer record api for stopwatch application. 
 * Exceptions are handled by ApiExceptionHandler.
 * 
 */
public class TimerRecordsApi {
	@Autowired
	@Qualifier("timer_record_controller")
	private ITimerRecordController controller;
	
	/**
	 * 
	 * @return list of records inside response body
	 * @throws Exception
	 */
	@RequestMapping(value="",method=RequestMethod.GET,produces="application/json; charset=UTF-8")
	public ResponseEntity<List<TimerRecord>> getTimerRecords() throws Exception{
		List<TimerRecord> recoeds = controller.getTimeRecords();
		return new ResponseEntity<List<TimerRecord>>(recoeds,HttpStatus.OK);
	}
	/**
	 * Creates a new timer record in DB
	 * @param record
	 * @return the new record created in DB
	 * @throws Exception
	 */
	@RequestMapping(value="", method=RequestMethod.POST,consumes="application/json; charset=UTF-8")
	public ResponseEntity<TimerRecord> createNewTimeRecord
		(@RequestBody TimerRecord record) throws Exception{
		record =  this.controller.createTimerRecord(record);
		return new ResponseEntity<TimerRecord>(record,HttpStatus.OK);
	}
	
	/**
	 * Deletes a certain record in DB
	 * @param recordID
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTimerRecord(@PathVariable(value = "id") 
	String recordID) throws Exception{
		this.controller.removeRecord(Integer.parseInt(recordID));
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	/**
	 * Deletes all records in DB
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAllRecords() throws Exception{
		this.controller.removeAllRecords();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
