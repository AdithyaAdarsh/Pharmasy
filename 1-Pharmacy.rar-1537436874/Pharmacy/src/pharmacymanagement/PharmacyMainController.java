/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class PharmacyMainController implements Initializable {

    @FXML
    private BorderPane rootLayout;
    @FXML
    private ToggleGroup g1;
    @FXML
    private ToggleGroup g11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeScene("SaleScene.fxml");
        // TODO
    }    
public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){
        }

     }
    @FXML
    private void setSaleScene(ActionEvent event) {
        changeScene("SaleScene.fxml");
        
    }

    @FXML
    private void setPurchaseScene(ActionEvent event) {
        changeScene("PurchaseScene.fxml");
    }

    @FXML
    private void setCustomerScene(ActionEvent event) {
        changeScene("CustomerScene.fxml");
    }

    @FXML
    private void setDealerScene(ActionEvent event) {
        changeScene("DealerScene.fxml");
    }

    
    @FXML
    private void setInventoryScene(ActionEvent event) {
        changeScene("InventoryScene.fxml");
    }
    
}
