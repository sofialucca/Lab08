package it.polito.tdp.extflightdelays.model;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	Graph <Airport, DefaultWeightedEdge> grafo;
	
	public  Graph<Airport, DefaultWeightedEdge> creaGrafo(int x) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		List<Airport> aeroporti = dao.loadAllAirports();
		
		Graphs.addAllVertices(this.grafo, aeroporti);
		
		for(Flight f1 : dao.voliDistanzaMedia()) {
			if(grafo.containsEdge(aeroporti.get(f1.getDestinationAirportId()),aeroporti.get(f1.getOriginAirportId()))) {
				DefaultWeightedEdge arco = grafo.getEdge(aeroporti.get(f1.getDestinationAirportId()),aeroporti.get(f1.getOriginAirportId()));
				double media = (f1.getDistance() + grafo.getEdgeWeight(arco))/2;
				if(media > x) {
					Graphs.addEdge(grafo, aeroporti.get(f1.getDestinationAirportId()),aeroporti.get(f1.getOriginAirportId()), media);
				}else {
					grafo.removeEdge(arco);
				}
			}else {
				if(f1.getDistance()>x) {
					Graphs.addEdge(grafo, aeroporti.get(f1.getOriginAirportId()),aeroporti.get(f1.getDestinationAirportId()), f1.getDistance());					
				}

			}
		}
		
		return grafo;
	}
	
	public int getNumeroVertici() {
		return grafo.vertexSet().size();
	}
	
	public Set<DefaultWeightedEdge> getArchi() {
		return grafo.edgeSet();
	}
}
