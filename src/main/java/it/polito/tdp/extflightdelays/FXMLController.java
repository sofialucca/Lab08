/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Rotta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	String input = distanzaMinima.getText();
    	txtResult.clear();
    	if(!isValid(input)) {
    		return;
    	}
    	int distanzaMinima = Integer.parseInt(input);
    	model.creaGrafo(distanzaMinima);
    	txtResult.appendText("Grafo creato con "+model.getNumeroVertici()+" vertici e "+model.getNumeroArchi()+" archi\n\n");
    	
    	List<Rotta> rotte = model.getRotte();
    	if(!rotte.isEmpty()) {
    		txtResult.appendText("ELENCO ROTTE:\n");
        	for(Rotta r: rotte) {
        		txtResult.appendText(r.getA1().getAirportName()+"  -  "+r.getA2().getAirportName()+": "+r.getDistanza()+"\n");
        	}    		
    	}

    }

    private boolean isValid(String input) {
		if(input.isBlank()) {
			txtResult.setText("ERRORE: inserire un numero");
			return false;
		}
		try {
			Integer.parseInt(input);
		}catch(NumberFormatException nfe){
			txtResult.setText("ERRORE: inserire un numero nel formato corretto");
			return false;
		}
		return true;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
