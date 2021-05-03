package it.polito.tdp.poweroutages.model;

import java.util.Comparator;

public class ComparatorePOPerPersone implements Comparator<PowerOutage> {
	
	@Override
	public int compare (PowerOutage po1, PowerOutage po2) {
		String s1 = String.valueOf(po1.getCustomers_affected());
		String s2 = String.valueOf(po2.getCustomers_affected());
		return s1.compareTo(s2);
	}

}
