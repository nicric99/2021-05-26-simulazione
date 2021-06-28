package it.polito.tdp.yelp.model;

public class testDao {

	public static void main(String[] args) {
		Model model= new Model();
		model.creaGrafo(2005, "Phoenix");
		System.out.println(model.getNVertici());
		System.out.println(" "+model.getNArchi());
		System.out.println(" "+model.getMigliore().toString());

	}

}
