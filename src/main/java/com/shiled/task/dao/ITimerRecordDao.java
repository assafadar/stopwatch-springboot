package com.shiled.task.dao;

import java.util.List;

import com.shiled.task.beans.TimerRecord;

public interface ITimerRecordDao {
	List<TimerRecord> getTimeRecords()throws Exception;
	TimerRecord createTimerRecord(TimerRecord record) throws Exception;
	void removeRecord(int recordID)throws Exception;
	void removeAllRecords() throws Exception;
	TimerRecord getRecord(int recordID) throws Exception;
}
