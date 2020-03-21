package com.exp.mcsimulator.visit;

import java.util.Iterator;
import java.util.logging.Level;

import com.exp.mcsimulator.Action;
import com.exp.mcsimulator.Patient;
import com.exp.mcsimulator.Treatment;

public class ShortInfoPostMedicalVisit extends PostMedicalVisit {

	@Override
	public void generateReport() {
		int cp = 0;
		int ca,ct;
		Patient p = null;
		Action a = null;
		Treatment t = null;
		log.log(Level.ALL,"Patients visited: ");
	for(int i=0;i<this.getPatients().size();i++) {
		
		p = this.getPatients().get(i);
		if(p==null) {
			log.log(Level.ALL,"Patient :" + i + " is null");
			continue;
		}
		log.log(Level.ALL, i + ":  Patient name: " + p.getName() + " address: "+ p.getAddress());
		log.log(Level.ALL,"Actions taken: ");
		int j = 0;
		Iterator<Action> iter = p.getActions().iterator(); 
		while(iter.hasNext()) {
			a = iter.next();
			log.log(Level.ALL, j + ": Action Id:"+ a.getId() + " Description: " + a.getDescription());
			j++;
		 }
		
	}
		
	}

}
