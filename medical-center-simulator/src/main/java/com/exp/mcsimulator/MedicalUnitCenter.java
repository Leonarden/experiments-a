package com.exp.mcsimulator;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exp.mcsimulator.util.MedicalUnitStatus;
/*
 * Main class
 * Manages runnable MedicalUnits
 * 
 * Generates random data for every MedicalUnit
 * Launches all medicalUnit as a thread and returns its associated ThreadGroup
 *
 * 
 * 
 * 
 */
public class MedicalUnitCenter {
	
	List<MedicalUnit> medicalUnits;
	List<Boolean> configured;
	List<Boolean> launched;
	ThreadGroup muThreadGroup;
	
	private static int MAXPATIENTS = 150;
	private static int MAXMESSAGES = 100;
	private static int MAXDISTANCE = 50; //Km
	
	static {
	      System.setProperty("java.util.logging.config.file",
	              "src/main/resources/loggin-bas.properties");
	      //must initialize loggers after setting above property
	   
	  }
	
	Logger  log = Logger.getLogger(MedicalUnitCenter.class.getName());

	
	public MedicalUnitCenter() {
		medicalUnits = new LinkedList<MedicalUnit>();
		configured = new LinkedList<Boolean>();
		launched = new LinkedList<Boolean>();
	}
	/**
	 * Adds a Runnable MedicalUnit to list
	 * @param unit
	 * @return list's size
	 */
	public int addMedicalUnit(MedicalUnit unit) {
		medicalUnits.add(unit);
		return medicalUnits.size();
	}

	/**
	 * sets initial parameters to medical unit at position "pos" in the list
	 * @param pos
	 * @return
	 * @throws Exception
	 */
   private int configure(int pos) throws Exception {
		MedicalUnit medU = null;
		try {
		 medU = medicalUnits.get(pos);
		
		 if(medU == null) throw new Exception("Non existing Medical Unit to configure");
		//Initial medicalUnit status
		medU.setMedicalUnitStatus(MedicalUnitStatus.UNIT_STOPPED);
		
		medU.setPatRepository(this.generatePatientRepository());
		medU.setMessages(this.generateMessages());
		medU.setTreatments(this.generateTreatments());
		medU.setPathologies(this.generatePathologies());
		
		configured.add(pos,Boolean.TRUE);
		launched.add(pos,Boolean.FALSE);
		return 0;
		
		}catch(Exception ex) {
			configured.add(pos,Boolean.FALSE);
		    log.log(Level.SEVERE,"Configure MedicalUnit at position: " +  pos + "failed");
			throw new Exception(ex.getMessage()+":::MedicalUnit:"+ medU+ "failed to configure");
		}
	
	}
	/**
	 * Set's initial parameters to all medicalUnits 
	 * 
	 * @throws Exception
	 */
	public void configureAll()throws Exception {
		for(int i=0;i<medicalUnits.size();i++) {
			configure(i);
		}
	}
	
	/**
	 * Creates a thread from the runnable MedicalUnit at position "pos"
	 * @return Thread created
	 * 
	 */
	
	 private Thread launch(int pos,ThreadGroup tg)  {
	   
		MedicalUnit medu = null;
		Thread t = null;
		try {
		 medu = medicalUnits.get(pos);
		if(medu == null) throw new Exception("Non existing Medical Unit to lauch");
	
		t = new Thread(tg,medu);
		
		t.setName(medu.getId());
		
		t.start();
		launched.add(Boolean.TRUE);
		
		}catch(Exception ex) {
			launched.add(pos,Boolean.FALSE);
			log.log(Level.SEVERE,"LAUNCH MedicalUnit at position: " +  pos + "failed");
			ex.printStackTrace();
		}
		
		return t;
	}
	/**
	 * launch of all runnable medicalUnits and return the ThreadGroup associated at all threads created
	 * @param gname :ThreadGroup name
	 * @return
	 */
	public ThreadGroup launchAll(String gname) {
		//
		
		ThreadGroup threadG = new ThreadGroup(gname);
		
		for(int i=0;i<medicalUnits.size();i++) {
		
			if(configured.get(i) && ! launched.get(i)) {
				Thread t = launch(i,threadG);
				
			}else {
				log.log(Level.ALL,"Couldn't launch task: "+ this.medicalUnits.get(i).getId());
			}		
		}	
		this.muThreadGroup = threadG;		
				
		return threadG;
		
	}
	

/*
 * 
 * 
 *
 * 
 * 
 * 
 * */

public static void main(String[]args) {
	
	int stat = -1;

	MedicalUnitCenter mucenter = new MedicalUnitCenter();
	
	
	MedicalUnit medicalUnit1 = new MedicalUnit("C-XX-ALFA"); 
	
//	MedicalUnit medicalUnit2 = new MedicalUnit("C-XX-BETA"); 
	
	
	ThreadGroup tg = null;
	
	
	try {
		mucenter.addMedicalUnit(medicalUnit1);
		//muLauncher.addMedicalUnit(medicalUnit2);
		mucenter.configureAll();
		tg =	mucenter.launchAll("NUMOfMEDUNITS:1");
	
	stat = 0;
	}catch(Exception ex) {
		tg.stop();
		ex.printStackTrace();
	}
	
	
	
	System.exit(stat);
	
}	
	


public List<Patient> generatePatientRepository() {
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

public List<Message> generateMessages() {
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
public SortedMap<String,Treatment> generateTreatments() {
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
public String[][] generatePathologies() {
	String[][]diag = {
			{"hemorragy","diabetes","cancer"},
			{"depression","stomatache","dyarrea"},
			{"flu","fever","vomit","axiety","cold"}
			};
	return diag;
} 
	
	
	
	
}
