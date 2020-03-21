package com.exp.mcsimulator.visit;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exp.mcsimulator.Message;
import com.exp.mcsimulator.Patient;
import com.exp.mcsimulator.util.MedicalUnitStatus;

public abstract class PreMedicalVisit {
	
	private List<Patient> patientsRepository; 
	private List<Patient> patients = new LinkedList<Patient>();
	private List<Message> messages;
	private MedicalUnitStatus medicalUnitStatus;
	static {
	      System.setProperty("java.util.logging.config.file",
	              "src/main/resources/loggin-bas.properties");
	      //must initialize loggers after setting above property
	   
	  }
	
	Logger log = Logger.getLogger(this.getClass().getName());

	
	/* 0:max priority keywords, 1:less priority keywords... */
	String[][] emergencyKWords = {{"can't breath","bleeding","emergency","poisoned"},
									{"can't move","fever","vomit","can't think clear"},
									{"flu","nausea","stomach","can stand up"}
									};
	
	Comparator<Patient> comparByPriority = new Comparator<Patient>() {

		public int compare(Patient o1, Patient o2) {
			int p1=-1;
			int p2=-1;
			try{ 
				p1 = o1.getMessages().get(0).getPriority();
			}catch(Exception ex) {
				ex.printStackTrace();
				p1 = 100;
			}
			try{ 
				p2 = o2.getMessages().get(0).getPriority();
			}catch(Exception ex) {
				ex.printStackTrace();
				p2 = 100;
			}
			
			if(p1>p2)
				return 1;
			if(p1<p2)
				return -1;
			
			return 0;
		}
	
	
	
	
	}; 
	
	Comparator<Patient> comparByDistance = new Comparator<Patient>() {

		public int compare(Patient o1, Patient o2) {
			int d1=-1;
			int d2=-1;
			try{ 
			   d1 =	Integer.valueOf(o1.getAddress().substring(o1.getAddress().lastIndexOf("-")+1,o1.getAddress().length())).intValue();
				
			}catch(Exception ex) {
				ex.printStackTrace();
				d1 = 1000;
			}
			try{ 
				d2 = Integer.valueOf(o2.getAddress().substring(o2.getAddress().lastIndexOf("-")+1,o2.getAddress().length())).intValue();
			}catch(Exception ex) {
				ex.printStackTrace();
				d2 = 1000;
			}
			
			if(d1>d2)
				return 1;
			if(d1<d2)
				return -1;
			
			return 0;
		}
	
	
	
	
	}; 
	
	
	
	
	/**
	 * Template method
	 */
	public void preVisit() throws Exception {
		medicalUnitStatus = MedicalUnitStatus.PREVISIT_START;
		receiveMessages();
		asignPriority();
		searchPatientsRecords();
		scheduleVisit();
		medicalUnitStatus = MedicalUnitStatus.PREVISIT_DONE;
	   log.log(Level.ALL, "Previsit Done");
	}
	/**
	 * 
	 */
	public abstract void receiveMessages();
	/**
	 * This implementation classifies by keywords  message content
	 */
	public  void asignPriority() {
		log.log(Level.ALL,"Asigning priority to messages");
		for(Message m: messages) {
			for(int i = emergencyKWords.length-1;i>=0;i--) { //we decide to evaluate first the less critical priority 
				String[] keywords = emergencyKWords[i];
				for(int j=0;j<keywords.length; j++) {
				if(	m.getContent().contains(keywords[j]))
				 m.setPriority(i);
				}		
			}	
			if(m.getPriority()<0) {
				m.setPriority(emergencyKWords.length); //if no priority has been assigned then  lastpriority +1
				
			}
			log.log(Level.INFO," Message " + m.getContent()+ ",Priority: " + m.getPriority());
		}
	}

	/**
	 * This method will search patients from repository (in this case a list), it will use message.patientId
	 * It will also add the message to the messages list inside patient object 
	 * It generates the matching message-patient list
	 */
	public abstract void searchPatientsRecords();
	/**
	 * Sorts patient list in 2 ways first by priority second by shortest distance
	 */
	public void scheduleVisit() {

		List<Patient> sortedA = new LinkedList<Patient>();
		Collections.sort(patients,comparByPriority);
		Patient p1 = patients.get(0);
		Patient p2;
		boolean samepriority = false;
		List<Patient> sublist = new LinkedList<Patient>();
		
		if(patients.size()>1)
			sublist.add(p1);
		else
			sortedA.add(p1);
		
		for(int i=1;i<patients.size();i++) {
			p2 = patients.get(i);
			if(comparByPriority.compare(p1, p2)==0) {
				sublist.add(p2);
				samepriority = true;
			}else {
				//sublist was recreated : elements in 
				//patients.list are sorted by priority: here is the case 0-0-0-*1-2*
				if(sublist.size()==0)
					sublist.add(p1);
				
			   // we reach the last element in the case  000111333 *45*
				if(i==patients.size()-1 )
		        	sublist.add(p2);
		        samepriority = false;	
		 					
			   }
		
			
			if(sublist.size()>1 && samepriority) 
				Collections.sort(sublist,comparByDistance);
		    if(sublist.size()>0) {
		    	for(Patient p:sublist)
		    		sortedA.add(p);
		    	
		    }
		    
			p1 = p2;
			sublist = new LinkedList<Patient>();
		}
		
		patients = sortedA;
		
	}

	

	
	
	
	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<Patient> getPatientsRepository() {
		return patientsRepository;
	}
	public void setPatientsRepository(List<Patient> patientsRepository) {
		this.patientsRepository = patientsRepository;
	}
	public MedicalUnitStatus getMedicalUnitStatus() {
		return medicalUnitStatus;
	}
	public void setMedicalUnitStatus(MedicalUnitStatus medicalUnitStatus) {
		this.medicalUnitStatus = medicalUnitStatus;
	}
	
	
	
	
	
	
	
	
	
}
