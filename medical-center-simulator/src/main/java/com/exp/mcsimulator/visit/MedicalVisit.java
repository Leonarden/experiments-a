package com.exp.mcsimulator.visit;

import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exp.mcsimulator.Action;
import com.exp.mcsimulator.Patient;
import com.exp.mcsimulator.Treatment;
import com.exp.mcsimulator.util.MUSequenceGenerator;
import com.exp.mcsimulator.util.MedicalUnitStatus;

public abstract class MedicalVisit {

	private Patient patient;
	Logger log = Logger.getLogger(this.getClass().getName());
	private MedicalUnitStatus medicalUnitStatus;
	private String[][] pathologies;
	private Map<String,Treatment> treatments;
	
	/**
	 * This is the class template method
	 */
	public void visit() {
		medicalUnitStatus = MedicalUnitStatus.VISIT_START;
		goToPlace();
		int inspec = inspectPatient();
		if(inspec==0)
			takeToHospital();
		else if(inspec==1 || inspec>1) {
			if(inspec==1)
			prescribeTreatment();
			else
			noMedicalAction();
		}
		goBackToCenter();
		
		medicalUnitStatus = MedicalUnitStatus.VISIT_DONE;	
	}

	public abstract void goToPlace(); //Different ways of going to a place depending on the cases
	
	public int inspectPatient() {
		int action = 0;
		Date d = new Date();
		Action a = new Action();
		a.setPatientId(patient.getId());
		
		a.setId(""+new MUSequenceGenerator().getNext());
		a.setDate(d);
		String mes = "Inspecting patient:"+ patient.getName();
		//we will simulate a random (0-3) action
		log.log(Level.ALL,mes );
		
		//we also take into account the priority previously registered (there should exist correlation)
		int pri = patient.getMessages().get(0).getPriority(); //
		
		double stat = ((Math.random()*100)%3);
		
		stat = (stat + pri)/2;
		// to select index for a pathologies+ pathology
		int k1;
		
		if(stat<1) {
			k1 = (int) (Math.random()*100)%pathologies[0].length;
			mes = mes + " Critic, Pathology:" + pathologies[0][k1];
			action = 0;
		}else if(stat>=1 && stat<2) {
			k1 = (int) (Math.random()*100)%pathologies[1].length;
			mes = mes + "Ill but not Critic, Pathology:" + pathologies[1][k1];
			action = 1;
		}else if(stat>=2 ) {
			k1 = (int) (Math.random()*100)%pathologies[2].length;
			mes = mes + " Not Critic and no fever, Pathology:" + pathologies[2][k1];
			action = 2;
		}
		
		log.log(Level.ALL,"Date" + a.getDate() +"" +mes);

		a.setDescription(mes);
		a.setType(action);
		
		patient.getActions().add(a);
		
		
		return action;
	}
	
	public void takeToHospital() {
		String distanceFromOriginStr = patient.getAddress().substring(patient.getAddress().lastIndexOf("-")+1,patient.getAddress().length());
		String m = "Taking patient :" + patient.getName() + " To the nearest available Hospital in a perimeter of:"+ distanceFromOriginStr+ " Km";
		//Simulate 
		
		
		
		log.log(Level.ALL,m);
		
	}
	
	public abstract void prescribeTreatment();
	
	
	public abstract void noMedicalAction(); //this could be a sanction or a data registration

	public void goBackToCenter() {
		String distanceFromOriginStr = patient.getAddress().substring(patient.getAddress().lastIndexOf("-")+1,patient.getAddress().length());
		String m = "Going back to Medical Unit Center :"+ distanceFromOriginStr+ " Km";
	//simulate trip
		log.log(Level.ALL,m);
		
	}
	
	
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicalUnitStatus getMedicalUnitStatus() {
		return medicalUnitStatus;
	}

	public void setMedicalUnitStatus(MedicalUnitStatus medicalUnitStatus) {
		this.medicalUnitStatus = medicalUnitStatus;
	}

	public Map<String, Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Map<String, Treatment> treatments) {
		this.treatments = treatments;
	}

	public String[][] getPathologies() {
		return pathologies;
	}

	public void setPathologies(String[][] pathologies) {
		this.pathologies = pathologies;
	}

	
	

}
