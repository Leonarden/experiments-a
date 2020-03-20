package com.exp.mcsimulator;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.exp.mcsimulator.util.MedicalUnitStatus;

import junit.framework.TestSuite;

public class MedicalUnitTest1 {

	private static int MAXPATIENTS = 10;
	private static int MAXMESSAGES = 10;
	private static int MAXDISTANCE = 50; //Km
	private  boolean configured = false;
	private MedicalUnit medicalUnit;

	static {
		System.setProperty("java.util.logging.config.file",
				"src/main/resources/loggin-bas.properties");
		//must initialize loggers after setting above property

	}

	Logger  log = Logger.getLogger(MedicalUnitTest1.class.getName());




	
	/**
	 * Test Configure and Run
	 */
	@Test
	public void testConfigureRunMedicalUnit()
	{

		try {
			medicalUnit = new MedicalUnit("TEST-1");
			assertTrue(medicalUnit!=null);
			///Initial medicalUnit status
			medicalUnit.setMedicalUnitStatus(MedicalUnitStatus.UNIT_STOPPED);

			medicalUnit.setPatRepository(this.generatePatientRepository());
			assertTrue(medicalUnit.getPatRepository().size()>0);
			medicalUnit.setMessages(this.generateMessages());
			assertTrue(medicalUnit.getMessages().size()>0);

			medicalUnit.setTreatments(this.generateTreatments());
			assertTrue(medicalUnit.getTreatments().size()>0);

			medicalUnit.setPathologies(this.generatePathologies());

			assertTrue(medicalUnit.getPathologies().length>0);

			configured = true;
			assertTrue(medicalUnit!=null);
			assertTrue(configured);
			medicalUnit.run();
			
		}catch(Exception ex) {

			log.log(Level.SEVERE,"Configure and Run MedicalUnit failed");
			ex.printStackTrace();
		}



	}

	List<Patient> generatePatientRepository() {
		List<Patient> repository = Collections.synchronizedList(new LinkedList<Patient>());
		int numOf = (int)(Math.random()*500)%MAXPATIENTS;
		String[] names = {"Sarah","Thomas","Alice","John","Mary","Annie"};
		String[] addresses = {"Pitchburg ST","Alamo ST","Space ST","Green ST","Colombus ST","Ocean ST","Holmes ST"};
		Patient p;
		int dist,idx;
		for(int i=0;i<numOf;i++) {
			p = new Patient();
			p.setId(i);
			dist = (int)(Math.random()*100)%MAXDISTANCE;
			idx = (int)(Math.random()*100)%addresses.length;		
			p.setAddress(addresses[idx]+","+i + " -"+dist);
			idx = (int)(Math.random()*100)%names.length;
			p.setName(names[idx] +" the " + i + " th");

			repository.add(p);
		}
		return repository;
	}

	List<Message> generateMessages() {
		String[] contents = {"It's an emergency I can't move I think I've got flu","I think I've got fever and stomach pain","I've fallen down and I'm bleeding","I don't feel well but I can stand up, I have anxiety"};
		int r1=0,r2=0;
		List<Message> messages = Collections.synchronizedList(new LinkedList<Message>());
		Message m = null;
		r1 = (int)(Math.random()*200)% MAXMESSAGES;
		for(int i=0;i<r1;i++) {
			m = new Message();
			r2 = (int)(Math.random()*100)% contents.length;
			m.setDate(new Date());
			m.setPatientId(i);
			m.setPriority(-1);
			m.setContent(contents[r2] +" for patient ID:"+ i);
			messages.add(m);
		}
		return messages;
	}
	/**
	 * Fills treatments Map, "the content is only illustrative"
	 */
	SortedMap<String,Treatment> generateTreatments() {
		String adv = ".Visit the doctor if persits";
		String[][] treatmStr = {{"flu","stomachache-dyarrea","anxiety"},
				{"Anti-flu meds"+adv,"Stomach's trouble medicaments"+adv,"Stress reduction medicaments"+adv}};
		Treatment tr = null;
		SortedMap<String,Treatment> treatments = new TreeMap<String,Treatment>();
		log.log(Level.ALL,"Generating treatments:");

		for(int i=0;i<treatmStr[0].length;i++) {
			tr = new Treatment();
			tr.setDescription(treatmStr[1][i]);
			treatments.put(treatmStr[0][i],tr );
		}
		log.log(Level.ALL,"Treatments generated #: " + treatments.keySet().size());

		return treatments;

	}
	/***
	 * 
	 */
	String[][] generatePathologies() {
		String[][]diag = {
				{"hemorragy","diabetes","cancer"},
				{"depression","stomatache","dyarrea"},
				{"flu","fever","vomit","axiety","cold"}
		};
		return diag;
	} 

}
