package com.exp.mcsimulator.visit;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

import com.exp.mcsimulator.Message;
import com.exp.mcsimulator.Patient;
import com.exp.mcsimulator.util.MedicalUnitStatus;

public class BigCapacityPreMedicalVisit extends PreMedicalVisit {

	@Override
	public void receiveMessages() {
		if(this.getMessages()==null || this.getMessages().size()==0) {
			this.setMedicalUnitStatus(MedicalUnitStatus.PREVISIT_ERROR);
			log.log(Level.ALL,"No messages received");
		}else {
			log.log(Level.ALL,"Received " + this.getMessages().size() + " messages to process");
			log.log(Level.ALL,"Filtering invalid messages");
			filterInvalidMessages();
		}

	}

	@Override
	public void searchPatientsRecords() {
	   List<Message> messages = this.getMessages();
	   List<Patient> repository = this.getPatientsRepository();
	   List<Patient> patients = this.getPatients();
	   Patient p = null;
	   if(patients==null)
		   patients =  Collections.synchronizedList(new LinkedList<Patient>());
	   
	   Collections.sort(repository); //for further binarySearch
	   
	   for(Message m:messages) {
		   p = new Patient();
		   p.setId(m.getPatientId());
		   p = searchPatient(p,repository);
		   if(p==null) {
			   log.log(Level.ALL,"Patient not found for message: " + m.getContent());
			   continue;
		   	}
			   if(p.getMessages()==null)
			   p.setMessages(new LinkedList<Message>());
		   p.getMessages().add(m);
		   patients.add(p);
		      
	   }
	   
	   this.setPatients(patients);
	}

	
	Patient searchPatient(Patient p,List<Patient> patients) {
		
		Comparator<Patient> comp = new Comparator<Patient>() {

			public int compare(Patient o1, Patient o2) {
				if(o1.getId()>o2.getId())
					return -1;
				if(o1.getId()<o2.getId())
					return 1;
				
				return 0;
			}
			
		};
				 
		int pos = Collections.binarySearch(patients, p, comp);
		if(pos>=0)
		return patients.get(pos);	
		else
			return null;
	}
	void filterInvalidMessages(){
		List<Message> mgs = this.getMessages();
	
		Comparator<Message> comparator = new Comparator<Message>() {

			public int compare(Message o1, Message o2) {
				
				if(o1.getContent().equalsIgnoreCase(o2.getContent()))
				return 0;
				else
				 return	o1.getContent().compareTo(o2.getContent());
			
			}
			
		};
	
		SortedSet<Message> mgset = new TreeSet<Message>(comparator);
		int inv = 0;
	
	    boolean skip = false;
		for(Message m: mgs) {
			//basic veryfication
			if(m.getPatientId()<0 || m.getPatientId()>=this.getPatientsRepository().size()) {
				log.log(Level.ALL,"Invalid message " + m.getContent());
				skip = true;
			}else if(m.getContent().contains("I made a wrong call") || //add content restrictions
					false ||
					false ) {
				
						skip = true;
						log.log(Level.ALL,"Message filtered by content: " +  m.getContent());
			}
				
			if(!skip) {
				mgset.add(m);
			}else
				skip = false;
		
		}
		inv = mgs.size() - mgset.size();
		
		log.log(Level.ALL,"Invalid messages number: " + inv);
		
		
		mgs = Collections.synchronizedList(new LinkedList<Message>(mgset));
		
		this.setMessages(mgs);
		
		
	
	}
	
}
