package com.exp.mcsimulator.visit;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exp.mcsimulator.Patient;
import com.exp.mcsimulator.util.MedicalUnitStatus;

public abstract class PostMedicalVisit {
	static {
	      System.setProperty("java.util.logging.config.file",
	              "src/main/resources/loggin-bas.properties");
	      //must initialize loggers after setting above property
	   
	  }
	
	Logger log = Logger.getLogger(this.getClass().getName());
	List<Patient> patients = new LinkedList<Patient>();
	MedicalUnitStatus medicalUnitStatus;
	
	/**
	 * Template method
	 */

	public void postVisit() {
		medicalUnitStatus = MedicalUnitStatus.POSTVISIT_START;
		
		generateReport();
		
		medicalUnitStatus = MedicalUnitStatus.POSTVISIT_DONE;
		log.log(Level.ALL,"Post visit done");
		notifyStatus();
	}
	
	
	public void notifyStatus() {
		medicalUnitStatus = MedicalUnitStatus.UNIT_FREE;	
	}
	
	public abstract void generateReport();
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	public MedicalUnitStatus getMedicalUnitStatus() {
		return medicalUnitStatus;
	}
	public void setMedicalUnitStatus(MedicalUnitStatus medicalUnitStatus) {
		this.medicalUnitStatus = medicalUnitStatus;
	}


}
