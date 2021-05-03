/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.*;
import java.sql.*;
import it.polito.tdp.poweroutages.DAO.*;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    PowerOutageDAO podao = new PowerOutageDAO();
    
    @FXML
    void doRun(ActionEvent event) {
    	Nerc nerc = this.cmbNerc.getValue();
    	int x = Integer.parseInt(txtYears.getText());
    	int y = Integer.parseInt(this.txtHours.getText());
    	//txtResult.clear();
    	
    	String s = "";
    	
    	List<PowerOutage> lista = model.trovaSequenza(nerc, x, y);
    	if (lista.isEmpty()) {
    		txtResult.setText("Non ci sono blackout!");
    	}
    	else {
    		for (PowerOutage po : lista) {
	    		s = s + po.toString() + "\n";
	    	}
    		txtResult.setText(s);
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
        
        
        ObservableList<Nerc> data =FXCollections.observableArrayList();
        List<Nerc> nerc = podao.getNercList();
        for (Nerc n : nerc) {
        	data.add(n);
        }
        this.cmbNerc.setItems(data);
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
