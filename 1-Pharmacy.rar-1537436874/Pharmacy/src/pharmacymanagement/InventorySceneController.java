/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagement;

import DB.DisplayDatabase;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sameer
 */
public class InventorySceneController implements Initializable {

    @FXML
    private TextField iPName;
    @FXML
    private TextField iBatch;
    @FXML
    private TextField iQty;
    @FXML
    private DatePicker iEDate;
    @FXML
    private TableView<?> iTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       String query = "select * from inventoryTable ORDER BY id DESC LIMIT 100;";
        iData.buildData(iTableView, query);
    }    
     DisplayDatabase iData = new DisplayDatabase();
    @FXML
    private void searchInventory(ActionEvent event) {
        String name = iPName.getText();
        String batch = iBatch.getText();
        String qty = iQty.getText();
        LocalDate eDate = iEDate.getValue();
        
       String query = "select * from inventoryTable WHERE (";
    
               
       if (name!=null && !name.isEmpty()){
        
         query += " ProductName LIKE '%"+name+"%'";
            if( batch!=null && !batch.isEmpty() || qty!=null && !qty.isEmpty() || eDate!=null ){
            query +=" AND ";
            }
        }
        
        if (batch!=null && !batch.isEmpty() )
        {
         query += " BatchNumber='"+batch+"'";
          if( qty!=null && !qty.isEmpty() || eDate!=null ){
            query +=" AND ";
            }
        }
        
        if (qty!=null && !qty.isEmpty() )
        {
         query += "Quantity <= '"+qty+"'";
         if(eDate!=null ){
            query +=" AND ";
           }
        }
      
        
        if(eDate!=null){
        query += "ExpiryDate<='"+eDate+"'";
        }    
        
       
        query += ") ORDER BY id DESC;";
       
              
      if( (name==null || name.isEmpty()) && (batch==null || batch.isEmpty()) && (qty==null || qty.isEmpty()) && eDate==null ){
        
      query = "select * from inventoryTable ORDER BY id DESC LIMIT 100;";
       }
      System.out.println(name+" "+batch);
      System.out.println(query);
      iData.buildData(iTableView, query);
      
    }
    
}
