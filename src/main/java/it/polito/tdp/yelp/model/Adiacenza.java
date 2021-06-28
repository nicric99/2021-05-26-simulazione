package it.polito.tdp.yelp.model;

public class Adiacenza {
	private Business sorgente;
	private Business destinazione;
	private Double peso;
	public Business getSorgente() {
		return sorgente;
	}
	public void setSorgente(Business sorgente) {
		this.sorgente = sorgente;
	}
	public Business getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(Business destinazione) {
		this.destinazione = destinazione;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Adiacenza(Business sorgente, Business destinazione, Double peso) {
		super();
		this.sorgente = sorgente;
		this.destinazione = destinazione;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Adiacenza [sorgente=" + sorgente + ", destinazione=" + destinazione + ", peso=" + peso + "]";
	}
	
}
