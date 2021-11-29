/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagement;

import DB.DBConnection;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import Model.Medicines;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class SaleSceneController implements Initializable {

    @FXML
    private TextField pName;
    @FXML
    private TextField quantity;
    @FXML
    private TextField cName;
    @FXML
    private DatePicker sDate;
    @FXML
    private ComboBox<String> batchCombo;
  
    @FXML
    private TableView<Medicines> saleTable;
    @FXML
    private Label expiry;
//DisplayDatabase invoiceData = new DisplayDatabase();
ObservableList<String> bList = FXCollections.observableArrayList(); 
ObservableList<String> pList = FXCollections.observableArrayList(); 
ObservableList<String> cList = FXCollections.observableArrayList(); 
 
@FXML
    private Label sellRate;
    @FXML
    private Label totalL;
    @FXML
    private Label warnMsg;
    @FXML
    private Label gTotal;
    @FXML
    private AnchorPane suggestionPane;
    @FXML
    private TableView<?> sTableView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       createSaleTable();
//         invoiceData.buildData(invoiceTable, "Select * from InvoiceTable;");
        ResultSet rs = QueryDatabase.QueryDatabase("Select CustomerName from customertable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    cList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> autoCust = TextFields.bindAutoCompletion(cName,cList); 
      
          rs = QueryDatabase.QueryDatabase("Select Distinct(ProductName) from InventoryTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    pList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(pName,pList); 
      auto.setOnAutoCompleted( 
      
      e -> {
      setBatch(e.getCompletion());
      });
        // TODO
    }    
String nameP = "";
String batch = "";
double qty = 0;
String nameC = "";
double tAmount = 0;
LocalDateTime iDate;
double rate;
double amount=0;
String expiryD = "";
ObservableList<Medicines> saleList = FXCollections.observableArrayList();
  

    @FXML
    private void addItem(ActionEvent event) {
         getSaleFields();
                
        saleList.add(new Medicines(nameP,batch,qty,String.valueOf(amount)));
        saleTable.setItems(saleList);
        tAmount = tAmount+amount;
        gTotal.setText(String.format("%.2f", tAmount));
        clearSaleFields();
        
         AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(pName,pList); 
      auto.setOnAutoCompleted( 
      
      e -> {
      setBatch(e.getCompletion());
      });
        
        
    }
 private void createSaleTable() {
       
        TableColumn pro_name = new TableColumn("Medicine Name");
        pro_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medicines,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Medicines, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getNameM());              
           }            
         });  
        saleTable.getColumns().addAll(pro_name);
        
           TableColumn col_batch = new TableColumn("Batch");
        col_batch.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medicines,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Medicines, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getBatch());              
           }            
         });  
        saleTable.getColumns().addAll(col_batch);
        
           TableColumn col_qty = new TableColumn("Quantity");
        col_qty.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medicines,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Medicines, String> param) {                                                 
             return new SimpleStringProperty(String.valueOf(param.getValue().getQty()));              
           }            
         }); 
        
        saleTable.getColumns().addAll(col_qty);
        
        TableColumn col_cName = new TableColumn("Amount");
        col_cName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medicines,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Medicines, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getAmount());              
           }            
         });  
        saleTable.getColumns().addAll(col_cName);
        
        System.out.println("Sasd");
        
    }
 
    DisplayDatabase sData = new DisplayDatabase();
    @FXML
    private void getRate() {
//        sellRate.setText("");
        
        String baCode = batchCombo.getValue();
        String proName = pName.getText();
        String sQty = quantity.getText();
        
        if(sQty==null || sQty.isEmpty()){
        qty=1;
        
        }else{
        qty = Double.parseDouble(sQty);
        }
        
        if(baCode==null || baCode.isEmpty()){
        return;
        }
        
        if(proName==null || proName.isEmpty()){
        return;
        }
        ResultSet rs = QueryDatabase.QueryDatabase("Select SellingRate,ExpiryDate,Quantity from inventoryTable where ProductName='"+proName+"' AND BatchNumber = '"+baCode+"';");
        if(rs!=null){
            try {
               if(rs.next()){
                   
                   if(Double.parseDouble(rs.getString(3))<=0){
                       
                       sData.buildData(sTableView, "Select Id,ProductName,BatchNumber,Quantity,ExpiryDate from inventorytable where  salt=(Select salt from  inventoryTable where ProductName='"+proName+"' AND BatchNumber = '"+baCode+"') AND Quantity>0;");
                       suggestionPane.setVisible(true);
                       
                       return;
                   }
                   
                    rate = Double.parseDouble(rs.getString(1));
                     sellRate.setText(rs.getString(1));
                     
                    expiry.setText(rs.getString(2));
                    amount = rate*qty;
                    totalL.setText(String.valueOf(amount));
                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      
    }


    @FXML
    private void RecordSale(ActionEvent event) {
         boolean val =  getInvoiceFields();
       if(!val){
       return;
       }
       
       Connection c;
       try{
           c = DBConnection.connect();
           
            String query = "INSERT INTO SaleTable (Date,Customer,TotalAmount)VALUES("+
                            "'"+iDate+"',\n" +
                            "'"+nameC+"',\n" +
                            "'"+tAmount+"');";                    
         
         PreparedStatement ps =  c.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
         ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String invoId = rs.getString(1);
            for(Medicines i: saleList){
          
             query = "INSERT INTO SaleItemTable (InvoId,ProductName,Batch,Quantity,Amount)VALUES("+
                "'"+invoId+"',\n" +
                "'"+i.getNameM()+"',\n" +
                "'"+i.getBatch()+"',\n" +
                "'"+i.getQty()+"',\n" +
                "'"+i.getAmount()+"');";                    
         
            c.createStatement().execute(query);
            
            String iQuery = "Update inventoryTable set Quantity=Quantity-"+i.getQty()+" where ProductName='"+i.getNameM()+"' And BatchNumber='"+i.getBatch()+"';";
            c.createStatement().execute(iQuery);
            
            }
            
            
            
            c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
//         invoiceData.buildData(invoiceTable, "Select * from InvoiceTable;");
    }

    @FXML
    private void DeleteSale(ActionEvent event) {
        int index = saleTable.getSelectionModel().getSelectedIndex();
        Medicines i = saleList.get(index);
        tAmount-=Double.parseDouble(i.getAmount());
        gTotal.setText(String.format("%.2f", tAmount));
        saleList.remove(index);
        saleTable.refresh();
    }


    private void getSaleFields() {
        nameP =pName.getText();
       
       
        
        if(nameP==null || nameP.isEmpty()){
        pName.requestFocus();
        warnMsg.setText("Pls enter product name.");
            return;
        }
         batch = batchCombo.getValue();
         qty = Double.parseDouble(quantity.getText());
    }

    private void clearSaleFields() {
        pName.clear();
        sellRate.setText("");
        expiry.setText("");
        quantity.clear();
        pList.remove(nameP);
        
        totalL.setText("0.0");
        
    }

    private boolean getInvoiceFields() {
        nameC = cName.getText();
        iDate =LocalDateTime.of( sDate.getValue(),LocalTime.now());
        
        tAmount = rate*qty;
        
        
        return true;
       
    }

    private void clearAllFields() {
      cName.clear();
      amount = 0;
      tAmount = 0;
      gTotal.setText("0.0");
      saleList.clear();
    }

    private void setBatch(String completion) {
        
        String proName = pName.getText();
         ResultSet rs = QueryDatabase.QueryDatabase("Select Distinct(BatchNumber) from inventoryTable where productName = '"+proName+"';");
        if(rs!=null){
            try {
                while(rs.next()){
                    
                  bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        batchCombo.setItems(bList);
        batchCombo.setValue(bList.get(0));
        qty=1;
        quantity.setText("1");
        getRate();
    }

    @FXML
    private void getBatch() {
        String proName = pName.getText();
        bList.clear();
         ResultSet rs = QueryDatabase.QueryDatabase("Select Distinct(BatchNumber) from InventoryTable where productName = '"+proName+"';");
        if(rs!=null){
            try {
                while(rs.next()){
                    
                  bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        batchCombo.setItems(bList);
    }

    @FXML
    private void calcTotal(ActionEvent event) {
       
        String proName = pName.getText();
        String sQty = quantity.getText();
        
        if(sQty==null || sQty.isEmpty()){
        qty=1;
        
        }else{
        qty = Double.parseDouble(sQty);
        }
        
     
        
        if(proName==null || proName.isEmpty()){
            warnMsg.setText("Select Product Name.");
        return;
        }
        
                    amount = rate*qty;
                    totalL.setText(String.valueOf(amount));
                   
               
           
        
     }

    @FXML
    private void closeSuggestion(ActionEvent event) {
        
        suggestionPane.setVisible(false);
        
    }

    @FXML
    private void useMedicine(ActionEvent event) {
        
        int index = sTableView.getSelectionModel().getSelectedIndex();
     ObservableList<ObservableList> data = sData.getData();
     ObservableList<String> items = data.get(index);
        pName.setText(items.get(1));
        getBatch();
        suggestionPane.setVisible(false);
        
    }
      
    
    
}
