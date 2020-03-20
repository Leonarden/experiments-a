package com.exp.mcsimulator.visit;

import java.util.LinkedList;
import java.util.List;

import com.exp.mcsimulator.Message;
import com.exp.mcsimulator.Patient;

public class AverageCapacityPreMedicalVisit extends PreMedicalVisit {

	@Override
	public void receiveMessages() {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchPatientsRecords() {
	   List<Message> messages = this.getMessages();
	   List<Patient> repository = this.getPatientsRepository();
	   List<Patient> patients = this.getPatients();
	   if(patients==null)
		   patients = new LinkedList<Patient>();
	   for(Message m:messages) {
		   for(Patient p:repository) {
			   if(m.getPatientId() == p.getId()) {
				   if(p.getMessages()==null)
					   p.setMessages(new LinkedList<Message>());
				   p.getMessages().add(m);
				   patients.add(p);
				   break;
			   }
		   }
		   
	   }
	   this.setPatients(patients);
	}

}
