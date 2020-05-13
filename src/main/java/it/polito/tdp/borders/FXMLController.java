package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<Country> chBoxStato;

    @FXML
    private Button btnVicini;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	String annoS = txtAnno.getText();
    	int anno;
    	
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    		if (anno<1816 || anno>2016) {
        		txtResult.appendText("Inserire un anno nell'intervallo valido, dal 1816 al 2016");
        		txtAnno.clear();
        		return;
        	}
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero!\n");
    		txtAnno.clear();
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	
    	for (Country co: this.model.getCountry()) {
    		if (chBoxStato == null) {
    			chBoxStato.getItems().addAll(this.model.getCountry());
    			return;
    		} else {
    			if (chBoxStato.getItems().contains(co)) {
    				chBoxStato.getItems().remove(co);
    			}
    			chBoxStato.getItems().add(co);
    		}
    	}
    	
    	txtResult.appendText(this.model.nAdiacenti()+"\n");
    	txtResult.appendText("Numero componenti connesse del grafo: "+this.model.componentiConnesse()+"\n");
    	txtResult.appendText("# VERTICI "+ this.model.vertexNumber()+"\n");
    	txtResult.appendText("# ARCHI "+ this.model.edgeNumber()+"\n");
    	
 

    }

    @FXML
    void trovaVicini(ActionEvent event) {
    	txtResult.clear();
    	
    	Country c = chBoxStato.getValue();
    	//chBoxStato.getItems().removeAll(this.model.getCountry());
    	//this.model.trovaVicini(c);
    	
    	for (Country c1: this.model.trovaVicini(c)) {
    		txtResult.appendText(c1+"\n");
    	}
    	//txtResult.appendText("Vicini: "+this.model.trovaVicini(c)+"\n");

    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert chBoxStato != null : "fx:id=\"chBoxStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
