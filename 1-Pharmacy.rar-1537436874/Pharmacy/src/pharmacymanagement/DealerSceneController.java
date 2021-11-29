/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class DealerSceneController implements Initializable {

    @FXML
    private TextField dName;
    @FXML
    private TextField dContact;
    @FXML
    private Label warnMsg;
    @FXML
    private TextArea dAdd;
    @FXML
    private TableView<?> dealerTable;
DisplayDatabase sData = new DisplayDatabase();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         sData.buildData(dealerTable, "Select * from dealerTable;");
        // TODO
    }    
String nameD = "";
String contactD = "";
String addD = "";  

    private boolean GetDealerFields() {
       nameD = dName.getText();
        
 contactD = dContact.getText();

 addD = dAdd.getText();
if(nameD==null || nameD.isEmpty()){
           dName.requestFocus();
            warnMsg.setText("Pls enter Dealer's Name.");
            return false;
 }
  if(contactD==null || contactD.isEmpty()){
           dContact.requestFocus();
            warnMsg.setText("Pls Dealer's Contact.");
            return false;
 }
   if(addD==null || addD.isEmpty()){
           dAdd.requestFocus();
            warnMsg.setText("Pls enter Dealer's Address.");
            return false;
}
return true;

    }

      

    @FXML
    private void addDealer(ActionEvent event) {
        Connection c;
        boolean val =  GetDealerFields();
       if(!val){
       return;
       }
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO dealerTable (DealerName,DealerContact,DealerAddress)VALUES("+
"'"+nameD+"',\n" +
"'"+contactD+"',\n" +                  
"'"+addD+"');";            
         
            c.createStatement().execute(query);
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(DealerSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         sData.buildData(dealerTable, "Select * from dealerTable;");
    }

    @FXML
    private void DeleteCustomer(ActionEvent event) {
        int index = dealerTable.getSelectionModel().getSelectedIndex();
         ObservableList data = sData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "dealerTable");
         
         sData.buildData(dealerTable, "Select * from dealerTable;");
    }

    private void clearAllFields() {
       dName.clear();
       dContact.clear();
       dAdd.clear();
    }
    
}


    /**
     * Initializes the controller class.
     */
 

