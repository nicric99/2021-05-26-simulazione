package it.polito.tdp.yelp.model;

public class LocaleMigliore implements Comparable<LocaleMigliore> {
	private Business locale;
	private Double punteggio;
	public LocaleMigliore(Business locale,Double punteggio) {
		super();
		this.locale = locale;
		this.punteggio = punteggio;
	}
	public Business getLocale() {
		return locale;
	}
	public void setLocale(Business locale) {
		this.locale = locale;
	}
	public Double getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(Double punteggio) {
		this.punteggio = punteggio;
	}
	@Override
	public String toString() {
		return "LocaleMigliore [locale=" + locale + ", punteggio=" + punteggio + "]";
	}
	@Override
	public int compareTo(LocaleMigliore o) {
		// TODO Auto-generated method stub
		return Double.compare(this.punteggio, o.punteggio);
	}
	
}
