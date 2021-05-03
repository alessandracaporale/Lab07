package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.*;
import it.polito.tdp.poweroutages.DAO.*;


public class Model {
	
	PowerOutageDAO podao;
	private List<PowerOutage> soluzione;
	private List<PowerOutage> partenza;
	private List<PowerOutage> parziale;
	int maxPersoneSoluzione;
	private double numOreParziale;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getPObyNerc (Nerc nerc) {
		return podao.getPObyNerc(nerc);
	}
	
	public double contaOre(List<PowerOutage> lista) {
		if(lista.isEmpty()) {
			return 0;
		}
		double ore =0;
		for (PowerOutage po : lista) {
			ore = ore + Duration.between(po.getDate_event_began(), po.getDate_event_finished()).toMinutes()/60;
		}
		return ore;
	}
	
	public int contaAnni (List<PowerOutage> lista) {
		if(lista.isEmpty()) {
			return 0;
		}
		int anni =0;
		PowerOutage vecchio = lista.get(0);
		PowerOutage nuovo = lista.get(0);
		
		for(PowerOutage po : lista) {
			if (po.getDate_event_finished().getYear() > nuovo.getDate_event_finished().getYear()) {
				nuovo = po;
			}
			if(po.getDate_event_began().getYear() < vecchio.getDate_event_began().getYear()) {
				vecchio = po;
			}
		}
		return (nuovo.getDate_event_finished().getYear() - vecchio.getDate_event_finished().getYear());
	}
	public int contaPersone(List<PowerOutage> lista) {
		if(lista.isEmpty()) {
			return 0;
		}
		int persone=0;
		for(PowerOutage po: lista) {
			persone = persone+ po.getCustomers_affected();
		}
		return persone;
	}
	
	public List<PowerOutage> trovaSequenza (Nerc nerc, int x, int y) {
		parziale = new ArrayList<>();
		partenza = podao.getPObyNerc(nerc);
		soluzione = new ArrayList<>();
		numOreParziale=0;
		maxPersoneSoluzione=0;
		recursive(parziale, 0, x, y);
		
		return soluzione;
	}
	
	public void recursive (List<PowerOutage> parziale, int livello, int x, int y) {
		double numOre = contaOre(parziale);
		int numAnni = contaAnni(parziale);
		int numPersone = contaPersone(parziale);
		
		if(partenza.isEmpty()) {
			return;
		}
		
		if (numOre > x || numAnni > y) {
			//fine
			return;
		}
		
		if(numPersone > maxPersoneSoluzione) {
			soluzione = new ArrayList<>(parziale);
			maxPersoneSoluzione = numPersone;
			numOreParziale = numOre;
			System.out.println(maxPersoneSoluzione);
		}
		
		if(livello == partenza.size()) {
			return;
		}
		
		//genero il sottoproblema
		parziale.add(partenza.get(livello));
		recursive(parziale, livello+1, x, y);
		
		//backtracking
		parziale.remove(partenza.get(livello));
		recursive(parziale, livello+1, x, y);
	}

}
