package com.exp.mcsimulator.visit;


import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.logging.Level;

import com.exp.mcsimulator.Action;
import com.exp.mcsimulator.Treatment;
import com.exp.mcsimulator.util.MUSequenceGenerator;

public class SmallUltraFastMedicalVisit extends MedicalVisit {

	@Override
	public void goToPlace() {
	
	double maxspeed = 120;
	double minspeed = 40;
	double speed = 100;
	double delta;
	double sign;
	int dist;
	int minutes = 0;

	try {
	
		dist = Integer.valueOf(this.getPatient().getAddress().substring(this.getPatient().getAddress().lastIndexOf("-")+1, this.getPatient().getAddress().length()));
		log.log(Level.ALL,"Medical visit to patient:" + this.getPatient().getName() + "at address:"+ this.getPatient().getAddress() + "Distance:"+ dist  );
		
		while(dist>0) {
			
		delta = (Math.random()%100)%20;
		sign = (Math.random()%100)%2;
		if(sign<1 && speed>minspeed+20)
			speed = speed - delta;
		else if(speed<maxspeed-20)
			speed = speed + delta;
	
		minutes = minutes + 10;
	
		
		dist = dist - (int)(10*speed)/60;
		log.log(Level.ALL,"Driving at:" +speed + "Km/h for 10 min; Distance:"+dist);
		
		long millis = (long) (1/speed * 100);
		
		Thread.sleep(millis);
		
		}
		
		log.log(Level.ALL,"Arrived at patient house at address:"+this.getPatient().getAddress()+" in : " + minutes + "minutes");		
		
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}
		

	}

	@Override
	public void prescribeTreatment() {
	    SortedSet<Action> actions =this.getPatient().getActions();
	    //actions are sorted  decreasingly by  date
	    Action act = actions.first();
	    Treatment treatment = new Treatment();
	    Treatment trecord;
	    String description = "";
	    int tnumber = 0;
	    for(String key:this.getTreatments().keySet()) {
	    	String[] kwords = key.split("-");
	    	boolean done = false;
	    	for(int j=0;j<kwords.length;j++)
	    		if(act.getDescription().contains(kwords[j])) {
	    			tnumber++;
	    			trecord = this.getTreatments().get(key);
	    			description = " " + tnumber + ":" + trecord.getDescription();
	    			done = true;
	    		}
	
	    	if(done) {
	    		treatment.setActionId(act.getId());
	    		treatment.setDate(new Date());
	    		treatment.setId("" + new MUSequenceGenerator().getNext2()); //id generation
	    		treatment.setDescription(description);
	    		this.getPatient().getTreatments().add(treatment);
	    	
	    	}
	    
	    }
	    log.log(Level.ALL," Prescribed treatments: "+ this.getPatient().getTreatments().size());
	}

	@Override
	public void noMedicalAction() {
		// TODO Auto-generated method stub

	}

}
