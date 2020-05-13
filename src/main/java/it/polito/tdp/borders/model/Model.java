package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private SimpleGraph <Country, DefaultEdge> grafo;
	private Map <Integer, Country> idMap;
	private BordersDAO dao;
	private Map <Country, Country> visita = new HashMap<>();

	public Model() {
		idMap = new HashMap<>();
		dao = new BordersDAO();
		this.dao.loadAllCountries(idMap);
	
	}
	
	//creiamo il grafo 
	public void creaGrafo (int x) {
		this.grafo= new SimpleGraph <>(DefaultEdge.class);
		
		//aggiungo i vertici che rispettano il vincolo (l'ho controllato nel DAO)
		for (Country c : dao.getCountry(x, idMap)) {
			if (!this.grafo.containsVertex(c)) {
				this.grafo.addVertex(c);
			}
		}
		
		//aggiungo gli archi
		for (Border b : dao.getCountryPairs(x, idMap)) {
			//controllo che il grafo contenga i due vertici che rappresentano i due paesi
			this.grafo.addEdge(b.getNazione1(), b.getNazione2());
		}
	}//chiudo crea grafo

	public int vertexNumber() {
		return this.grafo.vertexSet().size();
	}
	public int edgeNumber() {
		return this.grafo.edgeSet().size();
	}
	
	public String nAdiacenti() {
		String result = "";
		for (Country c: grafo.vertexSet()) {
			result += c.getName() + " # stati confinanti: " + Graphs.neighborListOf(grafo, c).size()+ "\n";
		}
 		
		return result.substring(0, result.length()-1);
	}
	
	public int componentiConnesse() {
		List <Set<Country>> connessioni = new ConnectivityInspector <>(grafo).connectedSets();
		return connessioni.size();
	}

	public Collection<Country> getCountry() {
		return this.grafo.vertexSet();
	}
	
	public Set<Country> trovaPercorso (Country c1){
		Set <Country> percorso = new HashSet <>(); 
		
		//metto nel mio albero di visita la radice dell'albero
		visita.put(c1, null);
		
		//definisco l'iteratore in ampiezza
		BreadthFirstIterator <Country, DefaultEdge> bfi = new BreadthFirstIterator <>(this.grafo, c1);
		bfi.addTraversalListener(new TraversalListener <Country, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {}

			@Override
			//quando attraverso un arco salvo la relazionne tra il nodo sorgente e il nodo di arrivo 
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				//prendiamo il nodo sorgente e quello di destinazione dell'arco che stiamo attraversando
				Country partenza = grafo.getEdgeSource(e.getEdge());
				Country arrivo = grafo.getEdgeTarget(e.getEdge());
				
				//se la visita non contiene la destinazione, ma contiene il nodo sorgente
				if (visita.containsKey(partenza)) {
					//la destinazione si raggiunge dalla partenza
					visita.put(arrivo, partenza);
				} else {
					visita.put(partenza, arrivo);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {}
			
		});
		//visitiamo il grafo e ogni volta che attraversiamo un arco ne teniamo conto con il TraversalListener
		while (bfi.hasNext()) {
			bfi.next();
		}
		
		for (Country c : visita.keySet()) {
			percorso.add(c);
		}
		
		return percorso;		
	}
	
	public Set<Country> trovaVicini (Country c){
		Set <Country> vicini = new HashSet <>(); 

		GraphIterator<Country, DefaultEdge> bfv= new BreadthFirstIterator<>(grafo, c);
		
		vicini.add(c);
		
		while (bfv.hasNext()) {
			vicini.add(bfv.next());
		}
		
		return vicini;
	}
	
	
	
	
}
