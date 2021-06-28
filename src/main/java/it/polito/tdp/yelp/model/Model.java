package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private Graph<Business,DefaultWeightedEdge> grafo;
	private Map<String,Business> idMap;
	private YelpDao dao;
	public Model() {
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao=new YelpDao();
		idMap= new HashMap<>();
	}
	
	public List<String> getCitta(){
		return dao.getCitta();
	}
	public void creaGrafo(Integer anno,String citta) {
		dao.getAllBusiness(idMap);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, anno, citta));
		for(Adiacenza a: dao.getArchi(idMap, anno, citta)) {
			Graphs.addEdge(this.grafo, a.getSorgente(),a.getDestinazione(), a.getPeso());
		}
		
	}
	public Integer getNVertici() {
		return this.grafo.vertexSet().size();
	}
	public Integer getNArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<LocaleMigliore> getMigliore(){
		List<LocaleMigliore> result= new ArrayList<>();
		for(Business b:this.grafo.vertexSet()) {
			Double somma1=0.00;
			Double somma2=0.00;
			for(DefaultWeightedEdge e:this.grafo.incomingEdgesOf(b)) {
				double peso= this.grafo.getEdgeWeight(e);
				somma1+=peso;
			}
			for(DefaultWeightedEdge e:this.grafo.outgoingEdgesOf(b)) {
				int peso=(int) this.grafo.getEdgeWeight(e);
				somma2+=peso;
			}
			double punteggio = somma1-somma2;
			result.add(new LocaleMigliore(b,punteggio));
		}
		Collections.sort(result);
		Collections.reverse(result);
		return result;
	}
	
}
