/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.LocaleMigliore;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Business migliore;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnLocaleMigliore"
    private Button btnLocaleMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	String val= txtX.getText();
    	Double valore= -1.00;
    	Business sorgente= cmbLocale.getValue();
    	try {
    		valore=Double.parseDouble(val);
    		
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Attenzione, devi selezionare un valore corretto per x\n");
    		return;
    	}
    	if(valore>1.00 || valore <0.00) {
    		txtResult.appendText("Attenzione il valore deve essere compreso tra 0 e 1\n");
    		return;
    	}
    	if(sorgente==null) {
    		txtResult.appendText("Devi selezionare un locale dalla tendina\n");
    		return;
    	}
    	List<Business> result= new ArrayList<Business>(model.trovaCammino(valore,sorgente,this.migliore));
    	if(result.size()==0) {
    		txtResult.appendText("Attenzione non abbiamo identificato il percorso \n");
    		return;
    	}
    	txtResult.appendText("Cammino minimo\n");
    	for(Business b: result) {
    		txtResult.appendText(b.toString()+"\n");
    	}
    	int i= result.size();
    	txtResult.appendText("Passi"+(i-1));
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String citta= cmbCitta.getValue();
    	Integer anno= cmbAnno.getValue();
    	if(citta==null || anno==null) {
    		txtResult.appendText("Attenzione devi selezionare anno e città\n");
    		return;
    	}
    	model.creaGrafo(anno, citta);
    	txtResult.appendText("I vertici sono "+model.getNVertici()+"\n");
    	txtResult.appendText("Gli archi sono "+model.getNArchi()+"\n");
    	btnLocaleMigliore.setDisable(false);
    	//cmbLocale.getItems().addAll(model.getVertici());
    }

    @FXML
    void doLocaleMigliore(ActionEvent event) {
    	List<LocaleMigliore> result= new ArrayList<>(model.getMigliore());
    	
    	txtResult.appendText("Locale migliore =\n"+ result.get(0).getLocale().getBusinessName()+"\n");
    	txtResult.appendText("Locale migliore =\n"+ result.get(0).getPunteggio()+"\n");
    	this.migliore=result.get(0).getLocale();
    	double mig= result.get(0).getPunteggio();
    	for(LocaleMigliore l:result) {
    		if(l.getPunteggio()!=mig) {
    			cmbLocale.getItems().add(l.getLocale());
    		}
    	}
    	btnLocaleMigliore.setDisable(true);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnLocaleMigliore != null : "fx:id=\"btnLocaleMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int i=2005; i<=2013;i++) {
    		cmbAnno.getItems().add(i);
    	}
    	cmbCitta.getItems().addAll(model.getCitta());
    }
}
