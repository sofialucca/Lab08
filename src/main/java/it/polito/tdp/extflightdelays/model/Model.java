package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph <Airport, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer,Airport> idMap;
	private List<Rotta> rotte;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<>();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int x) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		rotte = new ArrayList<>();
		
		Graphs.addAllVertices(this.grafo,idMap.values());
		
		for(Rotta r : dao.voliDistanzaMedia(idMap,x)) {
			DefaultWeightedEdge e = grafo.getEdge(r.getA2(),r.getA1());
			if(e != null) {
				double nuovaMedia = (r.getDistanza() + grafo.getEdgeWeight(e))/2;
				if(nuovaMedia > x) {
					this.grafo.setEdgeWeight(e, nuovaMedia);
					rotte.add(new Rotta(r.getA1(),r.getA2(),nuovaMedia));
				}else {
					this.grafo.removeEdge(e);
				}
				rotte.remove(new Rotta(r.getA2(),r.getA1(),0));
			}else {
				Graphs.addEdge(grafo, r.getA1(), r.getA2(), r.getDistanza());
				rotte.add(new Rotta(r.getA1(),r.getA2(),r.getDistanza()));
			}
		}
	
	}
	
	public int getNumeroVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Rotta> getRotte(){
		return rotte;
	}
}
