package com.exp.mcsimulator.util;

public class MUSequenceGenerator {
	
	static long SEC=0;
	static long SEC2=0;
	
	public MUSequenceGenerator() {
		
	}
	
	public  long getNext() {
		SEC = SEC+1;
		return SEC;
	}

	
	public  long getNext2() {
		SEC2 = SEC2+1;
		return SEC2;
	}
	
}
