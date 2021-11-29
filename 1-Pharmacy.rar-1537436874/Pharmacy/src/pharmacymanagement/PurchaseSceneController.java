/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class PurchaseSceneController implements Initializable {
    

@FXML
private TextField bNum;
@FXML
private TextField quantity;
@FXML
private TextField pRate;
@FXML
private TextField sRate;
@FXML
private DatePicker eDate;
@FXML
private DatePicker pDate;
@FXML
    private TextField salt;
    @FXML
    private Label warnMsg;
    ObservableList<String> pList = FXCollections.observableArrayList(); 
     DisplayDatabase pData = new DisplayDatabase();
@FXML
     private TableView<?> purchaseTable;
    @FXML
    private TextField mName;
    @FXML
    private TextField dName;
    @FXML
    private Button btnAdd;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        pData.buildData(purchaseTable, "Select * from purchaseTable;");
        
        // TODO
    }    
String nameM = "";
String nameD = "";
String batchNum = "";
String qunt = "";
LocalDateTime dateP;
double rateP = 0;
double rateS = 0;
LocalDate dateE = null;
String mSalt = "";

@FXML
    private void addPurchase(ActionEvent event) {
        Connection c;
        boolean val =  GetPurchaseFields();
       if(!val){
       return;
       }
       String query;
       try{
            if(!update){
           c = DBConnection.connect();
             query = "INSERT INTO purchaseTable (MedicineName,DealerName,BatchNumber,Quantity,Salt,PurchaseDate,PurchaseRate,SellingRate,ExpiryDate)VALUES("+
"'"+nameM+"',\n" +
"'"+nameD+"',\n" +
"'"+batchNum+"',\n" + 
"'"+qunt+"',\n" +
"'"+mSalt+"',\n" +
"'"+dateP+"',\n" + 
"'"+rateP+"',\n" +                     
 "'"+rateS+"',\n" +                    
"'"+dateE+"');";        
            
            
         
            c.createStatement().execute(query);
            }else{
              c = DBConnection.connect();
            query = "Update purchaseTable set MedicineName='"+nameM+"',DealerName='"+nameD+"',BatchNumber='"+batchNum+"',"
                   + "Quantity='"+qunt+"',Salt='"+mSalt+"',PurchaseDate='"+dateP+"',PurchaseRate='"+rateP+"',SellingRate='"+rateS+"',ExpiryDate='"+dateE+"'Where Id='"+id+"';";
                  System.out.println(query);
           c.createStatement().executeUpdate(query);
          
            }
            
            if(update){
                
                
                qunt=String.valueOf(Double.parseDouble(qunt)-Double.parseDouble(oldQty));
                query = "Update inventoryTable set Quantity=Quantity+"+qunt+" where ProductName='"+nameM+"' And BatchNumber='"+batchNum+"';";
                c.createStatement().executeUpdate(query);
                       
             c.close();
            }else{
            
            ResultSet rs = QueryDatabase.QueryDatabase("Select * from InventoryTable where ProductName='"+nameM+"' And BatchNumber='"+batchNum+"';");
            if(rs!=null){
            if(rs.next()){
                        query = "Update inventoryTable set Quantity=Quantity+"+qunt+" where ProductName='"+nameM+"' And BatchNumber='"+batchNum+"';";
                    }else{
                        query = "INSERT INTO inventoryTable (ProductName,Salt,BatchNumber,Quantity,PurchaseDate,PurchaseRate,SellingRate,ExpiryDate)VALUES("+
                                "'"+nameM+"',\n" +
                                "'"+mSalt+"',\n" +
                                "'"+batchNum+"',\n" + 
                                "'"+qunt+"',\n" +
                                "'"+dateP+"',\n" + 
                                "'"+rateP+"',\n" +                     
                                 "'"+rateS+"',\n" +                    
                                "'"+dateE+"');";      
                    }
            } else{
                        query = "INSERT INTO inventoryTable (ProductName,Salt,BatchNumber,Quantity,PurchaseDate,PurchaseRate,SellingRate,ExpiryDate)VALUES("+
                                "'"+nameM+"',\n" +
                                "'"+mSalt+"',\n" +
                                "'"+batchNum+"',\n" + 
                                "'"+qunt+"',\n" +
                                "'"+dateP+"',\n" + 
                                "'"+rateP+"',\n" +                     
                                 "'"+rateS+"',\n" +                    
                                "'"+dateE+"');";      
                    }
                   
         
            c.createStatement().execute(query);
            
            
            
             
            
             c.close();
            }
            
            
       } catch (SQLException ex) {
            Logger.getLogger(PurchaseSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       clearAllFields();
       pData.buildData(purchaseTable, "Select * from PurchaseTable;");
               
        
        
        
    }

    private boolean GetPurchaseFields() {
         nameM = mName.getText();
        
 nameD = dName.getText();

 batchNum = bNum.getText();
 qunt = quantity.getText();
 dateP = LocalDateTime.of( pDate.getValue(),LocalTime.now());
 rateP = Double.parseDouble(pRate.getText());
 rateS = Double.parseDouble(sRate.getText());
 dateE = eDate.getValue();
 mSalt = salt.getText();
 
 if(nameM==null || nameM.isEmpty()){
           mName.requestFocus();
            warnMsg.setText("Pls enter Medicines Name.");
            return false;
 }
  if(nameD==null || nameD.isEmpty()){
           dName.requestFocus();
            warnMsg.setText("Pls enterSellers Name.");
            return false;
 }
   if(batchNum==null || batchNum.isEmpty()){
           bNum.requestFocus();
            warnMsg.setText("Pls enter Batch Number.");
            return false;
 }
    if(qunt==null || qunt.isEmpty()){
           quantity.requestFocus();
            warnMsg.setText("Pls enter Quantity.");
            return false;
 }
      if(dateE==null ){
           eDate.requestFocus();
            warnMsg.setText("Pls enter Expiry Date.");
            return false;
 }
      if(mSalt==null || mSalt.isEmpty()){
           salt.requestFocus();
            warnMsg.setText("Pls enter Salt.");
            return false;
 }
    

 
 return true;
    }

    private void clearAllFields() {
        pDate.setValue(LocalDate.now());
      mName.clear();
      dName.clear();
      bNum.clear();
      quantity.clear();
      salt.clear();
      pRate.clear();
      sRate.clear();
      eDate.setValue(LocalDate.now());
      update = false;
      btnAdd.setText("Add");
    }

@FXML
    private void DeletePurchcase(ActionEvent event) {
        int index = purchaseTable.getSelectionModel().getSelectedIndex();
         ObservableList data = pData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "PurchaseTable");
         pData.buildData(purchaseTable, "Select * from PurchaseTable;");
         
          try (Connection c = DBConnection.connect()) {
        String query = "Update inventoryTable set Quantity=Quantity-"+items.get(4)+" where ProductName='"+items.get(1)+"' And BatchNumber='"+items.get(3)+"';";
        c.createStatement().executeUpdate(query);
        c.close();
    } catch (SQLException ex) {
        Logger.getLogger(PurchaseSceneController.class.getName()).log(Level.SEVERE, null, ex);
    }
         
         
    }
    int id;
boolean update = false;
    String oldQty="0";
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private void UpdatePurchcase(ActionEvent event) {
        int index = purchaseTable.getSelectionModel().getFocusedIndex();
      ObservableList<ObservableList> data = pData.getData();
      ObservableList<String> itemData = data.get(index);
      
      oldQty = itemData.get(4);
      id = Integer.parseInt(itemData.get(0));
      mName.setText(itemData.get(1));
        dName.setText(itemData.get(2));
        bNum.setText(itemData.get(3));
        quantity.setText(oldQty);
        salt.setText(itemData.get(5));
        
         String[] pdate = itemData.get(6).split(" ");  // get only date from DateTime
               pDate.setValue(LocalDate.parse(pdate[0],format));
   
       
        pRate.setText(itemData.get(7));
        sRate.setText(itemData.get(8));
        eDate.setValue(LocalDate.parse(itemData.get(9)));
        update=true;
        btnAdd.setText("Update");
    }

 
}
