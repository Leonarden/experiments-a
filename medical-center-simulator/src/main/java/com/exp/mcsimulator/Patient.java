package com.exp.mcsimulator;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Patient implements Comparable<Patient>{
	private long id;
	private String name;
	private String address;
	private List<Message> messages = new LinkedList<Message>();
	private SortedSet<Action> actions = new TreeSet<Action>();
	private List<Treatment> treatments = new LinkedList<Treatment>();
//...should add medical files
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public int compareTo(Patient o) {
	    if(this.getId()>o.getId())
	    	return -1;
	    if(this.getId()<o.getId())
	    	return 1;
		return 0;
		//this.getMessages().get(0).compareTo(o.getMessages().get(0));
	}
	public SortedSet<Action> getActions() {
		return actions;
	}
	public void setActions(SortedSet<Action> actions) {
		this.actions = actions;
	}
	public List<Treatment> getTreatments() {
		return treatments;
	}
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	
	
	
}
