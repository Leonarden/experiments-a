package com.exp.mcsimulator;

import java.util.Date;

public class Action implements Comparable<Action>{
private String   id;
private long patientId;
private Date date;
private String description;
private int type; 

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public long getPatientId() {
	return patientId;
}
public void setPatientId(long patientId) {
	this.patientId = patientId;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int compareTo(Action o) {
	return this.getDate().compareTo(o.getDate());
}
public int getType() {
	return type;
}
/**
 * Type of action taken when inspecting the patient
 * @param type
 */
public void setType(int type) {
	this.type = type;
}




}
