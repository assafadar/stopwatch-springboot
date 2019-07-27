package com.shiled.task.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author assaf
 * TimerRecord entity bean.
 */
@Entity
@Table(name="times")
public class TimerRecord {
	
	private int id;
	private String minutes;
	private String seconds;
	private String millis;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="record_id",unique=true, nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="minutes",nullable=false)
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
	@Column(name="seconds")
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String secods) {
		this.seconds = secods;
	}
	
	@Column(name="millis")
	public String getMillis() {
		return millis;
	}
	public void setMillis(String millis) {
		this.millis = millis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimerRecord other = (TimerRecord) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
