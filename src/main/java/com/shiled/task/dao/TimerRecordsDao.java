package com.shiled.task.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.shiled.task.beans.TimerRecord;
import com.shiled.task.dao.hibernate.HibernateUtils;

/**
 * 
 * 
 * @author assaf
 * Hibernate dao layer uses as the app ORM 
 * communicating with the app's DB.
 * 
 * Each request takes a new hibernate session and closes it afterwards, possible also to create session pull.
 */
@Component("timer_record_dao")
public class TimerRecordsDao implements ITimerRecordDao{
	private Session session;
	/**
	 * SELECT * FROM times;
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TimerRecord> getTimeRecords()throws Exception{
		this.session = getNewSession();
		try {
			session.beginTransaction();
			String query = "from TimerRecord";
			List<TimerRecord> timeRecords =(List<TimerRecord>) 
					session.createQuery(query).list();
			session.getTransaction().commit();
			return timeRecords;
		}catch (HibernateException e) {
			throw new Exception("Hibernate failure,"
					+ "failed reading data from db: "+e.getMessage(),e);
		}finally {
			session.close();
		}
	}
	
	/**
	 * INSERT INTO times(minutes,seconds,millis) VALUES (record.getMinutes(),record.getSeconds(),record.getMillis();
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Override
	public TimerRecord createTimerRecord(TimerRecord record)throws Exception{
		Session session = getNewSession();
		try {
			session.beginTransaction();
			session.save(record);
			session.getTransaction().commit();
			return record;
		}catch (HibernateException e) {
			throw new Exception("Hibernate failure, "
					+ "failed creating new record: "+e.getMessage(),e);
		}finally {
			session.close();
		}
	}
	/**
	 * DELETE FROM times WHERE record_id = recordID;
	 * @param recordID
	 * @throws Exception
	 */
	@Override
	public void removeRecord(int recordID)throws Exception{
		Session session = getNewSession();
		try {
			TimerRecord timerRecord = new TimerRecord();
			timerRecord.setId(recordID);
			session.getTransaction().begin();
			session.remove(timerRecord);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			throw new Exception("Hibernate failure, "
					+ "failed to delete record: "+recordID+" dueto: "+e.getMessage(),e);
		}finally {
			session.close();
		}
	}
	/**
	 * TRUNCATES the table, faster than deleting all records.
	 * @throws Exception
	 */
	@Override
	public void removeAllRecords()throws Exception{
		Session session = getNewSession();
		try {
			session.getTransaction().begin();
			String sql = "TRUNCATE TABLE times";
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			throw new Exception("Failed deleting all records: "+e.getMessage(),e);
		}finally {
			session.close();
		}
	}
	@Override
	public TimerRecord getRecord(int recordID) throws Exception{
		Session session = getNewSession();
		try {
			session.getTransaction().begin();
			TimerRecord record = (TimerRecord)session.get(TimerRecord.class, recordID);
			session.getTransaction().commit();
			return record;
		}catch (HibernateException e) {
			throw new Exception("Hibernate failed fetching recod: "
					+ ""+recordID+" due to: "+e.getMessage());
		}finally {
			session.close();
		}
	}
	
	/**
	 * Asks the hibernate session factory for a new session
	 * @return
	 */
	private Session getNewSession() {
		return HibernateUtils.getSessionFactory().openSession();
	}

}
