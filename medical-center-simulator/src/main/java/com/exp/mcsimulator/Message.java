package com.exp.mcsimulator;

import java.io.Serializable;
import java.util.Date;

//message sent by a patient
public class Message implements Serializable,Comparable<Message>{

	private int priority;
	private String content;
	private Date date;
	private long patientId;
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public int compareTo(Message o) {
		if(this.getPriority() < o.getPriority())
			return 1;
		else
		if(this.getPriority() > o.getPriority())
			return -1;
		else
			return 0;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
