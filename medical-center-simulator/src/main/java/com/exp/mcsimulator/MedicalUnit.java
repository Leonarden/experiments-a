package com.exp.mcsimulator;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exp.mcsimulator.util.MedicalUnitStatus;
import com.exp.mcsimulator.visit.AverageCapacityPreMedicalVisit;
import com.exp.mcsimulator.visit.BigCapacityPreMedicalVisit;
import com.exp.mcsimulator.visit.DeepInfoPostMedicalVisit;
import com.exp.mcsimulator.visit.MedicalVisit;
import com.exp.mcsimulator.visit.PostMedicalVisit;
import com.exp.mcsimulator.visit.PreMedicalVisit;
import com.exp.mcsimulator.visit.ShortInfoPostMedicalVisit;
import com.exp.mcsimulator.visit.SmallUltraFastMedicalVisit;
/*
 * It simulates a medicalUnit that has to perform medical visits
 *  @see method run
 *  
 *  It adapts classes PreMedicalVisit, MedicalVisit, PostMedicalVisit
 *  @see methods preVist(), visit(), postVisit()
 * 
 */
public class MedicalUnit implements Runnable {
private String id;
private PreMedicalVisit preMedicalVisit;
private MedicalVisit medicalVisit;
private PostMedicalVisit postMedicalVisit;
private MedicalUnitStatus medicalUnitStatus;
private List<Patient> patRepository;
List<Patient> patients;
List<Message> messages;
String[][] pathologies;
Map<String,Treatment> treatments;
private static int MAXPATIENTS = 150;
private static int MAXMESSAGES = 100;
private static int MAXDISTANCE = 50; //miles
 
static {
    	System.setProperty("java.util.logging.config.file",
            "src/main/resources/loggin-bas.properties");
    //must initialize loggers after setting above property

}
Logger log = Logger.getLogger(MedicalUnit.class.getName()); 
 

public MedicalUnit(String id) {

	this.id = id;
	medicalUnitStatus = MedicalUnitStatus.UNIT_FREE;
	
}

public void preVisit() throws Exception {
		
		//	if(messages.size()>2/3*MAXMESSAGES)
		if(true)	
		preMedicalVisit = new BigCapacityPreMedicalVisit();
			else
				preMedicalVisit = new AverageCapacityPreMedicalVisit();
			
			preMedicalVisit.setPatientsRepository(patRepository);
			preMedicalVisit.setMedicalUnitStatus(medicalUnitStatus);
			preMedicalVisit.setMessages(messages);
			
			
			preMedicalVisit.preVisit();
			
			log.log(Level.ALL,"Pre visit done, status:" + preMedicalVisit.getMedicalUnitStatus());
			
 }

public void visit() throws Exception {
	 
	 //if(preMedicalVisit.getPatients().size()> MAXPATIENTS/2)
		if(true)	
	 medicalVisit = new SmallUltraFastMedicalVisit();
		else
			medicalVisit = new SmallUltraFastMedicalVisit();
		
		medicalVisit.setMedicalUnitStatus(preMedicalVisit.getMedicalUnitStatus());
		
		medicalVisit.setTreatments(treatments);
		medicalVisit.setPathologies(this.pathologies);
		//The real list of patients onche filtered and scheduled
		patients = preMedicalVisit.getPatients();
		for(Patient p:patients) {
			medicalVisit.setPatient(p);
			medicalVisit.visit();
			log.log(Level.ALL,"Visited patient : " + p.getName() +" address:" +p.getAddress());
		
		}


	 
 }
 /**
  * 
  * @throws Exception
  */
 public void postVisit() throws Exception {
//	 if(patients.size()> 2/3*MAXPATIENTS)  arbitrary criteria
	if(true)	
	 postMedicalVisit = new ShortInfoPostMedicalVisit();
		else
			postMedicalVisit = new DeepInfoPostMedicalVisit();
		
		postMedicalVisit.setPatients(patients);
		postMedicalVisit.setMedicalUnitStatus(medicalVisit.getMedicalUnitStatus());
		postMedicalVisit.postVisit();	
		log.log(Level.ALL,"Post medical visit done with status: " + postMedicalVisit.getMedicalUnitStatus());
 
 }
/**
 *  
 */
public void run() {

	try {
		preVisit();
		visit();
		postVisit();
		
	}catch(Exception ex) {
		
		ex.printStackTrace();
	}
}
	



public PreMedicalVisit getPreMedicalVisit() {
	return preMedicalVisit;
}


public void setPreMedicalVisit(PreMedicalVisit preMedicalVisit) {
	this.preMedicalVisit = preMedicalVisit;
}


public MedicalVisit getMedicalVisit() {
	return medicalVisit;
}


public void setMedicalVisit(MedicalVisit medicalVisit) {
	this.medicalVisit = medicalVisit;
}


public PostMedicalVisit getPostMedicalVisit() {
	return postMedicalVisit;
}


public void setPostMedicalVisit(PostMedicalVisit postMedicalVisit) {
	this.postMedicalVisit = postMedicalVisit;
}


public MedicalUnitStatus getMedicalUnitStatus() {
	return medicalUnitStatus;
}


public void setMedicalUnitStatus(MedicalUnitStatus medicalUnitStatus) {
	this.medicalUnitStatus = medicalUnitStatus;
}

public List<Patient> getPatRepository() {
	return patRepository;
}

public void setPatRepository(List<Patient> patRepository) {
	this.patRepository = patRepository;
}

public List<Message> getMessages() {
	return messages;
}

public void setMessages(List<Message> messages) {
	this.messages = messages;
}

public Map<String, Treatment> getTreatments() {
	return treatments;
}

public void setTreatments(Map<String, Treatment> treatments) {
	this.treatments = treatments;
}

public String getId() {
	// TODO Auto-generated method stub
	return id;
}

public String[][] getPathologies() {
	return pathologies;
}

public void setPathologies(String[][] pathologies) {
	this.pathologies = pathologies;
}




}
